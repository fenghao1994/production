package com.qdxy.app.lhjh.activities.exceptions;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempFragment.TempFragment;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullablePreDefault;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullableViewI;
import com.lf.tempcore.tempModule.tempRemotComm.TempRemotAPIConnecter;
import com.lf.tempcore.tempViews.tempRecyclerView.OnItemClickListener;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVCommonAdapter;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVHolder;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRefreshRecyclerView;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.api.TempAPI;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import retrofit2.Call;

/**刀具异常列表
 * Created by KY on 2016/11/9.
 */

public class FragExceptionDaoJuList extends TempFragment{
    @Bind(R.id.temp_refresh_recyclerView)  TempRefreshRecyclerView mTempRefreshRecyclerView;
    @Bind(R.id.parent_view)    LinearLayout parentView;
    private TempPullablePreDefault<RespExpList> mTempPullablePresenterI;
    private TempPullableViewI<RespExpList> mTempPullableViewI;
    private TempRVCommonAdapter<RespExpList.DataBean.DatasBean> mTempRVCommonAdapter;
    private boolean isJudge;
    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_exception_list_layout,null);
    }

    @Override
    protected void setListeners(View view, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(FragExceptionDaoJuList.this);
        isJudge = getArguments().getBoolean("isJudge");
        mTempRVCommonAdapter = new TempRVCommonAdapter<RespExpList.DataBean.DatasBean>(getActivity(), R.layout.item_exception_daoju_layout) {
            @Override
            public void bindItemValues(TempRVHolder holder, RespExpList.DataBean.DatasBean s) {
                holder.setText(R.id.item_exception_daoju_creationTime,isJudge?s.getHandleTime():s.getCreationTime());
                holder.setText(R.id.item_exception_daoju_index0,"发送人："+s.getSender());
                holder.setText(R.id.item_exception_daoju_index1,"工序："+s.getProcedureName());
                holder.setText(R.id.item_exception_daoju_index2,"刀具名："+s.getDeviceToolName());
                holder.setText(R.id.item_exception_daoju_index3,"生产线："+s.getProductionLineName());
            }
        };
        mTempPullableViewI = new TempPullableViewI<RespExpList>() {
            @Override
            public void onInit(RespExpList tempResponse) {

            }

            @Override
            public void onRefresh(RespExpList tempResponse) {
                mTempRVCommonAdapter.updateRefresh(tempResponse.getData().getDatas());
            }

            @Override
            public void onLoadmore(RespExpList tempResponse) {
                mTempRVCommonAdapter.updateLoadMore(tempResponse.getData().getDatas());
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
                superViewError(parentView,code,message);
            }

        };
        mTempPullablePresenterI = new TempPullablePreDefault<RespExpList>(mTempPullableViewI) {
            @Override
            public Call<RespExpList> createObservable(int queryPage, int querysize, int currentPage) {
                return TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).deviceToolProblemList(isJudge,queryPage);
            }
        };
    }

    @Override
    protected void bundleValues(View view, @Nullable Bundle savedInstanceState) {
        initReceiverview(mTempRefreshRecyclerView, mTempRVCommonAdapter);
//        List<String> data = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            data.add("i" + i);
//        }
//        mTempRVCommonAdapter.updateRefresh(data);
        mTempPullablePresenterI.requestRefresh();
    }

    /*@Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_exception_list_layout);
    }

    @Override
    protected void findViews() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        initToolbar(toolbarTop, "刀具异常列表");
    }*/
/*
    @Override
    protected void setListeners() {

    }*/

    /*@Override
    protected void bindValues() {

    }*/

    @Override
    protected void OnViewClicked(View v) {

    }
    private void initReceiverview(TempRefreshRecyclerView recyclerview, TempRVCommonAdapter adapter) {
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(new OnItemClickListener<RespExpList.DataBean.DatasBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, RespExpList.DataBean.DatasBean o, int position) {
                Intent dajuDealIntent  = new Intent(getActivity(),ActDaoJuDeal.class);
                dajuDealIntent.putExtra("id",o.getId());
                dajuDealIntent.putExtra("isJudge",isJudge);
                startActivity(dajuDealIntent);
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, RespExpList.DataBean.DatasBean o, int position) {
                return false;
            }
        });
        adapter.setMore(new TempRVCommonAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mTempPullablePresenterI.requestLoadMore();
            }
        });
        recyclerview.setRefreshListener(new TempRefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTempPullablePresenterI.requestRefresh();
            }
        });
        recyclerview.setAdapter(adapter);
//        recyclerview.addItemDecoration(new TempRVDividerDecoration(Color.parseColor("#EEEFF3"), TempDensityUtil.dip2px(ActExceptionDaoJuList.this, 12.0f)));
    }
    public void onEventMainThread(String event) {
//        Debug.info(TAG, "event 接受到数据" + event);
        if (event.equals("40")) {
            mTempPullablePresenterI.requestRefresh();
            if (getUserVisibleHint()){
                showSnackBar(parentView,"提交成功！",0, Snackbar.LENGTH_LONG);
            }
        }
       /* String msg = "onEventMainThread收到了消息：" + event.getMsg();
        Log.d("harvic", msg);
        tv.setText(msg);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();*/
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(FragExceptionDaoJuList.this);
        super.onDestroy();
    }
}
