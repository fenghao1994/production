package com.qdxy.app.lhjh.activities.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempFragment.TempFragment;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullablePreDefault;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullableViewI;
import com.lf.tempcore.tempModule.tempRemotComm.TempRemotAPIConnecter;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVCommonAdapter;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVHolder;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRefreshRecyclerView;
import com.qdxy.app.lhjh.MyApplication;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.activities.product.PreProduct;
import com.qdxy.app.lhjh.activities.product.RespBatchByInBox;
import com.qdxy.app.lhjh.activities.product.RespFeedNum;
import com.qdxy.app.lhjh.activities.product.RespInBoxDetail;
import com.qdxy.app.lhjh.activities.product.RespMachineOperation;
import com.qdxy.app.lhjh.activities.product.RespProductList;
import com.qdxy.app.lhjh.activities.product.RespProductionCount;
import com.qdxy.app.lhjh.activities.product.RespSendMatrialList;
import com.qdxy.app.lhjh.activities.product.ViewProductI;
import com.qdxy.app.lhjh.api.TempAPI;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit2.Call;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by KY on 2016/10/11.
 */

public class FragTouLiaoV2 extends TempFragment {
    private final String TAG = "FragTouLiaoV2";
    @Bind(R.id.body_id_touliao_nestedlayout_recycler_view)    TempRefreshRecyclerView mRecyclerView;
    @Bind(R.id.frag_touliao_pici_textview)    TextView frag_touliao_pici_textview;//批次
    @Bind(R.id.frag_touliao_product_code)    EditText frag_touliao_product_code;//编号
    @Bind(R.id.frag_touliao_product_code_confirm)    EditText frag_touliao_product_code_confirm;//确认编号
    @Bind(R.id.parent_view)    LinearLayout parentView;
    private RespBatchByInBox respBatchByInBox;//批次数据
    private PreProduct preProduct;
    private ViewProductI viewProductI;
    private TempPullablePreDefault<RespSendMatrialList> pullableDefault;
    private TempPullableViewI<RespSendMatrialList> pullableViewI;
    private TempRVCommonAdapter<RespSendMatrialList.DataBean.DatasBean> adapter;

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Debug.info(TAG, "initViews");
        return inflater.inflate(R.layout.frag_tou_liao_v2_layout, null);
    }

    @Override
    protected void setListeners(View view, @Nullable Bundle savedInstanceState) {
        pullableViewI = new TempPullableViewI<RespSendMatrialList>() {
            @Override
            public void onInit(RespSendMatrialList o) {

            }

            @Override
            public void onRefresh(RespSendMatrialList o) {

                adapter.updateRefresh(o.getData().getDatas());
            }

            @Override
            public void onLoadmore(RespSendMatrialList o) {
                adapter.updateLoadMore(o.getData().getDatas());
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
                superViewError(parentView,code,message);
            }
        };
        pullableDefault = new TempPullablePreDefault<RespSendMatrialList>(pullableViewI) {
            @Override
            public Call<RespSendMatrialList> createObservable(int queryPage, int querysize, int currentPage) {
                return TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).getBySendMaterial(queryPage+"");
            }
        };
        viewProductI = new ViewProductI() {
            @Override
            public void onMachiningCountByProcedure(RespProductionCount data) {

            }

            @Override
            public void onChangeTool(String message) {

            }

            @Override
            public void onFeedingNumSucceed(RespFeedNum data) {

            }

            @Override
            public void getroceduresByBatch(RespBatchByInBox data) {

            }

            @Override
            public void getBatchBySendMaterial(RespBatchByInBox data) {
                Debug.info(TAG, "获取能投料批次列表succeed");
                respBatchByInBox =data;
//                initListData(mRecyclerView, itemData);
//                frag_touliao_pici_textview = (TextView) headerView.findViewById(R.id.frag_touliao_pici_textview);
//                frag_touliao_product_code = (EditText) headerView.findViewById(R.id.frag_touliao_product_code);
//                frag_touliao_product_code_confirm = (EditText) headerView.findViewById(R.id.frag_touliao_product_code_confirm);
                frag_touliao_pici_textview.setText((data.getData() == null || data.getData().isEmpty()) ? "" : data.getData().get(0).getCode());
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
                superViewMessage(parentView,message);
                pullableDefault.requestRefresh();
                EventBus.getDefault().post("投料");
            }

            @Override
            public void touliaoFailed(String message) {
//                pullableDefault.requestRefresh();
                superViewError(parentView,TempErrorCode.ERROR_FAILED,message);
            }

            @Override
            public void onNextSucceed(String message) {

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
                superViewMessage(parentView,message);
            }

            @Override
            public void viewError(TempErrorCode code, String message) {
                superViewError(parentView, code, message);
            }
        };
        preProduct = new PreProduct(viewProductI);
    }

    @Override
    protected void bundleValues(View view, @Nullable Bundle savedInstanceState) {
        Debug.info(TAG, "bundleValues");
        preProduct.getBatchBySendMaterial();
        initListData();
        pullableDefault.requestRefresh();
//        view.requestLayout();
//        mRecyclerView = (TempRefreshRecyclerView) view.findViewById(R.id.body_id_touliao_nestedlayout_recycler_view);
//        List<String> itemData = new ArrayList<>();
//        itemData.add("1");
//        itemData.add("1");
//        itemData.add("1");
//        itemData.add("1");
//        itemData.add("1");


    }

        @OnClick({R.id.touliao_btn})
    @Override
    protected void OnViewClicked(View v) {
        switch (v.getId()) {
            case R.id.touliao_btn://投料
                Debug.info(TAG,"点击投料");
                //隐藏键盘
                InputMethodManager mInputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                 mInputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

//                adapter.notifyDataSetChanged();
//                InputMethodManager manager = (InputMethodManager) (getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
//                manager.hideSoftInputFromWindow(token,
//                        InputMethodManager.HIDE_NOT_ALWAYS);
//                superViewError(parentView,TempErrorCode.ERROR_FAILED, "text");
//                if ()else{
//                preProduct.createProduction();
                if (TextUtils.isEmpty(frag_touliao_pici_textview.getText().toString().trim())){
                        showSnackBar(parentView,"产品批次不能为空！",2, Snackbar.LENGTH_LONG);
                }else if(TextUtils.isEmpty(frag_touliao_product_code.getText().toString().trim())){
                    showSnackBar(parentView,"产品编号不能为空！",2, Snackbar.LENGTH_LONG);
                }else if(TextUtils.isEmpty(frag_touliao_product_code_confirm.getText().toString().trim())){
                    showSnackBar(parentView,"产品编号不能为空！",2, Snackbar.LENGTH_LONG);
                } else if (!frag_touliao_product_code_confirm.getText().toString().trim().equals(frag_touliao_product_code.getText().toString().trim())) {
                    showSnackBar(parentView,"产品编号不一致！",2, Snackbar.LENGTH_LONG);
                }else{
                    String code = String.format("%s%s", frag_touliao_pici_textview.getText().toString().trim(), frag_touliao_product_code_confirm.getText().toString().trim());
                    preProduct.createProduction(respBatchByInBox.getData().get(0).getId()+"",code);
                }
                break;
        }
    }

    private void initListData() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TempRVCommonAdapter<RespSendMatrialList.DataBean.DatasBean>(getActivity(), R.layout.item_tou_liao_layout) {
            @Override
            public void bindItemValues(TempRVHolder holder, RespSendMatrialList.DataBean.DatasBean s) {
                holder.setText(R.id.item_touliao_pici_textv,s.getProductionBatchCode());
                holder.setText(R.id.item_touliao_time_textv,s.getCreationTime());
                holder.setText(R.id.item_touliao_num_textv,s.getProductCode().replace(s.getProductionBatchCode(),""));
                holder.setText(R.id.item_touliao_remark_textv,s.getRemark());
            }
        };
       /* adapter.addHeader(new TempRVItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
               return getActivity().getLayoutInflater().inflate(R.layout.body_tou_liao_creat_layout,null,false);
//                return null;
            }

            @Override
            public void bindItemValues(View headerView) {
                 }
        });*/
        mRecyclerView.setAdapter(adapter);
        adapter.setMore(new TempRVCommonAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pullableDefault.requestLoadMore();
            }
        });
        mRecyclerView.setRefreshListener(new TempRefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullableDefault.requestRefresh();
            }
        });
//        adapter.updateRefresh(itemData);
    }
}
