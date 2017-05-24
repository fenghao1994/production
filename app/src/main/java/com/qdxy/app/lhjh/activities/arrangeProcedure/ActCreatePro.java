package com.qdxy.app.lhjh.activities.arrangeProcedure;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.qdxy.app.lhjh.activities.alarm.RespAlarmList;
import com.qdxy.app.lhjh.activities.global.PreGlobal;
import com.qdxy.app.lhjh.activities.global.ViewGlobal;
import com.qdxy.app.lhjh.activities.selectors.ActSelector;
import com.qdxy.app.lhjh.api.TempAPI;
import com.qdxy.app.lhjh.bean.RespProductLines;
import com.qdxy.app.lhjh.bean.RespProductModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;

import static com.qdxy.app.lhjh.R.id.actcreat_pro_check_index1;


/**创建工序安排
 * Created by KY on 2016/12/7.
 */

public class ActCreatePro extends TempActivity{
    private final String TAG = "ActCreatePro";
    private PreGlobal preGlobal;
    private ViewGlobal viewGlobal;
    private PopupWindow popwindowLines, popwndowPrd;
    private TempRVCommonAdapter<RespProductLines.DataBean> lineAdapter;
    private TempRVCommonAdapter<RespAlarmList.DataBean.DatasBean> prdAdapter;
    private RespProductLines.DataBean checkedLineData;//选中的生产线数据
//    private RespArrangeProList.DataBean.DatasBean checkedPrdeData;//选中的生产线数据
    private List<String> checkPrdIds;//选中工序id
    private List<String> checkPrdNames;//选中工序名称
    private int personId=-1;
    @Bind(R.id.parent_view) LinearLayout parentView;
    @Bind(R.id.actcreat_pro_check_index0) TextView check_index0;
    @Bind(R.id.actcreat_pro_check_index1) TextView check_index1;
    @Bind(R.id.actcreat_pro_check_index2) TextView check_index2;
    @Bind(R.id.act_create_pici_add_btn)  AppCompatButton act_create_pici_add_btn;
    private TempPullablePreDefault<RespAlarmList> pullableDefault;
    private TempPullableViewI<RespAlarmList> pullableViewI;
    private PreArrange preArrange;
    private ViewArrangeI viewArrangeI;
    private boolean lineDataCheckChanged,isOrgEditData;
    /**
     * 生产数据
     */
    private RespProductLines linesData;
    private List<RespAlarmList.DataBean.DatasBean> prdData;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_create_pro_layout);
    }

    @Override
    protected void findViews() {
//        intent.putExtra("isEdit",true);
//        intent.putExtra("prdName",datasBean.getProcedureName());
//        intent.putExtra("lineName",datasBean.getLineName());
//        intent.putExtra("userId",datasBean.getUserId());
//        intent.putExtra("userName",datasBean.getUserName());
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
       if(getIntent().getBooleanExtra("isEdit",false)){
           initToolbar(toolbarTop, "修改工序安排");
           act_create_pici_add_btn.setText("修改");
           personId =Integer.valueOf(getIntent().getStringExtra("userId"));
           checkedLineData = new RespProductLines.DataBean();
//           int lineId=Integer.valueOf();
           checkedLineData.setId(Integer.valueOf(getIntent().getStringExtra("lineId")));
           checkedLineData.setName(getIntent().getStringExtra("lineName"));
           isOrgEditData=true;
           check_index0.setText(getIntent().getStringExtra("userName"));
           check_index1.setText(getIntent().getStringExtra("lineName"));
           check_index2.setText(getIntent().getStringExtra("prdName"));
       } else{
           act_create_pici_add_btn.setText("创建");
           initToolbar(toolbarTop, "创建工序安排");
       }
    }

    @Override
    protected void setListeners() {
        viewArrangeI = new ViewArrangeI() {
            @Override
            public void onSetprocdurewithuserSucceed(String messsage) {
                Intent intent = getIntent().putExtra("message",messsage);
                setResult(200,intent);
                pullableDefault.requestRefresh();
                finish();
            }

            @Override
            public void onProcedureClearSucceed(String messsage) {

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
        viewGlobal = new ViewGlobal() {
            @Override
            public void onGetProductionLinesSucceed(RespProductLines data) {
                Debug.info("获取到生产线数据" + data.toString());
                linesData = data;
            }

            @Override
            public void onGetProductionLinesFailed(TempErrorCode code, String message) {
                superViewMessage(message);
            }

            @Override
            public void onGetProductTypeSucceed(RespProductModel data) {
                Debug.info("获取到产品型号数据" + data.toString());
//                typeData = data;
            }

            @Override
            public void onGetProductTypeFailed(TempErrorCode code, String message) {
                superViewMessage(message);
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
        };
        preGlobal = new PreGlobal(viewGlobal);

        pullableViewI = new TempPullableViewI<RespAlarmList>() {
            @Override
            public void onInit(RespAlarmList respProcedure) {

            }

            @Override
            public void onRefresh(RespAlarmList respProcedure) {
                Debug.debug("加工数据刷新返回" + respProcedure.getData().getDatas().size());


                prdAdapter.updateRefresh(respProcedure.getData().getDatas());
                prdData=prdAdapter.getData();
            }

            @Override
            public void onLoadmore(RespAlarmList respProcedure) {
                // 默认全部展开
                prdAdapter.updateLoadMore(respProcedure.getData().getDatas());
                prdData=prdAdapter.getData();
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
        pullableDefault = new TempPullablePreDefault<RespAlarmList>(pullableViewI) {
            @Override
            public Call<RespAlarmList> createObservable(int queryPage, int querysize, int currentPage) {
                return TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).userprocedures("0",checkedLineData.getId()+"",queryPage);
            }
        };
    }

    @Override
    protected void bindValues() {
        preGlobal.requestProductedLinesAll();

    }
    @OnClick({R.id.actcreat_pro_check_index0, actcreat_pro_check_index1, R.id.actcreat_pro_check_index2,R.id.act_create_pici_add_btn})
    @Override
    protected void OnViewClicked(View v) {
        switch (v.getId()){
            case R.id.actcreat_pro_check_index0:
                //选择人员
                startActivityForResult(new Intent(ActCreatePro.this, ActSelector.class),99);
                break;
            case R.id.actcreat_pro_check_index1:
                //选择生产线
                showLinePop();
                break;
            case R.id.actcreat_pro_check_index2:
                if (checkedLineData==null){
                    showSnackBar("请先选择生产线",2, Snackbar.LENGTH_LONG);
                }else{
                    showPrdPop();
                }
                //选择工序
                break;
            case R.id.act_create_pici_add_btn:
                //创建工序安排
                if (isOrgEditData){
                    showSnackBar("你还没有修改安排！",2,Snackbar.LENGTH_LONG);
                }else if (personId==-1){
                    showSnackBar("请选择操作员！",2,Snackbar.LENGTH_LONG);
                }else if(checkedLineData==null){
                    showSnackBar("请选择生产线",2, Snackbar.LENGTH_LONG);
                }else if(checkPrdIds==null||checkPrdIds.isEmpty()){
                    showSnackBar("请选择工序",2,Snackbar.LENGTH_LONG);
                }else{
                    preArrange.setprocdurewithuser(personId+"",checkPrdIds.toArray(new String[checkPrdIds.size()]));
                }
                break;

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==99&&resultCode==200&&data!=null){
            personId=data.getIntExtra("id",-1);
            String name=data.getStringExtra("name");
            check_index0.setText(name);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
    private void initlineReceiverview(TempRefreshRecyclerView recyclerview, List<RespProductLines.DataBean> datas) {
        if (lineAdapter==null){
            lineAdapter = new TempRVCommonAdapter<RespProductLines.DataBean>(ActCreatePro.this, R.layout.item_dialog_check_list_layout) {
                @Override
                public void bindItemValues(TempRVHolder holder, RespProductLines.DataBean s) {
                    holder.setText(R.id.item_dialog_check_radioButton, s.getName());
                    holder.setChecked(R.id.item_dialog_check_radioButton, s.isChecked());
                }
            };
            lineAdapter.setOnItemClickListener(new OnItemClickListener<RespProductLines.DataBean>() {
                @Override
                public void onItemClick(ViewGroup parent, View view, RespProductLines.DataBean o, int position) {
                    if (!o.isChecked()) {
                        lineDataCheckChanged=true;
                        List<RespProductLines.DataBean> data= lineAdapter.getData();
                        for (int i=0;i<lineAdapter.getData().size();i++){
                            data.get(i).setChecked(false);
                        }
                        o.setChecked(true);
                        checkedLineData = o;
                        lineAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public boolean onItemLongClick(ViewGroup parent, View view, RespProductLines.DataBean o, int position) {
                    return false;
                }
            });


        }
        recyclerview.setLayoutManager(new LinearLayoutManager(ActCreatePro.this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(lineAdapter);
        recyclerview.addItemDecoration(new TempRVDividerDecoration(Color.parseColor("#EEEFF3"), TempDensityUtil.dip2px(ActCreatePro.this, 1.0f)));


            lineAdapter.updateRefresh(datas);
    }
    private void initPrdRecyclerView(TempRefreshRecyclerView recyclerview) {
            prdAdapter = new TempRVCommonAdapter<RespAlarmList.DataBean.DatasBean>(ActCreatePro.this, R.layout.item_dialog_check_list_layout) {
                @Override
                public void bindItemValues(TempRVHolder holder, RespAlarmList.DataBean.DatasBean s) {
                    holder.setText(R.id.item_dialog_check_radioButton, s.getName());
                    holder.setChecked(R.id.item_dialog_check_radioButton, s.isChecked());
                }
            };
            prdAdapter.setMore(new TempRVCommonAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    pullableDefault.requestLoadMore();
                }
            });
            prdAdapter.setOnItemClickListener(new OnItemClickListener<RespAlarmList.DataBean.DatasBean>() {
                @Override
                public void onItemClick(ViewGroup parent, View view, RespAlarmList.DataBean.DatasBean o, int position) {
                    prdData.get(position).setChecked(!o.isChecked());
//                    List<RespAlarmList.DataBean.DatasBean> prdAdapterDatas = prdAdapter.getData();
//                    prdAdapterDatas.get(position).setChecked(!o.isChecked());
                    checkPrdIds=new ArrayList<String>();
                    checkPrdNames= new ArrayList<String>();
                    prdAdapter.notifyDataSetChanged();

                        for (int i=0;i<prdData.size();i++){
                            if (prdData.get(i).isChecked()){
                                checkPrdIds.add(prdData.get(i).getId()+"");
                                checkPrdNames.add(prdData.get(i).getName());

                            }
//                            data.get(i).setChecked(false);
                        }

//                        o.setChecked(true);
//                        checkedPrdeData = o;
                        prdAdapter.notifyDataSetChanged();
                }

                @Override
                public boolean onItemLongClick(ViewGroup parent, View view, RespAlarmList.DataBean.DatasBean o, int position) {
                    return false;
                }
            });

        recyclerview.setRefreshListener(new TempRefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullableDefault.requestRefresh();
            }
        });
        recyclerview.setLayoutManager(new LinearLayoutManager(ActCreatePro.this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(prdAdapter);
        recyclerview.addItemDecoration(new TempRVDividerDecoration(Color.parseColor("#EEEFF3"), TempDensityUtil.dip2px(ActCreatePro.this, 1.0f)));
        pullableDefault.requestRefresh();
    }
    private void showLinePop(){
        if (null==popwindowLines){
            View popupWindow_view = getLayoutInflater().inflate(R.layout.dialog_check_list_layout, null,
                    false);
            TextView title = (TextView) popupWindow_view.findViewById(R.id.dialog_check_list_title);
            TextView dialog_check_list_ok = (TextView) popupWindow_view.findViewById(R.id.dialog_check_list_ok);
            TempRefreshRecyclerView act_create_pici_check_recycler = (TempRefreshRecyclerView) popupWindow_view.findViewById(R.id.act_create_pici_check_recycler);
            initlineReceiverview(act_create_pici_check_recycler, linesData.getData());
            dialog_check_list_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popwindowLines.dismiss();
                    if (lineDataCheckChanged){
                        //如果生产线选择被修改清除工序数据
                        isOrgEditData=false;
                        prdData=null;
                        checkPrdIds=null;
                        checkPrdNames=null;
                        check_index2.setText("");
                        lineDataCheckChanged=false;
                        check_index1.setText(checkedLineData==null?"":checkedLineData.getName());
                    }
//                popwindowLines = null;

//                upDateResult();
                }
            });
            TextView dialog_check_list_cancel = (TextView) popupWindow_view.findViewById(R.id.dialog_check_list_cancel);
            dialog_check_list_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popwindowLines.dismiss();
                }
            });
            title.setText("生产线");
            // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
            popwindowLines = new PopupWindow(popupWindow_view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            popwindowLines.setOutsideTouchable(false);
            // 设置动画效果
            popwindowLines.setAnimationStyle(R.style.popwin_anim_style);
            popwindowLines.setBackgroundDrawable(new BitmapDrawable(null, ""));
        }
        if (!popwindowLines.isShowing()) {
            popwindowLines.showAtLocation(parentView, Gravity.CENTER, 0, 0);
        }
    }
    private void showPrdPop(){
        if (null==popwndowPrd||null==prdData){
            View popupWindow_view = getLayoutInflater().inflate(R.layout.dialog_check_list_layout, null,
                    false);
            TextView title = (TextView) popupWindow_view.findViewById(R.id.dialog_check_list_title);
            TextView dialog_check_list_ok = (TextView) popupWindow_view.findViewById(R.id.dialog_check_list_ok);
            TempRefreshRecyclerView act_create_pici_check_recycler = (TempRefreshRecyclerView) popupWindow_view.findViewById(R.id.act_create_pici_check_recycler);
            initPrdRecyclerView(act_create_pici_check_recycler);
            dialog_check_list_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popwndowPrd.dismiss();
                    isOrgEditData=false;
                    check_index2.setText(checkPrdNames.toString());
//                    check_index1.setText(checkedLineData==null?"":checkedLineData.getName());
//                upDateResult();
                }
            });
            TextView dialog_check_list_cancel = (TextView) popupWindow_view.findViewById(R.id.dialog_check_list_cancel);
            dialog_check_list_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popwndowPrd.dismiss();
                }
            });
            title.setText("生产线");
            // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
            popwndowPrd = new PopupWindow(popupWindow_view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            popwndowPrd.setOutsideTouchable(false);
            // 设置动画效果
            popwndowPrd.setAnimationStyle(R.style.popwin_anim_style);
            popwndowPrd.setBackgroundDrawable(new BitmapDrawable(null, ""));
        }

        if (!popwndowPrd.isShowing()) {
            popwndowPrd.showAtLocation(parentView, Gravity.CENTER, 0, 0);
        }
    }
}
