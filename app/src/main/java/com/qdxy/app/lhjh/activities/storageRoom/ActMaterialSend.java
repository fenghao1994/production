package com.qdxy.app.lhjh.activities.storageRoom;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import com.qdxy.app.lhjh.adapters.ActProductionTabAdapter;
import com.qdxy.app.lhjh.emues.CommonType;

import java.util.ArrayList;
import java.util.List;

/**辅料领用发货
 * Created by mac on 2017/2/23.
 */

public class ActMaterialSend extends ActTempTabActivity{
    private final String TAG ="ActMaterialApprove";
    @Override
    protected void findViews() {
        initToolbar("辅料发货列表");
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void bindValues() {
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        getTablayout().removeAllTabs();
        getViewPager().removeAllViews();

        titleList.add("待处理");
        Fragment fragPrdUnchecked = new FragMaterialList();
        Bundle bundlePrdUnchecked = new Bundle();
        bundlePrdUnchecked.putBoolean("isHandle", false);
        bundlePrdUnchecked.putSerializable("type", CommonType.MATERIAL_FH);
        fragPrdUnchecked.setArguments(bundlePrdUnchecked);
        fragmentList.add(fragPrdUnchecked);

        titleList.add("已处理");
        Fragment fragPrdChecked = new FragMaterialList();
        Bundle bundlePrdChecked = new Bundle();
        bundlePrdChecked.putBoolean("isHandle", true);
        bundlePrdChecked.putSerializable("type",CommonType.MATERIAL_FH);
        fragPrdChecked.setArguments(bundlePrdChecked);
        fragmentList.add(fragPrdChecked);
        getTablayout().setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < titleList.size(); i++) {
            getTablayout().addTab(getTablayout().newTab().setText(titleList.get(i)));
        }
        ActProductionTabAdapter adapter = new ActProductionTabAdapter(getSupportFragmentManager(), fragmentList, titleList);
        getViewPager().setAdapter(adapter);//给ViewPager设置适配器
        getTablayout().setupWithViewPager(viewPager);//将getTablayout()和ViewPager关联起来。
        getTablayout().setTabsFromPagerAdapter(adapter);//给Tabs设置适配器
    }

    @Override
    protected void OnViewClicked(View v) {

    }
}
