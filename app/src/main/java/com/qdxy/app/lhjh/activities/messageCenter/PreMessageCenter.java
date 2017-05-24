package com.qdxy.app.lhjh.activities.messageCenter;

import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempModule.tempMVPCommI.TempPresenterDefault;
import com.lf.tempcore.tempModule.tempRemotComm.TempRemotAPIConnecter;
import com.qdxy.app.lhjh.api.TempAPI;
import com.qdxy.app.lhjh.config.AppConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KY on 2016/12/14.
 */

public class PreMessageCenter extends TempPresenterDefault<ViewMessageCenterI> {
    public PreMessageCenter(ViewMessageCenterI viewMessageCenterI) {
        super(viewMessageCenterI);
    }
    /**
     * 未读消息
     *
     * @param id
     */
    public void systemMessageDetail(int id) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).systemMessageDetail(id), new Callback<RespMessageDetail>() {
                @Override
                public void onResponse(Call<RespMessageDetail> call, Response<RespMessageDetail> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onMesaageSucceed(response.body());
                        } else {
                            getView().viewError(TempErrorCode.ERROR_FAILED,response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespMessageDetail> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }
  /*  *//**
     * 已未读消息
     *
     * @param page
     *//*
    public void systemMessageReaded(int page) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).systemMessageReaded(page), new Callback<RespMessage>() {
                @Override
                public void onResponse(Call<RespMessage> call, Response<RespMessage> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onMesaageSucceed(response.body());
                        } else {
                            getView().viewError(TempErrorCode.ERROR_FAILED,response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespMessage> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }*/
}
