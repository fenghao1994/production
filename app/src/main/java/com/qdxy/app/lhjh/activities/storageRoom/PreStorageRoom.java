package com.qdxy.app.lhjh.activities.storageRoom;

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
 * Created by mac on 2017/2/9.
 */

public class PreStorageRoom extends TempPresenterDefault<ViewStorageRoom> {
    public PreStorageRoom(ViewStorageRoom viewStorageRoom) {
        super(viewStorageRoom);
    }

    /**
     * 获取产品入库详情
     */
    public void productBoxApplyDetail(int id) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).productBoxApplyDetail(id), new Callback<RespMaterialDetail>() {
                @Override
                public void onResponse(Call<RespMaterialDetail> call, Response<RespMaterialDetail> response) {
                    getView().viewDismissProgress();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onSucceed(response.body(), 19);
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespMaterialDetail> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }

    /**
     * 创建产品入库清点
     */
    public void applyCreate_productBox(String[] items) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).applyCreate_productBox(items), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onSucceed(response.body(), 18);
//                            getView().onMachineOperationSucceed(response.body());
                        } else {
//                            getView().onMachineOperationFailed(response.body().getErrorMsg());
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
     * 修改毛坯状态 2完成  3取消
     */
    public void orderStatus(int id, int status) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).orderStatus(id, status), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onSucceed(response.body(), 17);
//                            getView().onMachineOperationSucceed(response.body());
                        } else {
//                            getView().onMachineOperationFailed(response.body().getErrorMsg());
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
     * 修改辅料领用状态
     * status:3发货 4完成 5取消
     */
    public void orderStatus_auxiliary(int id, int status) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).orderStatus_auxiliary(id, status), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onSucceed(response.body(), 18);
//                            getView().onMachineOperationSucceed(response.body());
                        } else {
//                            getView().onMachineOperationFailed(response.body().getErrorMsg());
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
     * 修改刀具领用状态
     * status:3发货 4完成 5取消
     */

    public void orderStatus_deviceTool(int id, int status) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).orderStatus_deviceTool(id, status), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onSucceed(response.body(), 18);
//                            getView().onMachineOperationSucceed(response.body());
                        } else {
//                            getView().onMachineOperationFailed(response.body().getErrorMsg());
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
     * 毛坯发货
     */
    public void orderSend(int id, int count) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).orderSend(id, count), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onSucceed(response.body(), 16);
//                            getView().onMachineOperationSucceed(response.body());
                        } else {
//                            getView().onMachineOperationFailed(response.body().getErrorMsg());
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
     * 提交毛坯抽检
     */
    public void applyRandomCheck(int id, int unqualifiedCount) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).applyRandomCheck(id, unqualifiedCount), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onSucceed(response.body(), 15);
//                            getView().onMachineOperationSucceed(response.body());
                        } else {
//                            getView().onMachineOperationFailed(response.body().getErrorMsg());
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
     * 毛坯入库审批
     */
    public void applyApprove(int id, boolean status) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).applyApprove(id, status), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onSucceed(response.body(), 14);
//                            getView().onMachineOperationSucceed(response.body());
                        } else {
//                            getView().onMachineOperationFailed(response.body().getErrorMsg());
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
     * 产品入库审批
     *
     * @param id
     * @param status 2为通过 1拒绝
     */
    public void applyApprove_productBox(int id, int status) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).applyApprove_productBox(id, status, ""), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onSucceed(response.body(), 14);
//                            getView().onMachineOperationSucceed(response.body());
                        } else {
//                            getView().onMachineOperationFailed(response.body().getErrorMsg());
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
     * 产品确认入库
     *
     * @param id
     *
     */
    public void applyStatus_productBox(int id) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).applyStatus_productBox(id, 3), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onSucceed(response.body(), 14);
//                            getView().onMachineOperationSucceed(response.body());
                        } else {
//                            getView().onMachineOperationFailed(response.body().getErrorMsg());
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
     * 辅料审批
     *
     * @param id
     * @param status 2为通过
     */
    public void orderApprove_auxiliary(int id, int status) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).orderApprove_auxiliary(id, status, ""), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onSucceed(response.body(), 14);
//                            getView().onMachineOperationSucceed(response.body());
                        } else {
//                            getView().onMachineOperationFailed(response.body().getErrorMsg());
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
     * 刀具审批
     *
     * @param id
     * @param status 2为通过
     */
    public void orderApprove_deviceTool(int id, int status) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).orderApprove_deviceTool(id, status, ""), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onSucceed(response.body(), 14);
//                            getView().onMachineOperationSucceed(response.body());
                        } else {
//                            getView().onMachineOperationFailed(response.body().getErrorMsg());
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
     * 取消毛坯入库申请
     */
    public void applyCancel(int id) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).applyCancel(id), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onSucceed(response.body(), 13);
//                            getView().onMachineOperationSucceed(response.body());
                        } else {
//                            getView().onMachineOperationFailed(response.body().getErrorMsg());
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
     * 创建辅料领用订单
     */
    public void orderCreate_roughcast(RequestBodyMaterial body) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).orderCreate_roughcast(body), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onSucceed(response.body(), 12);
//                            getView().onMachineOperationSucceed(response.body());
                        } else {
//                            getView().onMachineOperationFailed(response.body().getErrorMsg());
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
     * 创建刀具领用订单
     */
    public void orderCreate_deviceTool(RequestBodyMaterial body) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).orderCreate_deviceTool(body), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onSucceed(response.body(), 12);
//                            getView().onMachineOperationSucceed(response.body());
                        } else {
//                            getView().onMachineOperationFailed(response.body().getErrorMsg());
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
     * 创建毛坯入库申请
     */
    public void applyCreate(int manufactorId, int modelId, int count) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).applyCreate(manufactorId, modelId, count), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onSucceed(response.body(), 12);
//                            getView().onMachineOperationSucceed(response.body());
                        } else {
//                            getView().onMachineOperationFailed(response.body().getErrorMsg());
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
     * 创建毛坯入库申请
     */
    public void orderCreate(int manufactorId, int modelId, int count, int productionLineId, String remark, String sendTime) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).orderCreate(manufactorId, modelId, count, productionLineId, remark), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onSucceed(response.body(), 12);
//                            getView().onMachineOperationSucceed(response.body());
                        } else {
//                            getView().onMachineOperationFailed(response.body().getErrorMsg());
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
     * 获取厂家数据
     */
    public void getRoughcastFactory() {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).getRoughcastFactory(), new Callback<RespStorageItem>() {
                @Override
                public void onResponse(Call<RespStorageItem> call, Response<RespStorageItem> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onSucceed(response.body(), 11);
//                            getView().onMachineOperationSucceed(response.body());
                        } else {
//                            getView().onMachineOperationFailed(response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespStorageItem> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }

    /**
     * 获取毛坯类型数据
     */
    public void getRoughcastType() {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).getRoughcastType(), new Callback<RespStorageItem>() {
                @Override
                public void onResponse(Call<RespStorageItem> call, Response<RespStorageItem> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onSucceed(response.body(), 10);
//                            getView().onMachineOperationSucceed(response.body());
                        } else {
//                            getView().onMachineOperationFailed(response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespStorageItem> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }

    /**
     * 获取辅料类型数据
     */
    public void getAuxiliaryType() {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).getAuxiliaryType(), new Callback<RespStorageItem>() {
                @Override
                public void onResponse(Call<RespStorageItem> call, Response<RespStorageItem> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onSucceed(response.body(), 10);
//                            getView().onMachineOperationSucceed(response.body());
                        } else {
//                            getView().onMachineOperationFailed(response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespStorageItem> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }

    /**
     * 获取刀具类型数据
     */
    public void getDeviceToolType() {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).getDeviceToolType(), new Callback<RespStorageItem>() {
                @Override
                public void onResponse(Call<RespStorageItem> call, Response<RespStorageItem> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onSucceed(response.body(), 10);
//                            getView().onMachineOperationSucceed(response.body());
                        } else {
//                            getView().onMachineOperationFailed(response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespStorageItem> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }

    /**
     * 获取毛坯管理详情
     */
    public void orderGet(int id) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).orderGet(id), new Callback<RespStorageDetail>() {
                @Override
                public void onResponse(Call<RespStorageDetail> call, Response<RespStorageDetail> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onSucceed(response.body(), 1);
//                            getView().onMachineOperationSucceed(response.body());
                        } else {
//                            getView().onMachineOperationFailed(response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespStorageDetail> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }

    /**
     * 辅料领用详情
     */
    public void orderGet_auxiliary(int id) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).orderGet_auxiliary(id), new Callback<RespMaterialDetail>() {
                @Override
                public void onResponse(Call<RespMaterialDetail> call, Response<RespMaterialDetail> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onSucceed(response.body(), 2);
//                            getView().onMachineOperationSucceed(response.body());
                        } else {
//                            getView().onMachineOperationFailed(response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespMaterialDetail> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }

    /**
     * 辅料领用详情
     */
    public void orderGet_deviceTool(int id) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).orderGet_deviceTool(id), new Callback<RespMaterialDetail>() {
                @Override
                public void onResponse(Call<RespMaterialDetail> call, Response<RespMaterialDetail> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onSucceed(response.body(), 3);
//                            getView().onMachineOperationSucceed(response.body());
                        } else {
//                            getView().onMachineOperationFailed(response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespMaterialDetail> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }

    /**
     * 获取毛坯管理详情
     */
    public void applyGet(int id) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).applyGet(id), new Callback<RespStorageDetail>() {
                @Override
                public void onResponse(Call<RespStorageDetail> call, Response<RespStorageDetail> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onSucceed(response.body(), 1);
//                            getView().onMachineOperationSucceed(response.body());
                        } else {
//                            getView().onMachineOperationFailed(response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespStorageDetail> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }

}
