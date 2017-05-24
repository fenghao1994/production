package com.qdxy.app.lhjh.activities.alarm;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempFragment.TempFragment;
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
import com.qdxy.app.lhjh.api.TempAPI;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by KY on 2016/12/7.
 */

public class FragAlarmMangment extends TempFragment{
    private final String TAG ="FragAlarmMangment";
    private TempPullablePreDefault<RespAlarmList> pullablePreDefault;
    private TempPullableViewI<RespAlarmList> pullableViewI;
    private TempRVCommonAdapter<RespAlarmList.DataBean.DatasBean> adapter;
    @Bind(R.id.frag_alarm_RecyclerView)   TempRefreshRecyclerView refreshRecyclerView;
    @Bind(R.id.parent_view)    LinearLayout parentView;
    @Bind(R.id.frag_alarm_select_all_image)    ImageView selected_all_image;
//    @Bind(R.id.body_operation_check_frame)    ImageView selected_all_image;
    private PreAlarm preAlarm;
    private ViewAlarmI viewAlarmI;
    private int lineId;
    private boolean isSelectedAll;

//    AppCompatButton offBtn
    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_alarm_management_layout,null);
    }

    @Override
    protected void setListeners(View view, @Nullable Bundle savedInstanceState) {
        viewAlarmI = new ViewAlarmI() {
            @Override
            public void onStopSucceed(String message) {
                Debug.info("关闭报警成功");
                superViewMessage(parentView,"关闭报警成功");
                pullablePreDefault.requestRefresh();
            }

            @Override
            public void onStartSucceed(String message) {
                Debug.info("启动报警成功");
                superViewMessage(parentView,"启动报警成功");
                pullablePreDefault.requestRefresh();
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
                superViewMessage(parentView,message);
            }

            @Override
            public void viewError(TempErrorCode code, String message) {
                superViewError(parentView,code,message);
            }
        };
        preAlarm = new PreAlarm(viewAlarmI);
        pullableViewI = new TempPullableViewI<RespAlarmList>() {
            @Override
            public void onInit(RespAlarmList data) {

            }

            @Override
            public void onRefresh(RespAlarmList data) {
                Debug.info("报警数据刷新返回");
                adapter.updateRefresh(data.getData().getDatas());
                isSelectedAll=false;
                upDateSelcetedImageStatus(isSelectedAll);
            }

            @Override
            public void onLoadmore(RespAlarmList data) {
                adapter.updateLoadMore(data.getData().getDatas());
                isSelectedAll=false;
                upDateSelcetedImageStatus(isSelectedAll);
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
        pullablePreDefault = new TempPullablePreDefault<RespAlarmList>(pullableViewI) {
            @Override
            public Call<RespAlarmList> createObservable(int queryPage, int querysize, int currentPage) {
                return TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).lineprocedures(lineId+"",queryPage);
            }
        };
    }

    @Override
    protected void bundleValues(View view, @Nullable Bundle savedInstanceState) {
        lineId = getArguments().getInt("lineId",-1);

        if(   lineId==-1){
            showSnackBar(parentView,"没有获取到生产线数据！",2, Snackbar.LENGTH_LONG);
            return;
        }
        initAdapter();
        pullablePreDefault.requestRefresh();
    }
    @OnClick({R.id.frag_alarm_off_btn,R.id.frag_alarm_on_btn,R.id.frag_alarm_select_all_frame})
    @Override
    protected void OnViewClicked(View v) {
        switch (v.getId()){
            case R.id.frag_alarm_off_btn:
                //关闭报警
                List<String> selectedList = new ArrayList<>();
                if (adapter!=null&&adapter.getData()!=null&&!adapter.getData().isEmpty()){
                    List<RespAlarmList.DataBean.DatasBean> alarmList=adapter.getData();
                    for (int i=0;i<alarmList.size();i++){
                        if (alarmList.get(i).isChecked()){
                            selectedList.add(alarmList.get(i).getId()+"");
                        }
                    }
                    if (selectedList.isEmpty()){
                        showSnackBar(parentView,"请先选择工序",2,Snackbar.LENGTH_LONG);
                    }else{
                        preAlarm.stopalarm(selectedList.toArray(new String[selectedList.size()]));
                    }
                }else{
                    showSnackBar(parentView,"没有可操作的数据",2,Snackbar.LENGTH_LONG);
                }
                break;
            case R.id.frag_alarm_on_btn:
                //启动报警
                List<String> selectedList1 = new ArrayList<>();
                if (adapter!=null&&adapter.getData()!=null&&!adapter.getData().isEmpty()){
                    List<RespAlarmList.DataBean.DatasBean> alarmList=adapter.getData();
                    for (int i=0;i<alarmList.size();i++){
                        if (alarmList.get(i).isChecked()){
                            selectedList1.add(alarmList.get(i).getId()+"");
                        }
                    }
                    if (selectedList1.isEmpty()){
                        showSnackBar(parentView,"请先选择工序",2,Snackbar.LENGTH_LONG);
                    }else{
                        preAlarm.startpalarm(selectedList1.toArray(new String[selectedList1.size()]));
                    }
                }else{
                    showSnackBar(parentView,"没有可操作的数据",2,Snackbar.LENGTH_LONG);
                }
                break;
            case R.id.frag_alarm_select_all_frame:
                //选中全部
                if (adapter!=null&&adapter.getData()!=null&&!adapter.getData().isEmpty()){
                    isSelectedAll=!isSelectedAll;
                    upDateSelcetedImageStatus(isSelectedAll);
                        List<RespAlarmList.DataBean.DatasBean> alarmList=adapter.getData();
                        for (int i=0;i<alarmList.size();i++){
                            alarmList.get(i).setChecked(isSelectedAll);
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    showSnackBar(parentView,"没有可操作的数据",2,Snackbar.LENGTH_LONG);
                }
                break;

        }
    }
    private void upDateSelcetedImageStatus(boolean status){
        selected_all_image.setImageResource(status?R.mipmap.icon_checked_l:R.mipmap.icon_unchecked_l);
    }
    private void initAdapter() {
        refreshRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TempRVCommonAdapter<RespAlarmList.DataBean.DatasBean>(getActivity(), R.layout.item_alarm_management_layout) {
            @Override
            public void bindItemValues(TempRVHolder holder, RespAlarmList.DataBean.DatasBean datasBean) {
                holder.setImageResource(R.id.item_alarm_checked_image,datasBean.isChecked()?R.mipmap.icon_checked_l:R.mipmap.icon_unchecked_l);
                holder.setText(R.id.item_alarm_content_text,datasBean.getName());
                holder.setText(R.id.item_alarm_status_text,datasBean.isIsStartedAlarm()?"已开启":"已关闭");
                holder.setTextColorRes(R.id.item_alarm_status_text,datasBean.isIsStartedAlarm()?R.color.colorPrimary:R.color.colorAccent);
            }};
        adapter.setOnItemClickListener(new OnItemClickListener<RespAlarmList.DataBean.DatasBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, RespAlarmList.DataBean.DatasBean o, int position) {

                adapter.getData().get(position).setChecked(!o.isChecked());
                List<RespAlarmList.DataBean.DatasBean>  items =adapter.getData();
                boolean all =true;
                for (int i=0;i<items.size();i++){
                    if (!items.get(i).isChecked()){
                        all=false;
                        break;
                    }
                }
                isSelectedAll=all;
                upDateSelcetedImageStatus(isSelectedAll);
                adapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, RespAlarmList.DataBean.DatasBean o, int position) {
                return false;
            }
        });
        refreshRecyclerView.setRefreshListener(new TempRefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullablePreDefault.requestRefresh();
            }
        });
        adapter.setMore(new TempRVCommonAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pullablePreDefault.requestLoadMore();
            }
        });
        refreshRecyclerView.addItemDecoration(new TempRVDividerDecoration(Color.parseColor("#ECECEC"), TempDensityUtil.dip2px(getActivity(),16f)));
        refreshRecyclerView.setAdapter(adapter);
    }
}
