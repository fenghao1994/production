package com.qdxy.app.lhjh;

import com.lf.tempcore.tempApplication.TempApplication;
import com.lf.tempcore.tempModule.tempRemotComm.TempRemotAPIConfiguration;
import com.lf.tempcore.tempModule.tempRemotComm.TempRemotAPIConnecter;
import com.qdxy.app.lhjh.config.AppConfig;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by KY on 2016/11/3.
 */

public class MyApplication extends TempApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        TempRemotAPIConfiguration.TempRemptAPIBuilder builder = new TempRemotAPIConfiguration.TempRemptAPIBuilder(AppConfig.BASE_URL);
        TempRemotAPIConfiguration configuration = new TempRemotAPIConfiguration(builder);
        TempRemotAPIConnecter.INSTANCE.init(configuration);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
