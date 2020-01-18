package com.github.douruanliang.m_login.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * @author: douruanliang
 * @date: 2020-01-13
 */
public class TestUser implements Parcelable {

    public String name;
    public String address;


    public TestUser() {
    }

    protected TestUser(Parcel in) {
        name = in.readString();
        address = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TestUser> CREATOR = new Creator<TestUser>() {
        @Override
        public TestUser createFromParcel(Parcel in) {
            return new TestUser(in);
        }

        @Override
        public TestUser[] newArray(int size) {
            return new TestUser[size];
        }
    };

}
