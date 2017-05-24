package com.qdxy.app.lhjh.activities.storageRoom;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.lf.tempcore.tempActivity.TempActivity;
import com.lf.tempcore.tempEnum.TempErrorCode;
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
import retrofit2.Call;


/**
 * 产品入库审批
 * Created by mac on 2017/2/21.
 */

public class ActApprovePrdStorage extends TempActivity {

    @Bind(R.id.act_apply_prd_receiverView)
    TempRefreshRecyclerView mRV;

    private TempRVCommonAdapter<RespApprovePrdList.DataBean.DatasBean> mAdapter;
    private final String TAG = "ActApprovePrdStorage";
    private TempPullablePreDefault<RespApprovePrdList> mPrePullable;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_apply_for_prd_layout);
    }

    @Override
    protected void findViews() {
        initToolbar("产品入库审批列表");
    }

    @Override
    protected void setListeners() {
        mPrePullable = new TempPullablePreDefault<RespApprovePrdList>(new TempPullableViewI<RespApprovePrdList>() {
            @Override
            public void onInit(RespApprovePrdList data) {

            }

            @Override
            public void onRefresh(RespApprovePrdList data) {
                mAdapter.updateRefresh(data.getData().getDatas());

            }

            @Override
            public void onLoadmore(RespApprovePrdList data) {

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
                superViewError(code, message);
            }
        }) {
            @Override
            public Call<RespApprovePrdList> createObservable(int queryPage, int querysize, int currentPage) {
                return TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).requestProductApplyList(1, false, queryPage);
            }
        };
    }

    @Override
    protected void bindValues() {
        mRV.setLayoutManager(new LinearLayoutManager(getTempContext()));
        mRV.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new TempRVCommonAdapter<RespApprovePrdList.DataBean.DatasBean>(getTempContext(), R.layout.item_material_create_layout) {
            @Override
            public void bindItemValues(TempRVHolder holder, RespApprovePrdList.DataBean.DatasBean o) {
                holder.setImageResource(R.id.item_material_image_status, R.mipmap.icon_maopi_renyuan);
                holder.setText(R.id.item_material_text_name, "申请人：" + o.getApplicant());
                holder.setText(R.id.item_material_text_count, o.getTime());
                holder.setTextColor(R.id.item_material_text_count, Color.parseColor("#8b8b8b"));
//                holder.setText(R.id.item_common_list_text0,"批次：" + o.getBatchCode());
//                holder.setText(R.id.item_common_list_text1, "数量：" + o.getCount()+" / "+o.getSize());
            }


        };
        mAdapter.setOnItemClickListener(new OnItemClickListener<RespApprovePrdList.DataBean.DatasBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, RespApprovePrdList.DataBean.DatasBean o, int position) {
//                mListPrd.get(position).setSeleted(!mListPrd.get(position).isSeleted());
//                mAdapter.notifyDataSetChanged();
                Intent startDetail = new Intent(ActApprovePrdStorage.this,ActApplyPrdDetail.class);
                startDetail.putExtra("id",o.getId());
                startActivityForResult(startDetail,82);
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, RespApprovePrdList.DataBean.DatasBean o, int position) {
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

    @Override
    protected void OnViewClicked(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==200){
            showSnackBar("提交成功",0, Snackbar.LENGTH_LONG);
            mPrePullable.requestRefresh();
        }
    }
}
