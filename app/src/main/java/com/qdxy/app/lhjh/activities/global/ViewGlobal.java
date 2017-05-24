package com.qdxy.app.lhjh.activities.global;

import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempModule.tempMVPCommI.TempViewI;
import com.qdxy.app.lhjh.bean.RespProductLines;
import com.qdxy.app.lhjh.bean.RespProductModel;

/**通用View
 * Created by KY on 2016/11/23.
 */

public interface ViewGlobal extends TempViewI{
    void onGetProductionLinesSucceed(RespProductLines data);
    void onGetProductionLinesFailed(TempErrorCode code, String message);
    void onGetProductTypeSucceed(RespProductModel data);
    void onGetProductTypeFailed(TempErrorCode code,String message);
}

