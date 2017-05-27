package com.qdxy.app.lhjh.activities.count;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.lf.tempcore.tempActivity.TempActivity;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullablePreDefault;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullableViewI;
import com.lf.tempcore.tempModule.tempRemotComm.TempRemotAPIConnecter;
import com.lf.tempcore.tempModule.tempUtils.TempDensityUtil;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVCommonAdapter;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVDividerDecoration;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVHolder;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRefreshRecyclerView;
import com.qdxy.app.lhjh.MyApplication;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.activities.arrangeProcedure.ActArrangeProList;
import com.qdxy.app.lhjh.activities.arrangeProcedure.RespArrangeProList;
import com.qdxy.app.lhjh.api.TempAPI;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;

//统计里面  管理的人
public class ProductionPeopleActivity extends TempActivity {

    @Bind(R.id.toolbar_top)
    Toolbar toolbarTop;
    @Bind(R.id.act_arrange_pro_RecyclerView)
    TempRefreshRecyclerView refreshRecyclerView;


    private TempPullablePreDefault<RespCountList> pullableDefault;
    private TempPullableViewI<RespCountList> pullableViewI;
    private TempRVCommonAdapter<RespCountList.DataBean.DatasBean> arrangeAdapter;
    private PreCount preCount;
    private ViewCountI viewCountI;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_production_people);
        initToolbar(toolbarTop, "分管人员");
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void setListeners() {
            viewCountI = new ViewCountI() {
                @Override
                public void onMessageSuccessed(String message) {
                    Toast.makeText(ProductionPeopleActivity.this, "获取数据成功", Toast.LENGTH_SHORT).show();
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

                }

                @Override
                public void viewError(TempErrorCode code, String message) {
                    superViewError(code,message);
                }
            };
        preCount = new PreCount(viewCountI);
        pullableViewI = new TempPullableViewI<RespCountList>() {
            @Override
            public void onInit(RespCountList data) {

            }

            @Override
            public void onRefresh(RespCountList data) {
                arrangeAdapter.updateRefresh(data.getData().getDatas());
            }

            @Override
            public void onLoadmore(RespCountList data) {
                // 默认全部展开
                arrangeAdapter.updateLoadMore(data.getData().getDatas());
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
        pullableDefault = new TempPullablePreDefault<RespCountList>(pullableViewI) {
            @Override
            public Call<RespCountList> createObservable(int queryPage, int querysize, int currentPage) {
                return TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).getListManager(queryPage);
            }
        };
    }

    @Override
    protected void bindValues() {
        initAdapter();
        pullableDefault.requestRefresh();
    }

    private void initAdapter() {
        refreshRecyclerView.setLayoutManager(new LinearLayoutManager(ProductionPeopleActivity.this));

        arrangeAdapter = new TempRVCommonAdapter<RespCountList.DataBean.DatasBean>(ProductionPeopleActivity.this, R.layout.item_production_people) {
            @Override
            public void bindItemValues(TempRVHolder holder, final RespCountList.DataBean.DatasBean datasBean) {
                holder.setText(R.id.name,datasBean.getName());


                holder.setOnClickListener(R.id.item_production_people_layout, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent =new Intent(ProductionPeopleActivity.this,ProductionCountActivity.class);
                        intent.putExtra("id",datasBean.getId());
                        intent.putExtra("name",datasBean.getName());
                        startActivity(intent);
                    }
                });
            }
        };
        arrangeAdapter.setMore(new TempRVCommonAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pullableDefault.requestLoadMore();
            }
        });
        refreshRecyclerView.setRefreshListener(new TempRefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullableDefault.requestRefresh();
            }
        });
        refreshRecyclerView.addItemDecoration(new TempRVDividerDecoration(Color.parseColor("#ECECEC"), TempDensityUtil.dip2px(ProductionPeopleActivity.this,16f)));
        refreshRecyclerView.setAdapter(arrangeAdapter);
    }

    @Override
    protected void OnViewClicked(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
