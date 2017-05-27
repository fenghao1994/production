package com.qdxy.app.lhjh.activities.count;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lf.tempcore.tempActivity.TempActivity;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullablePreDefault;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullableViewI;
import com.lf.tempcore.tempModule.tempRemotComm.TempRemotAPIConnecter;
import com.lf.tempcore.tempModule.tempUtils.TempDensityUtil;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVCommonAdapter;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVDividerDecoration;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVHolder;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRefreshRecyclerView;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.api.TempAPI;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * Created by fenghao on 2017/5/25.
 */
//分管的人的具体工序的具体统计
public class ProductionCountActivity extends TempActivity {

    @Bind(R.id.toolbar_top)
    Toolbar toolbarTop;
    @Bind(R.id.time_start)
    TextView timeStart;
    @Bind(R.id.time_end)
    TextView timeEnd;
    @Bind(R.id.start_data)
    TextView startData;
    @Bind(R.id.end_data)
    TextView endData;
    @Bind(R.id.choose_date)
    LinearLayout chooseDate;
    @Bind(R.id.datePicker)
    DatePicker datePicker;
    @Bind(R.id.count_cancel)
    TextView countCancel;
    @Bind(R.id.count_ok)
    TextView countOk;
    @Bind(R.id.choose_start_end_date)
    FrameLayout chooseStartEndDate;
    @Bind(R.id.act_arrange_pro_RecyclerView)
    TempRefreshRecyclerView refreshRecyclerView;



    private TempPullablePreDefault<RespCountProductionList> pullableDefault;
    private TempPullableViewI<RespCountProductionList> pullableViewI;
    private TempRVCommonAdapter<RespCountProductionList.DataBean> arrangeAdapter;
    private ViewCountI viewCountI;


    private String begin;
    private String end;

    private int id;

    private int startYear, startMonth, startDay, endYear, endMonth, endDay;
    //判断是点击起始时间还是结束时间    起始时间为true  结束时间为false
    private boolean flag;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_production_count);
        initToolbar(toolbarTop, "统计");
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void setListeners() {
        timeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;
                if (chooseStartEndDate.getVisibility() == View.VISIBLE) {
                    chooseStartEndDate.setVisibility(View.GONE);
                    startImg(R.drawable.triangle_up);
                } else {
                    chooseStartEndDate.setVisibility(View.VISIBLE);
                    startImg(R.drawable.triangle_lower);
                }
            }
        });
        timeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = false;
                if (chooseStartEndDate.getVisibility() == View.VISIBLE) {
                    chooseStartEndDate.setVisibility(View.GONE);
                    endImg(R.drawable.triangle_up);
                } else {
                    chooseStartEndDate.setVisibility(View.VISIBLE);
                    endImg(R.drawable.triangle_lower);
                }
            }
        });
        countCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseStartEndDate.setVisibility(View.GONE);
                startImg(R.drawable.triangle_up);
                endImg(R.drawable.triangle_up);
            }
        });
        countOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    startYear = datePicker.getYear();
                    startMonth = datePicker.getMonth() + 1; //月数是从0开始算的
                    startDay = datePicker.getDayOfMonth();
                } else {
                    endYear = datePicker.getYear();
                    endMonth = datePicker.getMonth() + 1; //月数是从0开始算的
                    endDay = datePicker.getDayOfMonth();
                }
                chooseStartEndDate.setVisibility(View.GONE);
                startImg(R.drawable.triangle_up);
                endImg(R.drawable.triangle_up);


                if (startYear != 0 && endYear != 0){
                    chooseDate.setVisibility(View.VISIBLE);
                    begin = startYear + "-" + startMonth + "-" + startDay;
                    end = endYear + "-" + endMonth + "-" + endDay;
                    startData.setText(begin);
                    endData.setText(end);
                    sendRequest();
                }
            }
        });



        pullableViewI = new TempPullableViewI<RespCountProductionList>() {
            @Override
            public void onInit(RespCountProductionList data) {

            }

            @Override
            public void onRefresh(RespCountProductionList data) {
                arrangeAdapter.updateRefresh(data.getData());
            }

            @Override
            public void onLoadmore(RespCountProductionList data) {
                // 默认全部展开
                arrangeAdapter.updateLoadMore(data.getData());
            }

            @Override
            public void refreshStatus(boolean succeed) {

            }

            @Override
            public void loadMoreStatus(boolean succeed) {

            }

            @Override
            public void showPullableProgressDialog() {
                superViewProgress();
            }

            @Override
            public void dismissPullableProgressDialog() {
                superViewDismissProgress();
            }

            @Override
            public void onError(TempErrorCode code, String message) {
                superViewError(code, message);
            }
        };


        pullableDefault = new TempPullablePreDefault<RespCountProductionList>(pullableViewI) {
            @Override
            public Call<RespCountProductionList> createObservable(int queryPage, int querysize, int currentPage) {

                return TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).getMachiningCountTotal(begin, end, id);
            }
        };
    }

    /**
     * 发送查询请求
     */
    private void sendRequest() {
        pullableDefault.requestRefresh();
    }

    public void startImg(int resId) {
        Drawable navUp = getResources().getDrawable(resId);
        navUp.setBounds(0, 0, navUp.getMinimumWidth(), navUp.getMinimumHeight());
        timeStart.setCompoundDrawables(null, null, navUp, null);
    }

    public void endImg(int resId) {
        Drawable navUp = getResources().getDrawable(resId);
        navUp.setBounds(0, 0, navUp.getMinimumWidth(), navUp.getMinimumHeight());
        timeEnd.setCompoundDrawables(null, null, navUp, null);
    }

    @Override
    protected void bindValues() {
        initAdapter();
    }

    private void initAdapter() {
        refreshRecyclerView.setLayoutManager(new LinearLayoutManager(ProductionCountActivity.this));

        arrangeAdapter = new TempRVCommonAdapter<RespCountProductionList.DataBean>(ProductionCountActivity.this, R.layout.item_production_count) {
            @Override
            public void bindItemValues(TempRVHolder holder, final RespCountProductionList.DataBean dataBean) {
                holder.setText(R.id.production_name,dataBean.getName());
                holder.setOnClickListener(R.id.item_production_count_layout, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ProductionCountActivity.this, ProductionCountMoreInfoActivity.class);
                        intent.putExtra("DATA", (Serializable) dataBean);
                        startActivity(intent);
                    }
                });
            }
        };
        arrangeAdapter.setMore(new TempRVCommonAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                superViewDismissProgress();
            }
        });
        refreshRecyclerView.setRefreshListener(new TempRefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullableDefault.requestRefresh();
            }
        });
        refreshRecyclerView.addItemDecoration(new TempRVDividerDecoration(Color.parseColor("#ECECEC"), TempDensityUtil.dip2px(ProductionCountActivity.this,16f)));
        refreshRecyclerView.setAdapter(arrangeAdapter);
    }

    @Override
    protected void OnViewClicked(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        id = getIntent().getIntExtra("id", -1);
    }
}
