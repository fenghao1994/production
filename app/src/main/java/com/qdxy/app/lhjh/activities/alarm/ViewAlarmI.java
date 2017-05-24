package com.qdxy.app.lhjh.activities.alarm;

import com.lf.tempcore.tempModule.tempMVPCommI.TempViewI;

/**
 * Created by KY on 2016/12/7.
 */

public interface ViewAlarmI extends TempViewI{
    void onStopSucceed(String message);
    void onStartSucceed(String message);
}
