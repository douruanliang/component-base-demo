package com.github.douruanliang.m_login;

public interface CommandInterface {
    String V1 = "/v1/";
    String USER = V1 + "user/";
    String USER_INFO = USER + "login_name";

    String MAIN_PATH = "/v1/";
    //主域
    String DOMAIN = MAIN_PATH + "xsan/";

    String USRE = "user/";
    String LOGIN_URL = DOMAIN + USRE + "login";

    String USR_INFO =  USRE;

    String USR_INFO_S = "getUser";
}
