package com.qdxy.app.lhjh.activities.arrangeProcedure;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
import com.qdxy.app.lhjh.api.TempAPI;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;

/**安排工序列表
 * Created by KY on 2016/12/7.
 */

public class ActArrangeProList extends TempActivity {
    private final String TAG = "ActArrangeProList";
    @Bind(R.id.parent_view)    CoordinatorLayout parentView;
    @Bind(R.id.act_arrange_pro_RecyclerView)
    TempRefreshRecyclerView refreshRecyclerView;
    private TempPullablePreDefault<RespArrangeProList> pullableDefault;
    private TempPullableViewI<RespArrangeProList> pullableViewI;
    private TempRVCommonAdapter<RespArrangeProList.DataBean.DatasBean> arrangeAdapter;
    private PreArrange preArrange;
    private ViewArrangeI viewArrangeI;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_arrange_prd_list_layout);
    }

    @Override
    protected void findViews() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        initToolbar(toolbarTop, "工序安排");
    }

    @Override
    protected void setListeners() {
        viewArrangeI = new ViewArrangeI() {
            @Override
            public void onSetprocdurewithuserSucceed(String messsage) {
            }

            @Override
            public void onProcedureClearSucceed(String messsage) {
                superViewMessage(messsage);
                pullableDefault.requestRefresh();
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
                superViewError(code,message);
            }
        };
        preArrange = new PreArrange(viewArrangeI);
        pullableViewI = new TempPullableViewI<RespArrangeProList>() {
            @Override
            public void onInit(RespArrangeProList respProcedure) {

            }

            @Override
            public void onRefresh(RespArrangeProList respProcedure) {
                Debug.debug("加工数据刷新返回" + respProcedure.getData().getDatas().size());


                arrangeAdapter.updateRefresh(respProcedure.getData().getDatas());
            }

            @Override
            public void onLoadmore(RespArrangeProList respProcedure) {
                // 默认全部展开
                arrangeAdapter.updateLoadMore(respProcedure.getData().getDatas());
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
        pullableDefault = new TempPullablePreDefault<RespArrangeProList>(pullableViewI) {
            @Override
            public Call<RespArrangeProList> createObservable(int queryPage, int querysize, int currentPage) {
                return TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).procedurePageList(queryPage);
            }
        };
    }

    @Override
    protected void bindValues() {
        initAdapter();
        pullableDefault.requestRefresh();
    }
    @OnClick({R.id.act_arrange_pro_list__fab})
    @Override
    protected void OnViewClicked(View v) {
        switch (v.getId()){
            case R.id.act_arrange_pro_list__fab:
                //创建工序安排

                startActivityForResult(new Intent(ActArrangeProList.this,ActCreatePro.class),99);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==200){
            showSnackBar("设置成功！",0, Snackbar.LENGTH_LONG);
            pullableDefault.requestRefresh();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initAdapter() {
        refreshRecyclerView.setLayoutManager(new LinearLayoutManager(ActArrangeProList.this));
        arrangeAdapter = new TempRVCommonAdapter<RespArrangeProList.DataBean.DatasBean>(ActArrangeProList.this, R.layout.item_arrange_prd_layout) {
            @Override
            public void bindItemValues(TempRVHolder holder, final RespArrangeProList.DataBean.DatasBean datasBean) {
                holder.setText(R.id.item_arrange_prd_title_text,datasBean.getUserName());
                holder.setText(R.id.item_arrange_prd_content1_text,"生产线："+datasBean.getLineName());
                holder.setText(R.id.item_arrange_prd_content2_text,"工序："+datasBean.getProcedureName());
                holder.setOnClickListener(R.id.item_arrange_prd_index0_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //取消工序
                        preArrange.procedureClear(datasBean.getUserId());
                    }
                });
                holder.setOnClickListener(R.id.item_arrange_prd_index1_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //安排工序
                        Intent intent =new Intent(ActArrangeProList.this,ActCreatePro.class);

                        intent.putExtra("isEdit",true);
                        intent.putExtra("prdName",datasBean.getProcedureName());
                        intent.putExtra("lineName",datasBean.getLineName());
                        intent.putExtra("lineId",datasBean.getLineId());
                        intent.putExtra("userId",datasBean.getUserId());
                        intent.putExtra("userName",datasBean.getUserName());
                        startActivityForResult(intent ,98);
//                        startActivityForResult(new );
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
        refreshRecyclerView.addItemDecoration(new TempRVDividerDecoration(Color.parseColor("#ECECEC"), TempDensityUtil.dip2px(ActArrangeProList.this,16f)));
        refreshRecyclerView.setAdapter(arrangeAdapter);
//        mCustomExpandListview.setDividerHeight(0);
//        mCustomExpandListview.setAdapter(arrangeAdapter);
//        mCustomExpandListview.setHeaderView(ActArrangeProList.this.getLayoutInflater().inflate(
//                R.layout.item_jia_gong_group_layout, mCustomExpandListview, false));

       /* mCustomExpandListview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Toast.makeText(getActivity(), "点击了第" + (i + 1) + " 类的第" + i1 + "项", Toast.LENGTH_SHORT).show();
                return true;
            }
        });*/

    }
}
