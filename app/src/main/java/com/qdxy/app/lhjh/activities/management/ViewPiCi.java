package com.qdxy.app.lhjh.activities.management;

import com.lf.tempcore.tempModule.tempMVPCommI.TempViewI;

/**
 * Created by KY on 2016/11/22.
 */

public interface ViewPiCi extends TempViewI {
    void viewCreateSucceed(String message);

    void viewCreateFailed(String message);
    void viewUpdateSucceed(String message);
    void viewUpdateFailed(String message);
}

