package com.qdxy.app.lhjh.activities.product;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.lf.tempcore.tempActivity.TempActivity;
import com.qdxy.app.lhjh.R;

/**
 * Created by mac on 2017/2/13.
 */

public class ActInBox extends TempActivity {
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_inbox_layout);
    }

    @Override
    protected void findViews() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        initToolbar(toolbarTop, "产品装箱");
    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void bindValues() {
//        FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
//        FragZhuangXiang fragZhuangXiang = new FragZhuangXiang();
//        fragmentTransaction.add(R.id.production_container,fragZhuangXiang);
//        fragmentTransaction.commit();

    }

    @Override
    protected void OnViewClicked(View v) {

    }
}
