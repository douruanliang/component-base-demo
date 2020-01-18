package com.github.douruanliang.lib_https.response;

import com.github.douruanliang.lib_https.model.BaseObject;
import com.github.douruanliang.lib_https.model.CommonObject;
import com.github.douruanliang.lib_https.model.IResponse;

/**
 * author: dourl
 * created on: 2018/7/30 下午2:25
 * description: 网络请求返回基础数据类
 */
public class BaseResponse implements BaseObject, IResponse {


    //注意返回信息 字段要和 服务端返回一致
    public int code = 0;
    public String message;
    public CommonObject common;

    @Override
    public boolean isSucceeded() {
        return code == 0;
    }


    /**
     * 错误状态码 0 特殊
     * @return
     */
    @Override
    public int getErrorCode() {
        return code;
    }

    /**
     * 错误信息
     * @return
     */
    @Override
    public String getErrorMessage() {
        return message;
    }
}
