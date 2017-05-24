package com.lf.tempcore.tempConfig;

/**
 * Created by Administrator on 2015/11/14.
 */
public class TempURIConfig {

//    public final static String BASE_IMG_URL = "http://192.168.1.102:8080/edumap/common/file/download.do?storeFileName=";
//    public final static String BASE_SERVICE_URL = "http://192.168.1.102:8080/";

    public final static String BASE_IMG_URL = "http://115.28.86.42:8083/edumap/common/file/download.do?storeFileName=";
    public final static String BASE_SERVICE_URL = "http://115.28.86.42:8083/";

   /* public static String makeImageUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            return "";
        }
        return BASE_IMG_URL + url;
    }

    public static String makeImageUrl(String url, int width, int height) {
        if (TextUtils.isEmpty(url))
            return "";
        if (TextUtils.isEmpty(width + "") || TextUtils.isEmpty(height + ""))
            return BASE_IMG_URL + url + "&imgwidth=" + width + "&imgheight=" + height;
        return BASE_IMG_URL + url;
    }
*/

    //<!--以下为JSP或者HTML页面-->

    //关于我们
    public final static String PLARMINFO = BASE_SERVICE_URL+"app/public/mall/queryProject.do";
    //用户协议
    public final static String USER_AGRESSMENT = BASE_SERVICE_URL+"userAgreement.html";



}
