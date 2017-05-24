package com.qdxy.app.lhjh.activities.onOffLine;

import com.lf.tempcore.tempModule.tempMVPCommI.TempViewI;

/**
 * Created by KY on 2016/12/5.
 */

public interface ViewOnOffLineI extends TempViewI{
    void onOfflineSucceed(String message);
    void onBeforeProcedureSucceed(RespProcedureList message);
}
