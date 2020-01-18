package com.github.douruanliang.lib_https.security;

import com.github.douruanliang.lib_https.BuildConfig;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.internal.tls.OkHostnameVerifier;

/**
 * author: dourl
 * created on: 2019/2/13 5:26 PM
 * description:
 */
public class ReleaseHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        //测试环境下忽略
        if (BuildConfig.DEBUG){
            return true;
        }else{
            //TODO host = 签名一致
            return OkHostnameVerifier.INSTANCE.verify(hostname,session);
        }

    }
}
