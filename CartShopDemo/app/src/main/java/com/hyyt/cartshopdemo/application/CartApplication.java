package com.hyyt.cartshopdemo.application;

import android.app.Application;

import com.lzy.okgo.OkGo;

/**
 * Created by Administrator on 2016/10/21.
 */

public class CartApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.init(this);
        OkGo.getInstance().debug("OkGo");
    }
}
