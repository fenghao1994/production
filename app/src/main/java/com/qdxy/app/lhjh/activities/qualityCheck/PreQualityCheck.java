package com.qdxy.app.lhjh.activities.qualityCheck;

import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempModule.tempMVPCommI.TempPresenterDefault;
import com.lf.tempcore.tempModule.tempRemotComm.TempRemotAPIConnecter;
import com.lf.tempcore.tempResponse.TempResponse;
import com.qdxy.app.lhjh.api.TempAPI;
import com.qdxy.app.lhjh.config.AppConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mac on 2017/1/16.
 */

public class PreQualityCheck extends TempPresenterDefault<ViewQualityCheck> {
    public PreQualityCheck(ViewQualityCheck viewQualityCheck) {
        super(viewQualityCheck);
    }
    /**
     * 异常判断
     *
     * @param id
     * @param procedureId
     */
    public void qualityCheckDetail(int id, int procedureId,int type) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).qualityCheckDetail(id,procedureId,type==0?14:17), new Callback<RespQualityDetail>() {
                @Override
                public void onResponse(Call<RespQualityDetail> call, Response<RespQualityDetail> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onDetail(response.body());
                        } else {
                            getView().viewError(TempErrorCode.ERROR_FAILED,response.body().getErrorMsg());
//                            getView().touliaoFailed(response.body().getErrorMsg());
//                            getView().viewUpdateFailed(response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespQualityDetail> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }
    /**
     * 异常判断
     *
     * @param id
     * @param procedureId
     */
    public void handleQualityCheck(int id, int procedureId,boolean Result,String Remark,boolean isNext) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).handleQualityCheck(id,procedureId,Result,Remark,isNext), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onHandleResultSucceed();
                        } else {
                            getView().viewError(TempErrorCode.ERROR_FAILED,response.body().getErrorMsg());
//                            getView().touliaoFailed(response.body().getErrorMsg());
//                            getView().viewUpdateFailed(response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<TempResponse> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }
}
