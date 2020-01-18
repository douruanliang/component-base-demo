package com.github.douruanliang.base_lib.utils.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: douruanliang
 * @date: 2020-01-13
 */
public class NetStateChangeReceiver extends BroadcastReceiver {

    /**
     * 单例
     */
    private static class InstanceHolder {
        private static final NetStateChangeReceiver INSTANCE = new NetStateChangeReceiver();
    }

    private List<NetStateChangeObserver> mObservers = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            NetworkType networkType = NetworkUtil.getNetworkType(context);
            notifyObservers(networkType);
        }
    }


    public static void registerObServer(NetStateChangeObserver observer) {
        if (null == observer) {
            return;
        }

        if (!InstanceHolder.INSTANCE.mObservers.contains(observer)) {
            InstanceHolder.INSTANCE.mObservers.add(observer);
        }

    }

    public static void unRegisterObServer(NetStateChangeObserver observer) {
        if (null == observer) {
            return;
        }
        if (InstanceHolder.INSTANCE.mObservers.contains(observer)) {
            InstanceHolder.INSTANCE.mObservers.remove(observer);
        }
    }

    /**
     * 注册
     *
     * @param context
     */
    public static void registerReceiver(Context context) {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(InstanceHolder.INSTANCE, intentFilter);
    }

    /**
     * 移除
     *
     * @param context
     */
    public static void unRegisterReceiver(Context context) {
        context.unregisterReceiver(InstanceHolder.INSTANCE);
    }

    /**
     * 通知
     *
     * @param networkType
     */
    private void notifyObservers(NetworkType networkType) {

        if (networkType == NetworkType.NETWORK_NO) {
            for (NetStateChangeObserver observer : mObservers) {
                observer.onNetDisconnected();
            }
        } else {
            for (NetStateChangeObserver observer : mObservers) {
                observer.onNetConnected(networkType);
            }
        }
    }
}
