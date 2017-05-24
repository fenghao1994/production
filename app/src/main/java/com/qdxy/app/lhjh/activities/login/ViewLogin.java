package com.qdxy.app.lhjh.activities.login;

import com.lf.tempcore.tempModule.tempMVPCommI.TempViewI;

/**
 * Created by KY on 2016/11/18.
 */

public interface ViewLogin extends TempViewI {
    void viewPswInvalid(String message);
    void onLoginSucceed(RespLogin data);

}
