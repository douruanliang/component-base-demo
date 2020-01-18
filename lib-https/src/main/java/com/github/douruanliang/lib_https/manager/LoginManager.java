package com.github.douruanliang.lib_https.manager;

import android.content.ContentResolver;

import androidx.lifecycle.MutableLiveData;


/**
 * author: dourl
 * created on: 2018/8/7 下午1:46
 * description:已登录用户管理类
 */
public class LoginManager {
    private static LoginManager mInstance;

    //private BaseUser mCurrentUser;

    /**
     * access_token
     */
    private String mToken = "";
    /**
     * app_secret
     */
    private String mSecret = "";

    //private MutableLiveData<BaseUser> mUserLiveData = new MutableLiveData<>();
    private ContentResolver mContentResolver;

    private LoginManager() {

    }

    public static LoginManager getInstance() {
        if (mInstance == null) {
            synchronized (LoginManager.class) {
                if (mInstance == null) {
                    mInstance = new LoginManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 是否已经登录
     *
     * @return true为已登录
     */
    public boolean isLogin() {
        //return (getSecret() != null && getCurrentUserId() != null && getToken() != null);
        return true;
    }

    public String getToken() {
        return mToken;
    }
}
