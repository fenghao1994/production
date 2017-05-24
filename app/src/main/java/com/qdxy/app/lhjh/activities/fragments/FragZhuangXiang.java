package com.qdxy.app.lhjh.activities.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempFragment.TempFragment;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullablePreDefault;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullableViewI;
import com.lf.tempcore.tempModule.tempRemotComm.TempRemotAPIConnecter;
import com.lf.tempcore.tempViews.tempRecyclerView.OnItemClickListener;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVCommonAdapter;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVHolder;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRefreshRecyclerView;
import com.qdxy.app.lhjh.MyApplication;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.activities.product.ActCreateProductBox;
import com.qdxy.app.lhjh.activities.product.ActInBoxDetail;
import com.qdxy.app.lhjh.activities.product.PreProduct;
import com.qdxy.app.lhjh.activities.product.RespBatchByInBox;
import com.qdxy.app.lhjh.activities.product.RespFeedNum;
import com.qdxy.app.lhjh.activities.product.RespInBoxDetail;
import com.qdxy.app.lhjh.activities.product.RespMachineOperation;
import com.qdxy.app.lhjh.activities.product.RespProductBox;
import com.qdxy.app.lhjh.activities.product.RespProductList;
import com.qdxy.app.lhjh.activities.product.RespProductionCount;
import com.qdxy.app.lhjh.activities.product.ViewProductI;
import com.qdxy.app.lhjh.api.TempAPI;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit2.Call;

/**
 * Created by KY on 2016/10/9.
 * 装箱
 */

public class FragZhuangXiang extends TempFragment {
    private final String TAG = "FragZhuangXiang";
    @Bind(R.id.parent_view)
    CoordinatorLayout parentView;
    @Bind(R.id.frag_zhuang_xiang_RecyclerView)
    TempRefreshRecyclerView mRecyclerView;
    private PreProduct preProduct;
    private ViewProductI viewProductI;
    private int checkedBoxId;//选中箱子id
    private List<String> checkedIds;
//    private RespBatchByInBox respBatchByInBox;//批次数据
    private List<RespProductList.DataBean.DatasBean> respProductList;//未装箱产品数据

    private TempPullablePreDefault<RespProductBox> pullablePreDefault;
    private TempPullableViewI<RespProductBox> pullableViewI;
    private TempRVCommonAdapter<RespProductBox.DataBean.DatasBean> adapter;
    private RespProductBox.DataBean.DatasBean checkItemData;

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Debug.info("启动装箱fragment");
        return inflater.inflate(R.layout.frag_zhuang_xiang_layout, null);
    }

    @Override
    protected void setListeners(View view, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(FragZhuangXiang.this);
        pullableViewI = new TempPullableViewI<RespProductBox>() {
            @Override
            public void onInit(RespProductBox respProductBox) {

            }

            @Override
            public void onRefresh(RespProductBox respProductBox) {
                adapter.updateRefresh(respProductBox.getData().getDatas());
            }

            @Override
            public void onLoadmore(RespProductBox respProductBox) {
                adapter.updateLoadMore(respProductBox.getData().getDatas());
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
        pullablePreDefault = new TempPullablePreDefault<RespProductBox>(pullableViewI) {
            @Override
            public Call<RespProductBox> createObservable(int queryPage, int querysize, int currentPage) {
                return TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).getProductBoxList("0", "0", queryPage + "");
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
            public void onInBoxSucceed(String message) {
                Debug.info(TAG, "装箱succeed");
                superViewMessage(parentView, message);
                pullablePreDefault.requestRefresh();
            }

            @Override
            public void onCreateProductSucceed(String message) {
                Debug.info(TAG, "创建箱子succeed");
                superViewMessage(parentView, message);
                pullablePreDefault.requestRefresh();
            }

            @Override
            public void onProductListSucceed(RespProductList data) {
                if (data.getData().getDatas() == null || data.getData().getDatas().isEmpty()) {
                    viewMsg("没有可装箱的产品！");
                } else {
                    respProductList = data.getData().getDatas();
                    showChangeKnife();
                }
            }

            @Override
            public void onInBoxDetailSucceed(RespInBoxDetail data) {

            }

            @Override
            public void getBatchBySendMaterial(RespBatchByInBox data) {

            }

            @Override
            public void touliaoSucceed(String message) {

            }

            @Override
            public void touliaoFailed(String message) {

            }

            @Override
            public void onMachineOperationSucceed(RespMachineOperation data) {

            }

            @Override
            public void onMachineOperationFailed(String message) {

            }

            @Override
            public void getBatchByInBox(RespBatchByInBox data) {
                Debug.info(TAG, "获取能装箱批次列表succeed");
//                respBatchByInBox = data;
//                if (data == null || data.getData() == null || data.getData().isEmpty()) {
//                    Debug.info(TAG, "没有获取到批次数据无法获取装箱列表");
//                } else {
//                    Debug.debug(TAG, "请求装箱列表数据");

//                    preProduct.getProductBoxList(data.getData().get(0).getId()+"","0");
//                }
//                (data.getData() == null || data.getData().isEmpty()) ? "" : data.getData().get(0).getCode()
            }

            @Override
            public void onFailed(int who, String message) {

            }

            @Override
            public void onNextSucceed(String message) {

            }

            @Override
            public void onUpdateStatusSucceed(String message) {
                superViewMessage(parentView,message);
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
                superViewMessage(parentView, message);
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
        Debug.info(TAG,"装箱批次获取");
        initListData(mRecyclerView);
        pullablePreDefault.requestRefresh();
//        preProduct.getBatchByInBox();
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.frag_zhuang_xiang_RecyclerView);
//        List<String> itemData = new ArrayList<>();
//        itemData.add("0");
//        itemData.add("1");
//        itemData.add("2");
//        itemData.add("3");
//        itemData.add("4");
//


//        preProduct.getProductBoxList();
    }

    @OnClick(R.id.fab)
    @Override
    protected void OnViewClicked(View v) {
        switch (v.getId()) {
            case R.id.fab:
                startActivity(new Intent(getActivity(), ActCreateProductBox.class));
                break;
        }
    }

    public void onEventMainThread(String event) {
        Debug.info(TAG, "event 接受到数据" + event);
        if (event.equals("200")) {
            pullablePreDefault.requestRefresh();
        }
       /* String msg = "onEventMainThread收到了消息：" + event.getMsg();
        Log.d("harvic", msg);
        tv.setText(msg);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();*/
    }

    private void initListData(TempRefreshRecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (adapter==null){
            adapter = new TempRVCommonAdapter<RespProductBox.DataBean.DatasBean>(getActivity(), R.layout.item_zhuang_xiang_layout) {
                @Override
                public void bindItemValues(TempRVHolder holder, final RespProductBox.DataBean.DatasBean s) {
                    holder.setText(R.id.item_zhuangxiang_name, "装箱编号：" + s.getName());
                    holder.setText(R.id.item_zhuangxiang_productTypeCode, "产品类型：" + s.getProductTypeCode());
                    holder.setText(R.id.item_zhuangxiang_num, s.getCount() + "/" + s.getSize());
                    holder.setOnClickListener(R.id.item_zhuang_xiang_btn, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkItemData =s;
//                            checkedBoxId = s.getId();
                            preProduct.getProductList(checkItemData.getId()+"");
//                        Toast.makeText(getActivity(), "content="+s, Toast.LENGTH_SHORT).show();
                        }
                    });
                    holder.setOnClickListener(R.id.item_zhuang_xiang_commit_btn, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (s.getCount()==0){
                                showSnackBar(parentView,"请先装箱！",2, Snackbar.LENGTH_LONG);
                            }else if (s.getCount() < s.getSize()) {
//                            showSnackBar(parentView,);
                                showConfirmationDialog(getActivity(), true, "", "当前箱子未装满,确定提交该箱子吗？", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO: 2016/11/28  提交箱子
                                        preProduct.upDateProductBox(s.getId()+"","1");
                                    }
                                }, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                            } else {
                                showConfirmationDialog(getActivity(), true, "", "确定提交该箱子嘛？", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO: 2016/11/28  提交箱子
                                        preProduct.upDateProductBox(s.getId()+"","1");
                                    }
                                }, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                            }
//                        Toast.makeText(getActivity(), "content=" + s, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            };
            adapter.setMore(new TempRVCommonAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    pullablePreDefault.requestLoadMore();
                }
            });
            adapter.setOnItemClickListener(new OnItemClickListener<RespProductBox.DataBean.DatasBean>() {
                @Override
                public void onItemClick(ViewGroup parent, View view, RespProductBox.DataBean.DatasBean o, int position) {
                    Intent intent = new Intent(getActivity(), ActInBoxDetail.class);
                    intent.putExtra("id",o.getId()+"");
                    startActivity(intent);

                }

                @Override
                public boolean onItemLongClick(ViewGroup parent, View view, RespProductBox.DataBean.DatasBean o, int position) {
                    return false;
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

    /**
     * 更换刀具对话框
     */
    private void showChangeKnife() {
        String[] data = new String[respProductList.size()];
        boolean[] choose = new boolean[respProductList.size()];
        checkedIds = new ArrayList<>();
        for (int i = 0; i < respProductList.size(); i++) {
            data[i] = respProductList.get(i).getProductCode();
            choose[i] = respProductList.get(i).isChecked();
        }
        /*data =new String[]{"产品编号：wd120600909101","产品编号：wd120600909102",
                "产品编号：wd120600909103","产品编号：wd120600909104",
                "产品编号：wd120600909105","产品编号：wd120600909105",
                "产品编号：wd120600909105","产品编号：wd120600909105",
                "产品编号：wd120600909105","产品编号：wd120600909105",
                "产品编号：wd120600909105","产品编号：wd120600909105",
                "产品编号：wd120600909105","产品编号：wd120600909105"};*/
//       choose =new boolean[]{true,false,false,false,false,false,false,false,false,false,false,false,false,false};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMultiChoiceItems(data, choose, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
               int shouleChecedCount =checkItemData.getSize()-checkItemData.getCount();
//                Debug.debug(TAG, "点击=" + which + "状态=" + isChecked);
                respProductList.get(which).setChecked(isChecked);
            }
        }).setNegativeButton("取消", null).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int a = 0; a < respProductList.size(); a++) {
                    if (respProductList.get(a).isChecked()) {
                        checkedIds.add(respProductList.get(a).getId() + "");
                    }
                }
                if (checkedIds.isEmpty()) {
                    showToast("请选在至少一个产品");
//                    showSnackBar(parentView,"没有课提交",2, Snackbar.LENGTH_LONG);
                } else {

                    preProduct.requestInBox(checkItemData.getId() + "", (String[]) checkedIds.toArray(new String[checkedIds.size()]));
                }
            }
        }).show();


    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(FragZhuangXiang.this);//反注册EventBus
        super.onDestroy();

    }
}
