package com.qdxy.app.lhjh.activities.product;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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
import com.qdxy.app.lhjh.bean.RespProductModel;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 创建产品箱
 * Created by KY on 2016/11/29.
 */

public class ActCreateProductBox extends TempActivity {
    private final String TAG = "ActCreateProductBox";
    private PreProduct preProduct;
    private ViewProductI viewProductI;
    private RespBatchByInBox respBatchByInBox;//批次数据
    private RespProductModel typeData;
    private TempRVCommonAdapter typeAdapter;
    private PopupWindow popwindowLines;
    private RespBatchByInBox.DataBean checkedBatchData;
    //    private RespProductLines.DataBean checkedLineData;
//    private RespProductModel.DataBean checkedTypeData;
    @Bind(R.id.dialog_create_product_box_index0)    EditText dialog_create_product_box_index0;
    @Bind(R.id.act_create_pici_rootView)    LinearLayout act_create_pici_rootView;
    @Bind(R.id.dialog_create_product_box_index2)    EditText dialog_create_product_box_index2;
    @Bind(R.id.dialog_create_product_box_index1)    TextView dialog_create_product_box_index1;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_create_product_box_layout);
    }

    @Override
    protected void findViews() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        initToolbar(toolbarTop, "创建产品箱");
        setKeyboardAutoHide(true);
    }

    @Override
    protected void setListeners() {

        viewProductI = new ViewProductI() {
            @Override
            public void onMachiningCountByProcedure(RespProductionCount data) {

            }

            @Override
            public void onProductListSucceed(RespProductList data) {

            }

            @Override
            public void onFeedingNumSucceed(RespFeedNum data) {

            }

            @Override
            public void onChangeTool(String message) {

            }

            @Override
            public void getroceduresByBatch(RespBatchByInBox data) {

            }

            @Override
            public void onCreateProductSucceed(String message) {
//                superViewMessage(message);
                showToast(message);
                EventBus.getDefault().post("200");
                finish();
            }

            @Override
            public void onInBoxSucceed(String message) {

            }

            @Override
            public void onMachineOperationSucceed(RespMachineOperation data) {

            }

            @Override
            public void getBatchBySendMaterial(RespBatchByInBox data) {

            }

            @Override
            public void onNextSucceed(String message) {

            }

            @Override
            public void onUpdateStatusSucceed(String message) {

            }

            @Override
            public void onInBoxDetailSucceed(RespInBoxDetail data) {

            }

            @Override
            public void getBatchByInBox(RespBatchByInBox data) {
                Debug.info(TAG, "获取装箱料批次列表succeed");
                respBatchByInBox = data;
            }

            @Override
            public void onFailed(int who, String message) {

            }

            @Override
            public void onMachineOperationFailed(String message) {

            }

            @Override
            public void touliaoSucceed(String message) {

            }

            @Override
            public void touliaoFailed(String message) {

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
                viewMsg(message);
            }

            @Override
            public void viewError(TempErrorCode code, String message) {
                superViewError(code, message);
            }
        };
        preProduct = new PreProduct(viewProductI);
    }

    @Override
    protected void bindValues() {
        preProduct.getBatchByInBox();
    }

    @OnClick({R.id.dialog_create_product_box_index1,R.id.dialog_create_product_box_add_btn})
    @Override
    protected void OnViewClicked(View v) {
        switch (v.getId()) {
            case R.id.dialog_create_product_box_index1:
                showTypePop();
                break;
            case R.id.dialog_create_product_box_add_btn:
                EventBus.getDefault().post("data");
                if (TextUtils.isEmpty(dialog_create_product_box_index0.getText().toString().trim())) {
                    showSnackBar("请输入箱子编号",2, Snackbar.LENGTH_LONG);
                }else if(TextUtils.isEmpty(dialog_create_product_box_index1.getText().toString().trim())){
                    showSnackBar("请选择产品批次",2, Snackbar.LENGTH_LONG);
                }else if(TextUtils.isEmpty(dialog_create_product_box_index2.getText().toString().trim())){
                    showSnackBar("请输入箱子容量",2, Snackbar.LENGTH_LONG);
                }else{

                    preProduct.requestcCreateProductBox(dialog_create_product_box_index0.getText().toString().trim(),checkedBatchData.getId()+"",dialog_create_product_box_index2.getText().toString().trim());
                }
                break;
        }
    }

    private void initTypeReceiverview(TempRefreshRecyclerView recyclerview, List<RespBatchByInBox.DataBean> datas) {
        typeAdapter = new TempRVCommonAdapter<RespBatchByInBox.DataBean>(ActCreateProductBox.this, R.layout.item_dialog_check_list_layout) {
            @Override
            public void bindItemValues(TempRVHolder holder, RespBatchByInBox.DataBean s) {
                holder.setText(R.id.item_dialog_check_radioButton, s.getCode());
                holder.setChecked(R.id.item_dialog_check_radioButton, s.isChecked());
            }
        };
        recyclerview.setLayoutManager(new LinearLayoutManager(ActCreateProductBox.this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        typeAdapter.setOnItemClickListener(new OnItemClickListener<RespBatchByInBox.DataBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, RespBatchByInBox.DataBean o, int position) {
                if (!o.isChecked()) {
                    List<RespBatchByInBox.DataBean> data = typeAdapter.getData();
                    for (int i = 0; i < typeAdapter.getData().size(); i++) {
                        data.get(i).setChecked(false);
                    }
                    o.setChecked(true);
                    checkedBatchData = o;
                    typeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, RespBatchByInBox.DataBean o, int position) {
                return false;
            }
        });
        recyclerview.setAdapter(typeAdapter);
        recyclerview.addItemDecoration(new TempRVDividerDecoration(Color.parseColor("#EEEFF3"), TempDensityUtil.dip2px(ActCreateProductBox.this, 1.0f)));
        typeAdapter.updateRefresh(datas);
    }

    /**
     * 创建PopupWindow
     */
    protected void initLinesWindow() {
       if (respBatchByInBox.getData()==null||respBatchByInBox.getData().isEmpty()){
           showSnackBar(act_create_pici_rootView,"没有获取到批次数据！请重试",2, Snackbar.LENGTH_LONG);
           preProduct.getBatchByInBox();
           return;
       }
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getLayoutInflater().inflate(R.layout.dialog_check_list_layout, null,
                false);
        TextView title = (TextView) popupWindow_view.findViewById(R.id.dialog_check_list_title);
        TextView dialog_check_list_ok = (TextView) popupWindow_view.findViewById(R.id.dialog_check_list_ok);
        TempRefreshRecyclerView act_create_pici_check_recycler = (TempRefreshRecyclerView) popupWindow_view.findViewById(R.id.act_create_pici_check_recycler);
        initTypeReceiverview(act_create_pici_check_recycler, respBatchByInBox.getData());
        dialog_check_list_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popwindowLines.dismiss();
                popwindowLines = null;
                dialog_create_product_box_index1.setText(checkedBatchData == null ? "" : checkedBatchData.getCode());
            }
        });
        TextView dialog_check_list_cancel = (TextView) popupWindow_view.findViewById(R.id.dialog_check_list_cancel);
        dialog_check_list_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popwindowLines.dismiss();
            }
        });
        title.setText("创建箱子");
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popwindowLines = new PopupWindow(popupWindow_view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popwindowLines.setOutsideTouchable(false);
        // 设置动画效果
        popwindowLines.setAnimationStyle(R.style.popwin_anim_style);
        popwindowLines.setBackgroundDrawable(new BitmapDrawable(null, ""));
    }

    private void showTypePop() {
        if (null == popwindowLines) {
            initLinesWindow();
        }
        if (popwindowLines!=null&&!popwindowLines.isShowing()) {
            popwindowLines.showAtLocation(act_create_pici_rootView, Gravity.CENTER, 0, 0);
        }
    }
}
