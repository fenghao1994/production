package com.qdxy.app.lhjh.activities.product;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import com.qdxy.app.lhjh.activities.deviceToolChange.ActDeviceToolChange;
import com.qdxy.app.lhjh.api.TempAPI;
import com.qdxy.app.lhjh.bean.RespProductLines;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;


/**产品加工第二版本
 * Created by mac on 2017/2/13.
 */

public class ActProduction2 extends TempActivity {
    private final String TAG ="ActProduction2";
    private PreProduct preProduct;
    private ViewProductI viewProductI;
    private Toolbar.OnMenuItemClickListener onMenuItemClick;
    private String mPiCiCode,mProcedureName;
    private int mProceduresId=-1,mPiCiId=-1,mNumber=-1,mType=-1,mPrdSelectedPoi=-1,mFeedingSelectedPoi=-1;//工序id；批次id；工序号：0投料工序；工序类型 0：普通工序 1：检测工序;被选中的加工item位置；被选中的投料item位置
    private RespBatchByInBox mDataPiCi,mDataProdures;
    private TempRVCommonAdapter<RespProduction2.DataBean.DatasBean> mListAdapter,mFeedAdapter;
    private TempPullablePreDefault<RespProduction2> mPullablePre;
//    private List<RespProduction2.DataBean.DatasBean> mFeedingData;//投料备选数据
    private PopupWindow popwindowLines;//检测弹出框
    private TempRVCommonAdapter lineAdapter;
    private String checkStatus;
    @Bind(R.id.parent_view)
    LinearLayout parentView;
    @Bind(R.id.act_production2_sp)
    AppCompatSpinner mSP;
    @Bind(R.id.act_production2_sp1)
    AppCompatSpinner mSP1;
//    @Bind(R.id.act_production2_alarm_btn)
//    AppCompatButton mBtnAlarm;//生成投料列表按钮
    @Bind(R.id.act_production2_createNum_btn)
    AppCompatButton mBtnCreateNum;//生成投料列表按钮
    @Bind(R.id.act_production2_edit_num)
    EditText mEditNnum;//编号编辑框
    @Bind(R.id.act_production2_btn_remark)
    EditText mEditNRemark;//备注编辑框
    @Bind(R.id.act_production2_btn_feeding)
    AppCompatButton mBtnFeeding;//投料
    @Bind(R.id.act_production2_btn_selfCheck)
    AppCompatButton mBtnSelfCheck;//自检
    @Bind(R.id.act_production2_btn_complete)
    AppCompatButton mBtnComplete;//加工完成
    @Bind(R.id.act_production2_btn_sendCheck)
    AppCompatButton mBtnSendCheck;//送检
    @Bind(R.id.act_production2_btn_sendRepair)
    AppCompatButton mBtnSendRepair;//送修
    @Bind(R.id.act_production2_btn_checkComplete)
    AppCompatButton mBtnCheckComplete;//检测
    @Bind(R.id.act_production2_RV)
    TempRefreshRecyclerView mRV_production;
    @Bind(R.id.act_production2_RV1)
    TempRefreshRecyclerView mRV_Feed;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_production2_layout);
    }

    @Override
    protected void findViews() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        initToolbar(toolbarTop, "产品加工");
        onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                String msg = "";
                switch (menuItem.getItemId()) {
                    case R.id.action_query:
//                        msg += "加工统计";
                        preProduct.getMachiningCountByProcedure();
                        break;
//                    case R.id.action_receive_accessories:
//                        msg += "辅料领用";
//                        showSnackBar("");
//                        break;

                }

//                if(!msg.equals("")) {
//                    Toast.makeText(getTempContext(), msg, Toast.LENGTH_SHORT).show();
//                }
                return true;
            }
        };
        toolbarTop.setOnMenuItemClickListener(onMenuItemClick);
//        toolbarTop.set
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.production_menu, menu);

        return true;
    }

    /**
     * 检测并获取当前投料编号
     */
    private void requestFeedNum(){
        if (null==mFeedAdapter.getData()){
//            mFeedingData = new ArrayList<>();
            preProduct.getBySendMaterialNumber(mPiCiId);
        }else if(mFeedAdapter.getData().isEmpty()){
            preProduct.getBySendMaterialNumber(mPiCiId);
        }else{
            showSnackBar("待投料编号还未投料完，请投料完之后再创建!",2,Snackbar.LENGTH_LONG);
        }

    }
    /**
     * 创建投料数据
     * @param num
     * @param length
     */
    private void createFeedingData(int num,int length){
        List<RespProduction2.DataBean.DatasBean> mFeedingData=new ArrayList<>();
        if(length<=0){
            length=3;//默认长度为3
        }
        for (int i = 0; i < 5; i++) {
            RespProduction2.DataBean.DatasBean item = new RespProduction2.DataBean.DatasBean();
            item.setProductionBatchCode("test");
            String reg="%0"+length+"d";
            String data =String.format(reg,num+i);
            item.setProductCode("test"+data);
            mFeedingData.add(item);
        }
        mFeedAdapter.updateRefresh(mFeedingData);
    }
    private void reset(){
        mEditNnum.getText().clear();
        mEditNRemark.getText().clear();
        if (mPrdSelectedPoi!=-1&&mListAdapter.getData()!=null){
            mListAdapter.getData().get(mPrdSelectedPoi).setSelected(false);
            mListAdapter.notifyDataSetChanged();
        }
        if (mFeedingSelectedPoi!=-1&&mFeedAdapter.getData()!=null){
            mFeedAdapter.getData().get(mFeedingSelectedPoi).setSelected(false);
            mFeedAdapter.notifyDataSetChanged();
        }
        mPrdSelectedPoi=-1;
        mFeedingSelectedPoi=-1;
        updateBtnStatus(false);
    }
    /**
     * 显示投料数据
     */
    private void showFeedList(){
        mBtnCreateNum.setVisibility(View.VISIBLE);
        mRV_Feed.setVisibility(View.VISIBLE);
        mRV_production.setVisibility(View.INVISIBLE);
//        mFeedAdapter.updateRefresh(mFeedingData);
//        mListAdapter.set
    }
    private void showProductionList(){
        mBtnCreateNum.setVisibility(View.INVISIBLE);
        mRV_Feed.setVisibility(View.INVISIBLE);
        mRV_production.setVisibility(View.VISIBLE);
//        mFeedAdapter.updateRefresh(mFeedingData);
    }
    private class MySpinnerItemSelectedListener implements AppCompatSpinner.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            TextView txtvwSpinner=(TextView) parent.getChildAt(position);
            txtvwSpinner.setPadding(16,16,16,16);
            txtvwSpinner.setTextColor(Color.RED);
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//            lp.
// 根据选择项来设置显示的字体颜色


        }



        @Override

        public void onNothingSelected(AdapterView<?> parent) {

// TODO Auto-generated method stub

        }

    }
    @Override
    protected void setListeners() {
        //批次spinner
        mSP.setOnItemSelectedListener(new MySpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPiCiId = mDataPiCi.getData().get(position).getId();
                mPiCiCode=mDataPiCi.getData().get(position).getName();
                Debug.info(TAG,"被选中的的pici id="+mPiCiId);
                Debug.info(TAG,"被选中的的pici号="+mPiCiCode);
                updateBtnStatus(false);
                reset();
                mFeedAdapter.clear();//清除投料数据
                if (mType!=-1){
                    Debug.info(TAG,"工序加载完成");
                    //当type为-1，表示工序数据已初始化好
                    if (mNumber==1){
                        //投料工序
                        showFeedList();//显示待投料数据
//                        mListAdapter.clear();
//                        mListAdapter.updateRefresh(new ArrayList<RespProduction2.DataBean.DatasBean>());
                    }else{
                        showProductionList();//显示工序数据列表
                        mListAdapter.clear();//预先清除旧数据
                        mPullablePre.requestRefresh();//请求工序数据列表
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //工序spinner
        mSP1.setOnItemSelectedListener(new MySpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mProceduresId = mDataProdures.getData().get(position).getId();
                mNumber =mDataProdures.getData().get(position).getNumber();
                mProcedureName = mDataProdures.getData().get(position).getName();
//
//                if (mNumber!=1){
//                    // TODO: 2017/2/14 隐藏生成编号按钮
//                }else{
//                    // TODO: 2017/2/14 显示生成编号按钮
//                }
                mType = mDataProdures.getData().get(position).getType();
//                switch (mType){
//                    case 0:
//                        //普通工序
//                        break;
//                    case 1:
//                        //检测工序
//                        break;
//               }
                updateBtnStatus(false);
                reset();
                Debug.info(TAG,"被选中的的工序 id="+mProceduresId);
                if (mPiCiId!=-1){
                    //当type为-1，表示批次数据已初始化好
                    Debug.info(TAG,"批次加载完成加载完成");
                    if (mNumber==1){
                        //投料工序
//                        mListAdapter.clear();
                        showFeedList();//显示待投料数据
//                        mListAdapter.updateRefresh(new ArrayList<RespProduction2.DataBean.DatasBean>());
                    }else{
                        showProductionList();
                        mListAdapter.clear();
                        mPullablePre.requestRefresh();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mPullablePre = new TempPullablePreDefault<RespProduction2>(new TempPullableViewI<RespProduction2>() {
            @Override
            public void onInit(RespProduction2 data) {

            }

            @Override
            public void onRefresh(RespProduction2 data) {
                mPrdSelectedPoi=-1;
                mFeedingSelectedPoi=-1;
                mListAdapter.updateRefresh(data.getData().getDatas());
            }

            @Override
            public void onLoadmore(RespProduction2 data) {
                mListAdapter.updateLoadMore(data.getData().getDatas());
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
                superViewError(code,message);
            }
        }) {
            @Override
            public Call<RespProduction2> createObservable(int queryPage, int querysize, int currentPage) {
                return TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).getList(mPiCiId,mProceduresId,queryPage);
            }
        };
        viewProductI = new ViewProductI() {
            @Override
            public void onMachiningCountByProcedure(RespProductionCount data) {
                StringBuilder sb=new StringBuilder();
                if (data.getData()==null||data.getData().getDatas()==null||data.getData().getDatas().isEmpty()){
                    showMessageDialog(getTempContext(),true,"加工统计","没有加工统计数据",null);
                }else{
                    List<RespProductionCount.DataBean.DatasBean> listResult =  data.getData().getDatas();
                    for (int i = 0; i < listResult.size(); i++) {
                        sb.append(listResult.get(i).getId());
                        sb.append("：");
                        sb.append(listResult.get(i).getName());
                        sb.append("个\n");
                    }
                    showMessageDialog(getTempContext(),true,"加工统计",sb.toString(),null);
                }

            }

            @Override
            public void onChangeTool(String message) {
                showTempDialog(getTempContext(), true, "刀具更换提醒", message, "更换", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getTempContext(), ActDeviceToolChange.class));
                    }
                }, "取消", null);
            }

            @Override
            public void onFeedingNumSucceed(RespFeedNum data) {
                Debug.info(TAG, "获取投料编成功");
                createFeedingData(data.getData().getNumber(),data.getData().getLength());
                showFeedList();
            }

            @Override
            public void getBatchBySendMaterial(RespBatchByInBox data) {
                Debug.info(TAG, "获取批次列表成功");
//                respBatchByInBox =data;
                if (null==data.getData()||data.getData().isEmpty()){
                    showSnackBar("批次数据为空,请检查批次是否创建或启动",2, Snackbar.LENGTH_LONG);
                }else{
                    mDataPiCi=data;
                    ArrayList<String> items = new ArrayList<>();
                    for (int i = 0; i <mDataPiCi.getData().size() ; i++) {
                        items.add(mDataPiCi.getData().get(i).getName());
                    }
                    ArrayAdapter<String> tempAdapter1 =new ArrayAdapter<>(getTempContext(),android.R.layout.simple_spinner_item,items);
                    tempAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    SPAdapter tempAdapter = new SPAdapter(mDataPiCi.getData(),getTempContext());
                    mSP.setAdapter(tempAdapter1);
                }
            }

            @Override
            public void getroceduresByBatch(RespBatchByInBox data) {
                Debug.info(TAG, "获取工序列表成功");
                if (null==data.getData()||data.getData().isEmpty()){
                    showSnackBar("工序数据为空,请检查是否配置工序",2, Snackbar.LENGTH_LONG);
                }else{
                    mDataProdures=data;
                    ArrayList<String> items = new ArrayList<>();
                    for (int i = 0; i <mDataProdures.getData().size() ; i++) {
                        items.add(mDataProdures.getData().get(i).getName());
                    }
                    ArrayAdapter<String> tempAdapter1 =new ArrayAdapter<>(getTempContext(),android.R.layout.simple_spinner_item,items);
                    tempAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    SPAdapter tempAdapter = new SPAdapter(mDataProdures.getData(),getTempContext());
                    mSP1.setAdapter(tempAdapter1);
                }
            }

            @Override
            public void onProductListSucceed(RespProductList data) {
                Debug.info(TAG, "装箱成功succeed");
            }

            @Override
            public void onInBoxDetailSucceed(RespInBoxDetail data) {

            }

            @Override
            public void onCreateProductSucceed(String message) {

            }

            @Override
            public void getBatchByInBox(RespBatchByInBox data) {


            }

            @Override
            public void onInBoxSucceed(String message) {

            }

            @Override
            public void onFailed(int who, String message) {

            }

            @Override
            public void touliaoSucceed(String message) {
                superViewMessage(message);
                mFeedAdapter.getData().remove(mFeedingSelectedPoi);
                mFeedAdapter.notifyDataSetChanged();
                mFeedingSelectedPoi=-1;
//                updateBtnStatus(false);
                reset();
//                pullableDefault.requestRefresh();
//                EventBus.getDefault().post("投料");
            }

            @Override
            public void touliaoFailed(String message) {
//                pullableDefault.requestRefresh();
                superViewError(TempErrorCode.ERROR_FAILED,message);
            }

            @Override
            public void onNextSucceed(String message) {
                mPullablePre.requestRefresh();
                reset();
            }

            @Override
            public void onUpdateStatusSucceed(String message) {

            }

            @Override
            public void onMachineOperationSucceed(RespMachineOperation data) {

            }

            @Override
            public void onMachineOperationFailed(String message) {

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
                superViewError( code, message);
            }
        };
        preProduct = new PreProduct(viewProductI);
    }

    @Override
    protected void bindValues() {
        preProduct.getlistByMachining();
        preProduct.getroceduresByBatch();
        initListData();
    }
    @OnClick({R.id.act_production2_createNum_btn,R.id.act_production2_btn_feeding,
            R.id.act_production2_btn_selfCheck,R.id.act_production2_btn_complete,
            R.id.act_production2_btn_sendRepair,R.id.act_production2_btn_sendCheck,
            R.id.act_production2_btn_checkComplete,R.id.act_production2_alarm_btn})
    @Override
    protected void OnViewClicked(View v) {
        switch (v.getId()){
            case R.id.act_production2_createNum_btn:
                //创建投料数据
//                createFeedingData();
                requestFeedNum();
                break;
            case R.id.act_production2_btn_feeding:
                //投料
                InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                mInputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                if (mPiCiId==-1){
                    showSnackBar("产品批次不能为空！",2, Snackbar.LENGTH_LONG);
                }else if(mProceduresId==-1){
                    showSnackBar("工序不能为空！",2, Snackbar.LENGTH_LONG);
                }else if(TextUtils.isEmpty(mEditNnum.getText().toString().trim())){
                    showSnackBar("产品编号不能为空！",2, Snackbar.LENGTH_LONG);
                } else{
                    String code = String.format("%s%s", mPiCiCode, mEditNnum.getText().toString().trim());
                    preProduct.createProduction(mPiCiId+"",code);
                }
                break;
            case R.id.act_production2_btn_selfCheck:
                //自检提交
                if (mPrdSelectedPoi==-1){
                    showSnackBar("请先选择一个工件",2,Snackbar.LENGTH_LONG);
                }else{
                    showTempDialog(getTempContext(), true, "工件自检提交确认", String.format("确定提交编号为：%s 的工件吗？", mListAdapter.getData().get(mPrdSelectedPoi).getProductCode()), "正常", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            preProduct.machiningParts_next( mListAdapter.getData().get(mPrdSelectedPoi).getId()+ "", 1 + "", mEditNRemark.getText().toString().trim(), "0", false);

                        }
                    }, "异常", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            preProduct.machiningParts_next(mListAdapter.getData().get(mPrdSelectedPoi).getId() + "", 1 + "", mEditNRemark.getText().toString().trim(), "1", false);
                        }
                    });
                }

                break;
            case R.id.act_production2_btn_complete:
                //创建投料数据
                showConfirmationDialog(getTempContext(), true, "工件提交确认", String.format("确定提交编号为：%s 的工件吗？", mListAdapter.getData().get(mPrdSelectedPoi).getProductCode()), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                preProduct.machiningParts_next(mListAdapter.getData().get(mPrdSelectedPoi).getId() + "", 0 + "", "", "", false);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
                break;
            case R.id.act_production2_btn_sendRepair:
                //送修
                if (mPrdSelectedPoi==-1){
                    showSnackBar("请先选择一个工件",2,Snackbar.LENGTH_LONG);
                }else{

                    showConfirmationDialog(getTempContext(), true, "工件送修确认", String.format("确定送修编号为：%s 的工件吗？", mListAdapter.getData().get(mPrdSelectedPoi).getProductCode()), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            preProduct.machiningParts_next(mListAdapter.getData().get(mPrdSelectedPoi).getId() + "", 17 + "", mEditNRemark.getText().toString().trim(), "", false);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                }
                break;
            case R.id.act_production2_btn_sendCheck:
                //送检
                if (mPrdSelectedPoi==-1){
                    showSnackBar("请先选择一个工件",2,Snackbar.LENGTH_LONG);
                }else{

                    showConfirmationDialog(getTempContext(), true, "工件送检确认", String.format("确定送检编号为：%s 的工件吗？", mListAdapter.getData().get(mPrdSelectedPoi).getProductCode()), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            preProduct.machiningParts_next(mListAdapter.getData().get(mPrdSelectedPoi).getId() + "", 14 + "", mEditNRemark.getText().toString().trim(), "", false);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                }
                break;
            case R.id.act_production2_btn_checkComplete:
                //检测
                if (mPrdSelectedPoi==-1){
                    showSnackBar("请先选择一个工件",2,Snackbar.LENGTH_LONG);
                }else {
                    initLinesWindow(mListAdapter.getData().get(mPrdSelectedPoi).getId() + "");
                }
                break;
            case R.id.act_production2_alarm_btn:
                //报警
                showTempDialog(getTempContext(), true, "", String.format("确定为工序：%s 发送报警吗？", mProcedureName), "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        preProduct.workingTimeoutProblemCreate(mProceduresId, "");
                    }
                }, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                break;

        }
    }
    /**
     * 更新操作按钮显示状态
     * @param status    true显示，false 全隐藏
     */
    private void updateBtnStatus(boolean status){
        mBtnFeeding.setVisibility(View.GONE);
        mBtnSelfCheck.setVisibility(View.GONE);
        mBtnComplete.setVisibility(View.GONE);
        mBtnSendCheck.setVisibility(View.GONE);
        mBtnSendRepair.setVisibility(View.GONE);
        mBtnCheckComplete.setVisibility(View.GONE);
        if (!status){

            return;
        }
        if (mNumber==1){
            mBtnFeeding.setVisibility(View.VISIBLE);
        }else if (mType==0){
            mBtnSelfCheck.setVisibility(View.VISIBLE);
            mBtnComplete.setVisibility(View.VISIBLE);
            mBtnSendCheck.setVisibility(View.VISIBLE);
            mBtnSendRepair.setVisibility(View.VISIBLE);
        }else if(mType==1){
            mBtnCheckComplete.setVisibility(View.VISIBLE);
        }

    }
    /**
     * 创建PopupWindow
     */
    protected void initLinesWindow(final String tempId) {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getLayoutInflater().inflate(R.layout.dialog_check_list_layout, null,
                false);
        TextView title = (TextView) popupWindow_view.findViewById(R.id.dialog_check_list_title);
        TextView dialog_check_list_ok = (TextView) popupWindow_view.findViewById(R.id.dialog_check_list_ok);
        TempRefreshRecyclerView act_create_pici_check_recycler = (TempRefreshRecyclerView) popupWindow_view.findViewById(R.id.act_create_pici_check_recycler);
        initlineReceiverview(act_create_pici_check_recycler);
        dialog_check_list_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popwindowLines.dismiss();
                popwindowLines = null;
                if (!checkStatus.equals("-1")) {

                    preProduct.machiningParts_next(tempId, checkStatus, mEditNRemark.getText().toString().trim(), "", true);
                } else {
                    showSnackBar( "未选择检测检测结果", 2, Snackbar.LENGTH_LONG);
                }
            }
        });
        TextView dialog_check_list_cancel = (TextView) popupWindow_view.findViewById(R.id.dialog_check_list_cancel);
        dialog_check_list_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popwindowLines.dismiss();

            }
        });
        title.setText("选择检测结果");
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popwindowLines = new PopupWindow(popupWindow_view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        popwindowLines.setOutsideTouchable(false);
        // 设置动画效果
        popwindowLines.setAnimationStyle(R.style.popwin_anim_style);
        popwindowLines.setBackgroundDrawable(new BitmapDrawable(null, ""));
        if (popwindowLines != null && !popwindowLines.isShowing()) {
            popwindowLines.showAtLocation(parentView, Gravity.CENTER, 0, 0);
        }
    }
    private void initlineReceiverview(TempRefreshRecyclerView recyclerview) {
        checkStatus = "-1";
        List<RespProductLines.DataBean> datas = new ArrayList<>();
        RespProductLines.DataBean item;
        item = new RespProductLines.DataBean();
        item.setChecked(false);
        item.setCode("0");
        item.setName("合格");
        datas.add(item);

        item = new RespProductLines.DataBean();
        item.setChecked(false);
        item.setCode("6");
        item.setName("工废");
        datas.add(item);

        item = new RespProductLines.DataBean();
        item.setChecked(false);
        item.setCode("7");
        item.setName("料废");
        datas.add(item);

        item = new RespProductLines.DataBean();
        item.setChecked(false);
        item.setCode("8");
        item.setName("工二级");
        datas.add(item);

        item = new RespProductLines.DataBean();
        item.setChecked(false);
        item.setCode("9");
        item.setName("料二级");
        datas.add(item);

        item = new RespProductLines.DataBean();
        item.setCode("4");
        item.setChecked(false);
        item.setName("返工");
        datas.add(item);

        lineAdapter = new TempRVCommonAdapter<RespProductLines.DataBean>(getTempContext(), R.layout.item_dialog_check_list_layout) {
            @Override
            public void bindItemValues(TempRVHolder holder, RespProductLines.DataBean s) {
                holder.setText(R.id.item_dialog_check_radioButton, s.getName());
                holder.setChecked(R.id.item_dialog_check_radioButton, s.isChecked());
            }
        };
        recyclerview.setLayoutManager(new LinearLayoutManager(getTempContext()));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        lineAdapter.setOnItemClickListener(new OnItemClickListener<RespProductLines.DataBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, RespProductLines.DataBean o, int position) {
                if (!o.isChecked()) {
                    List<RespProductLines.DataBean> data = lineAdapter.getData();
                    for (int i = 0; i < lineAdapter.getData().size(); i++) {
                        data.get(i).setChecked(false);
                    }
                    data.get(position).setChecked(true);
                    checkStatus = o.getCode();
                    lineAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, RespProductLines.DataBean o, int position) {
                return false;
            }
        });
        recyclerview.setAdapter(lineAdapter);
        recyclerview.addItemDecoration(new TempRVDividerDecoration(Color.parseColor("#EEEFF3"), TempDensityUtil.dip2px(getTempContext(), 1.0f)));

        lineAdapter.updateRefresh(datas);
    }
    private void initListData() {

        //初始化加工列表
        mRV_production.setLayoutManager(new LinearLayoutManager(getTempContext()));
        mListAdapter = new TempRVCommonAdapter<RespProduction2.DataBean.DatasBean>(getTempContext(),R.layout.item_production2_layout) {
            @Override
            public void bindItemValues(TempRVHolder holder, RespProduction2.DataBean.DatasBean respProduction2) {
//                holder.setTextColor(R.id.item_production2_txv_num,R.color.temp_pink_color);
                holder.getView(R.id.item_production2_cardview).setBackgroundResource(respProduction2.isSelected()?R.drawable.shape_prd_selected:R.drawable.shape_prd_unselected);
                holder.setText(R.id.item_production2_txv_num,"产品编号："+respProduction2.getProductCode().replace(respProduction2.getProductionBatchCode(),""));
                holder.setText(R.id.item_production2_txv_time, TextUtils.isEmpty(respProduction2.getCreationTime())?"":respProduction2.getCreationTime());
            }
        };
        mListAdapter.setMore(new TempRVCommonAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPullablePre.requestLoadMore();
            }
        });
        mRV_production.setRefreshListener(new TempRefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullablePre.requestRefresh();
            }
        });
        mListAdapter.setOnItemClickListener(new OnItemClickListener<RespProduction2.DataBean.DatasBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, RespProduction2.DataBean.DatasBean o, int position) {
                if (!o.isSelected()){
                    if (mPrdSelectedPoi!=-1){
                        mListAdapter.getData().get(mPrdSelectedPoi).setSelected(false);
                    }
                    mPrdSelectedPoi=position;
                    mListAdapter.getData().get(position).setSelected(true);
                    mListAdapter.notifyDataSetChanged();

                    //处理选中数据
                    mEditNnum.setEnabled(false);
                    mEditNnum.setText(o.getProductCode().replace(o.getProductionBatchCode(),""));
                    updateBtnStatus(true);
                }
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, RespProduction2.DataBean.DatasBean o, int position) {
                return false;
            }
        });
        mRV_production.setAdapter(mListAdapter);

            //初始化投料列表
        mRV_Feed.setLayoutManager(new LinearLayoutManager(getTempContext()));
        mFeedAdapter = new TempRVCommonAdapter<RespProduction2.DataBean.DatasBean>(getTempContext(),R.layout.item_production2_layout) {
            @Override
            public void bindItemValues(TempRVHolder holder, RespProduction2.DataBean.DatasBean respProduction2) {
//                holder.getView(R.id.item_production2_cardview).setBackgroundResource(respProduction2.isSelected()?R.color.colorPrimaryDark:R.color.white);
//                holder.setTextColor(R.id.item_production2_txv_num,R.color.temp_pink_color);
//                holder.setTextColor(R.id.item_production2_txv_time,respProduction2.isSelected()?R.color.white:R.color.white);
                holder.getView(R.id.item_production2_cardview).setBackgroundResource(respProduction2.isSelected()?R.drawable.shape_prd_selected:R.drawable.shape_prd_unselected);
                holder.setText(R.id.item_production2_txv_num,"产品编号："+respProduction2.getProductCode().replace(respProduction2.getProductionBatchCode(),""));
                holder.setText(R.id.item_production2_txv_time, TextUtils.isEmpty(respProduction2.getCreationTime())?"":respProduction2.getCreationTime());
            }
        };
        mFeedAdapter.setOnItemClickListener(new OnItemClickListener<RespProduction2.DataBean.DatasBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, RespProduction2.DataBean.DatasBean o, int position) {
                if (!o.isSelected()){
                    //处理选中效果
                    if (mFeedingSelectedPoi!=-1){
                        mFeedAdapter.getData().get(mFeedingSelectedPoi).setSelected(false);
                    }
                    mFeedingSelectedPoi=position;
                    mFeedAdapter.getData().get(position).setSelected(true);
//                    mFeedAdapter.notifyItemChanged(position);
                    mFeedAdapter.notifyDataSetChanged();
                    //处理选中数据
                    mEditNnum.setEnabled(true);
                    mEditNnum.setText(o.getProductCode().replace(o.getProductionBatchCode(),""));
                    updateBtnStatus(true);
                }
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, RespProduction2.DataBean.DatasBean o, int position) {
                return false;
            }
        });
        mRV_Feed.setAdapter(mFeedAdapter);
    }
    /*private class SPHolder{
        public TextView name;

    }
    private class SPAdapter extends TempListAdapter<RespBatchByInBox.DataBean,SPHolder>{

         SPAdapter(List<RespBatchByInBox.DataBean> data, Context context) {
            super(data, context, android.R.layout.simple_spinner_item);
        }

        @Override
        protected SPHolder createHolder() {
            return new SPHolder();
        }

        @Override
        protected void initHolder(int position, View v, SPHolder holder) {
            holder.name= (TextView) v.findViewById(android.R.id.text1);
        }

        @Override
        public void bunldHolderValue(int position, SPHolder holder, RespBatchByInBox.DataBean item) {
            holder.name.setText(item.getName());
        }
    }*/
}
