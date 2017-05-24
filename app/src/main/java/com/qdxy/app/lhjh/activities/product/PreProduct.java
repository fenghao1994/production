package com.qdxy.app.lhjh.activities.product;

import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.lf.tempcore.tempModule.tempMVPCommI.TempPresenterDefault;
import com.lf.tempcore.tempModule.tempRemotComm.TempRemotAPIConnecter;
import com.lf.tempcore.tempResponse.TempResponse;
import com.qdxy.app.lhjh.api.TempAPI;
import com.qdxy.app.lhjh.config.AppConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KY on 2016/11/24.
 */

public class PreProduct extends TempPresenterDefault<ViewProductI> {
    private final String TAG = "PreProduct";

    public PreProduct(ViewProductI viewProductI) {
        super(viewProductI);
    }



    /**
     * 清点产品箱
     */
    public void productBoxStatus(String id,int status) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).productBoxStatus(id,status), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onInBoxSucceed("");
                        } else {
                            getView().onMachineOperationFailed(response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<TempResponse> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    } /**
     * 获取自己在加工里能做的操作
     */
    public void getMachineOperation() {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).getMachineOperation(), new Callback<RespMachineOperation>() {
                @Override
                public void onResponse(Call<RespMachineOperation> call, Response<RespMachineOperation> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onMachineOperationSucceed(response.body());
                        } else {
                            getView().onMachineOperationFailed(response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespMachineOperation> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }
    /**
     * 获取加工统计
     */
    public void getMachiningCountByProcedure() {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).getMachiningCountByProcedure(), new Callback<RespProductionCount>() {
                @Override
                public void onResponse(Call<RespProductionCount> call, Response<RespProductionCount> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onMachiningCountByProcedure(response.body());
                        } else {
                            getView().onMachineOperationFailed(response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespProductionCount> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }
    /**
     * 获取当前投料的序号
     */
    public void getBySendMaterialNumber(int piciID) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).getBySendMaterialNumber(piciID), new Callback<RespFeedNum>() {
                @Override
                public void onResponse(Call<RespFeedNum> call, Response<RespFeedNum> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onFeedingNumSucceed(response.body());
                        } else {
                            getView().onMachineOperationFailed(response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespFeedNum> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }
    /**
     * 获取自己能投料的批次列表
     */
    public void getBatchByInBox() {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).getBatchByInBox(), new Callback<RespBatchByInBox>() {
                @Override
                public void onResponse(Call<RespBatchByInBox> call, Response<RespBatchByInBox> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().getBatchByInBox(response.body());
                        } else {
                            getView().onFailed(2,response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespBatchByInBox> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }

    /**
     * 获取自己能投料的批次列表
     */
    public void getBatchBySendMaterial() {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).getBatchBySendMaterial(), new Callback<RespBatchByInBox>() {
                @Override
                public void onResponse(Call<RespBatchByInBox> call, Response<RespBatchByInBox> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().getBatchBySendMaterial(response.body());
                        } else {
                            getView().onFailed(2,response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespBatchByInBox> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }

    /**
     * 获取自己能投料的批次列表
     */
    public void getlistByMachining() {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).getlistByMachining(), new Callback<RespBatchByInBox>() {
                @Override
                public void onResponse(Call<RespBatchByInBox> call, Response<RespBatchByInBox> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().getBatchBySendMaterial(response.body());
                        } else {
                            getView().onFailed(2,response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespBatchByInBox> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }
    /**
     * 获取自己能投料的批次列表
     */
    public void getroceduresByBatch() {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).getroceduresByBatch(), new Callback<RespBatchByInBox>() {
                @Override
                public void onResponse(Call<RespBatchByInBox> call, Response<RespBatchByInBox> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().getroceduresByBatch(response.body());
                        } else {
                            getView().onFailed(2,response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespBatchByInBox> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }
    /**
     * 创建工件(投料)
     *
     * @param ProductBatchId
     * @param ProductCode
     */
      public void createProduction(String ProductBatchId, String ProductCode) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).createProduction(ProductBatchId, ProductCode), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().touliaoSucceed("创建成功");
                        } else {
                            getView().touliaoFailed(response.body().getErrorMsg());
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

   /* public void getBySendMaterial() {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).getBySendMaterial(), new Callback<RespSendMatrialList>() {
                @Override
                public void onResponse(Call<RespSendMatrialList> call, Response<RespSendMatrialList> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().touliaoSucceed("创建成功");
                        } else {
                            getView().touliaoFailed(response.body().getErrorMsg());
//                            mView.viewUpdateFailed(response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespSendMatrialList> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }*/

    /**
     * 提交工件(进入下一工序)
     *
     * @param id
     * @param operationType
     * @param remark
     * @param selfCheckResult
     */
    public void machiningParts_next(String id,  String operationType, String remark,String selfCheckResult,boolean IsCheckProcedure) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).machiningParts_next(id, operationType,remark,selfCheckResult,IsCheckProcedure), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {

                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));

                    } else {
                        Debug.info(TAG,response.body().toString());
                        if (response.body().isSuccess()&&response.body().getErrorCode().equals("1")) {
                            //提交成功，需要更换刀具
                            getView().onNextSucceed("提交工件成功");
                            getView().onChangeTool("提交工件成功，刀具已达到使用次数上限，请更换刀具！");
                        } else if(!response.body().isSuccess()&&response.body().getErrorCode().equals("-1")){
                            //提交失败需要自检
                            Debug.info("提交工件失败，工件需要自检！");
                            getView().viewError(TempErrorCode.ERROR_FAILED,"提交刀具失败，工件需要自检！");
                        }else if (response.body().isSuccess()&&!response.body().getErrorCode().equals("-1")&&!response.body().getErrorCode().equals("1")){
                                getView().onNextSucceed("提交工件成功");
                            getView().viewMsg("提交工件成功！");

                        }else{
                            Debug.info(TAG,"提交工件失败！");
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
     * 修改批次状态
     *
     * @param status
     * @param lineId
     */
   /* public void getProductionByProcedure(String status, String lineId) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).getProductionByProcedure(status, lineId), new Callback<RespProductionList>() {
                @Override
                public void onResponse(Call<RespProductionList> call, Response<RespProductionList> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
//                            mView.viewUpdateSucceed("修改成功");
                        } else {
//                            mView.viewUpdateFailed(response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespProductionList> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }*/

    /**
     * 获取未装箱的产品列表
     *
     * @param boxId
     */
    public void getProductList(String boxId) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).getProductList(boxId,"30"), new Callback<RespProductList>() {
                @Override
                public void onResponse(Call<RespProductList> call, Response<RespProductList> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onProductListSucceed(response.body());
//                            getView().touliaoSucceed("创建成功");
                        } else {
                            getView().viewError(TempErrorCode.ERROR_FAILED,response.body().getErrorMsg());
//                            getView().touliaoFailed(response.body().getErrorMsg());
//                            mView.viewUpdateFailed(response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespProductList> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }

    /**
     * 装箱
     *
     * @param boxId
     */
    public void requestInBox(String boxId,String[] ids) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).requestInBox(boxId,ids), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onInBoxSucceed("装箱成功！");
//                            getView().touliaoSucceed("创建成功");
                        } else {
                            getView().viewError(TempErrorCode.ERROR_FAILED,response.body().getErrorMsg());
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

    /**
     *创建箱子
     *
     * @param name
     * @param productBatchId
     * @param Size
     */
    public void requestcCreateProductBox(String name, String productBatchId,String Size) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).requestcCreateProductBox(name,productBatchId,Size), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onCreateProductSucceed("创建箱子成功！");
//                            getView().touliaoSucceed("创建成功");
                        } else {
                            getView().viewError(TempErrorCode.ERROR_FAILED,response.body().getErrorMsg());
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
    /**
     *更新产品箱状态
     *
     * @param id
     * @param status
     */
    public void upDateProductBox(String id ,String status) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).upDateProductBox(id,status), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onUpdateStatusSucceed("完成装箱成功！");
//                            getView().touliaoSucceed("创建成功");
                        } else {
                            getView().viewError(TempErrorCode.ERROR_FAILED,response.body().getErrorMsg());
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
    /**
     *查看产品箱
     *
     * @param id
     */
    public void requestProductBox(String id ) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).requestProductBox(id), new Callback<RespInBoxDetail>() {
                @Override
                public void onResponse(Call<RespInBoxDetail> call, Response<RespInBoxDetail> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().onInBoxDetailSucceed(response.body());
                        } else {
                            getView().viewError(TempErrorCode.ERROR_FAILED,response.body().getErrorMsg());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RespInBoxDetail> call, Throwable t) {
                    superOnFailure(call, t);
                }
            });
        }
    }

    /**
     *加工异常报警
     *
     * @param procedureId
     * @param description
     */
    public void workingTimeoutProblemCreate(int procedureId,  String description) {
        if (AppConfig.APP_DEBUG) {

        } else if (superCheckForValidity()) {
            getView().viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).workingTimeoutProblemCreate(procedureId,description), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    getView().viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body() == null) {
                        getView().viewError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else {
                        if (response.body().isSuccess()) {
                            getView().viewMsg("提交报警成功！");
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
