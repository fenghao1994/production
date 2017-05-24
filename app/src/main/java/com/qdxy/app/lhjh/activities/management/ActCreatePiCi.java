package com.qdxy.app.lhjh.activities.management;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lf.tempcore.tempActivity.TempActivity;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
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
import com.qdxy.app.lhjh.bean.RespProductLines;
import com.qdxy.app.lhjh.bean.RespProductModel;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 添加批次
 * Created by KY on 2016/11/22.
 */

public class ActCreatePiCi extends TempActivity implements ViewPiCi {
    private final String TAG = "ActCreatePiCi";
    private PrePiCi prePiCi;
    private PreGlobal preGlobal;
    private ViewGlobal viewGlobal;
    @Bind(R.id.act_create_pici_add_btn)    Button act_create_pici_add_btn;
    @Bind(R.id.act_create_pici_rootView)    LinearLayout act_create_pici_rootView;
    @Bind(R.id.act_create_pici_check_index1)    TextView act_create_pici_check_index1;
    @Bind(R.id.act_create_pici_check_index2)    TextView act_create_pici_check_index2;
    @Bind(R.id.act_create_pici_check_index3)    EditText act_create_pici_check_index3;
    @Bind(R.id.act_create_pici_result)    TextView act_create_pici_result;
    private PopupWindow popwindowLines, popwndowType;
    /**
     * 生产数据
     */
    private RespProductLines linesData;
    private RespProductModel typeData;
    private TempRVCommonAdapter lineAdapter,typeAdapter;
        private RespProductLines.DataBean checkedLineData;
        private RespProductModel.DataBean checkedTypeData;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_create_pici_layout);
    }

    @Override
    protected void findViews() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        initToolbar(toolbarTop, "添加批次");
        setKeyboardAutoHide(true);
    }

    @Override
    protected void setListeners() {
        act_create_pici_check_index3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                upDateResult();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
                typeData = data;
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
    }

    @Override
    protected void bindValues() {
        prePiCi = new PrePiCi(this);
        preGlobal = new PreGlobal(viewGlobal);
        if (linesData == null) {
            preGlobal.requestProductedLines();
        }
        if (typeData == null) {

            preGlobal.requestProductedModel();
        }
//        prePiCi.createProductionBatch();
    }

    @OnClick({R.id.act_create_pici_check_index1, R.id.act_create_pici_check_index2, R.id.act_create_pici_check_index3, R.id.act_create_pici_add_btn})
    @Override
    protected void OnViewClicked(View v) {
        switch (v.getId()) {
            case R.id.act_create_pici_add_btn:
                // TODO: 2016/11/23 添加批次
//                prePiCi.createProductionBatch();
                Debug.info(TAG,"点击添加批次");
                if (null==checkedLineData){
                    showSnackBar("请选择生产线",2, Snackbar.LENGTH_LONG);
                }else if(null==checkedTypeData){
                    showSnackBar("请选择让产品型号",2, Snackbar.LENGTH_LONG);
                } else if (TextUtils.isEmpty(act_create_pici_check_index3.getText().toString().trim())) {
                    showSnackBar("请输入批次日期",2, Snackbar.LENGTH_LONG);
                }else{

                    prePiCi.createProductionBatch(checkedLineData.getId()+"",checkedTypeData.getProductTypeId()+"",act_create_pici_result.getText().toString().trim());
                }
                break;
            case R.id.act_create_pici_check_index1://生产线
                showLinePop();
                break;
            case R.id.act_create_pici_check_index2://型号
                showTypePop();
                break;

        }
    }

    @Override
    public void viewCreateSucceed(String message) {
        showToast(message);
        setResult(200);
        finish();
    }

    @Override
    public void viewUpdateSucceed(String message) {

    }

    @Override
    public void viewUpdateFailed(String message) {

    }

    @Override
    public void viewCreateFailed(String message) {
        showToast(message);
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

    private void upDateResult() {
        if (!TextUtils.isEmpty(act_create_pici_check_index1.getText().toString().trim())&&!TextUtils.isEmpty(act_create_pici_check_index2.getText().toString().trim()) && !TextUtils.isEmpty(act_create_pici_check_index3.getText().toString().trim())) {
            act_create_pici_result.setText(String.format("%s%s%s", act_create_pici_check_index2.getText().toString().trim(), checkedLineData.getCode(),act_create_pici_check_index3.getText().toString().trim()));
        }
    }

    private void initlineReceiverview(TempRefreshRecyclerView recyclerview, List<RespProductLines.DataBean> datas) {
        lineAdapter = new TempRVCommonAdapter<RespProductLines.DataBean>(ActCreatePiCi.this, R.layout.item_dialog_check_list_layout) {
            @Override
            public void bindItemValues(TempRVHolder holder, RespProductLines.DataBean s) {
                holder.setText(R.id.item_dialog_check_radioButton, s.getName());
                holder.setChecked(R.id.item_dialog_check_radioButton, s.isChecked());
            }
        };
        recyclerview.setLayoutManager(new LinearLayoutManager(ActCreatePiCi.this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        lineAdapter.setOnItemClickListener(new OnItemClickListener<RespProductLines.DataBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, RespProductLines.DataBean o, int position) {
                if (!o.isChecked()) {
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
        recyclerview.setAdapter(lineAdapter);
        recyclerview.addItemDecoration(new TempRVDividerDecoration(Color.parseColor("#EEEFF3"), TempDensityUtil.dip2px(ActCreatePiCi.this, 1.0f)));
        lineAdapter.updateRefresh(datas);
    }
    private void initTypeReceiverview(TempRefreshRecyclerView recyclerview, List<RespProductModel.DataBean> datas) {
       typeAdapter = new TempRVCommonAdapter<RespProductModel.DataBean>(ActCreatePiCi.this, R.layout.item_dialog_check_list_layout) {
            @Override
            public void bindItemValues(TempRVHolder holder, RespProductModel.DataBean s) {
                holder.setText(R.id.item_dialog_check_radioButton, s.getProductTypeCode());
                holder.setChecked(R.id.item_dialog_check_radioButton, s.isChecked());
            }
        };
        recyclerview.setLayoutManager(new LinearLayoutManager(ActCreatePiCi.this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        typeAdapter.setOnItemClickListener(new OnItemClickListener<RespProductModel.DataBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, RespProductModel.DataBean o, int position) {
                if (!o.isChecked()) {
                    List<RespProductModel.DataBean> data= typeAdapter.getData();
                    for (int i=0;i<typeAdapter.getData().size();i++){
                        data.get(i).setChecked(false);
                    }
                    o.setChecked(true);
                    checkedTypeData =o;
                    typeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, RespProductModel.DataBean o, int position) {
                return false;
            }
        });
        recyclerview.setAdapter(typeAdapter);
        recyclerview.addItemDecoration(new TempRVDividerDecoration(Color.parseColor("#EEEFF3"), TempDensityUtil.dip2px(ActCreatePiCi.this, 1.0f)));
        typeAdapter.updateRefresh(datas);
    }
    /**
     * 创建PopupWindow
     */
    protected void initLinesWindow() {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
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
                popwindowLines = null;
                act_create_pici_check_index1.setText(checkedLineData==null?"":checkedLineData.getName());
                upDateResult();
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

    /**
     * 创建PopupWindow
     */
    protected void initTypeWindow() {
        // TODO Auto-generated method stub

        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getLayoutInflater().inflate(R.layout.dialog_check_list_layout, null, false);
        TextView title = (TextView) popupWindow_view.findViewById(R.id.dialog_check_list_title);
        TextView dialog_check_list_ok = (TextView) popupWindow_view.findViewById(R.id.dialog_check_list_ok);
        TempRefreshRecyclerView act_create_pici_check_recycler = (TempRefreshRecyclerView) popupWindow_view.findViewById(R.id.act_create_pici_check_recycler);
        initTypeReceiverview(act_create_pici_check_recycler, typeData.getData());
        dialog_check_list_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popwndowType.dismiss();
                act_create_pici_check_index2.setText(checkedTypeData==null?"":checkedTypeData.getProductTypeCode());
                upDateResult();
            }
        });
        TextView dialog_check_list_cancel = (TextView) popupWindow_view.findViewById(R.id.dialog_check_list_cancel);
        dialog_check_list_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popwndowType.dismiss();
            }
        });
        title.setText("产品型号");
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popwndowType = new PopupWindow(popupWindow_view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popwndowType.setOutsideTouchable(false);
        // 设置动画效果
        popwndowType.setAnimationStyle(R.style.popwin_anim_style);
        popwndowType.setBackgroundDrawable(new BitmapDrawable(null, ""));
    }

    private void showLinePop() {
        if (linesData==null||linesData.getData()==null){
            showSnackBar("没有获取到产品类型数据！",2,Snackbar.LENGTH_LONG);
        }else  if (null == popwindowLines) {
            initLinesWindow();
        }
        if (popwindowLines!=null&&!popwindowLines.isShowing()) {
            popwindowLines.showAtLocation(act_create_pici_rootView, Gravity.CENTER, 0, 0);
        }
    }

    private void showTypePop() {
        if (typeData==null||typeData.getData()==null){
            showSnackBar("没有获取到产品类型数据！",2,Snackbar.LENGTH_LONG);
        }else  if (null == popwndowType) {
            initTypeWindow();
        }
        if (popwndowType!=null&&!popwndowType.isShowing()) {
            popwndowType.showAtLocation(act_create_pici_rootView, Gravity.CENTER, 0, 0);
        }
    }

}
