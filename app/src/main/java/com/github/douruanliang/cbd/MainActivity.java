package com.github.douruanliang.cbd;

import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.douruanliang.base_lib.router.ARouterPaths;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.id_login).setOnClickListener(c -> {
            ARouter.getInstance().build(ARouterPaths.LOGIN_PATH).navigation();
        });
    }
}
