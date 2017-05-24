package com.qdxy.app.lhjh.activities.storageRoom;

import android.os.Bundle;

import com.lf.tempcore.tempActivity.TempActivity;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullablePreDefault;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullableViewI;
import com.lf.tempcore.tempModule.tempRemotComm.TempRemotAPIConnecter;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRefreshRecyclerView;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.activities.exceptions.RespExpList;
import com.qdxy.app.lhjh.api.TempAPI;

import butterknife.Bind;
import retrofit2.Call;

/**
 * Created by mac on 2017/2/9.
 */

public abstract class ActTempStorageRoom extends TempActivity{
    private TempPullablePreDefault<RespExpList> mPrePullable;
//    private AdapterCommmonList<RespExpList.DataBean.DatasBean> mAdapter;
    @Bind(R.id.temp_refresh_recyclerView)
     TempRefreshRecyclerView mRV;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_exception_list_layout);
    }


    @Override
    protected void setListeners() {
        mPrePullable = new TempPullablePreDefault<RespExpList>(new TempPullableViewI<RespExpList>() {
            @Override
            public void onInit(RespExpList data) {

            }

            @Override
            public void onRefresh(RespExpList data) {
//                mAdapter.updateRefresh(data.getData().getDatas());
            }

            @Override
            public void onLoadmore(RespExpList data) {
//                mAdapter.updateLoadMore(data.getData().getDatas());
            }

            @Override
            public void refreshStatus(boolean succeed) {

            }

            @Override
            public void loadMoreStatus(boolean succeed) {

            }

            @Override
            public void showPullableProgressDialog() {
                superViewProgress();
            }

            @Override
            public void dismissPullableProgressDialog() {
                superViewDismissProgress();
            }

            @Override
            public void onError(TempErrorCode code, String message) {
                superViewError(code,message);
            }
        }) {
            @Override
            public Call<RespExpList> createObservable(int queryPage, int querysize, int currentPage) {
                return TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).qualityCheckList(false,1,queryPage);
            }
        };


    }
//    protected void initAdapter(CommonType type){
//        mRV.setLayoutManager(new LinearLayoutManager(getTempContext()));
//        mRV.setItemAnimator(new DefaultItemAnimator());
//        mAdapter = new AdapterCommmonList(getTempContext(), type);
//
//        mAdapter.setMore(new TempRVCommonAdapter.OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                mPrePullable.requestLoadMore();
//            }
//        });
//        mRV.setRefreshListener(new TempRefreshRecyclerView.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                mPrePullable.requestRefresh();
//            }
//        });
//
//        mRV.setAdapter(mAdapter);
//    }
}
