package com.qdxy.app.lhjh.activities.qualityCheck;

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
 * 质量检查/送修
 * Created by mac on 2017/1/16.
 */

public class ActQualityCheckList extends TempActivity{
    private final String TAG="ActExceptionDaoJuList";
    /**
     * 0：送檢 1：送修
     */
    public int mType=0;
//    public String tab0="未检测",tab1="已检测";
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_production_layout);
    }

    @Override
    protected void findViews() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        initToolbar(toolbarTop, mType==0?"质检列表":"工件维修列表");
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

        titleList.add(mType==0?"未检测":"未维修");
        Fragment fragPrdUnchecked = new FragQualityCheckList();
        Bundle bundlePrdUnchecked = new Bundle();
        bundlePrdUnchecked.putBoolean("isJudge", false);
        bundlePrdUnchecked.putInt("type", mType);
        fragPrdUnchecked.setArguments(bundlePrdUnchecked);
        fragmentList.add(fragPrdUnchecked);

        titleList.add(mType==0?"已检测":"已维修");
        Fragment fragPrdChecked = new FragQualityCheckList();
        Bundle bundlePrdChecked = new Bundle();
        bundlePrdChecked.putBoolean("isJudge", true);
        bundlePrdChecked.putInt("type", mType);
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
}
