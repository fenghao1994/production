package com.qdxy.app.lhjh.activities.messageCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempFragment.TempFragment;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullablePreDefault;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullableViewI;
import com.lf.tempcore.tempModule.tempRemotComm.TempRemotAPIConnecter;
import com.lf.tempcore.tempViews.tempRecyclerView.OnItemClickListener;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVCommonAdapter;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVHolder;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRefreshRecyclerView;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.api.TempAPI;

import butterknife.Bind;
import retrofit2.Call;

/**
 * Created by KY on 2016/12/14.
 */

public class FragMessageCenter extends TempFragment {
    private final String TAG = "ActExceptionJiaGongList";
    @Bind(R.id.temp_refresh_recyclerView)
    TempRefreshRecyclerView mTempRefreshRecyclerView;
    @Bind(R.id.parent_view)
    LinearLayout parentView;
    private TempPullablePreDefault<RespMessage> mTempPullablePresenterI;
    private TempPullableViewI<RespMessage> mTempPullableViewI;
    private TempRVCommonAdapter<RespMessage.DataBean.DatasBean> mTempRVCommonAdapter;
    private boolean idRead;

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_exception_list_layout, null);
    }

    @Override
    protected void setListeners(View view, @Nullable Bundle savedInstanceState) {
        idRead = getArguments().getBoolean("read");
        mTempRVCommonAdapter = new TempRVCommonAdapter<RespMessage.DataBean.DatasBean>(getActivity(), R.layout.item_exception_message_center_layout) {
            @Override
            public void bindItemValues(TempRVHolder holder, RespMessage.DataBean.DatasBean s) {
                //                0工件异常
//                1设备异常
//                2刀具异常
//                3超时报警异常
//                4处理超时
                Intent intent = null;
                switch (s.getType()) {
                    case 0:
                        holder.setImageResource(R.id.item_message_center_type_image,R.mipmap.icon_exception_daoju);
                        break;
                    case 1:
                        holder.setImageResource(R.id.item_message_center_type_image,R.mipmap.icon_exception_equ);
                        break;
                    case 2:
                        holder.setImageResource(R.id.item_message_center_type_image,R.mipmap.icon_exception_gongjian);
                        break;
                    case 3:
                        holder.setImageResource(R.id.item_message_center_type_image,R.mipmap.icon_exception_alarm_outtime);
                        break;
                    case 4:
                        holder.setImageResource(R.id.item_message_center_type_image,R.mipmap.icon_exception_deal_outtime);
                        break;
                    default:
                        holder.setImageResource(R.id.item_message_center_type_image,R.mipmap.icon_exception_gongjian);

                }
//                item_message_center_type_image
//                switch (s.get)
                holder.setText(R.id.item_message_center_type_name, s.getTitle());
                holder.setText(R.id.item_message_center_creationTime, s.getCreationTime());
//                holder.setText(R.id.item_message_center_creationTime,s.get());//时间
                holder.setText(R.id.item_message_center_index0, "发送人：" + s.getSenderName());
                holder.setText(R.id.item_message_center_index1, "描述：" + s.getContent());
//                holder.setText(R.id.item_exception_content_index2,"设备名："+s.getDeviceCode());
            }
        };
        mTempPullableViewI = new TempPullableViewI<RespMessage>() {
            @Override
            public void onInit(RespMessage tempResponse) {

            }

            @Override
            public void onRefresh(RespMessage tempResponse) {
                mTempRVCommonAdapter.updateRefresh(tempResponse.getData().getDatas());
            }

            @Override
            public void onLoadmore(RespMessage tempResponse) {
                mTempRVCommonAdapter.updateLoadMore(tempResponse.getData().getDatas());
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
        mTempPullablePresenterI = new TempPullablePreDefault<RespMessage>(mTempPullableViewI) {
            @Override
            public Call<RespMessage> createObservable(int queryPage, int querysize, int currentPage) {
                return idRead ? TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).systemMessageReaded(queryPage) : TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).systemMessageUnread(queryPage);
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Debug.info(TAG,"fragment接受到onActivity result消息");
        mTempPullablePresenterI.requestRefresh();
    }

    @Override
    protected void bundleValues(View view, @Nullable Bundle savedInstanceState) {
        initReceiverview(mTempRefreshRecyclerView, mTempRVCommonAdapter);
        mTempPullablePresenterI.requestRefresh();
    }

    @Override
    protected void OnViewClicked(View v) {

    }

    private void initReceiverview(TempRefreshRecyclerView recyclerview, TempRVCommonAdapter adapter) {
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(new OnItemClickListener<RespMessage.DataBean.DatasBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, RespMessage.DataBean.DatasBean o, int position) {
//

                Intent intent = new Intent(getActivity(),ActMessageDetail.class);
                intent.putExtra("id",o.getId());
                intent.putExtra("type",o.getType());
                if (idRead){

                    startActivity(intent);
                }else{
                    startActivityForResult(intent,91);
                }

            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, RespMessage.DataBean.DatasBean o, int position) {
                return false;
            }
        });
        adapter.setMore(new TempRVCommonAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mTempPullablePresenterI.requestLoadMore();
            }
        });
        recyclerview.setRefreshListener(new TempRefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTempPullablePresenterI.requestRefresh();
            }
        });
        recyclerview.setAdapter(adapter);
//        recyclerview.addItemDecoration(new TempRVDividerDecoration(Color.parseColor("#EEEFF3"), TempDensityUtil.dip2px(getActivity(), 12.0f)));
    }
}
