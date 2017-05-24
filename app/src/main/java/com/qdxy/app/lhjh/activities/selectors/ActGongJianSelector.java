package com.qdxy.app.lhjh.activities.selectors;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.lf.tempcore.tempActivity.TempActivity;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullablePreDefault;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullablePresenterI;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullableViewI;
import com.lf.tempcore.tempModule.tempUtils.TempDensityUtil;
import com.lf.tempcore.tempResponse.TempResponse;
import com.lf.tempcore.tempViews.tempRecyclerView.OnItemClickListener;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVCommonAdapter;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVDividerDecoration;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVHolder;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRefreshRecyclerView;
import com.qdxy.app.lhjh.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit2.Call;

/**
 * 工件选择列表
 * Created by KY on 2016/11/8.
 */

public class ActGongJianSelector extends TempActivity {
    @Bind(R.id.temp_refresh_recyclerView) TempRefreshRecyclerView mTempRefreshRecyclerView;
    private TempRVCommonAdapter<String> mTempRVCommonAdapter;
    private TempPullablePresenterI mTempPullablePresenterI;
    private TempPullableViewI<TempResponse> mTempPullableViewI;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_selector_layout);
    }

    @Override
    protected void findViews() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        initToolbar(toolbarTop, "工件列表");
    }

    @Override
    protected void setListeners() {
        mTempRVCommonAdapter = new TempRVCommonAdapter<String>(ActGongJianSelector.this,R.layout.item_gongjian_selector_layout) {
            @Override
            public void bindItemValues(TempRVHolder holder, String s) {

            }
        };
        mTempPullableViewI = new TempPullableViewI<TempResponse>() {
            @Override
            public void onInit(TempResponse tempResponse) {

            }

            @Override
            public void onRefresh(TempResponse tempResponse) {

            }

            @Override
            public void onLoadmore(TempResponse tempResponse) {

            }

            @Override
            public void refreshStatus(boolean succeed) {

            }

            @Override
            public void loadMoreStatus(boolean succeed) {

            }

            @Override
            public void showPullableProgressDialog() {

            }

            @Override
            public void dismissPullableProgressDialog() {

            }
            @Override
            public void onError(TempErrorCode code, String message) {
                superViewError(code,message);
            }
        };
        mTempPullablePresenterI = new TempPullablePreDefault<TempResponse>(mTempPullableViewI) {
            @Override
            public Call<TempResponse> createObservable(int queryPage, int querysize, int currentPage) {
                return null;
            }
        };
    }

    @Override
    protected void bindValues() {
        initReceiverview(mTempRefreshRecyclerView, mTempRVCommonAdapter);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add("i" + i);
        }
        mTempRVCommonAdapter.updateRefresh(data);
    }

    @Override
    protected void OnViewClicked(View v) {

    }

    private void initReceiverview(TempRefreshRecyclerView recyclerview, TempRVCommonAdapter adapter) {
        recyclerview.setLayoutManager(new LinearLayoutManager(ActGongJianSelector.this));
        adapter.setOnItemClickListener(new OnItemClickListener<String>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, String o, int position) {
//                startActivity(new Intent(ActGongJianSelector.this,ActDealWithException.class));
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, String o, int position) {
                return false;
            }
        });
        recyclerview.setAdapter(adapter);
        recyclerview.addItemDecoration(new TempRVDividerDecoration(Color.parseColor("#EEEFF3"), TempDensityUtil.dip2px(ActGongJianSelector.this, 1.0f)));
    }
}
