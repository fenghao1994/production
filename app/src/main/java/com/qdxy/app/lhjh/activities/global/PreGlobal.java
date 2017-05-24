package com.qdxy.app.lhjh.activities.global;

import com.google.gson.JsonSyntaxException;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.lf.tempcore.tempModule.tempRemotComm.TempRemotAPIConnecter;
import com.lf.tempcore.tempResponse.TempResponse;
import com.qdxy.app.lhjh.api.TempAPI;
import com.qdxy.app.lhjh.bean.RespProductLines;
import com.qdxy.app.lhjh.bean.RespProductModel;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**通用Presenter
 * Created by KY on 2016/11/23.
 */

public class PreGlobal {
    private ViewGlobal mView;

    public PreGlobal(ViewGlobal v) {
        this.mView = v;
    }

    /**获取生产线
     */
    public void requestProductedLines(){
        if (mView == null) {
            Debug.info("view is null");
        }else if(mView.checkNetWork() == TempNetType.NET_DISABLED){
            mView.viewError(TempErrorCode.ERROR_NET_DISCONTECTED, "");
        }else{
            Debug.info("发送请求");
            mView.viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).requestProductedLines(), new Callback<RespProductLines>() {
                @Override
                public void onResponse(Call<RespProductLines> call, Response<RespProductLines> response) {
                    mView.viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body( )== null) {
                        mView.viewError(TempErrorCode.ERROR_FAILED,TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else  {
                        if (response.body( ).isSuccess()){
                            mView.onGetProductionLinesSucceed(response.body());
                        }else{
                            mView.onGetProductionLinesFailed(TempErrorCode.ERROR_FAILED,response.body( ).getErrorMsg());
                        }

                    }

                }

                @Override
                public void onFailure(Call<RespProductLines> call, Throwable t) {
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

    /**获取所有生产线
     */
    public void requestProductedLinesAll(){
        if (mView == null) {
            Debug.info("view is null");
        }else if(mView.checkNetWork() == TempNetType.NET_DISABLED){
            mView.viewError(TempErrorCode.ERROR_NET_DISCONTECTED, "");
        }else{
            Debug.info("发送请求");
            mView.viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).requestProductedLinesAll(), new Callback<RespProductLines>() {
                @Override
                public void onResponse(Call<RespProductLines> call, Response<RespProductLines> response) {
                    mView.viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body( )== null) {
                        mView.viewError(TempErrorCode.ERROR_FAILED,TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else  {
                        if (response.body( ).isSuccess()){
                            mView.onGetProductionLinesSucceed(response.body());
                        }else{
                            mView.onGetProductionLinesFailed(TempErrorCode.ERROR_FAILED,response.body( ).getErrorMsg());
                        }

                    }

                }

                @Override
                public void onFailure(Call<RespProductLines> call, Throwable t) {
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

/**提交更换刀具
     */
    public void changeDeviceTool(String[] ids,boolean IsNormalChange){
        if (mView == null) {
            Debug.info("view is null");
        }else if(mView.checkNetWork() == TempNetType.NET_DISABLED){
            mView.viewError(TempErrorCode.ERROR_NET_DISCONTECTED, "");
        }else{
            Debug.info("发送请求");
            mView.viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).changeDeviceTool(ids,IsNormalChange), new Callback<TempResponse>() {
                @Override
                public void onResponse(Call<TempResponse> call, Response<TempResponse> response) {
                    mView.viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body( )== null) {
                        mView.viewError(TempErrorCode.ERROR_FAILED,TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else  {
                        if (response.body( ).isSuccess()){
                            mView.onGetProductTypeSucceed(null);
                            mView.viewMsg("提交成功");
//                            mView.onGetProductionLinesSucceed(response.body());
                        }else{
                            mView.viewError(TempErrorCode.ERROR_FAILED,response.body( ).getErrorMsg());
//                            mView.onGetProductionLinesFailed(TempErrorCode.ERROR_FAILED,response.body( ).getErrorMsg());
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


    /**获取产品型号
     */
    public void requestProductedModel(){
        if (mView == null) {
            Debug.info("view is null");
        }else if(mView.checkNetWork() == TempNetType.NET_DISABLED){
            mView.viewError(TempErrorCode.ERROR_NET_DISCONTECTED, "");
        }else{
            Debug.info("发送请求");
            mView.viewProgress();
            TempRemotAPIConnecter.INSTANCE.executeRemotAPI(TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).requestProductedModel(), new Callback<RespProductModel>() {
                @Override
                public void onResponse(Call<RespProductModel> call, Response<RespProductModel> response) {
                    mView.viewDismissProgress();
//                    TempResponse data = response.body();
                    if (response.body( )== null) {
                        mView.viewError(TempErrorCode.ERROR_FAILED,TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    } else  {
                        if (response.body( ).isSuccess()){
                            mView.onGetProductTypeSucceed(response.body());
                        }else{
                            mView.onGetProductTypeFailed(TempErrorCode.ERROR_FAILED,response.body( ).getErrorMsg());
                        }

                    }

                }

                @Override
                public void onFailure(Call<RespProductModel> call, Throwable t) {
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
