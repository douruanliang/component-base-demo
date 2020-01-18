package com.github.douruanliang.m_login;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.github.douruanliang.base_lib.router.ARouterPaths;
import com.github.douruanliang.base_lib.utils.net.NetStateChangeObserver;
import com.github.douruanliang.base_lib.utils.net.NetStateChangeReceiver;
import com.github.douruanliang.base_lib.utils.net.NetworkType;
import com.github.douruanliang.lib_https.callback.ICallback;
import com.github.douruanliang.lib_https.response.BaseDataResponse;
import com.github.douruanliang.lib_https.response.DataListResponse;
import com.github.douruanliang.lib_https.retrofit.RetrofitManager;
import com.github.douruanliang.m_login.api.UserApi;
import com.github.douruanliang.m_login.model.TestUser;
import com.zd.serviceaidl.IMyAidlService;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


@Route(path = ARouterPaths.LOGIN_PATH)
public class LoginActivity extends AppCompatActivity implements NetStateChangeObserver {
    private static final String TAG = "LoginActivity";
    private UserApi mUserApi;
    private IMyAidlService mService ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ARouter.getInstance().inject(this);

        NetStateChangeReceiver.registerReceiver(this);

        mUserApi = RetrofitManager.get().create(UserApi.class);


        /*mUserApi.preLogin("123", "456").enqueue(new ICallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult baseData) {

            }

            @Override
            public boolean onFail(int statusCode, @Nullable LoginResult failDate, @Nullable Throwable error) {
                return false;
            }

            @Override
            public void onFinish() {

            }
        }, getLifecycle());*/


        findViewById(R.id.id_use).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserApi.login("xxx").enqueue(new ICallback<BaseDataResponse<TestUser>>() {
                    @Override
                    public void onSuccess(BaseDataResponse<TestUser> baseData) {


                        Log.d(TAG, "user:{address}" + baseData.code);
                        Log.d(TAG, "user:{msg}" + baseData.message);

                    }

                    @Override
                    public boolean onFail(int statusCode, @Nullable BaseDataResponse<TestUser> failDate, @Nullable Throwable error) {

                        Log.d(TAG, "onFail" + statusCode);
                        Log.d(TAG, "onFail" + error.getMessage());
                        return false;
                    }

                    @Override
                    public void onFinish() {

                    }
                }, getLifecycle());

            }
        });


        findViewById(R.id.id_uses).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mUserApi.getUsers().enqueue(new ICallback<DataListResponse<TestUser>>() {
                    @Override
                    public void onSuccess(DataListResponse<TestUser> baseData) {
                        Log.d(TAG, "user:{getList}" + baseData.data.size());
                    }

                    @Override
                    public boolean onFail(int statusCode, @Nullable DataListResponse<TestUser> failDate, @Nullable Throwable error) {
                        Log.d(TAG, "onFail" + statusCode);
                        Log.d(TAG, "onFail" + error.getMessage());
                        return false;
                    }

                    @Override
                    public void onFinish() {

                    }
                }, getLifecycle());

            }
        });


        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mService = IMyAidlService.Stub.asInterface(service);

                try {
                    Log.d(TAG, "onFail" + mService.getData());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        findViewById(R.id.id_bind_service).setOnClickListener((v) -> {

            Intent intent = new Intent();
            intent.setAction("com.zd.serviceaidl.myservice");
            intent.setPackage("com.zd.serviceaidl");
            bindService(intent, connection, BIND_AUTO_CREATE);

        });


        findViewById(R.id.id_unbind_service).setOnClickListener(v -> {
            unbindService(connection);
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        NetStateChangeReceiver.registerObServer(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NetStateChangeReceiver.unRegisterObServer(this);
    }

    @Override
    protected void onDestroy() {
        NetStateChangeReceiver.unRegisterReceiver(this);
        super.onDestroy();

    }

    @Override
    public void onNetDisconnected() {
        Toast.makeText(this, "网络连接失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetConnected(NetworkType networkType) {
        Toast.makeText(this, "当前网络为：" + networkType.toString(), Toast.LENGTH_SHORT).show();
    }
}
