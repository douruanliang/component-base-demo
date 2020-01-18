package com.github.douruanliang.lib_https.calladapter;

import com.github.douruanliang.lib_https.BuildConfig;
import com.github.douruanliang.lib_https.callback.ICallback;
import com.github.douruanliang.lib_https.exception.BusinessApiException;
import com.github.douruanliang.lib_https.model.IResponse;
import com.github.douruanliang.lib_https.response.BaseResponse;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.Executor;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * author: dourl
 * created on: 2018/10/24 11:45 AM
 * description: 自定义calladapter
 */
public class BaseCallAdapterFactory2 extends CallAdapter.Factory {

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {

        if (getRawType(returnType) != ImCall.class) {
            return null;
        }

        if (!(returnType instanceof ParameterizedType)) {
            throw new IllegalStateException(
                    " U Call must have generic type (e.g., Call<ResponseBody>)");
        }
        Type responseType = getParameterUpperBound(0, (ParameterizedType) returnType);
        Executor callbackExecutor = retrofit.callbackExecutor();
        return new LiveCallAdapter<>(responseType, callbackExecutor);
    }

    //自定义返回 Service 返回的类型
    public interface ImCall<T> extends BaseCall<T> {
        Call<T> getCall();
    }

    //自定义CallAdapter 两层数据结构
    private static final class LiveCallAdapter<R> implements CallAdapter<R, ImCall<R>> {
        private final Type responseType;
        private final Executor callbackExecutor;

        public LiveCallAdapter(Type responseType, Executor callbackExecutor) {
            this.responseType = responseType;
            this.callbackExecutor = callbackExecutor;
        }

        @Override
        public Type responseType() {
            return responseType;
        }

        @Override
        public ImCall<R> adapt(Call<R> call) {
            return new LiveCall<>(call, callbackExecutor);
        }
    }

    //自定义返回 Call<T>
    private static class LiveCall<T> implements ImCall<T> {
        private final Call<T> call;
        private final Executor callbackExecutor;

        LiveCall(Call<T> call, Executor callbackExecutor) {
            this.call = call;
            this.callbackExecutor = callbackExecutor;
        }

        @Override
        public Call<T> getCall() {
            return call;
        }

        @Override
        public void cancel() {
            call.cancel();
        }

        @Override
        public void enqueue(@Nullable ICallback<T> callback) {
            //走第二个参数的
            enqueue(callback, null);
        }

        @Override
        public void enqueue(@Nullable final ICallback<T> callback, final Lifecycle lifecycle) {

            call.enqueue(new Callback<T>() {
                @Override
                public void onResponse(Call<T> call, final Response<T> response) {
                    if (lifecycle != null) {
                        if (lifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
                            //TODO logo
                            return;
                        }
                    }

                    if (callbackExecutor != null) {

                        callbackExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                handleResponse(response, callback);
                            }
                        });
                    } else {
                        handleResponse(response, callback);
                    }
                }

                @Override
                public void onFailure(Call<T> call, final Throwable t) {
                    if (lifecycle != null) {
                        if (lifecycle.getCurrentState() == Lifecycle.State.DESTROYED) {
                            //TODO logo
                            return;
                        }
                    }

                    if (BuildConfig.DEBUG) {
                        t.printStackTrace();
                    }
                    if (callbackExecutor != null) {
                        callbackExecutor.execute(new Runnable() {
                            @Override
                            public void run() {
                                if (callback != null) {
                                    try {
                                        callback.onFail(getCode(t), null, t);
                                        callback.onFinish();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        if (BuildConfig.DEBUG) {
                                            throw e;
                                        }
                                    }
                                }
                            }
                        });
                    } else {
                        if (callback != null) {
                            try {
                                callback.onFail(getCode(t), null, t);
                                callback.onFinish();
                            } catch (Exception e) {
                                e.printStackTrace();
                                if (BuildConfig.DEBUG) {
                                    throw e;
                                }
                            }
                        }
                    }
                }
            });
        }

        /**
         * @param t 无授权/网络
         * @return
         */
        private int getCode(Throwable t) {
            int code = ICallback.OTHER_STATUS_CODE;
            if (t instanceof IOException) {
                code = ICallback.NO_NETWORK_STATUS_CODE;
            }
            return code;
        }

        //异步加工消息
        private void handleResponse(Response<T> response, ICallback<T> callback) {
            //服务端响应
            try {
                //响应成功
                if (response.isSuccessful()) {
                    T body = response.body();
                    //自定义数据响应
                    if (body instanceof IResponse) {
                        //自定义状态 这里的是状态码 0 为成功
                        if (((IResponse) body).isSucceeded()) {
                            // getInfoFromResponse(body);
                            if (callback != null) {
                                //返回给外部接口
                                callback.onSuccess(body);
                            }
                        } else {
                            //业务异常
                            BusinessApiException exception = new BusinessApiException(((IResponse) body).getErrorCode(), ((IResponse) body).getErrorMessage());

                            if (callback != null) {
                              callback.onFail(((IResponse) body).getErrorCode(), body, exception);
                            }



                        }

                    } else {
                        if (callback != null) {
                            //返回给外部接口
                            callback.onSuccess(body);
                        }
                    }
                    //--------------------------------成功 end------------------------------------------
                } else {
                    //响应失败 非 200
                    @SuppressWarnings("ThrowableNotThrown")
                    BusinessApiException exception = new BusinessApiException(response.code(),
                            response.message());
                    if (exception.needLogout()) {
                        // TODO 是否强制推出
                    }
                    if (callback != null) {
                        callback.onFail(response.code(), response.body(), new BusinessApiException(response.code(), response.message()));
                    }
                }
                if (callback != null) {
                    callback.onFinish();
                }
            } catch (Exception e) {
                e.printStackTrace();
                //TODO debug 模式下 throw
                if (BuildConfig.DEBUG) {
                    e.printStackTrace();
                }
            }

        }

        /**
         * 过滤最新的基础信息
         *
         * @param body
         */
        private void getInfoFromResponse(T body) {
            if (body instanceof BaseResponse && ((BaseResponse) body).common != null) {
                //TODO 更新基础数据状态 或者别的 比如积分，授权
            }
        }

        @Override
        public Response<T> execute() throws IOException {
            return call.execute();
        }

        @Override
        public BaseCall<T> clone() {
            return new LiveCall<>(call.clone(), callbackExecutor);
        }


    }
}
