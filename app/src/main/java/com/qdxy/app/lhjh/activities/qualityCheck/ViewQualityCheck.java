package com.qdxy.app.lhjh.activities.qualityCheck;

import com.lf.tempcore.tempModule.tempMVPCommI.TempViewI;

/**
 * Created by mac on 2017/1/16.
 */

public interface ViewQualityCheck extends TempViewI {
    void onDetail(RespQualityDetail data);
    void onHandleResultSucceed();
}
