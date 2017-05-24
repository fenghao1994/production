package com.qdxy.app.lhjh.activities.management;

import com.google.gson.JsonSyntaxException;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.lf.tempcore.tempModule.tempRemotComm.TempRemotAPIConnecter;
import com.lf.tempcore.tempResponse.TempResponse;
import com.qdxy.app.lhjh.api.TempAPI;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KY on 2016/11/22.
 */

public class PrePiCi {
    private final String TAG = "PrePiCi";
    private ViewPiCi mView;

    public PrePiCi(ViewPiCi View) {
        this.mView = View;
    }

    /**创建批次
     * @param productionLineId
     * @param productTypeId
     * @param batch
     */
    public void createProductionBatch(String productionLineId,String productTypeId,String batch){
        if (mView == null) {
            Debug.info("view is null");
        }else if(mView.checkNetWork() == TempNetType.NET_DISABLED){
            mView.viewError(TempErrorCode.ERROR_NET_DISCONTECTED, "");
        }else{
            Debug.info("发送请求");
            mView.viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).createProductionBatch(productionLineId,productTypeId,batch), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    mView.viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body( )== null) {
                        mView.viewError(TempErrorCode.ERROR_FAILED,TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else  {
                        if (response.body( ).isSuccess()){
                            mView.viewCreateSucceed("创建成功");
                        }else{
                            mView.viewCreateFailed(response.body( ).getErrorMsg());
                        }

                    }

                }

                @Override
                public void onFailure(Call<TempResponse> call, Throwable t) {
                    mView.viewDismissProgress();
                    if (t instanceof IllegalStateException||t instanceof JsonSyntaxException){
                        mView.viewError(TempErrorCode.PARSE_ERROR,"数据解析出错");
                    }else if (t instanceof SocketTimeoutException) {
                        mView.viewError(TempErrorCode.TIME_OUT, "");
                    } else {
                        mView.viewError(TempErrorCode.ERROR_FAILED, "");
                    }


                    t.printStackTrace();

                }
            });
        }
    }

    /**修改批次状态
     * @param status          0未启用,1启用，2停用
     * @param productionBatchId 批次id
     * @return
     */
    public void updateProductionBatchType(String status,String productionBatchId){
        if (mView == null) {
            Debug.info("view is null");
        }else if(mView.checkNetWork() == TempNetType.NET_DISABLED){
            mView.viewError(TempErrorCode.ERROR_NET_DISCONTECTED, "");
        }else{
            Debug.info("发送请求");
            mView.viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).updateProductionBatchType(status,productionBatchId), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    mView.viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body( )== null) {
                        mView.viewError(TempErrorCode.ERROR_FAILED,TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else  {
                        if (response.body( ).isSuccess()){
                            mView.viewUpdateSucceed("修改成功");
                        }else{
                            mView.viewUpdateFailed(response.body( ).getErrorMsg());
                        }

                    }

                }

                @Override
                public void onFailure(Call<TempResponse> call, Throwable t) {
                    mView.viewDismissProgress();
                    if (t instanceof IllegalStateException||t instanceof JsonSyntaxException){
                        mView.viewError(TempErrorCode.PARSE_ERROR,"数据解析出错");
                    }else if (t instanceof SocketTimeoutException) {
                        mView.viewError(TempErrorCode.TIME_OUT, "");
                    } else {
                        mView.viewError(TempErrorCode.ERROR_FAILED, "");
                    }


                    t.printStackTrace();

                }
            });
        }
    }
}
