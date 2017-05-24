package com.qdxy.app.lhjh.activities.storageRoom;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.lf.tempcore.tempActivity.TempActivity;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullablePreDefault;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullableViewI;
import com.lf.tempcore.tempModule.tempRemotComm.TempRemotAPIConnecter;
import com.lf.tempcore.tempViews.tempRecyclerView.OnItemClickListener;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVCommonAdapter;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVHolder;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRefreshRecyclerView;
import com.qdxy.app.lhjh.MyApplication;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.api.TempAPI;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * 产品入库申请列表
 * Created by mac on 2017/2/21.
 */

public class ActApplyForPrdStorage extends TempActivity {

    @Bind(R.id.prd_inbox_commit_btn)
    AppCompatButton prd_inbox_commit_btn;
    @Bind(R.id.act_apply_prd_receiverView)
    TempRefreshRecyclerView mRV;
    private List<RespStorageList.DataBean.DatasBean> mListPrd;
    private TempRVCommonAdapter<RespStorageList.DataBean.DatasBean> mAdapter;
    private final String TAG = "ActApplyForPrdStorage";
    private PreStorageRoom mPreStorageRoom;
    private TempPullablePreDefault<RespStorageList> mPrePullable;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_apply_for_prd_layout);
    }

    @Override
    protected void findViews() {
        initToolbar("产品入库申请列表");
    }

    @Override
    protected void setListeners() {
        mPrePullable = new TempPullablePreDefault<RespStorageList>(new TempPullableViewI<RespStorageList>() {
            @Override
            public void onInit(RespStorageList data) {

            }

            @Override
            public void onRefresh(RespStorageList data) {
//                mListPrd = data.getData().getDatas();

                mAdapter.updateRefresh(data.getData().getDatas());
                mListPrd = mAdapter.getData();
                if (mListPrd.isEmpty()) {
                    //隐藏提交按钮
                    prd_inbox_commit_btn.setVisibility(View.GONE);
                } else {
                    prd_inbox_commit_btn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLoadmore(RespStorageList data) {

//                mListPrd.addAll(data.getData().getDatas());

                mAdapter.updateLoadMore(data.getData().getDatas());
                mListPrd = mAdapter.getData();
                if (mListPrd.isEmpty()) {
                    //隐藏提交按钮
                    prd_inbox_commit_btn.setVisibility(View.GONE);
                } else {
                    prd_inbox_commit_btn.setVisibility(View.VISIBLE);
                }
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
                superViewError(code, message);
            }
        }) {
            @Override
            public Call<RespStorageList> createObservable(int queryPage, int querysize, int currentPage) {
                return TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).productBoxList(true, 2, queryPage);
            }
        };

        mPreStorageRoom = new PreStorageRoom(new ViewStorageRoom() {
            @Override
            public <RESULT> void onSucceed(RESULT result, int type) {
                showSnackBar("提交成功", 0, Snackbar.LENGTH_LONG);
                mPrePullable.requestRefresh();
            }

            @Override
            public TempNetType checkNetWork() {
                return MyApplication.getInstance().getNetType();
            }

            @Override
            public void viewProgress() {
                superViewProgress();
            }

            @Override
            public void viewDismissProgress() {
                superViewDismissProgress();
            }

            @Override
            public void viewMsg(String message) {
                superViewMessage(message);
            }

            @Override
            public void viewError(TempErrorCode code, String message) {
                superViewError(code, message);
            }
        });
    }

    @Override
    protected void bindValues() {
        mListPrd = new ArrayList<>();
        mRV.setLayoutManager(new LinearLayoutManager(getTempContext()));
        mRV.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new TempRVCommonAdapter<RespStorageList.DataBean.DatasBean>(getTempContext(), R.layout.item_apply_for_prd_layout) {
            @Override
            public void bindItemValues(TempRVHolder holder, RespStorageList.DataBean.DatasBean o) {
                holder.setImageResource(R.id.item_common_list_image, o.isSeleted() ? R.mipmap.icon_checked_l : R.mipmap.icon_unchecked_l);
                holder.setText(R.id.item_common_list_title, o.getName());
                holder.setText(R.id.item_common_list_time, o.getCreationTime());
                holder.setText(R.id.item_common_list_text0, "批次：" + o.getBatchCode());
                holder.setText(R.id.item_common_list_text1, "数量：" + o.getCount() + " / " + o.getSize());
            }


        };
        mAdapter.setOnItemClickListener(new OnItemClickListener<RespStorageList.DataBean.DatasBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, RespStorageList.DataBean.DatasBean o, int position) {
                mListPrd.get(position).setSeleted(!mListPrd.get(position).isSeleted());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, RespStorageList.DataBean.DatasBean o, int position) {
                return false;
            }
        });
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
        mRV.setAdapter(mAdapter);

        mPrePullable.requestRefresh();
    }

    @OnClick({R.id.prd_inbox_commit_btn})
    @Override
    protected void OnViewClicked(View v) {
        switch (v.getId()) {
            case R.id.prd_inbox_commit_btn:
                //提交入库申请
                List<String> ids = new ArrayList<>();
                for (int i = 0; i < mListPrd.size(); i++) {
                    if (mListPrd.get(i).isSeleted()) {
                        ids.add(mListPrd.get(i).getId() + "");
                    }
                }
                if (ids.isEmpty()) {
                    showSnackBar("请选择产品箱再提交", 2, Snackbar.LENGTH_LONG);
                } else {
                    mPreStorageRoom.applyCreate_productBox(ids.toArray(new String[ids.size()]));
                }
                break;
        }

    }
}
