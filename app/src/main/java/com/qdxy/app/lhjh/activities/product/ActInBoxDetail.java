package com.qdxy.app.lhjh.activities.product;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.lf.tempcore.tempActivity.TempActivity;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVCommonAdapter;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVHolder;
import com.qdxy.app.lhjh.MyApplication;
import com.qdxy.app.lhjh.R;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


/**装箱详情
 * Created by KY on 2016/11/29.
 */

public class ActInBoxDetail extends TempActivity{
    private final String TAG = "ActInBoxDetail";
    private String mId;
    private boolean isInBox;
    @Bind(R.id.act_inbox_detail_name_textv)TextView act_inbox_detail_name_textv;
    @Bind(R.id.act_inbox_detail_time_textv)TextView act_inbox_detail_time_textv;
    @Bind(R.id.act_inbox_detail_type_textv)TextView act_inbox_detail_type_textv;
    @Bind(R.id.prd_inbox_commit_btn)    AppCompatButton prd_inbox_commit_btn;
    @Bind(R.id.act_inbox_detail_recyclerView)RecyclerView act_inbox_detail_recyclerView;

    private PreProduct preProduct;
    private ViewProductI viewProductI;
    private TempRVCommonAdapter<RespInBoxDetail.DataBean.ProductsBean> adapter;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_product_in_box_detail_layout);
    }

    @Override
    protected void findViews() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        initToolbar(toolbarTop, "装箱详情");
    }

    @Override
    protected void setListeners() {
        viewProductI = new ViewProductI() {
            @Override
            public void onMachiningCountByProcedure(RespProductionCount data) {

            }

            @Override
            public void onChangeTool(String message) {

            }

            @Override
            public void onProductListSucceed(RespProductList data) {

            }

            @Override
            public void onFeedingNumSucceed(RespFeedNum data) {

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
                Debug.info(TAG,"清点完成");
                setResult(200);
                finish();
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
                Debug.info(TAG, "获取装箱详情数据succeed");
                act_inbox_detail_name_textv.setText(data.getData().getCreationUserName());
                act_inbox_detail_time_textv.setText(data.getData().getCreationTime());
                act_inbox_detail_type_textv.setText(data.getData().getProductTypeCode());
                initList(act_inbox_detail_recyclerView,data.getData().getProducts());
            }

            @Override
            public void getBatchByInBox(RespBatchByInBox data) {
//                Debug.info(TAG, "获取装箱料批次列表succeed");
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
                superViewMessage(message);
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
        mId = getIntent().getStringExtra("id");
        Debug.info(TAG,"获取到id="+mId);
        if (!TextUtils.isEmpty(mId)){
            preProduct.requestProductBox(mId);
        }
       boolean isHandler = getIntent().getBooleanExtra("isHandle",true);//是否清点
        if (!isHandler){
            //为清点显示清点按钮
            prd_inbox_commit_btn.setVisibility(View.VISIBLE);
        }
    }
    @OnClick({R.id.prd_inbox_commit_btn})
    @Override
    protected void OnViewClicked(View v) {
        switch (v.getId()){
            case R.id.prd_inbox_commit_btn:
                preProduct.productBoxStatus(mId,2);
                break;
        }
    }
    private void initList(RecyclerView rv, List<RespInBoxDetail.DataBean.ProductsBean> listData){
            rv.setLayoutManager(new LinearLayoutManager(ActInBoxDetail.this));
        if (adapter==null){
            adapter = new TempRVCommonAdapter<RespInBoxDetail.DataBean.ProductsBean>(ActInBoxDetail.this,R.layout.item_inbox_detail_layout) {
                @Override
                public void bindItemValues(TempRVHolder holder, RespInBoxDetail.DataBean.ProductsBean productsBean) {
                    holder.setText(R.id.item_inbox_detail_productCode,productsBean.getProductCode());
                }
            };
            rv.setAdapter(adapter);
        }

        adapter.updateRefresh(listData);
    }
}
