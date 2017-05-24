package com.qdxy.app.lhjh.activities.selectors;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.lf.tempcore.tempActivity.TempActivity;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullablePreDefault;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullableViewI;
import com.lf.tempcore.tempModule.tempRemotComm.TempRemotAPIConnecter;
import com.lf.tempcore.tempModule.tempUtils.TempDensityUtil;
import com.lf.tempcore.tempViews.tempRecyclerView.OnItemClickListener;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVCommonAdapter;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVDividerDecoration;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVHolder;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRefreshRecyclerView;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.api.TempAPI;
import com.qdxy.app.lhjh.bean.RespSelector;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * 人员选择
 * Created by KY on 2016/12/2.
 */

public class ActSelector extends TempActivity {
    private final String TAG = "ActSelector";
    TempPullablePreDefault<RespSelector> pullablePreDefault;
    TempPullableViewI<RespSelector> pullableViewI;
    private TempRVCommonAdapter<RespSelector.DataBean.DatasBean> adapter;
    @Bind(R.id.temp_refresh_recyclerView)    TempRefreshRecyclerView recyclerView;
    @Bind(R.id.act_selector_search_editText)    EditText act_selector_search_editText;
    @Bind(R.id.act_selector_search_frame)    LinearLayout act_selector_search_frame;
    private int selectedType;//0-选择人员 1-选择设备 2-工件选择
    private int deviceId;//刀具列表使用
    private int lineId;//刀具列表使用

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_selector_layout);
        selectedType =getIntent().getIntExtra("type",0);
        deviceId=getIntent().getIntExtra("deviceId",-1);
        lineId =getIntent().getIntExtra("lineId",-1);
    }

    @Override
    protected void findViews() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        switch (selectedType){
            case 1:
                initToolbar(toolbarTop, "设备列表");
                break;
            case 2:
                initToolbar(toolbarTop, "工件列表");
                break;
            case 3:
                act_selector_search_frame.setVisibility(View.GONE);
                initToolbar(toolbarTop, "刀具列表");
                break;
            default:
                initToolbar(toolbarTop, "人员列表");
                break;
        }

        pullableViewI = new TempPullableViewI<RespSelector>() {
            @Override
            public void onInit(RespSelector data) {

            }

            @Override
            public void onRefresh(RespSelector data) {
                adapter.updateRefresh(data.getData().getDatas());
            }

            @Override
            public void onLoadmore(RespSelector data) {
                adapter.updateLoadMore(data.getData().getDatas());
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
        pullablePreDefault = new TempPullablePreDefault<RespSelector>(pullableViewI) {
            @Override
            public Call<RespSelector> createObservable(int queryPage, int querysize, int currentPage) {
                switch (selectedType){
                    case 1:
                        return TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).userprocedures(lineId,act_selector_search_editText.getText().toString().trim(),queryPage+"","20");
                    case 2:
                        return TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).requestGongjianList(lineId,act_selector_search_editText.getText().toString().trim(),queryPage+"","20");
                    case 3:
                        return TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).deviceToolList(deviceId,queryPage+"","20");

                }
                return TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).userstarch(lineId,act_selector_search_editText.getText().toString().trim(),queryPage+"","20");
            }
        };
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void bindValues() {
        initListData(recyclerView);
        pullablePreDefault.requestRefresh();
    }
    @OnClick({R.id.act_selector_search_btn})
    @Override
    protected void OnViewClicked(View v) {
        switch (v.getId()){
            case R.id.act_selector_search_btn:
//                    showSnackBar("请输入搜索内容",2, Snackbar.LENGTH_LONG);
                pullablePreDefault.requestRefresh();
                break;
        }

    }

    private void initListData(TempRefreshRecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(getTempContext()));
        rv.addItemDecoration(new TempRVDividerDecoration(Color.parseColor("#EEEFF3"), TempDensityUtil.dip2px(getTempContext(), 1.0f)));
        if (adapter == null) {
            adapter = new TempRVCommonAdapter<RespSelector.DataBean.DatasBean>(getTempContext(), R.layout.item_gongjian_selector_layout) {
                @Override
                public void bindItemValues(TempRVHolder holder, RespSelector.DataBean.DatasBean dataBean) {
                    if (selectedType==3){
                        holder.setText(R.id.item_gongjian_right_text,"寿命："+dataBean.getCurrentDurability()+"/"+dataBean.getMaxDurability());
                        holder.setVisible(R.id.item_gongjian_right_text,true);
                        holder.setText(R.id.item_gongjian_radioButton, dataBean.getDeviceToolTypeName());
                    }else{
                        holder.setVisible(R.id.item_gongjian_right_text,false);
                        holder.setText(R.id.item_gongjian_radioButton, dataBean.getName());
                    }

//                    holder.setText(R.id.item_check_pici_num, "待抽检数：" + dataBean.getWaitCheckCount());
//                    holder.setText(R.id.item_check_pici_percent, "抽检比列：" + new DecimalFormat("0%").format(dataBean.getCheckPercentage()));
//                    holder.setText(R.id.item_check_pici_progress, "抽检进度：" + dataBean.getCheckCount() + "/" + dataBean.getNeedCheckCount());
                }
            };
            adapter.setOnItemClickListener(new OnItemClickListener<RespSelector.DataBean.DatasBean>() {
                @Override
                public void onItemClick(ViewGroup parent, View view, RespSelector.DataBean.DatasBean o, int position) {
                        Intent intent = getIntent();
                    intent.putExtra("id",o.getId());
                    intent.putExtra("name",o.getName());
                    intent.putExtra("deviceToolTypeName",o.getDeviceToolTypeName());
                    setResult(200,intent);
                    finish();
                }

                @Override
                public boolean onItemLongClick(ViewGroup parent, View view, RespSelector.DataBean.DatasBean o, int position) {
                    return false;
                }
            });
            adapter.setMore(new TempRVCommonAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    pullablePreDefault.requestLoadMore();
                }
            });
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
