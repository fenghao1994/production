package com.qdxy.app.lhjh.activities.check;

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
 * Created by KY on 2016/11/30.
 */

public class PreCheck extends TempPresenterDefault<ViewCheckI> {
    public PreCheck(ViewCheckI viewCheckI) {
        super(viewCheckI);
    }

    /**
     * 获取未装箱的产品列表
     *
     * @param id
     */
    public void requestRandomCheckDetail(String id) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).requestRandomCheckDetail(id), new Callback<RespCheckPrdDetail>() {
                @Override
                public void onResponse(Call<RespCheckPrdDetail> call, Response<RespCheckPrdDetail> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onCheckPrdDetailSucceed(response.body());
//                            getView().touliaoSucceed("创建成功");
                        } else {
                            getView().viewError(TempErrorCode.ERROR_FAILED, response.body().getErrorMsg());
//                            getView().touliaoFailed(response.body().getErrorMsg());
//                            mView.viewUpdateFailed(response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespCheckPrdDetail> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }

    /**
     * 创建抽检记录
     * @param RandomCheckTaskId
     * @param Result
     * @param ResponsiblePersonId
     */
    public void createRecord(String RandomCheckTaskId, String Result, String ResponsiblePersonId) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).createRecord(RandomCheckTaskId, Result, ResponsiblePersonId), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
//                            getView().viewMsg("提交成功！");
                            getView().onCreateRecordSucceed("提交成功！");
//                            getView().touliaoSucceed("创建成功");
                        } else {
                            getView().viewError(TempErrorCode.ERROR_FAILED, response.body().getErrorMsg());
//                            getView().touliaoFailed(response.body().getErrorMsg());
//                            mView.viewUpdateFailed(response.body().getErrorMsg());
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
