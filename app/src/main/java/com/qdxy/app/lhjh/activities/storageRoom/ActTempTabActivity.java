package com.qdxy.app.lhjh.activities.storageRoom;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lf.tempcore.tempActivity.TempActivity;
import com.qdxy.app.lhjh.R;

/**
 * Created by mac on 2017/2/17.
 */

public abstract class ActTempTabActivity extends TempActivity {
    private final String TAG="ActExceptionDaoJuList";
    private TabLayout tablayout;
    ViewPager viewPager;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_production_layout);
    }





    @Override
    protected void bindValues() {
//        TabLayout tablayout = (TabLayout) findViewById(R.id.act_production_TabLayout);
//        ViewPager viewPager = (ViewPager) findViewById(R.id.act_production_ViewPager);
//        viewPager.setOffscreenPageLimit(2);
//        initTabs(tablayout, viewPager);
    }

    @Override
    protected void OnViewClicked(View v) {

    }

    public TabLayout getTablayout() {
        if (null==tablayout){
            tablayout = (TabLayout) findViewById(R.id.act_production_TabLayout);
        }
        return tablayout;
    }


    public ViewPager getViewPager() {
        if (null==viewPager){
            viewPager=(ViewPager) findViewById(R.id.act_production_ViewPager);
        }
        return viewPager;
    }


//    protected abstract void initTabs();
  /*  private void initTabs(TabLayout tabLayout, ViewPager viewPager) {
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

    }*/
}
