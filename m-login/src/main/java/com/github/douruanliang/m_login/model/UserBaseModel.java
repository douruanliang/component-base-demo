package com.github.douruanliang.m_login.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.github.douruanliang.lib_https.model.BaseObject;

/**
 * @author: douruanliang
 * @date: 2019-12-24
 */
public class UserBaseModel implements BaseObject, Parcelable {

    public String uid;
    public String email;
    public String loginname;
    public String username;
    public String mobile;


    protected UserBaseModel(Parcel in) {
        uid = in.readString();
        email = in.readString();
        loginname = in.readString();
        username = in.readString();
        mobile = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(email);
        dest.writeString(loginname);
        dest.writeString(username);
        dest.writeString(mobile);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserBaseModel> CREATOR = new Creator<UserBaseModel>() {
        @Override
        public UserBaseModel createFromParcel(Parcel in) {
            return new UserBaseModel(in);
        }

        @Override
        public UserBaseModel[] newArray(int size) {
            return new UserBaseModel[size];
        }
    };
}
