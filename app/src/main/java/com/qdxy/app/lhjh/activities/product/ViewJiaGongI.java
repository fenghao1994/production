package com.qdxy.app.lhjh.activities.product;

import com.lf.tempcore.tempModule.tempMVPCommI.TempViewI;

/**
 * Created by KY on 2016/11/25.
 */

public interface ViewJiaGongI  extends TempViewI{
    void onUpdateStatusSucceed(String message);
    void onUpdateStatusFailed(String message);
}
