package com.qdxy.app.lhjh.activities.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lf.tempcore.tempFragment.TempFragment;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVCommonAdapter;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVHolder;
import com.qdxy.app.lhjh.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KY on 2016/10/9.
 * 投料
 */

public class FragTouLiao extends TempFragment {
    private RecyclerView mRecyclerView;
    private View mRootView;
    private FloatingActionButton mFab;
    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_tou_liao_layout, null);
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.fram_tou_liao_RecyclerView);
        return mRootView;
    }

    @Override
    protected void setListeners(View view, @Nullable Bundle savedInstanceState) {
    mFab = (FloatingActionButton) view.findViewById(R.id.frag_tou_liao_fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTouLiaoDialog();
            }
        });
    }

    @Override
    protected void bundleValues(View view, @Nullable Bundle savedInstanceState) {
        List<String> itemData = new ArrayList<>();
        itemData.add("1");
        itemData.add("1");
        itemData.add("1");
        itemData.add("1");
        itemData.add("1");

        initListData(mRecyclerView, itemData);
    }

    @Override
    protected void OnViewClicked(View v) {

    }

    private void initListData(RecyclerView rv, List<String> itemData) {
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        TempRVCommonAdapter<String> adapter = new TempRVCommonAdapter<String>(getActivity(), R.layout.item_tou_liao_layout) {
            @Override
            public void bindItemValues(TempRVHolder holder, String s) {

            }
        };
        rv.setAdapter(adapter);
        adapter.updateRefresh(itemData);
    }
    private void showTouLiaoDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.dialog_tou_liao_layout).setNegativeButton("取消",null).setPositiveButton("确定",null).show();

    }
}
