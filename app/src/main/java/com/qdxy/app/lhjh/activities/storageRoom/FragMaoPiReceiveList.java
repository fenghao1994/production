package com.qdxy.app.lhjh.activities.storageRoom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempFragment.TempFragment;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullablePreDefault;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullableViewI;
import com.lf.tempcore.tempModule.tempRemotComm.TempRemotAPIConnecter;
import com.lf.tempcore.tempViews.tempRecyclerView.OnItemClickListener;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVCommonAdapter;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRefreshRecyclerView;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.adapters.AdapterCommmonList;
import com.qdxy.app.lhjh.api.TempAPI;
import com.qdxy.app.lhjh.emues.CommonType;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by mac on 2017/2/21.
 */

public class FragMaoPiReceiveList extends TempFragment{
    private final String TAG = "FragTempList";
    private boolean mIsHandle;
    private CommonType mType;
    private TempPullablePreDefault<RespStorageList> mPrePullable;
    private AdapterCommmonList mAdapter;
    @Bind(R.id.parent_view)
    LinearLayout parentView;
    @Bind(R.id.temp_refresh_recyclerView)
    TempRefreshRecyclerView mRV;
    @Bind(R.id.fab)
    FloatingActionButton mFab;//添加floatActionBtn
    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_exception_list_layout,null);

    }

    @Override
    protected void setListeners(View view, @Nullable Bundle savedInstanceState) {
        mPrePullable = new TempPullablePreDefault<RespStorageList>(new TempPullableViewI<RespStorageList>() {
            @Override
            public void onInit(RespStorageList data) {

            }

            @Override
            public void onRefresh(RespStorageList data) {
                mAdapter.updateRefresh(data.getData().getDatas());
            }

            @Override
            public void onLoadmore(RespStorageList data) {
                mAdapter.updateLoadMore(data.getData().getDatas());
            }

            @Override
            public void refreshStatus(boolean succeed) {
                mRV.setRefreshing(false);
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
                superViewError(parentView,code,message);
            }
        }) {
            @Override
            public Call<RespStorageList> createObservable(int queryPage, int querysize, int currentPage) {
                int tempType=0;
                switch (mType){
                    case MAO_PI_LY:
                        Debug.info(TAG,"type类型为声请人查看毛坯领用列表");
                        tempType=0;
                        break;
                    case MAO_PI_STORAGE_LY:
                        Debug.info(TAG,"ype类型为库房查看毛坯领用列表");
                        tempType=1;
                        break;

                }
                return TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).orderList(tempType,mIsHandle,queryPage);
            }
        };

    }

    @Override
    protected void bundleValues(View view, @Nullable Bundle savedInstanceState) {
        mIsHandle = getArguments().getBoolean("isHandle");
        mType = (CommonType) getArguments().getSerializable("type");

        if (null!=mType){
            switch (mType){
                case MAO_PI_LY:
                    mFab.setVisibility(View.VISIBLE);
                    break;
                default:
                    mFab.setVisibility(View.GONE);
                    break;
            }
            initAdapter(mType);
            mPrePullable.requestRefresh();
        }else{
            showSnackBar(parentView,"没有获取到类型数据",2, Snackbar.LENGTH_LONG);
        }
    }

    @OnClick(R.id.fab)
    @Override
    protected void OnViewClicked(View v) {
        switch (v.getId()) {
            case R.id.fab:
                if (mType==CommonType.MAO_PI_LY){
                    Intent tempIntent = new Intent(getActivity(), ActMaoPiReceiveCreate.class);
                    startActivityForResult(tempIntent,80);
                }
                break;
        }
    }

    protected void initAdapter(CommonType type){
        mRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRV.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new AdapterCommmonList(getActivity(), type);

        mAdapter.setMore(new TempRVCommonAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPrePullable.requestLoadMore();
            }
        });
        mRV.setRefreshListener(new TempRefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPrePullable.requestRefresh();
            }
        });
        mAdapter.setOnItemClickListener(new OnItemClickListener<RespStorageList.DataBean.DatasBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, RespStorageList.DataBean.DatasBean data, int position) {
                Intent  tempIntent = new Intent(getActivity(),ActStorageDetail.class);
                tempIntent.putExtra("type",mType);
                tempIntent.putExtra("id",data.getId());
                startActivityForResult(tempIntent,81);
//                switch (mType){
//
//                    case MAO_PI_STORAGE_SQ:
//                        Debug.info(TAG,"type类型为毛坯入库申请");
//
//                        break;
//                    case MAO_PI_STORAGE_CJ:
//                        Debug.info(TAG,"type类型为毛坯入库抽检");
//                        break;
//                    case MAO_PI_STORAGE_SP:
//                        Debug.info(TAG,"type类型为毛坯入库审批");
//                        break;
//                }
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, RespStorageList.DataBean.DatasBean o, int position) {
                return false;
            }
        });
        mRV.setAdapter(mAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Debug.info(TAG,"进入fragment回掉");
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==200){
            showSnackBar(parentView,data!=null?data.getStringExtra("result"):"提交成功",0,Snackbar.LENGTH_LONG);
            mPrePullable.requestRefresh();
        }
    }
}
