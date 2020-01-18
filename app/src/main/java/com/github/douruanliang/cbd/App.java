package com.github.douruanliang.cbd;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.douruanliang.lib_https.HttpApiBase;

/**
 * @author: douruanliang
 * @date: 2019-12-23
 */
public class App extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ARouter.openDebug();
        ARouter.openLog();
        ARouter.init(this);
        HttpApiBase.init(this);
    }
}
