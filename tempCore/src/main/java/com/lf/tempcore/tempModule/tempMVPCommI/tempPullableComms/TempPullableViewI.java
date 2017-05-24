package com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms;

import com.lf.tempcore.tempEnum.TempErrorCode;

/**
 * Created by longf on 2016/5/6.
 */
public interface TempPullableViewI<RESPONSE>{
    void onInit(RESPONSE data);
    void onRefresh(RESPONSE data);
    void onLoadmore(RESPONSE data);
    void refreshStatus(boolean succeed);
    void loadMoreStatus(boolean succeed);
    void showPullableProgressDialog();
    void dismissPullableProgressDialog();
    void onError(TempErrorCode code,String message);
}
