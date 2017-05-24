package com.qdxy.app.lhjh.activities.home;

import com.lf.tempcore.tempModule.tempMVPCommI.TempViewI;

/**
 * Created by KY on 2016/11/21.
 */

public interface ViewHome extends TempViewI {
    void viewMenuSucceed(RespHomeMenuGroup data);
    void onLogoutSucceed();
    void onWorkSucceed();
    void onWorkFailed();
}
