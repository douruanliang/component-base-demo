package com.github.douruanliang.base_lib.utils.net;

/**
 * @author: douruanliang
 * @date: 2020-01-13
 */
public interface NetStateChangeObserver {

    /**
     *
     */
    void onNetDisconnected();

    /**&
     *
     * @param networkType
     */
    void onNetConnected(NetworkType networkType);
}
