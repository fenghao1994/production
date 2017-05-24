package com.lf.tempcore.tempModule.tempMVPCommI;

import com.google.gson.JsonSyntaxException;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.lf.tempcore.tempResponse.TempResponse;

import java.net.SocketTimeoutException;

import retrofit2.Call;

/**
 * Created by KY on 2016/11/24.
 */

public abstract class TempPresenterDefault<V extends TempViewI> {

    private V view;

    public TempPresenterDefault(V v) {
        view = v;
    }

    public void superOnFailure(Call<? extends TempResponse> call, Throwable t) {
        view.viewDismissProgress();
        if (t instanceof IllegalStateException || t instanceof JsonSyntaxException) {
            view.viewError(TempErrorCode.PARSE_ERROR, "数据解析出错");
        } else if (t instanceof SocketTimeoutException) {
            view.viewError(TempErrorCode.TIME_OUT, "");
        } else {
            view.viewError(TempErrorCode.ERROR_FAILED, "");
        }
        t.printStackTrace();
    }
    public boolean superCheckForValidity(){
        if (getView() == null) {
            Debug.info("view is null");
            return false;
        } else if (getView().checkNetWork() == TempNetType.NET_DISABLED) {
            getView().viewError(TempErrorCode.ERROR_NET_DISCONTECTED, "");
            return false;
        }
        return true;
    }
    public V getView() {
        return view;
    }

    public void setView(V view) {
        this.view = view;
    }
    /* @Override
    public void onFailure(Call<TempResponse> call, Throwable t) {
        V.viewDismissProgress();
        if (t instanceof IllegalStateException || t instanceof JsonSyntaxException) {
            V.viewError(TempErrorCode.PARSE_ERROR, "数据解析出错");
        } else if (t instanceof SocketTimeoutException) {
            V.viewError(TempErrorCode.TIME_OUT, "");
        } else {
            V.viewError(TempErrorCode.ERROR_FAILED, "");
        }


        t.printStackTrace();

    }*/
}
