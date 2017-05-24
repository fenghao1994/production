package com.qdxy.app.lhjh.activities.exceptions;

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
 * Created by KY on 2016/12/2.
 */

public class PreException extends TempPresenterDefault<ViewExceptionI> {
    public PreException(ViewExceptionI viewExceptionI) {

        super(viewExceptionI);
    }

    /**
     * 异常判断详情
     *
     */
    public void requestProblemGet( String id) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).requestProblemGet(id), new Callback<RespExceptionJudgement>() {
                @Override
                public void onResponse(Call<RespExceptionJudgement> call, Response<RespExceptionJudgement> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onJudgementDetailSucceed(response.body());
                        } else {
                            getView().viewError(TempErrorCode.ERROR_FAILED,response.body().getErrorMsg());
//                            getView().touliaoFailed(response.body().getErrorMsg());
//                            getView().viewUpdateFailed(response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespExceptionJudgement> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }
    /**
     * 异常判断
     *
     * @param id
     * @param handleType 异常判断类型40-无异常    41-工件异常  42-设备异常   43-刀具异常
     * @param prodcutionCode
     * @param deviceToolId
     * @param deviceCode
     * @param description
     */
    public void handProblem(String id, String handleType, String prodcutionCode,String deviceToolId, String deviceCode,  String description) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).handProblem(id,handleType,prodcutionCode,deviceToolId,deviceCode,description), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onOnProblemSucceed("提交成功！");
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
    /**
     * 刀具异常详情
     *
     * @param id
     */
    public void deviceToolProblemDetail(int id) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).deviceToolProblemDetail(id), new Callback<RespProblemDetail>() {
                @Override
                public void onResponse(Call<RespProblemDetail> call, Response<RespProblemDetail> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onDaojuDetailSucceed(response.body());
//                            getView().onOnProblemSucceed("提交成功！");
                        } else {
                            getView().viewError(TempErrorCode.ERROR_FAILED,response.body().getErrorMsg());
//                            getView().touliaoFailed(response.body().getErrorMsg());
//                            getView().viewUpdateFailed(response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespProblemDetail> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }
    /**
     * 设备异常详情
     *
     * @param id
     */
    public void deviceProblemDetail(int id) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).deviceProblemDetail(id), new Callback<RespProblemDetail>() {
                @Override
                public void onResponse(Call<RespProblemDetail> call, Response<RespProblemDetail> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onDaojuDetailSucceed(response.body());
//                            getView().onOnProblemSucceed("提交成功！");
                        } else {
                            getView().viewError(TempErrorCode.ERROR_FAILED,response.body().getErrorMsg());
//                            getView().touliaoFailed(response.body().getErrorMsg());
//                            getView().viewUpdateFailed(response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespProblemDetail> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }
 /**
     * 工件异常详情
     *
     * @param id
     */
    public void machiningPartProblemDetail(int id) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).machiningPartProblemDetail(id), new Callback<RespProblemDetail>() {
                @Override
                public void onResponse(Call<RespProblemDetail> call, Response<RespProblemDetail> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onDaojuDetailSucceed(response.body());
//                            getView().onOnProblemSucceed("提交成功！");
                        } else {
                            getView().viewError(TempErrorCode.ERROR_FAILED,response.body().getErrorMsg());
//                            getView().touliaoFailed(response.body().getErrorMsg());
//                            getView().viewUpdateFailed(response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespProblemDetail> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }

    /**
     * 刀具异常处理
     *
     * @param id
     */
    public void deviceToolProblemHandleSubmit(int id,int HandleType, int ResponsiblePersonId,  String Remark) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).deviceToolProblemHandleSubmit(id,HandleType,ResponsiblePersonId,Remark), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onDaojuHandeSucceed();
//                            getView().onOnProblemSucceed("提交成功！");
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
     /**
     * 设备异常处理
     *
     * @param id
     */
    public void deviceProblemHandleSubmit(int id,int HandleType, int ResponsiblePersonId,  String Remark) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).deviceProblemHandleSubmit(id,HandleType,ResponsiblePersonId,Remark), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onDaojuHandeSucceed();
//                            getView().onOnProblemSucceed("提交成功！");
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
      /**
     * 工件异常处理
     *
     * @param id
     */
    public void machiningPartProblemHandleSubmit(int id,int HandleType, int ResponsiblePersonId,  String Remark) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).machiningPartProblemHandleSubmit(id,HandleType,ResponsiblePersonId,Remark), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onDaojuHandeSucceed();
//                            getView().onOnProblemSucceed("提交成功！");
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
