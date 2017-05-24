package com.qdxy.app.lhjh.activities.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lf.tempcore.tempAdapter.TempExpandableListAdapter;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempFragment.TempFragment;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullablePreDefault;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullableViewI;
import com.lf.tempcore.tempModule.tempRemotComm.TempRemotAPIConnecter;
import com.lf.tempcore.tempModule.tempUtils.TempDensityUtil;
import com.lf.tempcore.tempViews.tempPullableViews.PullToRefreshLayout;
import com.lf.tempcore.tempViews.tempRecyclerView.OnItemClickListener;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVCommonAdapter;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVDividerDecoration;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVHolder;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRefreshRecyclerView;
import com.qdxy.app.lhjh.MyApplication;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.activities.deviceToolChange.ActDeviceToolChange;
import com.qdxy.app.lhjh.activities.product.PreProduct;
import com.qdxy.app.lhjh.activities.product.RespBatchByInBox;
import com.qdxy.app.lhjh.activities.product.RespFeedNum;
import com.qdxy.app.lhjh.activities.product.RespInBoxDetail;
import com.qdxy.app.lhjh.activities.product.RespJiaGong2;
import com.qdxy.app.lhjh.activities.product.RespMachineOperation;
import com.qdxy.app.lhjh.activities.product.RespProductList;
import com.qdxy.app.lhjh.activities.product.RespProductionCount;
import com.qdxy.app.lhjh.activities.product.ViewProductI;
import com.qdxy.app.lhjh.adapters.holders.HolderJiaGong;
import com.qdxy.app.lhjh.api.TempAPI;
import com.qdxy.app.lhjh.bean.RespProductLines;
import com.qdxy.app.lhjh.views.CustomExpandListview;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit2.Call;

/**
 * Created by KY on 2016/10/24.
 */

public class FragJiaGong2 extends TempFragment {
    private TempRVCommonAdapter lineAdapter;
    private PopupWindow popwindowLines;
    private String checkStatus;
    //    private List<RespJiaGong> mJiaGongData;
    private BottomSheetDialog mButtonSheet;
    private CustomExpandListview mCustomExpandListview;
    private LayoutInflater inflater;
    private JiaGongAdapter mJiaGongAdapter;
    private final String TAG = "FragJiaGong2";
    private String gongxu;//记录bottomSheet显示数据
    private PreProduct preProduct;
    private ViewProductI viewProductI;
    private int procedureId;
    //    @Bind(R.id.touliao_btn)
//    AppCompatButton mTouliaoButton;//投料按钮
    @Bind(R.id.parent_view)
    LinearLayout parentView;
    @Bind(R.id.jiagong2_PullToRefreshLayout)
    PullToRefreshLayout jiagong2_PullToRefreshLayout;
    private TempPullablePreDefault<RespJiaGong2> pullableDefault;
    private TempPullableViewI<RespJiaGong2> pullableViewI;
    //    @Bind(R.id.act_selector_search_btn)    AppCompatButton mSearchButton;
    @Bind(R.id.act_selector_search_editText)
    EditText mSearchEdit;
//    private TempRVCommonAdapter<RespJiaGong2.DataBean.DatasBean> adapter;

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Debug.info(TAG,"初始化initViews");
        return inflater.inflate(R.layout.frag_jia_gong_2_layout, null);
    }

    public void onEventMainThread(String event) {
//        Debug.info(TAG, "event 接受到数据" + event);
        if (event.equals("投料")) {
            pullableDefault.requestRefresh();
        }
       /* String msg = "onEventMainThread收到了消息：" + event.getMsg();
        Log.d("harvic", msg);
        tv.setText(msg);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();*/
    }

    @Override
    protected void setListeners(View view, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(FragJiaGong2.this);
        jiagong2_PullToRefreshLayout.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                pullableDefault.requestRefresh();
            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                pullableDefault.requestLoadMore();
            }
        });
        mCustomExpandListview = (CustomExpandListview) view.findViewById(R.id.jiagong2_listView);
        mCustomExpandListview.setSelector(ContextCompat.getDrawable(getActivity(), R.drawable.shape_list_selector));
        viewProductI = new ViewProductI() {
            @Override
            public void onMachiningCountByProcedure(RespProductionCount data) {

            }

            @Override
            public void onNextSucceed(String message) {

                pullableDefault.requestRefresh();
            }

            @Override
            public void onFeedingNumSucceed(RespFeedNum data) {

            }

            @Override
            public void onChangeTool(String message) {
                showTempDialog(getActivity(), true, "刀具更换提醒", message, "更换", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getActivity(), ActDeviceToolChange.class));
                    }
                }, "取消", null);
//                    Toast.makeText(getActivity(), "自检点击groupPosition=" + groupPosition + "||childPosition=" + childPosition, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void getroceduresByBatch(RespBatchByInBox data) {

            }

            @Override
            public void onInBoxSucceed(String message) {

            }

            @Override
            public void onInBoxDetailSucceed(RespInBoxDetail data) {

            }

            @Override
            public void onCreateProductSucceed(String message) {

            }

            @Override
            public void onProductListSucceed(RespProductList data) {

            }

            @Override
            public void onUpdateStatusSucceed(String message) {

            }

            @Override
            public void getBatchBySendMaterial(RespBatchByInBox data) {

            }

            @Override
            public void getBatchByInBox(RespBatchByInBox data) {

            }

            @Override
            public void onFailed(int who, String message) {

            }

            @Override
            public void touliaoFailed(String message) {

            }

            @Override
            public void touliaoSucceed(String message) {

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
                jiagong2_PullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
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
        pullableViewI = new TempPullableViewI<RespJiaGong2>() {
            @Override
            public void onInit(RespJiaGong2 respProcedure) {

            }

            @Override
            public void onRefresh(RespJiaGong2 respProcedure) {
                Debug.debug("加工数据刷新返回" + respProcedure.getData().getDatas().size());
                if (respProcedure.getData() == null || respProcedure.getData().getDatas() == null || respProcedure.getData().getDatas().isEmpty()) {
                    superViewMessage(parentView, "你目前还没有可加工的工件！");
                }
//                RespJiaGong2.getData().getDatas().FindAll()

                mJiaGongAdapter.upDateReflash(respProcedure.getData().getDatas());
//                jiagong2_PullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
            }

            @Override
            public void onLoadmore(RespJiaGong2 respProcedure) {
                mJiaGongAdapter.upDateLoadMore(respProcedure.getData().getDatas());
//                jiagong2_PullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
            }

            @Override
            public void refreshStatus(boolean succeed) {
                jiagong2_PullToRefreshLayout.refreshFinish(succeed ? PullToRefreshLayout.SUCCEED : PullToRefreshLayout.FAIL);
            }

            @Override
            public void loadMoreStatus(boolean succeed) {
                jiagong2_PullToRefreshLayout.loadmoreFinish(succeed ? PullToRefreshLayout.SUCCEED : PullToRefreshLayout.FAIL);
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
        pullableDefault = new TempPullablePreDefault<RespJiaGong2>(pullableViewI) {
            @Override
            public Call<RespJiaGong2> createObservable(int queryPage, int querysize, int currentPage) {
                return TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).getByProcedure(queryPage, mSearchEdit.getText().toString().trim());
            }
        };
    }

    @Override
    protected void bundleValues(View view, @Nullable Bundle savedInstanceState) {
//        Debug.info(TAG,"bundleValues");
        //初始化数据
        /*mJiaGongData = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            RespJiaGong2 jiaGong = new RespJiaGong2();
            List<RespJiaGong2.GongJian> gongJianList = new ArrayList<>();

            for (int j = 0; j < 3; j++) {
                RespJiaGong2.GongJian gongjian = new RespJiaGong2.GongJian();
                gongjian.setPru("WD1201060909");
                gongjian.setNum("101");
                gongJianList.add(gongjian);
            }

            jiaGong.setGongJian(gongJianList);
            jiaGong.setGongXu("工序" + i);
            mJiaGongData.add(jiaGong);
        }*/

        initAdapter();
        pullableDefault.requestRefresh();
    }

    /* // 默认全部展开
     for (int i = 0; i < mJiaGongData.size(); i++) {
         mCustomExpandListview.expandGroup(i);
     }*/
    private void initAdapter() {
        mJiaGongAdapter = new JiaGongAdapter(getActivity(), null);
        mCustomExpandListview.setDividerHeight(0);
        mCustomExpandListview.setGroupIndicator(null);
        mCustomExpandListview.setAdapter(mJiaGongAdapter);
        mCustomExpandListview.setHeaderView(getActivity().getLayoutInflater().inflate(
                R.layout.item_jia_gong_group_layout, mCustomExpandListview, false));

       /* mCustomExpandListview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Toast.makeText(getActivity(), "点击了第" + (i + 1) + " 类的第" + i1 + "项", Toast.LENGTH_SHORT).show();
                return true;
            }
        });*/

    }

    @OnClick(R.id.act_selector_search_btn)
    @Override
    protected void OnViewClicked(View v) {
        switch (v.getId()) {
            case R.id.act_selector_search_btn:
                pullableDefault.requestRefresh();
                break;
        }
    }

    private class JiaGongAdapter extends TempExpandableListAdapter<RespJiaGong2.DataBean.DatasBean> implements CustomExpandListview.HeaderAdapter {
        JiaGongAdapter(Context context, List<RespJiaGong2.DataBean.DatasBean> data) {
            super(context, data);
        }

        @Override
        public int getGroupCount() {
            Debug.info("getGroupCount=" + (getData() == null ? 0 : getData().size()));
            return getData() == null ? 0 : getData().size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return getData() == null || getData().isEmpty() || getData().get(groupPosition) == null || getData().get(groupPosition).getList() == null || getData().get(groupPosition).getList().isEmpty() ? 0 : getData().get(groupPosition).getList().size();
        }

        @Override
        public RespJiaGong2.DataBean.DatasBean getGroup(int groupPosition) {
            return getData().get(groupPosition);
        }

        @Override
        public RespJiaGong2.DataBean.DatasBean.ListBean getChild(int groupPosition, int childPosition) {
            return getGroup(groupPosition).getList().get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            HolderJiaGong viewHolder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_jia_gong_group_layout, null);
                viewHolder = new HolderJiaGong();
                viewHolder.devider = convertView.findViewById(R.id.item_jia_gong_divider);
                viewHolder.gongXu = (TextView) convertView.findViewById(R.id.item_jia_gong_gongxu);
                viewHolder.operationImage = (ImageView) convertView.findViewById(R.id.item_jia_gong_operation);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (HolderJiaGong) convertView.getTag();
            }
            viewHolder.gongXu.setText(getGroup(groupPosition).getGroupName() + " (" + getChildrenCount(groupPosition) + ")");
            viewHolder.operationImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    procedureId = getGroup(groupPosition).getId();
                    gongxu = getGroup(groupPosition).getGroupName();
                    showOperationButtomSheets();
                }
            });
          /*  if (getGroupCount()>0&&groupPosition==0){
                viewHolder.devider.setVisibility(View.GONE);
            }else{
                viewHolder.devider.setVisibility(View.VISIBLE);
            }*/

            return convertView;
        }

        @Override
        public int getChildType(int groupPosition, int childPosition) {

            return super.getChildType(groupPosition, childPosition);

        }

        @Override
        public void onGroupExpanded(int groupPosition) {
            super.onGroupExpanded(groupPosition);
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            HolderJiaGong viewHolder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_jia_gong_child_layout, null);
                viewHolder = new HolderJiaGong();
                viewHolder.checkButton = (TextView) convertView.findViewById(R.id.item_jia_gong_check_btn);
                viewHolder.num = (TextView) convertView.findViewById(R.id.item_jia_gong_num_textv);
                viewHolder.pici = (TextView) convertView.findViewById(R.id.item_jia_gong_pici_textv);
                viewHolder.checkCommit = (TextView) convertView.findViewById(R.id.item_jia_gong_check_commit_btn);
                viewHolder.setCardView((CardView) convertView.findViewById(R.id.item_jia_gong_child_cardView));
                viewHolder.commitButton = (TextView) convertView.findViewById(R.id.item_jia_gong_commit_btn);
                viewHolder.repairCommit = (TextView) convertView.findViewById(R.id.item_jia_gong_repair_commit);
                viewHolder.repairFrame = (LinearLayout) convertView.findViewById(R.id.item_jia_gong_repair_frame);
                viewHolder.devider = convertView.findViewById(R.id.item_jia_divider);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (HolderJiaGong) convertView.getTag();
            }


            viewHolder.checkCommit.setText(getGroup(groupPosition).getType() == 0 ? "送检" : "检测");
//            viewHolder.checkCommit.setVisibility(getGroup(groupPosition).getType() == 0 ? View.VISIBLE : View.INVISIBLE);
            viewHolder.checkCommit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getGroup(groupPosition).getType() == 0) {
                        //送检
                        showConfirmationDialog(getTempContext(), true, "工件送检确认", String.format("确定送检编号为：%s 的工件吗？", getChild(groupPosition, childPosition).getProductCode()), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                preProduct.machiningParts_next(getChild(groupPosition, childPosition).getId() + "", 14 + "", "", "", false);
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
//
                    } else {
                        initLinesWindow(getChild(groupPosition, childPosition).getId() + "");
//                        showToast("检测点击");
//                        initLinesWindow(getChild(groupPosition, childPosition).getId() + "");
                    }


                    }



            });

            viewHolder.repairFrame.setVisibility(getGroup(groupPosition).getType() == 0 ? View.VISIBLE : View.GONE);
            viewHolder.repairCommit.setVisibility(getGroup(groupPosition).getType() == 0 ? View.VISIBLE : View.INVISIBLE);
            viewHolder.repairCommit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getGroup(groupPosition).getType() == 0) {
                        //送修
                        showConfirmationDialog(getTempContext(), true, "工件送修确认", String.format("确定送修编号为：%s 的工件吗？", getChild(groupPosition, childPosition).getProductCode()), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                preProduct.machiningParts_next(getChild(groupPosition, childPosition).getId() + "", 17+ "", "", "", false);
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                    } else {
//                        showToast("检测点击");
                        initLinesWindow(getChild(groupPosition, childPosition).getId() + "");
                    }


                }
            });
            viewHolder.checkButton.setVisibility(getGroup(groupPosition).getType() == 0 ? View.VISIBLE : View.INVISIBLE);
            viewHolder.checkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Debug.info(TAG, "自检点击");
                        showTempDialog(getTempContext(), true, "工件自检提交确认", String.format("确定提交编号为：%s 的工件吗？", getChild(groupPosition, childPosition).getProductCode()), "正常", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                preProduct.machiningParts_next(getChild(groupPosition, childPosition).getId() + "", 1 + "", "", "0", false);

                            }
                        }, "异常", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                preProduct.machiningParts_next(getChild(groupPosition, childPosition).getId() + "", 1 + "", "", "1", false);
                            }
                        });


//                    Toast.makeText(getActivity(), "自检点击groupPosition=" + groupPosition + "||childPosition=" + childPosition, Toast.LENGTH_SHORT).show();
                }
            });
            viewHolder.num.setText(getChild(groupPosition, childPosition).getProductCode().replace(getChild(groupPosition, childPosition).getProductionBatchCode(), ""));
            viewHolder.pici.setText(getChild(groupPosition, childPosition).getProductionBatchCode());
            viewHolder.commitButton.setVisibility(getGroup(groupPosition).getType() == 0 ? View.VISIBLE : View.INVISIBLE);
            viewHolder.commitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showConfirmationDialog(getTempContext(), true, "工件提交确认", String.format("确定提交编号为：%s 的工件吗？", getChild(groupPosition, childPosition).getProductCode()), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            preProduct.machiningParts_next(getChild(groupPosition, childPosition).getId() + "", 0 + "", "", "", false);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                }
            });
            if (getChildrenCount(groupPosition) > 0) {

                if (getGroupCount() == 1) {
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewHolder.getCardView().getLayoutParams();
                    lp.setMargins(TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 16), 0);

                    viewHolder.devider.setVisibility(View.GONE);
                } else if ((childPosition == (getChildrenCount(groupPosition) - 1))) {
//                    ViewGroup.LayoutParams lp =  convertView.getLayoutParams();

//                    convertView.setPadding(16,16,16,16);

                    viewHolder.devider.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewHolder.getCardView().getLayoutParams();
                    lp.setMargins(TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 16));
//                    viewHolder.cardView.(TempDensityUtil.dip2px(getActivity(),16),TempDensityUtil.dip2px(getActivity(),16),TempDensityUtil.dip2px(getActivity(),16),TempDensityUtil.dip2px(getActivity(),16));
                } else {
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewHolder.getCardView().getLayoutParams();
                    lp.setMargins(TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 16), 0);

//                    viewHolder.cardView.setPadding(TempDensityUtil.dip2px(getActivity(),16),TempDensityUtil.dip2px(getActivity(),16),TempDensityUtil.dip2px(getActivity(),16),0);
//                    convertView.setPadding(16,16,16,0);
                    viewHolder.devider.setVisibility(View.GONE);
                }
            }

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public int getHeaderState(int groupPosition, int childPosition) {
            final int childCount = getChildrenCount(groupPosition);
            if (childPosition == childCount - 1) {
                return PINNED_HEADER_PUSHED_UP;
            } else if (childPosition == -1
                    && !mCustomExpandListview.isGroupExpanded(groupPosition)) {
                return PINNED_HEADER_GONE;
            } else {
                return PINNED_HEADER_VISIBLE;
            }
        }

        @Override
        public void configureHeader(View header, int groupPosition, int childPosition, int alpha) {
            if (groupPosition > -1) {

                ((TextView) header.findViewById(R.id.item_jia_gong_gongxu))

                        .setText(getGroup(groupPosition).getGroupName() + " (" + getChildrenCount(groupPosition) + ")");
            }
        }


        protected LayoutInflater getLayoutInflater() {
//		if (inflater==null) {
//			inflater = LayoutInflater.from(context);
//		}

            return inflater == null ? LayoutInflater.from(getActivity()) : inflater;
        }

        /**
         * 更换刀具对话框
         */
        private void showChangeKnife() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(R.layout.dialog_change_knife_layout).setNegativeButton("取消", null).setPositiveButton("更换刀具", null).show();

        }

        /**
         * 显示底部导航栏
         */
        private void showOperationButtomSheets() {
            Debug.info(TAG, "报警工序id=" + procedureId);
            if (mButtonSheet == null) {
                mButtonSheet = new BottomSheetDialog(getActivity());
                View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.buttom_sheets_jia_gong_content_layout, null);
                View cancel = contentView.findViewById(R.id.buttom_sheets_jia_gong_cancel);
                View alarmBtn = contentView.findViewById(R.id.buttom_sheets_jia_gong_alarm);
                alarmBtn.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Debug.info(TAG, "点击发送报警");
                                                    showTempDialog(getTempContext(), true, "", String.format("确定为工序：%s 发送报警吗？", gongxu), "确定", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            preProduct.workingTimeoutProblemCreate(procedureId, "");
                                                            mButtonSheet.dismiss();
                                                        }
                                                    }, "取消", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            mButtonSheet.dismiss();
                                                        }
                                                    });
                                                }
                                            }
/*
*    Debug.info(TAG, "点击发送报警");

* */
                );
                View requestBtn = contentView.findViewById(R.id.buttom_sheets_jia_gong_fuliao_request);
                requestBtn.setOnClickListener(new View.OnClickListener()

                                              {
                                                  @Override
                                                  public void onClick(View v) {
                                                      showSnackBar(parentView, "该功能还未开放", 2, Snackbar.LENGTH_LONG);
                                                      mButtonSheet.dismiss();
                                                  }
                                              }

                );
                cancel.setOnClickListener(new View.OnClickListener()

                                          {
                                              @Override
                                              public void onClick(View v) {
                                                  mButtonSheet.dismiss();
                                              }
                                          }

                );
                mButtonSheet.setContentView(contentView);

            }
            mButtonSheet.show();

        }
    }

    /**
     * 创建PopupWindow
     */
    protected void initLinesWindow(final String tempId) {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getActivity().getLayoutInflater().inflate(R.layout.dialog_check_list_layout, null,
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

                    preProduct.machiningParts_next(tempId, checkStatus, "", "", true);
                } else {
                    showSnackBar(parentView, "未选择检测检测结果", 2, Snackbar.LENGTH_LONG);
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

        lineAdapter = new TempRVCommonAdapter<RespProductLines.DataBean>(getActivity(), R.layout.item_dialog_check_list_layout) {
            @Override
            public void bindItemValues(TempRVHolder holder, RespProductLines.DataBean s) {
                holder.setText(R.id.item_dialog_check_radioButton, s.getName());
                holder.setChecked(R.id.item_dialog_check_radioButton, s.isChecked());
            }
        };
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        recyclerview.addItemDecoration(new TempRVDividerDecoration(Color.parseColor("#EEEFF3"), TempDensityUtil.dip2px(getActivity(), 1.0f)));

        lineAdapter.updateRefresh(datas);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(FragJiaGong2.this);
        super.onDestroy();
    }
}
