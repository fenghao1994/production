package com.qdxy.app.lhjh.config;

import android.text.TextUtils;

import static com.lf.tempcore.tempConfig.TempURIConfig.BASE_IMG_URL;

/**
 * Created by KY on 2016/11/2.
 */

public class AppConfig {
    /**
     * denbug 调试未对接阶段使用
     */
    public static final boolean APP_DEBUG=false;
    //    public static final  String BASE_URL = "http://192.168.199.102:8080/lhjh/";


//    public static final  String BASE_URL = "http://192.168.199.103:10127/api/";
//  public static final  String BASE_URL = "http://192.168.199.102:12800/api/";
   public static final  String BASE_URL = "http://120.76.23.240:12501/api/";
//    public static final String DEBUG_API="test/";
    public static final String DEBUG_API="";
//    public static final  String BASE_IMG_URL = BASE_URL + "common/file/download.do?storeFileName=";
    public static String makeImageUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        return BASE_IMG_URL + url;
    }
}
