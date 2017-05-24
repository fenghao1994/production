package com.qdxy.app.lhjh.activities.exceptions;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lf.tempcore.tempActivity.TempActivity;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.adapters.ActProductionTabAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 异常判断列表
 * Created by KY on 2016/11/7.
 */

public class ActExceptionJudgementList extends TempActivity {
    private final String TAG="ActExceptionJudgementList";
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_production_layout);
    }

    @Override
    protected void findViews() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        initToolbar(toolbarTop, "异常判断列表");
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void bindValues() {
        TabLayout tablayout = (TabLayout) findViewById(R.id.act_production_TabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.act_production_ViewPager);
//        viewPager.setOffscreenPageLimit(2);
        initTabs(tablayout, viewPager);
    }

    @Override
    protected void OnViewClicked(View v) {

    }

    private void initTabs(TabLayout tabLayout, ViewPager viewPager) {
//        String patchCode = getIntent().getStringExtra("batchCode");
//        Debug.info(TAG,"patchCode="+patchCode);
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        tabLayout.removeAllTabs();
        viewPager.removeAllViews();

        titleList.add("未处理");
        Fragment fragPrdUnchecked = new FragExceptionJudgementList();
        Bundle bundlePrdUnchecked = new Bundle();
        bundlePrdUnchecked.putBoolean("isJudge", false);
        fragPrdUnchecked.setArguments(bundlePrdUnchecked);
        fragmentList.add(fragPrdUnchecked);

        titleList.add("已处理");
        Fragment fragPrdChecked = new FragExceptionJudgementList();
        Bundle bundlePrdChecked = new Bundle();
        bundlePrdChecked.putBoolean("isJudge", true);
        fragPrdChecked.setArguments(bundlePrdChecked);
        fragmentList.add(fragPrdChecked);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < titleList.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(titleList.get(i)));
        }
        ActProductionTabAdapter adapter = new ActProductionTabAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(adapter);//给ViewPager设置适配器
        tabLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。
        tabLayout.setTabsFromPagerAdapter(adapter);//给Tabs设置适配器

    }
   /* @Bind(R.id.temp_refresh_recyclerView)
    TempRefreshRecyclerView mTempRefreshRecyclerView;
    private TempPullablePreDefault<RespExceptionList> mTempPullablePresenterI;
    private TempPullableViewI<RespExceptionList> mTempPullableViewI;
    private TempRVCommonAdapter<RespExceptionList.DataBean.DatasBean> mTempRVCommonAdapter;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_exception_list_layout);
    }

    @Override
    protected void findViews() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        initToolbar(toolbarTop, "异常判断列表");
    }

    @Override
    protected void setListeners() {
        mTempRVCommonAdapter = new TempRVCommonAdapter<RespExceptionList.DataBean.DatasBean>(ActExceptionJudgementList.this, R.layout.item_exception_judgement_layout) {
            @Override
            public void bindItemValues(TempRVHolder holder, RespExceptionList.DataBean.DatasBean s) {
                holder.setText(R.id.item_exception_judgement_creationTime,s.getCreationTime());
                holder.setText(R.id.item_exception_judgement_creatorName,"创建人："+s.getCreatorName());
                holder.setText(R.id.item_exception_judgement_procedureName,"工序："+s.getProcedureName());
            }
        };
        mTempPullableViewI = new TempPullableViewI<RespExceptionList>() {
            @Override
            public void onInit(RespExceptionList tempResponse) {

            }

            @Override
            public void onRefresh(RespExceptionList tempResponse) {
                mTempRVCommonAdapter.updateRefresh(tempResponse.getData().getDatas());
            }

            @Override
            public void onLoadmore(RespExceptionList tempResponse) {
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
                superViewError(code, message);
            }
        };
        mTempPullablePresenterI = new TempPullablePreDefault<RespExceptionList>(mTempPullableViewI) {
            @Override
            public Call<RespExceptionList> createObservable(int queryPage, int querysize, int currentPage) {
                return TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).requestProblemList(queryPage+"");
            }
        };
    }

    @Override
    protected void bindValues() {
        initReceiverview(mTempRefreshRecyclerView, mTempRVCommonAdapter);
       *//* List<String> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add("i" + i);
        }*//*
//        mTempRVCommonAdapter.updateRefresh(data);
        mTempPullablePresenterI.requestRefresh();
    }

    @Override
    protected void OnViewClicked(View v) {

    }

    private void initReceiverview(TempRefreshRecyclerView recyclerview, TempRVCommonAdapter adapter) {
        recyclerview.setLayoutManager(new LinearLayoutManager(ActExceptionJudgementList.this));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(new OnItemClickListener<RespExceptionList.DataBean.DatasBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, RespExceptionList.DataBean.DatasBean o, int position) {
               Intent intent = new Intent(ActExceptionJudgementList.this, ActExceptionJudgement.class);
                intent.putExtra("id",o.getId());
                startActivityForResult(intent,100);
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, RespExceptionList.DataBean.DatasBean o, int position) {
                return false;
            }
        });
        recyclerview.setAdapter(adapter);
//        recyclerview.addItemDecoration(new TempRVDividerDecoration(Color.parseColor("#EEEFF3"), TempDensityUtil.dip2px(ActExceptionJudgementList.this, 12.0f)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==100&&resultCode==200){
            superViewMessage("提交成功！");
            mTempPullablePresenterI.requestRefresh();
        }
        super.onActivityResult(requestCode, resultCode, data);

    }*/
}
