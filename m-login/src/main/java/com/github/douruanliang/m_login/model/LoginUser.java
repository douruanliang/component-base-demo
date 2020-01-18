package com.github.douruanliang.m_login.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author: dourl
 * created on: 2018/8/6 下午6:16
 * description:
 */
public class LoginUser implements Parcelable {

    public String secret;
    public String token;
    public int type;//1-开启邮件认证 2-开启手机认证 3-开启谷歌认证
    public String login_token;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //super.writeToParcel(dest, flags);
        dest.writeString(this.secret);
        dest.writeString(this.token);
        dest.writeInt(this.type);
        dest.writeString(this.login_token);
    }

    public LoginUser() {
    }

    protected LoginUser(Parcel in) {
        // super(in);
        this.secret = in.readString();
        this.token = in.readString();
        this.type = in.readInt();
        this.login_token = in.readString();
    }

    public static final Parcelable.Creator<LoginUser> CREATOR = new Parcelable.Creator<LoginUser>() {
        @Override
        public LoginUser createFromParcel(Parcel source) {
            return new LoginUser(source);
        }

        @Override
        public LoginUser[] newArray(int size) {
            return new LoginUser[size];
        }
    };
}
