package com.qdxy.app.lhjh.activities.repair;

import android.os.Bundle;

import com.qdxy.app.lhjh.activities.qualityCheck.ActQualityCheckList;

/**
 * Created by mac on 2017/1/22.
 */

public class ActRepairList extends ActQualityCheckList{
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        super.initContentView(savedInstanceState);
        mType=1;
    }
}
