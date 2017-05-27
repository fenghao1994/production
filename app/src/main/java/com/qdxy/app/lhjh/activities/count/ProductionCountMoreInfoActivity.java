package com.qdxy.app.lhjh.activities.count;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lf.tempcore.tempActivity.TempActivity;
import com.qdxy.app.lhjh.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.http.PUT;

//具体统计详情
public class ProductionCountMoreInfoActivity extends TempActivity {

    @Bind(R.id.toolbar_top)
    Toolbar toolbarTop;
    @Bind(R.id.listview)
    ExpandableListView listview;

    private RespCountProductionList.DataBean dataBean;
    private ArrayList<List<String>> chileList;
    private ArrayList<String> titleList;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_production_count_more_info);
        initToolbar(toolbarTop, "统计详情");
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void setListeners() {

    }

    @Override
    protected void bindValues() {

    }

    @Override
    protected void OnViewClicked(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        dataBean = (RespCountProductionList.DataBean) getIntent().getSerializableExtra("DATA");
        chileList = new ArrayList<>();
        titleList = new ArrayList<>();
        if (dataBean != null) {
            titleList.add("加工列表");
            if (dataBean.getMachList() != null) {
                chileList.add(dataBean.getMachList());
            }else {
                chileList.add(new ArrayList<String>());
            }
            titleList.add("工废列表");
            if (dataBean.getMachineWaste() != null) {
                chileList.add(dataBean.getMachineWaste());
            }else {
                chileList.add(new ArrayList<String>());
            }
            titleList.add("料废列表");
            if (dataBean.getMaterialWaste() != null) {
                chileList.add(dataBean.getMaterialWaste());
            }else {
                chileList.add(new ArrayList<String>());
            }
            titleList.add("机废列表");
            if (dataBean.getRobotWaste() != null) {
                chileList.add(dataBean.getRobotWaste());
            }else {
                chileList.add(new ArrayList<String>());
            }
        }
        ExpandableListViewaAdapter adapter = new ExpandableListViewaAdapter(this, titleList, chileList);
        listview.setAdapter(adapter);
        listview.setGroupIndicator(null);
    }
}
