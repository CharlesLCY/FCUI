package com.lcy.fcui;

import android.app.Application;
import android.content.Context;

/**
 * @author FanCoder.LCY
 * @date 2018/8/30 10:39
 * @email 15708478830@163.com
 * @desc
 **/
public class App extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
