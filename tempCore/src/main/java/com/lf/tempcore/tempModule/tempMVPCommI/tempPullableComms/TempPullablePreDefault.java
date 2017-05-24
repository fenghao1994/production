package com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms;

import com.google.gson.JsonSyntaxException;
import com.lf.tempcore.tempApplication.TempApplication;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.lf.tempcore.tempModule.tempRemotComm.TempRemotAPIConnecter;
import com.lf.tempcore.tempResponse.TempResponse;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by longf on 2016/5/6.
 */
public abstract class TempPullablePreDefault<RESPONSE extends TempResponse> implements TempPullablePresenterI {
    private int currentPage=0;
    private int size=10;
    private final int INIT =0,REFRESH=1,LOADMORE=2;
    private TempPullableViewI<RESPONSE> mView;
    public TempPullablePreDefault(TempPullableViewI v){
        this.mView =v;
    }
    @Override
    public void requestInit() {
        currentPage=0;
        queryData(INIT);
    }

    @Override
    public void requestRefresh() {
        currentPage=0;
        queryData(REFRESH);
    }

    @Override
    public void requestLoadMore() {
        queryData(LOADMORE);
    }

    private void queryData(final int status){
       if (!superCheckForValidity()){
           return;
       }
            mView.showPullableProgressDialog();
        TempRemotAPIConnecter.INSTANCE.executeRemotAPI(createObservable(getQueryPage(), getSize(), getCurrentPage()), new Callback<RESPONSE>() {
            @Override
            public void onResponse(Call<RESPONSE> call, Response<RESPONSE> response) {
                    mView.dismissPullableProgressDialog();
                    Debug.info("fanhui");
                if (response.body() == null) {
                    mView.onError(TempErrorCode.ERROR_FAILED, TempRemotAPIConnecter.INSTANCE.dealWithErrorMessage(response));
                    mView.refreshStatus(false);
                } else{
                    switch (status) {

                        case INIT:
                            currentPage++;
//                            setCurrentPage();
                            mView.onInit(response.body());
                            break;
                        case REFRESH:
                            currentPage++;
                            mView.refreshStatus(true);
                            mView.onRefresh(response.body());
                            break;
                        case LOADMORE:
                            currentPage++;
                            mView.loadMoreStatus(true);
                            mView.onLoadmore(response.body());
                            break;
                    }
                }


            }

            @Override
            public void onFailure(Call<RESPONSE> call, Throwable t) {
                t.printStackTrace();
                if (t instanceof IllegalStateException||t instanceof JsonSyntaxException){
                    mView.onError(TempErrorCode.PARSE_ERROR,"数据解析出错");
                }else  if (t instanceof SocketTimeoutException) {
                    mView.onError(TempErrorCode.TIME_OUT, "");
                } else {
                    mView.onError(TempErrorCode.ERROR_FAILED, "");
                }
                switch (status) {
                    case INIT:
                        break;
                    case REFRESH:
                        currentPage++;
                        mView.refreshStatus(false);
                        break;
                    case LOADMORE:
                        currentPage++;
                        mView.loadMoreStatus(false);
                        break;
                }

                    mView.dismissPullableProgressDialog();
            }
        } );
    }
    public boolean superCheckForValidity(){
        if (mView == null) {
            Debug.info("view is null");
            return false;
        } else if (TempApplication.getInstance().getNetType() == TempNetType.NET_DISABLED) {
            mView.onError(TempErrorCode.ERROR_NET_DISCONTECTED, "");
            return false;
        }
        return true;
    }
//    public abstract Observable<RESPONSE> createObservable(int queryPage, int querysize, int currentPage) ;
    public abstract Call<RESPONSE> createObservable(int queryPage, int querysize, int currentPage) ;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getQueryPage() {
        return currentPage+1;
    }
    public int getCurrentPage() {
        return this.currentPage ;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
