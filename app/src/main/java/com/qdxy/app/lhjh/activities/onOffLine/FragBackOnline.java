package com.qdxy.app.lhjh.activities.onOffLine;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.qdxy.app.lhjh.activities.product.RespProcedure;
import com.qdxy.app.lhjh.adapters.holders.HolderJiaGong;
import com.qdxy.app.lhjh.api.TempAPI;
import com.qdxy.app.lhjh.views.CustomExpandListview;

import java.util.List;

import butterknife.Bind;
import retrofit2.Call;

/**返工上线
 * Created by KY on 2016/12/6.
 */

public class FragBackOnline extends TempFragment{
    private final String TAG ="FragBackOnline";
    private int lineId,status;//3-返工上线
    @Bind(R.id.parent_view)    LinearLayout parentView;
    @Bind(R.id.act_selector_search_frame)    LinearLayout act_selector_search_frame;
    @Bind(R.id.jiagong2_PullToRefreshLayout)    PullToRefreshLayout jiagong2_PullToRefreshLayout;

    private TempPullablePreDefault<RespProcedure> pullableDefault;
    private TempPullableViewI<RespProcedure> pullableViewI;
    private CustomExpandListview mCustomExpandListview;
    private FragBackOnline.BackOnlineAdapter mBackOnlineAdapter;
    private ViewOnOffLineI viewOnOffLineI;
    private PreOnOffLine preOnOffLine;
    private PopupWindow producesPOP;
    private TempRVCommonAdapter producesAdapter;
    private RespProcedureList.DataBean checkedLineData;
    private String chechedPrdId;//被选中的工件id
//    private RespProcedureList linesData;
    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_jia_gong_2_layout, null);
    }

    @Override
    protected void setListeners(View view, @Nullable Bundle savedInstanceState) {
        act_selector_search_frame.setVisibility(View.GONE);
        viewOnOffLineI = new ViewOnOffLineI() {
            @Override
            public void onBeforeProcedureSucceed(RespProcedureList message) {
                Debug.info(TAG,"获取到工序列表数据");
//                linesData=message;
                checkedLineData=null;
                initLinesWindow(message);
                if (!producesPOP.isShowing()) {
                    producesPOP.showAtLocation(parentView, Gravity.CENTER, 0, 0);
                }
            }

            @Override
            public void onOfflineSucceed(String message) {
                superViewMessage(parentView,message);
                pullableDefault.requestRefresh();
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
        preOnOffLine = new PreOnOffLine(viewOnOffLineI);

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
        mCustomExpandListview.setGroupIndicator(null);
        mCustomExpandListview.setSelector(ContextCompat.getDrawable(getActivity(),R.drawable.shape_list_selector));
        pullableViewI = new TempPullableViewI<RespProcedure>() {
            @Override
            public void onInit(RespProcedure respProcedure) {

            }

            @Override
            public void onRefresh(RespProcedure respProcedure) {
                Debug.debug("加工数据刷新返回" + respProcedure.getData().getDatas().size());

//                if (respProcedure.getData() == null || respProcedure.getData().getDatas() == null || respProcedure.getData().getDatas().isEmpty()) {
//                    superViewMessage(parentView, "你目前还没有可加工的工件！");
//                }

                mBackOnlineAdapter.upDateReflash(respProcedure.getData().getDatas());
                // 默认全部展开
                for (int i = 0; i <(respProcedure.getData()==null||respProcedure.getData().getDatas()==null?0: respProcedure.getData().getDatas().size()); i++) {
                    mCustomExpandListview.expandGroup(i);
                }
//                jiagong2_PullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
            }

            @Override
            public void onLoadmore(RespProcedure respProcedure) {
                mBackOnlineAdapter.upDateLoadMore(respProcedure.getData().getDatas());
                // 默认全部展开
                for (int i = 0; i <(respProcedure.getData()==null||respProcedure.getData().getDatas()==null?0: respProcedure.getData().getDatas().size()); i++) {
                    mCustomExpandListview.expandGroup(i);
                }
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
                Debug.info(TAG,message);
                superViewError(parentView, code, message);
            }
        };
        pullableDefault = new TempPullablePreDefault<RespProcedure>(pullableViewI) {
            @Override
            public Call<RespProcedure> createObservable(int queryPage, int querysize, int currentPage) {
                return TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).getByBatch(status+"",lineId+"",queryPage + "");
            }
        };
    }

    @Override
    protected void bundleValues(View view, @Nullable Bundle savedInstanceState) {
        lineId = getArguments().getInt("lineId",-1);
        status = getArguments().getInt("status",-1);

        if(   lineId==-1 ||status==-1){
            showSnackBar(parentView,"没有获取到生产线数据！",2, Snackbar.LENGTH_LONG);
            return;
        }
        initAdapter();
        pullableDefault.requestRefresh();
    }

    @Override
    protected void OnViewClicked(View v) {

    }
    private void initAdapter() {
        mBackOnlineAdapter = new FragBackOnline.BackOnlineAdapter(getActivity(), null);
        mCustomExpandListview.setDividerHeight(0);
        mCustomExpandListview.setAdapter(mBackOnlineAdapter);
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
    private  class BackOnlineAdapter extends TempExpandableListAdapter<RespProcedure.DataBean.DatasBean> implements CustomExpandListview.HeaderAdapter {
        BackOnlineAdapter(Context context, List<RespProcedure.DataBean.DatasBean> data) {
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
        public RespProcedure.DataBean.DatasBean getGroup(int groupPosition) {
            return getData().get(groupPosition);
        }

        @Override
        public RespProcedure.DataBean.DatasBean.ListBean getChild(int groupPosition, int childPosition) {
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
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
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
            viewHolder.operationImage.setVisibility(View.GONE);
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
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            HolderJiaGong viewHolder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_jia_gong_child_layout, null);
                viewHolder = new HolderJiaGong();
                viewHolder.checkButton = (TextView) convertView.findViewById(R.id.item_jia_gong_check_btn);
                viewHolder.num = (TextView) convertView.findViewById(R.id.item_jia_gong_num_textv);
                viewHolder.pici = (TextView) convertView.findViewById(R.id.item_jia_gong_pici_textv);
                viewHolder.setCardView((CardView) convertView.findViewById(R.id.item_jia_gong_child_cardView));
                viewHolder.commitButton = (TextView) convertView.findViewById(R.id.item_jia_gong_commit_btn);
                viewHolder.repairFrame = (LinearLayout) convertView.findViewById(R.id.item_jia_gong_repair_frame);
                viewHolder.devider = convertView.findViewById(R.id.item_jia_divider);
                viewHolder.checkCommit = (TextView) convertView.findViewById(R.id.item_jia_gong_check_commit_btn);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (HolderJiaGong) convertView.getTag();
            }
            viewHolder.num.setText(getChild(groupPosition, childPosition).getProductCode().replace(getChild(groupPosition, childPosition).getProductionBatchCode(), ""));
            viewHolder.pici.setText(getChild(groupPosition, childPosition).getProductionBatchCode());
            viewHolder.checkButton.setVisibility(View.INVISIBLE);
            viewHolder.repairFrame.setVisibility(View.GONE);
            viewHolder.commitButton.setVisibility(View.INVISIBLE);
//            viewHolder.checkCommit.setVisibility(View.INVISIBLE);
            viewHolder.checkCommit.setText("选择工序");
            viewHolder.checkCommit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chechedPrdId=getChild(groupPosition,childPosition).getId()+"";
                    preOnOffLine.beforeProcedure(chechedPrdId);
                  /*  showConfirmationDialog(getTempContext(), true, "", "确定提交该工件吗？", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            preProduct.machiningParts_next(getChild(groupPosition, childPosition).getId() + "", 0 + "", "", "");
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });*/

                }
            });
            if (getChildrenCount(groupPosition) > 0) {
                if(getGroupCount()==1){
                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewHolder.getCardView().getLayoutParams();
                    lp.setMargins(TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 16), 0);

                    viewHolder.devider.setVisibility(View.GONE);
                }else if ((childPosition == (getChildrenCount(groupPosition) - 1))) {
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
                header.findViewById(R.id.item_jia_gong_operation).setVisibility(View.GONE);
            }
        }
    }

    private void initlineReceiverview(TempRefreshRecyclerView recyclerview, List<RespProcedureList.DataBean> datas) {
        producesAdapter = new TempRVCommonAdapter<RespProcedureList.DataBean>(getActivity(), R.layout.item_dialog_check_list_layout) {
            @Override
            public void bindItemValues(TempRVHolder holder, RespProcedureList.DataBean s) {
                holder.setText(R.id.item_dialog_check_radioButton, s.getName());
                holder.setChecked(R.id.item_dialog_check_radioButton, s.isChecked());
            }
        };
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        producesAdapter.setOnItemClickListener(new OnItemClickListener<RespProcedureList.DataBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, RespProcedureList.DataBean o, int position) {
                if (!o.isChecked()) {
                    List<RespProcedureList.DataBean> data= producesAdapter.getData();
                    for (int i=0;i<producesAdapter.getData().size();i++){
                        data.get(i).setChecked(false);
                    }
                    o.setChecked(true);
                    checkedLineData = o;
                    producesAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, RespProcedureList.DataBean o, int position) {
                return false;
            }
        });
        recyclerview.setAdapter(producesAdapter);
        recyclerview.addItemDecoration(new TempRVDividerDecoration(Color.parseColor("#EEEFF3"), TempDensityUtil.dip2px(getActivity(), 1.0f)));
        producesAdapter.updateRefresh(datas);
    }
    /**
     * 创建PopupWindow
     */
    protected void initLinesWindow(RespProcedureList mainData) {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getActivity().getLayoutInflater().inflate(R.layout.dialog_check_list_layout, null,
                false);
        TextView title = (TextView) popupWindow_view.findViewById(R.id.dialog_check_list_title);
        TextView dialog_check_list_ok = (TextView) popupWindow_view.findViewById(R.id.dialog_check_list_ok);
        TempRefreshRecyclerView act_create_pici_check_recycler = (TempRefreshRecyclerView) popupWindow_view.findViewById(R.id.act_create_pici_check_recycler);
        initlineReceiverview(act_create_pici_check_recycler, mainData.getData());
        dialog_check_list_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                producesPOP.dismiss();
                producesPOP = null;
                if (checkedLineData==null){
                    showSnackBar(parentView,"请选择工序！",2,Snackbar.LENGTH_LONG);
                }else{

                    preOnOffLine.updateStatus(chechedPrdId,"0","5",checkedLineData.getId()+"","");
                }
            }
        });
        TextView dialog_check_list_cancel = (TextView) popupWindow_view.findViewById(R.id.dialog_check_list_cancel);
        dialog_check_list_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                producesPOP.dismiss();
            }
        });
        title.setText("选择返工工序");
        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        producesPOP = new PopupWindow(popupWindow_view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        producesPOP.setOutsideTouchable(false);
        // 设置动画效果
        producesPOP.setAnimationStyle(R.style.popwin_anim_style);
        producesPOP.setBackgroundDrawable(new BitmapDrawable(null, ""));
    }
    /*private void showLinePop() {
            initLinesWindow();
        if (!producesPOP.isShowing()) {
            producesPOP.showAtLocation(parentView, Gravity.CENTER, 0, 0);
        }
    }*/
}
