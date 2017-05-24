package com.qdxy.app.lhjh.activities.product;

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
import com.qdxy.app.lhjh.activities.fragments.FragJiaGong2;
import com.qdxy.app.lhjh.activities.fragments.FragTouLiaoV2;
import com.qdxy.app.lhjh.activities.fragments.FragZhuangXiang;
import com.qdxy.app.lhjh.adapters.ActProductionTabAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by KY on 2016/10/8.
 * 生产加工
 */

public class ActProduction extends TempActivity {
    private final String TAG = ActProduction.class.getSimpleName();
    private PreProduct preProduct;
    private ViewProductI viewProductI;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_production_layout);

    }

    @Override
    protected void findViews() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        initToolbar(toolbarTop, "产品加工");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }

    @Override
    protected void setListeners() {
//        EventBus.getDefault().register(ActProduction.this);
        viewProductI = new ViewProductI() {
            @Override
            public void onMachiningCountByProcedure(RespProductionCount data) {

            }

            @Override
            public void onChangeTool(String message) {

            }

            @Override
            public void onFeedingNumSucceed(RespFeedNum data) {

            }

            @Override
            public void onProductListSucceed(RespProductList data) {

            }

            @Override
            public void getroceduresByBatch(RespBatchByInBox data) {

            }

            @Override
            public void onCreateProductSucceed(String message) {

            }

            @Override
            public void onInBoxSucceed(String message) {

            }

            @Override
            public void onInBoxDetailSucceed(RespInBoxDetail data) {

            }

            @Override
            public void onMachineOperationSucceed(RespMachineOperation data) {
                Debug.info("获取自己在加工里能做的操作succeed");
//                data.getData().setCanSendMaterial(false);
//                data.getData().setCanInBox(false);
//                data.getData().setCanMachine(false);
                /*List<String> tabs = new ArrayList<>();
                if (data.getData().isCanSendMaterial() && !data.getData().isCanMachine() && !data.getData().isCanInBox()) {
                    tabs.add("投料");

                    return;
                } else if (!data.getData().isCanSendMaterial() && data.getData().isCanMachine() && !data.getData().isCanInBox()) {

                } else if (!data.getData().isCanSendMaterial() && !data.getData().isCanMachine() && data.getData().isCanInBox()) {

                }
                if (data.getData().isCanSendMaterial()) {
                    tabs.add("投料");
                }
                if (data.getData().isCanMachine()) {
                    tabs.add("加工");
                }
                if (data.getData().isCanInBox()) {
                    tabs.add("装箱");
                }*/


                    TabLayout tablayout = (TabLayout) findViewById(R.id.act_production_TabLayout);
                    ViewPager viewPager = (ViewPager) findViewById(R.id.act_production_ViewPager);
                viewPager.setOffscreenPageLimit(2);
                    initTabs(tablayout, viewPager, data);

            }

            @Override
            public void getBatchBySendMaterial(RespBatchByInBox data) {

            }

            @Override
            public void onNextSucceed(String message) {

            }

            @Override
            public void onUpdateStatusSucceed(String message) {

            }

            @Override
            public void getBatchByInBox(RespBatchByInBox data) {

            }

            @Override
            public void onFailed(int who, String message) {

            }

            @Override
            public void onMachineOperationFailed(String message) {

            }

            @Override
            public void touliaoSucceed(String message) {

            }

            @Override
            public void touliaoFailed(String message) {

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
        preProduct = new PreProduct(viewProductI);
    }

    @Override
    protected void bindValues() {
        preProduct.getMachineOperation();
    }

    @Override
    protected void OnViewClicked(View v) {

    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Debug.info(TAG, "进入action点击" + item.getItemId());
        if (item.getItemId() == android.R.id.home) {
            Debug.info(TAG, "返回按钮点击");
        }
        return super.onOptionsItemSelected(item);
    }*/


    private void initTabs(TabLayout tabLayout, ViewPager viewPager, RespMachineOperation data) {
        List<Fragment> fragmentList = new ArrayList<>();
        List<String > titleList =new ArrayList<>();
        if (data.getData().isCanSendMaterial()) {
            titleList.add("投料");
            fragmentList.add(new FragTouLiaoV2());
        }
        if (data.getData().isCanMachine()) {
            titleList.add("加工");
            fragmentList.add(new FragJiaGong2());
        }
        if (data.getData().isCanInBox()) {
            titleList.add("装箱");
            fragmentList.add(new FragZhuangXiang());
        }
        if (fragmentList.isEmpty()) {
            // TODO: 2016/11/24 数据为空 
            superViewMessage("你没有可操作的工序");
        } else if (fragmentList.size() == 1) {
            // TODO: 2016/11/24 只有一个数据 
            /*    if (tabs.get(0).equals("投料")) {
                fragmentList.add(new FragTouLiaoV2());
            } else if (tabs.get(0).equals("加工")) {
                fragmentList.add(new FragJiaGong2());
            } else if (tabs.get(0).equals("装箱")) {
                fragmentList.add(new FragZhuangXiang());
            }*/
            ActProductionTabAdapter adapter = new ActProductionTabAdapter(getSupportFragmentManager(), fragmentList, titleList);
            tabLayout.setVisibility(View.GONE);
            viewPager.setAdapter(adapter);//给ViewPager设置适配器
        } else {
            // TODO: 2016/11/24 有多个加工工序 
            tabLayout.removeAllTabs();
            viewPager.removeAllViews();

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
    @Override
    protected void onDestroy() {
//        EventBus.getDefault().unregister(ActProduction.this);//反注册EventBus
        super.onDestroy();
    }
}
