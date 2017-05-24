package com.qdxy.app.lhjh.activities.onOffLine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lf.tempcore.tempActivity.TempActivity;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.qdxy.app.lhjh.MyApplication;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.activities.global.PreGlobal;
import com.qdxy.app.lhjh.activities.global.ViewGlobal;
import com.qdxy.app.lhjh.adapters.ActProductionTabAdapter;
import com.qdxy.app.lhjh.bean.RespProductLines;
import com.qdxy.app.lhjh.bean.RespProductModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2016/12/26.
 */

class ActProductionOnline extends TempActivity{
    private final String TAG ="ActProductionOnline";
    private PreGlobal preGlobal;
    private ViewGlobal viewGlobal;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_production_layout);
    }

    @Override
    protected void findViews() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        initToolbar(toolbarTop, "工件上线");
    }

    @Override
    protected void setListeners() {
        viewGlobal=new ViewGlobal() {
            @Override
            public void onGetProductionLinesSucceed(RespProductLines data) {
                Debug.info(TAG,"获取到生产线数据" + data.toString());
                TabLayout tablayout = (TabLayout) findViewById(R.id.act_production_TabLayout);
                ViewPager viewPager = (ViewPager) findViewById(R.id.act_production_ViewPager);
                viewPager.setOffscreenPageLimit(2);
                initTabs(tablayout, viewPager,data.getData());
            }

            @Override
            public void onGetProductionLinesFailed(TempErrorCode code, String message) {
                superViewMessage(message);
            }

            @Override
            public void onGetProductTypeSucceed(RespProductModel data) {
//                Debug.info("获取到产品型号数据" + data.toString());
            }

            @Override
            public void onGetProductTypeFailed(TempErrorCode code, String message) {
                superViewMessage(message);
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
                superViewMessage(message);
            }

            @Override
            public void viewError(TempErrorCode code, String message) {
                superViewError(code, message);
            }
        };
        preGlobal= new PreGlobal(viewGlobal);
        preGlobal.requestProductedLinesAll();

    }

    @Override
    protected void bindValues() {

    }

    @Override
    protected void OnViewClicked(View v) {

    }
    private void initTabs(TabLayout tabLayout, ViewPager viewPager,List<RespProductLines.DataBean> dataList) {
        List<Fragment> fragmentList = new ArrayList<>();
        List<String > titleList =new ArrayList<>();

        if (dataList==null||dataList.size()==0){
            superViewMessage("生产线数据为空！");
        }else if (dataList.size()==1){
            Debug.info("只有一个生产线");
            titleList.add(dataList.get(0).getName());
            Fragment fragPrdUnchecked = new FragOnLineList();
            Bundle bundlePrdUnchecked = new Bundle();
            bundlePrdUnchecked.putInt("lineId", dataList.get(0).getId());
            bundlePrdUnchecked.putInt("status", 2);
            fragPrdUnchecked.setArguments(bundlePrdUnchecked);
            fragmentList.add(fragPrdUnchecked);
            ActProductionTabAdapter adapter = new ActProductionTabAdapter(getSupportFragmentManager(), fragmentList, titleList);
            tabLayout.setVisibility(View.GONE);
            viewPager.setAdapter(adapter);//给ViewPager设置适配器
        }
        else {
            // TODO: 2016/11/24 有多个加工工序

            tabLayout.removeAllTabs();
            viewPager.removeAllViews();

            tabLayout.setTabMode(TabLayout.MODE_FIXED);
            for (int i = 0; i < dataList.size(); i++) {
                Fragment fragPrdUnchecked = new FragOnLineList();
                Bundle bundlePrdUnchecked = new Bundle();
                bundlePrdUnchecked.putInt("lineId", dataList.get(i).getId());
                bundlePrdUnchecked.putInt("status", 2);
                fragPrdUnchecked.setArguments(bundlePrdUnchecked);
                fragmentList.add(fragPrdUnchecked);
                titleList.add(dataList.get(i).getName());
                tabLayout.addTab(tabLayout.newTab().setText(dataList.get(i).getName()));
            }
            ActProductionTabAdapter adapter = new ActProductionTabAdapter(getSupportFragmentManager(), fragmentList, titleList);
            viewPager.setAdapter(adapter);//给ViewPager设置适配器
            tabLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。
            tabLayout.setTabsFromPagerAdapter(adapter);//给Tabs设置适配器
        }


    }
}
