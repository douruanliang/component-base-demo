package com.github.douruanliang.m_login.api;


import android.database.Observable;

import com.github.douruanliang.lib_https.calladapter.BaseCallAdapterFactory2;
import com.github.douruanliang.lib_https.response.BaseResponse;
import com.github.douruanliang.lib_https.response.DataListResponse;
import com.github.douruanliang.lib_https.response.BaseDataResponse;
import com.github.douruanliang.m_login.CommandInterface;
import com.github.douruanliang.m_login.model.TestUser;
import com.github.douruanliang.m_login.model.UserBaseModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 *
 */
public interface UserApi {


    @FormUrlEncoded
    @POST(CommandInterface.USER_INFO)
    BaseCallAdapterFactory2.ImCall<BaseDataResponse<UserBaseModel>> getUserByLoginName(@Field("loginName") String loginName);


    @FormUrlEncoded
    @POST(CommandInterface.USR_INFO)
    BaseCallAdapterFactory2.ImCall<BaseDataResponse<TestUser>> login(@Field("username") String username);


    @GET(CommandInterface.USR_INFO_S)
    BaseCallAdapterFactory2.ImCall<DataListResponse<TestUser>> getUsers();



    Observable<BaseResponse> getData(@Part("key")RequestBody  requestBody,
                                     @Part MultipartBody.Part file );

}
