package com.qdxy.app.lhjh.activities.arrangeProcedure;

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
 * Created by KY on 2016/12/8.
 */

public class PreArrange extends TempPresenterDefault<ViewArrangeI> {
    public PreArrange(ViewArrangeI viewArrangeI) {
        super(viewArrangeI);
    }
    /**
     * 设置工序安排
     *
     */
    public void setprocdurewithuser( String userId,String[] ids) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).setprocdurewithuser(userId,ids), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onSetprocdurewithuserSucceed("设置成功！");
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
    public void procedureClear( String userId) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).procedureClear(userId), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onProcedureClearSucceed("提交成功！");
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

}
