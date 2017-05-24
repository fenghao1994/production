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
 * 加工异常列表
 * Created by KY on 2016/11/9.
 */

public class ActExceptionJiaGongList extends TempActivity {
    private final String TAG="ActExceptionDaoJuList";
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_production_layout);
    }

    @Override
    protected void findViews() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        initToolbar(toolbarTop, "工件异常列表");
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
        Fragment fragPrdUnchecked = new FragExceptionJiaGongList();
        Bundle bundlePrdUnchecked = new Bundle();
        bundlePrdUnchecked.putBoolean("isJudge", false);
        fragPrdUnchecked.setArguments(bundlePrdUnchecked);
        fragmentList.add(fragPrdUnchecked);

        titleList.add("已处理");
        Fragment fragPrdChecked = new FragExceptionJiaGongList();
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
}
