package com.qdxy.app.lhjh.activities.check;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import de.greenrobot.event.EventBus;
import retrofit2.Call;

/**
 * 待抽检
 * Created by KY on 2016/11/30.
 */

public class FragPrdUnchecked extends TempFragment {
    private final String TAG = "FragPrdChecked";
    String patchCode;
    //    Debug.info(TAG,"获取到的batchCode="+patchCode);
    TempPullablePreDefault<RespCheckPrdList> pullablePreDefault;
    TempPullableViewI<RespCheckPrdList> pullableViewI;
    @Bind(R.id.parent_view)
    CoordinatorLayout parentView;
    @Bind(R.id.frag_prd_check_recyclerView)
    TempRefreshRecyclerView recyclerView;
    private TempRVCommonAdapter<RespCheckPrdList.DataBean.DatasBean> adapter;

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_prd_check_layout, null);
    }

    @Override
    protected void setListeners(View view, @Nullable Bundle savedInstanceState) {
        patchCode = getArguments().getString("patchCode");
    }

    @Override
    protected void bundleValues(View view, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(FragPrdUnchecked.this);
        pullableViewI = new TempPullableViewI<RespCheckPrdList>() {
            @Override
            public void onInit(RespCheckPrdList data) {

            }

            @Override
            public void onRefresh(RespCheckPrdList data) {
                adapter.updateRefresh(data.getData().getDatas());
            }

            @Override
            public void onLoadmore(RespCheckPrdList data) {
                adapter.updateLoadMore(data.getData().getDatas());
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
        pullablePreDefault = new TempPullablePreDefault<RespCheckPrdList>(pullableViewI) {
            @Override
            public Call<RespCheckPrdList> createObservable(int queryPage, int querysize, int currentPage) {
                return TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).requestRandomCheckPrdList(patchCode, false, queryPage + "");
            }
        };

        if (TextUtils.isEmpty(patchCode)) {
            showSnackBar(parentView, "没有获取到批次数据", 2, Snackbar.LENGTH_LONG);
        } else {
            initListData(recyclerView);
            pullablePreDefault.requestRefresh();
        }

    }

    @Override
    protected void OnViewClicked(View v) {

    }

    private void initListData(TempRefreshRecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (adapter == null) {
            adapter = new TempRVCommonAdapter<RespCheckPrdList.DataBean.DatasBean>(getActivity(), R.layout.item_check_prd_list_layout) {
                @Override
                public void bindItemValues(TempRVHolder holder, RespCheckPrdList.DataBean.DatasBean dataBean) {
                    holder.setText(R.id.item_check_prd_code, dataBean.getProductCode());
                    holder.setText(R.id.item_check_prd_person, "自检人：" + dataBean.getSelfCheckList());
                    holder.setText(R.id.item_check_prd_checker, "抽检人：" + dataBean.getRandomCheckList());
                }
            };
            adapter.setOnItemClickListener(new OnItemClickListener<RespCheckPrdList.DataBean.DatasBean>() {
                @Override
                public void onItemClick(ViewGroup parent, View view, RespCheckPrdList.DataBean.DatasBean o, int position) {
//                    MyApplication.getInstance().putExtralsObj("checkData",o);
                    Intent checkDetailIntent = new Intent(getActivity(),ActCheckPrdDetail.class);
                    checkDetailIntent.putExtra("id",o.getId());
                    startActivity(checkDetailIntent);
                }

                @Override
                public boolean onItemLongClick(ViewGroup parent, View view, RespCheckPrdList.DataBean.DatasBean o, int position) {
                    return false;
                }
            });
            adapter.setMore(new TempRVCommonAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    pullablePreDefault.requestLoadMore();
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

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(FragPrdUnchecked.this);
        super.onDestroy();
    }
    public void onEventMainThread(String event) {
        Debug.info(TAG, "event 接受到数据" + event);
        if (event.equals("check_1")) {
            pullablePreDefault.requestRefresh();
            if (getUserVisibleHint()){
                showSnackBar(parentView,"提交成功！",0,Snackbar.LENGTH_LONG);
            }
        }
       /* String msg = "onEventMainThread收到了消息：" + event.getMsg();
        Log.d("harvic", msg);
        tv.setText(msg);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();*/
    }
}
