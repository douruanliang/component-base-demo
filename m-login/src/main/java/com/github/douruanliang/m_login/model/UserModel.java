package com.github.douruanliang.m_login.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.github.douruanliang.lib_https.model.BaseObject;

/**
 * author: dourl
 * created on: 2018/7/30 下午2:32
 * description: 核心数据项
 */
public class UserModel implements BaseObject, Parcelable {
    protected String uid;
    protected String username;
    protected String fullname;
    protected String email;
    protected String mobile;
    protected String avatar;
    protected String avatar_thumb;
    protected int push_notify;
    protected String share_link;
    protected int age;
    protected String country;
    protected String province;
    protected String city;
    protected int level;
    protected int game_count;
    protected String birthday;
    protected String color;
    protected int gender;
    protected int is_friend;
    protected String country_code;
    protected int city_code;
    protected int save_static;//是否自动保存到相册
    protected int now_exp;
    protected int max_exp;
    protected int id_change_times;
    protected int from_third;
    /**
     * 性别 0:未知 1:男 2:女
     */
    public static final int GENDER_UNKOWN = 0;
    public static final int GENDER_BOY = 1;
    public static final int GENDER_GIRL = 2;


    /**
     * 优先返回fullName，为空则返回UserName
     */
    public String getFullNameOrUserName() {
        if (fullname != null && fullname.length() != 0) {
            return fullname;
        } else if (username != null && username.length() != 0) {
            return username;
        } else {
            return "";
        }
    }


    public UserModel() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uid);
        dest.writeString(this.username);
        dest.writeString(this.fullname);
        dest.writeString(this.email);
        dest.writeString(this.mobile);
        dest.writeString(this.avatar);
        dest.writeString(this.avatar_thumb);
        dest.writeInt(this.push_notify);
        dest.writeString(this.share_link);
        dest.writeInt(this.age);
        dest.writeString(this.country);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeInt(this.level);
        dest.writeInt(this.game_count);
        dest.writeString(this.birthday);
        dest.writeString(this.color);
        dest.writeInt(this.gender);
        dest.writeInt(this.is_friend);
        dest.writeString(this.country_code);
        dest.writeInt(this.city_code);
        dest.writeInt(this.save_static);
        dest.writeInt(this.now_exp);
        dest.writeInt(this.max_exp);
        dest.writeInt(this.id_change_times);
        dest.writeInt(this.from_third);
    }

    protected UserModel(Parcel in) {
        this.uid = in.readString();
        this.username = in.readString();
        this.fullname = in.readString();
        this.email = in.readString();
        this.mobile = in.readString();
        this.avatar = in.readString();
        this.avatar_thumb = in.readString();
        this.push_notify = in.readInt();
        this.share_link = in.readString();
        this.age = in.readInt();
        this.country = in.readString();
        this.province = in.readString();
        this.city = in.readString();
        this.level = in.readInt();
        this.game_count = in.readInt();
        this.birthday = in.readString();
        this.color = in.readString();
        this.gender = in.readInt();
        this.is_friend = in.readInt();
        this.country_code = in.readString();
        this.city_code = in.readInt();
        this.save_static = in.readInt();
        this.now_exp = in.readInt();
        this.max_exp = in.readInt();
        this.id_change_times = in.readInt();
        this.from_third = in.readInt();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel source) {
            return new UserModel(source);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}
