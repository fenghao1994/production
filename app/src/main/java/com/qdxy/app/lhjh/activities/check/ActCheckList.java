package com.qdxy.app.lhjh.activities.check;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
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

import java.text.DecimalFormat;

import butterknife.Bind;
import retrofit2.Call;

/**
 * 产品抽检批次列表
 * Created by KY on 2016/11/30.
 */

public class ActCheckList extends TempActivity {
    TempPullablePreDefault<RespCheckPiCiList> pullablePreDefault;
    TempPullableViewI<RespCheckPiCiList> pullableViewI;
    private TempRVCommonAdapter<RespCheckPiCiList.DataBean> adapter;
    @Bind(R.id.temp_refresh_recyclerView)
    TempRefreshRecyclerView recyclerView;

    //    @Bind(R.id.parent_view)    CoordinatorLayout parentView;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_exception_list_layout);
    }

    @Override
    protected void findViews() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        initToolbar(toolbarTop, "产品抽检");


    }

    @Override
    protected void setListeners() {
        pullableViewI = new TempPullableViewI<RespCheckPiCiList>() {
            @Override
            public void onInit(RespCheckPiCiList respCheckPiCiList) {

            }

            @Override
            public void onRefresh(RespCheckPiCiList respCheckPiCiList) {
                adapter.updateRefresh(respCheckPiCiList.getData());
            }

            @Override
            public void onLoadmore(RespCheckPiCiList respCheckPiCiList) {
                adapter.updateLoadMore(respCheckPiCiList.getData());
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
                superViewError(code, message);
            }
        };
        pullablePreDefault = new TempPullablePreDefault<RespCheckPiCiList>(pullableViewI) {
            @Override
            public Call<RespCheckPiCiList> createObservable(int queryPage, int querysize, int currentPage) {
                return TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).requestRandomCheckList();
            }
        };
    }

    @Override
    protected void bindValues() {
        initListData(recyclerView);
        pullablePreDefault.requestRefresh();
    }

    @Override
    protected void OnViewClicked(View v) {

    }

    private void initListData(TempRefreshRecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(ActCheckList.this));
        if (adapter == null) {
            adapter = new TempRVCommonAdapter<RespCheckPiCiList.DataBean>(ActCheckList.this, R.layout.item_check_pici_list_layout) {
                @Override
                public void bindItemValues(TempRVHolder holder, RespCheckPiCiList.DataBean dataBean) {
                    holder.setText(R.id.item_check_pici_code, dataBean.getProductionBatchCode());
                    holder.setText(R.id.item_check_pici_num, "待抽检数：" + dataBean.getWaitCheckCount());
                    holder.setText(R.id.item_check_pici_percent, "抽检比列：" + new DecimalFormat("0%").format(dataBean.getCheckPercentage()));
                    holder.setText(R.id.item_check_pici_progress, "抽检进度：" + dataBean.getCheckCount() + "/" + dataBean.getNeedCheckCount());
                }
            };
            adapter.setOnItemClickListener(new OnItemClickListener<RespCheckPiCiList.DataBean>() {
                @Override
                public void onItemClick(ViewGroup parent, View view, RespCheckPiCiList.DataBean o, int position) {
                    Intent intent = new Intent(ActCheckList.this, ActCheckPrdList.class);
                    intent.putExtra("batchCode", o.getProductionBatchCode());
                    startActivity(intent);
                }

                @Override
                public boolean onItemLongClick(ViewGroup parent, View view, RespCheckPiCiList.DataBean o, int position) {
                    return false;
                }
            });
          /*  adapter.setMore(new TempRVCommonAdapter.OnLoadMoreListener()0 {
                @Override
                public void onLoadMore() {
                    pullablePreDefault.requestLoadMore();
                }
            });*/
        }

        rv.setRefreshListener(new TempRefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullablePreDefault.requestRefresh();
            }
        });
        rv.setAdapter(adapter);
    }
}
