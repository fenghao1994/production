package com.qdxy.app.lhjh.activities;

import android.os.Bundle;
import android.view.View;

import com.lf.tempcore.tempActivity.TempActivity;

/**
 * Created by KY on 2016/11/3.
 */

public class TestActivity extends TempActivity {
    public static TestLT lt;
    @Override
    protected void initContentView(Bundle savedInstanceState) {

    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void bindValues() {
        lt = new TestLT();
    }

    @Override
    protected void OnViewClicked(View v) {

    }
    public static class TestLT{

    }
}
