package com.qdxy.app.lhjh.activities.exceptions;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
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
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVHolder;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRefreshRecyclerView;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.api.TempAPI;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import retrofit2.Call;

/**
 * Created by KY on 2016/12/9.
 */

public class FragExceptionJudgementList extends TempFragment {
    private final String TAG="FragExceptionJudgementList";
    @Bind(R.id.temp_refresh_recyclerView)    TempRefreshRecyclerView mTempRefreshRecyclerView;
    @Bind(R.id.parent_view)    LinearLayout parentView;
    private TempPullablePreDefault<RespExceptionJudgementList> mTempPullablePresenterI;
    private TempPullableViewI<RespExceptionJudgementList> mTempPullableViewI;
    private TempRVCommonAdapter<RespExceptionJudgementList.DataBean.DatasBean> mTempRVCommonAdapter;
    private boolean isJudge;
    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_exception_list_layout, null);
    }

    @Override
    protected void setListeners(View view, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(FragExceptionJudgementList.this);
        isJudge = getArguments().getBoolean("isJudge");
        mTempRVCommonAdapter = new TempRVCommonAdapter<RespExceptionJudgementList.DataBean.DatasBean>(getActivity(), R.layout.item_exception_judgement_layout) {
            @Override
            public void bindItemValues(TempRVHolder holder, RespExceptionJudgementList.DataBean.DatasBean s) {
                holder.setText(R.id.item_exception_judgement_creationTime, isJudge?s.getJudgeTime():s.getCreationTime());
                holder.setText(R.id.item_exception_judgement_creatorName, "创建人：" + (TextUtils.isEmpty(s.getSender())?"无":s.getSender()));
                holder.setText(R.id.item_exception_judgement_procedureName, "工序：" + s.getProcedureName());
                holder.setText(R.id.item_exception_judgement_productionLineName, "生产线：" + s.getProductionLineName());
            }
        };
        mTempPullableViewI = new TempPullableViewI<RespExceptionJudgementList>() {
            @Override
            public void onInit(RespExceptionJudgementList tempResponse) {

            }

            @Override
            public void onRefresh(RespExceptionJudgementList tempResponse) {
                mTempRVCommonAdapter.updateRefresh(tempResponse.getData().getDatas());
            }

            @Override
            public void onLoadmore(RespExceptionJudgementList tempResponse) {
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
                superViewError(parentView, code, message);
            }
        };
        mTempPullablePresenterI = new TempPullablePreDefault<RespExceptionJudgementList>(mTempPullableViewI) {
            @Override
            public Call<RespExceptionJudgementList> createObservable(int queryPage, int querysize, int currentPage) {
                return TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).requestProblemList(isJudge,queryPage);
            }
        };
    }

    @Override
    protected void bundleValues(View view, @Nullable Bundle savedInstanceState) {
        initReceiverview(mTempRefreshRecyclerView, mTempRVCommonAdapter);
      /*  List<String> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add("i" + i);
        }*/
//        mTempRVCommonAdapter.updateRefresh(data);
        mTempPullablePresenterI.requestRefresh();
    }

    @Override
    protected void OnViewClicked(View v) {

    }

    private void initReceiverview(TempRefreshRecyclerView recyclerview, TempRVCommonAdapter adapter) {
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(new OnItemClickListener<RespExceptionJudgementList.DataBean.DatasBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, RespExceptionJudgementList.DataBean.DatasBean o, int position) {
                Intent intent = new Intent(getActivity(), ActExceptionJudgement.class);
                intent.putExtra("isJudge",isJudge);
                intent.putExtra("id", o.getId());
                intent.putExtra("procedureName", o.getProcedureName());
                startActivityForResult(intent, 100);
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, RespExceptionJudgementList.DataBean.DatasBean o, int position) {
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
//        recyclerview.addItemDecoration(new TempRVDividerDecoration(Color.parseColor("#EEEFF3"), TempDensityUtil.dip2px(ActExceptionJudgementList.this, 12.0f)));

    }
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==100&&resultCode==200){
            superViewMessage("提交成功！");
            mTempPullablePresenterI.requestRefresh();
        }
        super.onActivityResult(requestCode, resultCode, data);

    }*/
    public void onEventMainThread(String event) {
        Debug.info(TAG, "event 接受到数据" + event);
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
        EventBus.getDefault().unregister(FragExceptionJudgementList.this);
        super.onDestroy();
    }
}
