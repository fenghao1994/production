package com.qdxy.app.lhjh.activities.onOffLine;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lf.tempcore.tempAdapter.TempExpandableListAdapter;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempFragment.TempFragment;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullablePreDefault;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullableViewI;
import com.lf.tempcore.tempModule.tempRemotComm.TempRemotAPIConnecter;
import com.lf.tempcore.tempViews.tempPullableViews.PullToRefreshLayout;
import com.qdxy.app.lhjh.MyApplication;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.activities.product.RespProcedure;
import com.qdxy.app.lhjh.adapters.holders.HolderJiaGong;
import com.qdxy.app.lhjh.api.TempAPI;
import com.qdxy.app.lhjh.views.CustomExpandListview;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;

import static com.qdxy.app.lhjh.R.id.body_operation_check_image;
import static com.qdxy.app.lhjh.R.id.body_operation_commit_btn;

/**
 * 产品上线/下线列表
 * Created by KY on 2016/12/5.
 */

public class FragOnLineList extends TempFragment {
    private final String TAG = "FragOnLineList";
    private boolean selectAll;
    private int lineId=-1,status=0;//0-下线
    @Bind(R.id.parent_view)    LinearLayout parentView;
    @Bind(R.id.jiagong2_PullToRefreshLayout)    PullToRefreshLayout jiagong2_PullToRefreshLayout;
    @Bind(body_operation_check_image)    ImageView check_image;
    @Bind(body_operation_commit_btn)    AppCompatButton commit_btn;
    private ExpandableListView mCustomExpandListview;
    private FragOnLineList.OnlineAdapter mOnlineAdapter;
    private TempPullablePreDefault<RespProcedure> pullableDefault;
    private TempPullableViewI<RespProcedure> pullableViewI;
    private ViewOnOffLineI viewOnOffLineI;
    private PreOnOffLine preOnOffLine;
    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_online_layout, null);
    }

    @Override
    protected void setListeners(View view, @Nullable Bundle savedInstanceState) {
        viewOnOffLineI = new ViewOnOffLineI() {
            @Override
            public void onBeforeProcedureSucceed(RespProcedureList message) {

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
        mCustomExpandListview = (ExpandableListView) view.findViewById(R.id.jiagong2_listView);

        pullableViewI = new TempPullableViewI<RespProcedure>() {
            @Override
            public void onInit(RespProcedure respProcedure) {

            }

            @Override
            public void onRefresh(RespProcedure respProcedure) {
                Debug.debug("加工数据刷新返回" + respProcedure.getData().getDatas().size());

               /* if (respProcedure.getData() == null || respProcedure.getData().getDatas() == null || respProcedure.getData().getDatas().isEmpty()) {
                    superViewMessage(parentView, "你目前还没有可加工的工件！");
                }*/

                mOnlineAdapter.upDateReflash(respProcedure.getData().getDatas());
                // 默认全部展开
                for (int i = 0; i <(respProcedure.getData()==null||respProcedure.getData().getDatas()==null?0: respProcedure.getData().getDatas().size()); i++) {
                    mCustomExpandListview.expandGroup(i);
                }
//                jiagong2_PullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
            }

            @Override
            public void onLoadmore(RespProcedure respProcedure) {
                mOnlineAdapter.upDateLoadMore(respProcedure.getData().getDatas());
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
                superViewError(parentView, code, message);
            }
        };
        pullableDefault = new TempPullablePreDefault<RespProcedure>(pullableViewI) {
            @Override
            public Call<RespProcedure> createObservable(int queryPage, int querysize, int currentPage) {
               return  status==0?TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).getByProcedure(status+"",lineId+"",queryPage + ""):TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).getByBatch(status+"",lineId+"",queryPage + "");
//                return TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).getByProcedure(status+"",lineId+"",queryPage + "");
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
        commit_btn.setText(status==0?"下线":"上线");
        initAdapter();
        pullableDefault.requestRefresh();
    }

    @OnClick({body_operation_commit_btn, body_operation_check_image})
    @Override
    protected void OnViewClicked(View v) {
        switch (v.getId()) {
            case body_operation_commit_btn:

                if (mOnlineAdapter==null){
                    return ;
                }
                List<String> ids = new ArrayList<>();
                List<RespProcedure.DataBean.DatasBean> dataBeen1=  mOnlineAdapter.getData();
               Debug.info(TAG,dataBeen1.toString());
                for (int k=0;k<dataBeen1.size();k++){
                    if (dataBeen1.get(k).isChecked()){
                        for(int m=0;m<dataBeen1.get(k).getList().size();m++){
                            if (dataBeen1.get(k).getList().get(m).isChecked()){
                                ids.add(dataBeen1.get(k).getList().get(m).getId()+"");//添加选中工件id
                            }
                        }
                    }
                }
                Debug.info(TAG,"工件下线选择id="+ids.toString());
                if (ids.isEmpty()){
                    showSnackBar(parentView,"请选择工件",2,Snackbar.LENGTH_LONG);
                }else{
                    if (status==0){
                        //提交工件下线
                        preOnOffLine.updateStatus(ids.toArray(new String[ids.size()]),"2","2","-1","");
                    }else{
                        //提交工件下线
                        preOnOffLine.updateStatus(ids.toArray(new String[ids.size()]),"0","3","-1","");
                    }

                }

                break;
            case body_operation_check_image:
                //全选
                selectAll = !selectAll;
                check_image.setImageResource(selectAll?R.mipmap.icon_checked_l:R.mipmap.icon_unchecked_l);
                if (mOnlineAdapter==null){
                    return ;
                }
              List<RespProcedure.DataBean.DatasBean> dataBeen=  mOnlineAdapter.getData();
                for (int i=0;i<dataBeen.size();i++){
                    dataBeen.get(i).setChecked(selectAll);
                    for (int j=0;j<dataBeen.get(i).getList().size();j++){
                        dataBeen.get(i).getList().get(j).setChecked(selectAll);
                    }
                }
                mOnlineAdapter.notifyDataSetChanged();
                break;


        }
    }
    private void initAdapter() {
        mOnlineAdapter = new OnlineAdapter(getActivity(), null);
//        mCustomExpandListview.setDividerHeight(0);
        mCustomExpandListview.setAdapter(mOnlineAdapter);
        mCustomExpandListview.setGroupIndicator(null);
//        mCustomExpandListview.setHeaderView(getActivity().getLayoutInflater().inflate(
//                R.layout.item_online_group_layout, mCustomExpandListview, false));
       /* mCustomExpandListview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Debug.info(TAG,"group 点击");
                return true;
            }
        });*/
       /* mCustomExpandListview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Debug.info(TAG,"child 点击");
                return false;
            }
        });*/

    }
    private class OnlineAdapter extends TempExpandableListAdapter<RespProcedure.DataBean.DatasBean> implements CustomExpandListview.HeaderAdapter {
        OnlineAdapter(Context context, List<RespProcedure.DataBean.DatasBean> data) {
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
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            HolderJiaGong viewHolder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_online_group_layout, null);
                viewHolder = new HolderJiaGong();
//                viewHolder.devider = convertView.findViewById(R.id.item_jia_gong_divider);
                viewHolder.gongXu = (TextView) convertView.findViewById(R.id.item_online_group_title_text);
                viewHolder.operationImage = (ImageView) convertView.findViewById(R.id.item_online_group_status_image);
                viewHolder.devider = convertView.findViewById(R.id.item_jia_group_divider);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (HolderJiaGong) convertView.getTag();
            }
            viewHolder.gongXu.setText(getGroup(groupPosition).getGroupName()+" ("+getChildrenCount(groupPosition)+")");
//            viewHolder.gongXu.setText(getGroup(groupPosition).getGroupName());
            viewHolder.operationImage.setImageResource( getGroup(groupPosition).isChecked()?R.mipmap.icon_checked_l:R.mipmap.icon_unchecked_l);
            viewHolder.devider.setVisibility(getChildrenCount(groupPosition)==0?View.VISIBLE:View.GONE);
            viewHolder.operationImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean tempchecked =!getGroup(groupPosition).isChecked();
                    Debug.info(TAG,"group点击"+tempchecked);
//                    getData().get(groupPosition).setChecked(tempchecked);
                    getGroup(groupPosition).setChecked(tempchecked);
                    for (int i=0;i<getChildrenCount(groupPosition);i++){
//                        getData().get(groupPosition).getList().get(i).setChecked(tempchecked);
                        getChild(groupPosition,i).setChecked(tempchecked);
                    }
                    boolean all=true;
                    for(int j=0;j<getGroupCount();j++){
                        if (!getGroup(j).isChecked()){
                            all=false;
                            break;
                        }
                    }
                    if (selectAll!=all){
                        selectAll = all;
                        check_image.setImageResource(selectAll?R.mipmap.icon_checked_l:R.mipmap.icon_unchecked_l);
                    }
                    notifyDataSetChanged();
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
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            HolderJiaGong viewHolder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_online_child_layout, null);
                viewHolder = new HolderJiaGong();
//                viewHolder.checkButton = (TextView) convertView.findViewById(R.id.item_jia_gong_check_btn);
                viewHolder.num = (TextView) convertView.findViewById(R.id.item_online_num_textv);
                viewHolder.pici = (TextView) convertView.findViewById(R.id.item_online_pici_textv);
                viewHolder.gongXu = (TextView) convertView.findViewById(R.id.item_online_gongxu_textv);
                viewHolder.operationImage = (ImageView) convertView.findViewById(R.id.item_online_child_status_image);
//                viewHolder.setCardView((CardView) convertView.findViewById(R.id.item_online_child_cardView));
//                viewHolder.commitButton = (TextView) convertView.findViewById(R.id.item_jia_gong_commit_btn);
                viewHolder.devider = convertView.findViewById(R.id.item_jia_divider);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (HolderJiaGong) convertView.getTag();
            }

            viewHolder.num.setText(getChild(groupPosition, childPosition).getProductCode().replace(getChild(groupPosition, childPosition).getProductionBatchCode(), ""));
            viewHolder.pici.setText(getChild(groupPosition, childPosition).getProductionBatchCode());
            viewHolder.gongXu.setText(String.format("%s", getChild(groupPosition, childPosition).getProcedureNumber()));
            viewHolder.operationImage.setImageResource( getChild(groupPosition,childPosition).isChecked()?R.mipmap.icon_checked_s:R.mipmap.icon_unchecked_s);
            viewHolder.operationImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean childchecked = !getChild(groupPosition,childPosition).isChecked();
                    Debug.info(TAG,"child点击"+childchecked);
//                    getData().get(groupPosition).getList().get(childPosition).setChecked(childchecked);
                    getChild(groupPosition,childPosition).setChecked(childchecked);
                    if (childchecked){
//                        getData().get(groupPosition).setChecked(true);
                        getGroup(groupPosition).setChecked(true);
                    }else{
                        for (int j=0;j<getChildrenCount(groupPosition);j++){
                           /* if (getData().get(groupPosition).getList().get(j).isChecked()){
                                getData().get(groupPosition).setChecked(true);
                                break;
                            }
                            getData().get(groupPosition).setChecked(false);*/
                            if (getChild(groupPosition,j).isChecked()){
                                getGroup(groupPosition).setChecked(true);
                                break;
                            }
                            getGroup(groupPosition).setChecked(false);
                        }
                    }
                    boolean all=true;
                    for(int j=0;j<getGroupCount();j++){
                        if (!getGroup(j).isChecked()){
                            all=false;
                            break;
                        }
                        for (int k=0;k<getChildrenCount(j);k++){
                            if (! getChild(j,k).isChecked()){
                                all=false;
                                break;
                            }
                        }
                    }
                    if (selectAll!=all){
                        selectAll = all;
                        check_image.setImageResource(selectAll?R.mipmap.icon_checked_l:R.mipmap.icon_unchecked_l);
                    }
                    notifyDataSetChanged();
                }
            });
            if (getChildrenCount(groupPosition) > 0) {

                if ((childPosition == (getChildrenCount(groupPosition) - 1))) {

                    viewHolder.devider.setVisibility(View.VISIBLE);
//                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewHolder.getCardView().getLayoutParams();
//                    lp.setMargins(TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 16));
                } else {
//                    LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewHolder.getCardView().getLayoutParams();
//                    lp.setMargins(TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 16), 0);

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
                ((TextView) header.findViewById(R.id.item_online_group_title_text))
                        .setText(getGroup(groupPosition).getGroupName()+" ("+getChildrenCount(groupPosition)+")");
            }
        }
    }
}
