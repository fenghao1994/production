package com.qdxy.app.lhjh.activities.check;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lf.tempcore.tempActivity.TempActivity;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.adapters.ActProductionTabAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品抽检列表
 * Created by KY on 2016/11/30.
 */

public class ActCheckPrdList extends TempActivity {
    private final String TAG="ActCheckPrdList";
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_production_layout);
    }

    @Override
    protected void findViews() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        initToolbar(toolbarTop, "产品抽检列表");
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void bindValues() {
        TabLayout tablayout = (TabLayout) findViewById(R.id.act_production_TabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.act_production_ViewPager);
        viewPager.setOffscreenPageLimit(2);
        initTabs(tablayout, viewPager);
    }

    @Override
    protected void OnViewClicked(View v) {

    }

    private void initTabs(TabLayout tabLayout, ViewPager viewPager) {
        String patchCode = getIntent().getStringExtra("batchCode");
        Debug.info(TAG,"patchCode="+patchCode);
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        // TODO: 2016/11/24 有多个加工工序
        tabLayout.removeAllTabs();
        viewPager.removeAllViews();
        titleList.add("待抽检");
        Fragment fragPrdUnchecked = new FragPrdUnchecked();
        Bundle bundlePrdUnchecked = new Bundle();
        bundlePrdUnchecked.putString("patchCode", patchCode);
        fragPrdUnchecked.setArguments(bundlePrdUnchecked);
        fragmentList.add(fragPrdUnchecked);

        titleList.add("已抽检");
        Fragment fragPrdChecked = new FragPrdChecked();
        Bundle bundlePrdChecked = new Bundle();
        bundlePrdChecked.putString("patchCode", patchCode);
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
