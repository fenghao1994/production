package com.lf.tempcore.tempModule.tempMVPCommI;

import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;

/**
 * Created by longf on 2016/4/25.
 */
public interface TempViewI {
    TempNetType checkNetWork();
    void viewProgress();
    void viewDismissProgress();
    void viewMsg(String message);
    void viewError(TempErrorCode code,String message);
}
