package com.zhbitzwz.vehiclesystems.Application;

import android.app.Application;

import com.wilddog.wilddogcore.WilddogApp;
import com.wilddog.wilddogcore.WilddogOptions;

/**
 * App Initial
 * Author: ZWZ
 * Date: 2016/10/3 on 12:51.
 */
public class App extends Application{
            private static App context;
        public static App getAppContext() {
            return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //TODO Initial 高德,采取官方推荐的初始化方式
//        AMapNavi.setApiKey(this,Config.gaodeApiKey);
        //Initial Wilddog
        WilddogOptions wilddogOptions = new WilddogOptions.Builder().setSyncUrl(Config.wilddog_url).build();
        WilddogApp wilddogApp = WilddogApp.initializeApp(this,wilddogOptions);

    }
}
