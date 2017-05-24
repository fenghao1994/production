package com.qdxy.app.lhjh.activities.onOffLine;

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
 * Created by KY on 2016/12/5.
 */

public class PreOnOffLine extends TempPresenterDefault<ViewOnOffLineI>{
    public PreOnOffLine(ViewOnOffLineI viewOnOffLineI) {
        super(viewOnOffLineI);
    }
    /**
     * 更新多个工件状态
     *
     * @param ids
     * @param status
     * @param operationType
     * @param procedureNumber
     * @param remark
     */
    public void updateStatus(String[] ids, String status, String operationType, String procedureNumber, String remark) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).updateStatus(ids,status,operationType,procedureNumber,remark), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onOfflineSucceed("提交成功！");
                        } else {
                            getView().viewError(TempErrorCode.ERROR_FAILED,response.body().getErrorMsg());
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
    /**
     * 更新多个工件状态
     *
     * @param id
     * @param status
     * @param operationType
     * @param procedureNumber
     * @param remark
     */
    public void updateStatus(String id, String status, String operationType, String procedureNumber, String remark) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).updateStatus(id,status,operationType,procedureNumber,remark), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onOfflineSucceed("提交成功！");
                        } else {
                            getView().viewError(TempErrorCode.ERROR_FAILED,response.body().getErrorMsg());
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
    /**
     * 返工上线获取工序列表
     *
     * @param machiningPartId    工序id
     */
    public void beforeProcedure( String machiningPartId) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).beforeProcedure(machiningPartId), new Callback<RespProcedureList>() {
                @Override
                public void onResponse(Call<RespProcedureList> call, Response<RespProcedureList> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onBeforeProcedureSucceed(response.body());
                        } else {
                            getView().viewError(TempErrorCode.ERROR_FAILED,response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespProcedureList> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }
}
