package com.qdxy.app.lhjh.activities.check;

import com.lf.tempcore.tempModule.tempMVPCommI.TempViewI;

/**
 * Created by KY on 2016/11/30.
 */

public interface ViewCheckI extends TempViewI{
    void onCheckPrdDetailSucceed(RespCheckPrdDetail data);
    void onCreateRecordSucceed(String message);
    void onFailed(int who,String message);
}
