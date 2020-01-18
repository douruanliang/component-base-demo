package com.github.douruanliang.lib_https.calladapter;
import com.github.douruanliang.lib_https.callback.ICallback;
import java.io.IOException;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import retrofit2.Response;

/**
 * author: dourl
 * created on: 2018/8/6 下午4:47
 * description: 模拟 Call 里面的内容
 */
public interface BaseCall<T> {
    void cancel();
    @Deprecated
    void enqueue(@Nullable ICallback<T> callback);

    void enqueue(@Nullable ICallback<T> callback, Lifecycle lifecycle);

    Response<T>execute() throws IOException;

    BaseCall<T> clone();
}
