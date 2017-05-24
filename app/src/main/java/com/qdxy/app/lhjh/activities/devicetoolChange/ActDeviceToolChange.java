package com.qdxy.app.lhjh.activities.deviceToolChange;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lf.tempcore.tempActivity.TempActivity;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullablePreDefault;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullableViewI;
import com.lf.tempcore.tempModule.tempRemotComm.TempRemotAPIConnecter;
import com.lf.tempcore.tempModule.tempUtils.TempDensityUtil;
import com.lf.tempcore.tempViews.tempRecyclerView.OnItemClickListener;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVCommonAdapter;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVDividerDecoration;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVHolder;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRefreshRecyclerView;
import com.qdxy.app.lhjh.MyApplication;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.activities.global.PreGlobal;
import com.qdxy.app.lhjh.activities.global.ViewGlobal;
import com.qdxy.app.lhjh.activities.selectors.ActSelector;
import com.qdxy.app.lhjh.api.TempAPI;
import com.qdxy.app.lhjh.bean.RespProductLines;
import com.qdxy.app.lhjh.bean.RespProductModel;
import com.qdxy.app.lhjh.bean.RespSelector;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;


/**
 * Created by KY on 2016/12/14.
 */

public class ActDeviceToolChange extends TempActivity {
    private final String TAG = "ActDeviceToolChange";
    private PreGlobal preGlobal;
    private int mDeviceId = -1, lineId;
    TempPullablePreDefault<RespSelector> pullablePreDefault;
    TempPullableViewI<RespSelector> pullableViewI;
    private TempRVCommonAdapter<RespSelector.DataBean.DatasBean> adapter;
    @Bind(R.id.temp_refresh_recyclerView)
    TempRefreshRecyclerView recyclerView;    @Bind(R.id.act_tool_select_text)
    TextView select_text;
    private List<String> deviceToolIds=new ArrayList<>();

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_tool_change_layout);
    }

    @Override
    protected void findViews() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        initToolbar(toolbarTop, "更换刀具");
    }

    @Override
    protected void setListeners() {
        preGlobal = new PreGlobal(new ViewGlobal() {
            @Override
            public void onGetProductionLinesSucceed(RespProductLines data) {
                Debug.info(TAG, "生产线数据返回");
                if (data.getData() == null || data.getData().isEmpty()) {
                    showSnackBar("没有获取到与你有关的生产线数据！", 2, Snackbar.LENGTH_LONG);
                } else {
                    lineId = data.getData().get(0).getId();
                }
            }

            @Override
            public void onGetProductionLinesFailed(TempErrorCode code, String message) {

            }

            @Override
            public void onGetProductTypeSucceed(RespProductModel data) {
                    pullablePreDefault.requestRefresh();
            }

            @Override
            public void onGetProductTypeFailed(TempErrorCode code, String message) {

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
                return TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).deviceToolList(mDeviceId,queryPage);
            }
        };
    }

    @Override
    protected void bindValues() {
        preGlobal.requestProductedLines();
        initListData(recyclerView);

    }

    @OnClick({R.id.act_tool_select_btn})
    @Override
    protected void OnViewClicked(View v) {
        switch (v.getId()) {
            case R.id.act_tool_select_btn:
                if (lineId==-1){
                    showSnackBar("没有获取到与你有关的生产线数据！",2,Snackbar.LENGTH_LONG);
                }else{
                    Intent intent = new Intent(ActDeviceToolChange.this, ActSelector.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("lineId", lineId);
                    startActivityForResult(intent, 80);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Debug.info(TAG,"requestCode="+requestCode+"||resultCode="+resultCode+"||data="+data.toString());
        if (requestCode == 80 && resultCode == 200 && data != null) {
            mDeviceId = data.getIntExtra("id", -1);
            Debug.info(TAG,"testDEviceId="+mDeviceId);
            String name = data.getStringExtra("name");
            select_text.setText(name);
            pullablePreDefault.requestRefresh();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void initListData(TempRefreshRecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(getTempContext()));
        rv.addItemDecoration(new TempRVDividerDecoration(Color.parseColor("#EEEFF3"), TempDensityUtil.dip2px(getTempContext(), 1.0f)));
        if (adapter == null) {
            adapter = new TempRVCommonAdapter<RespSelector.DataBean.DatasBean>(getTempContext(), R.layout.item_gongjian_selector_layout) {
                @Override
                public void bindItemValues(TempRVHolder holder, RespSelector.DataBean.DatasBean dataBean) {
                        holder.setText(R.id.item_gongjian_right_text,"寿命："+dataBean.getCurrentDurability()+"/"+dataBean.getMaxDurability());
                        holder.setVisible(R.id.item_gongjian_right_text,true);
                        holder.setText(R.id.item_gongjian_radioButton, dataBean.getDeviceToolTypeName());

//                    holder.setText(R.id.item_check_pici_num, "待抽检数：" + dataBean.getWaitCheckCount());
//                    holder.setText(R.id.item_check_pici_percent, "抽检比列：" + new DecimalFormat("0%").format(dataBean.getCheckPercentage()));
//                    holder.setText(R.id.item_check_pici_progress, "抽检进度：" + dataBean.getCheckCount() + "/" + dataBean.getNeedCheckCount());
                }
            };
            adapter.setOnItemClickListener(new OnItemClickListener<RespSelector.DataBean.DatasBean>() {
                @Override
                public void onItemClick(ViewGroup parent, View view, RespSelector.DataBean.DatasBean o, int position) {
                    if (!o.isChecked()){
                        adapter.getData().get(position).setChecked(true);
                        adapter.notifyDataSetChanged();
                    }
                    deviceToolIds.clear();
                    deviceToolIds.add(o.getId()+"");
                    showConfirmationDialog(ActDeviceToolChange.this, true, "刀具更换提醒", "确认更换该刀具?", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            preGlobal.changeDeviceTool(deviceToolIds.toArray(new String[deviceToolIds.size()]),true);
                        }
                    },null);

                  /*  Intent intent = getIntent();
                    intent.putExtra("id",o.getId());
                    intent.putExtra("name",o.getName());
                    intent.putExtra("deviceToolTypeName",o.getDeviceToolTypeName());
                    setResult(200,intent);
                    finish();*/
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
        rv.addItemDecoration(new TempRVDividerDecoration(Color.parseColor("#ECECEC"),TempDensityUtil.dip2px(ActDeviceToolChange.this,16.0f)));
        rv.setRefreshListener(new TempRefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullablePreDefault.requestRefresh();
            }
        });
        rv.setAdapter(adapter);
    }
}
