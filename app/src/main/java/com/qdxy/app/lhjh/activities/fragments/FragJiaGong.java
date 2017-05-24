package com.qdxy.app.lhjh.activities.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.lf.tempcore.tempFragment.TempFragment;
import com.lf.tempcore.tempModule.tempUtils.TempDensityUtil;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.adapters.FragJiaGongExpandableAdapter;

/**
 * Created by KY on 2016/10/9.
 * 加工
 */

public class FragJiaGong extends TempFragment {
    private ExpandableListView mExpandLV;
    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.frag_jia_gong_layout, null);
    }


    @Override
    protected void setListeners(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void bundleValues(View view, @Nullable Bundle savedInstanceState) {

        mExpandLV = (ExpandableListView) view.findViewById(R.id.frag_jia_gong_ExpandableLV);
        mExpandLV.setGroupIndicator(null);
        mExpandLV.setDividerHeight(0);
        FragJiaGongExpandableAdapter adapter = new FragJiaGongExpandableAdapter(getActivity(),null);
        mExpandLV.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                return true;
            }
        });
        mExpandLV.setAdapter(adapter);
        for (int i = 0; i < 5; i++) {
            mExpandLV.expandGroup(i);
        }
    }

    @Override
    protected void OnViewClicked(View v) {

    }




}
