package com.qdxy.app.lhjh.activities.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lf.tempcore.tempActivity.TempActivity;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullablePreDefault;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullablePresenterI;
import com.lf.tempcore.tempModule.tempMVPCommI.tempPullableComms.TempPullableViewI;
import com.lf.tempcore.tempModule.tempRemotComm.TempRemotAPIConnecter;
import com.lf.tempcore.tempViews.tempRecyclerView.OnItemClickListener;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVCommonAdapter;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVHolder;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRefreshRecyclerView;
import com.qdxy.app.lhjh.MyApplication;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.api.TempAPI;
import com.qdxy.app.lhjh.views.CustomTabView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * 批次管理
 * Created by KY on 2016/11/11.
 */

public class ActManagePiCi extends TempActivity implements ViewPiCi {
    private final String TAG = "ActManagePiCi";
    //    @Bind(R.id.temp_fab)
//    FloatingActionButton floatingActionButton;
    @Bind(R.id.temp_refresh_recyclerView)
    TempRefreshRecyclerView mTempRefreshRecyclerView;
    @Bind(R.id.customTabView)
    CustomTabView customTabView;
    private BottomSheetDialog mButtonSheet;
    private TempPullablePresenterI mTempPullablePresenterI;
    private TempPullableViewI<RespManagePiCi> mTempPullableViewI;
    private TempRVCommonAdapter<RespManagePiCi.DataBean.DatasBean> mTempRVCommonAdapter;
    private PrePiCi mPrePiCi;
    private int mActive = 1;//0未激活，2激活
//    private List<RespManagePiCi.DataBean.DatasBean> leftTabData, rightTabData;
    private RespManagePiCi.DataBean.DatasBean checkedData;
    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_pici_manage_layout);
    }

    @Override
    protected void findViews() {
        Toolbar toolbarTop = (Toolbar) findViewById(R.id.toolbar_top);
        toolbarTop.setNavigationIcon(com.lf.tempcore.R.mipmap.top_bar_back_icon);
        toolbarTop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void setListeners() {
        customTabView.setmOnTabSelectedListener(new CustomTabView.OnTabSelectedListener() {
            @Override
            public void onTabLeftSelected(String content) {
                if (mActive != 2) {
                    mActive = 2;
                    mTempRVCommonAdapter.updateRefresh(new ArrayList<RespManagePiCi.DataBean.DatasBean>());
                    mTempPullablePresenterI.requestRefresh();
                }
            }

            @Override
            public void onTabRightSelected(String content) {
                if (mActive != 0) {
                    mActive = 0;
                    mTempRVCommonAdapter.updateRefresh(new ArrayList<RespManagePiCi.DataBean.DatasBean>());
                    mTempPullablePresenterI.requestRefresh();
                }

            }
        });
        mTempRVCommonAdapter = new TempRVCommonAdapter<RespManagePiCi.DataBean.DatasBean>(ActManagePiCi.this, R.layout.item_manage_pici_layout) {
            @Override
            public void bindItemValues(TempRVHolder holder, RespManagePiCi.DataBean.DatasBean s) {
                switch (s.getStatus()){
                    case 0://未启用
                        holder.setImageResource(R.id.item_manage_pici_status_image,R.mipmap.icon_manage_pici_paused);
                        holder.setText(R.id.item_manage_pici_status_title_text,"未启用");

                        break;
                    case 1:
                        holder.setImageResource(R.id.item_manage_pici_status_image,R.mipmap.icon_manage_pici_started);
                        holder.setText(R.id.item_manage_pici_status_title_text,"已启用");
                        break;
                    case 2:
                        holder.setImageResource(R.id.item_manage_pici_status_image,R.mipmap.icon_manage_pici_stoped);
                        holder.setText(R.id.item_manage_pici_status_title_text,"已停用");
                        break;
                }
                holder.setText(R.id.item_manage_pici_status_time_text,s.getCreationTime());
                holder.setText(R.id.item_manage_pici_status_content_text,"批次："+s.getCode());
                holder.setText(R.id.item_manage_pici_status_line_text,"生产线："+s.getProductionLineName());
            }
        };
        mTempPullableViewI = new TempPullableViewI<RespManagePiCi>() {
            @Override
            public void onInit(RespManagePiCi tempResponse) {

            }

            @Override
            public void onRefresh(RespManagePiCi tempResponse) {
                Debug.info(TAG, "更新列表");
                if ( tempResponse.getData() == null || tempResponse.getData().getDatas().isEmpty()) {
                    showToast("没有数据");
//                    mTempRVCommonAdapter.
                }
                mTempRVCommonAdapter.updateRefresh(tempResponse.getData()==null?null:tempResponse.getData().getDatas());

            }

            @Override
            public void onLoadmore(RespManagePiCi tempResponse) {
                Debug.info(TAG, "加载更多");
                mTempRVCommonAdapter.updateLoadMore(tempResponse.getData().getDatas());
//                leftTabData = mTempRVCommonAdapter.getData();
            }

            @Override
            public void refreshStatus(boolean succeed) {

            }

            @Override
            public void loadMoreStatus(boolean succeed) {

            }

            @Override
            public void showPullableProgressDialog() {
//                Debug.info("显示dialog");
            }

            @Override
            public void dismissPullableProgressDialog() {

            }

            @Override
            public void onError(TempErrorCode code, String message) {
                superViewError(code, message);
            }
        };
        mTempPullablePresenterI = new TempPullablePreDefault<RespManagePiCi>(mTempPullableViewI) {
            @Override
            public Call<RespManagePiCi> createObservable(int queryPage, int querysize, int currentPage) {
                return TempRemotAPIConnecter.INSTANCE.createRemoteApi(TempAPI.class).requestManagePiCiList(mActive + "", queryPage + "");
//                return null;
            }
        };
    }

    @Override
    protected void bindValues() {
        mPrePiCi = new PrePiCi(this);
        initRecyclerview(mTempRefreshRecyclerView, mTempRVCommonAdapter);
        mTempPullablePresenterI.requestRefresh();
    }

    @OnClick({R.id.temp_fab})
    @Override
    protected void OnViewClicked(View v) {
        switch (v.getId()) {
            case R.id.temp_fab:
               Intent intent = new Intent(ActManagePiCi.this,ActCreatePiCi.class);
                startActivityForResult(intent,99);
                break;
        }
    }

    @Override
    public void viewCreateSucceed(String message) {
        showSnackBar(message, 3, Snackbar.LENGTH_LONG);
    }

    @Override
    public void viewUpdateSucceed(String message) {
        Debug.info(TAG,"更新批次状态成功");
        showSnackBar(message, 3, Snackbar.LENGTH_LONG);
        mTempPullablePresenterI.requestRefresh();
    }

    @Override
    public void viewUpdateFailed(String message) {
        showSnackBar(message, 1, Snackbar.LENGTH_LONG);
    }

    @Override
    public void viewCreateFailed(String message) {
        showSnackBar(message, 1, Snackbar.LENGTH_LONG);
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

    private void initRecyclerview(TempRefreshRecyclerView recyclerView, TempRVCommonAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(ActManagePiCi.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter.setOnItemClickListener(new OnItemClickListener<RespManagePiCi.DataBean.DatasBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, RespManagePiCi.DataBean.DatasBean o, int position) {
                // TODO: 2016/11/23 修改批次状态 批次状态0-未启用1-已启用2-已停止
                checkedData = o;
                    showOperationButtomSheets(o.getStatus());
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, RespManagePiCi.DataBean.DatasBean o, int position) {
                return false;
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.setMore(new TempRVCommonAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mTempPullablePresenterI.requestLoadMore();
            }
        });

        recyclerView.setRefreshListener(new TempRefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTempPullablePresenterI.requestRefresh();
            }
        });

//        recyclerview.addItemDecoration(new TempRVDividerDecoration(Color.parseColor("#EEEFF3"), TempDensityUtil.dip2px(ActExceptionEquList.this, 12.0f)));
    }

    /**
     * 显示底部导航栏
     */
    private void showOperationButtomSheets(int status) {
            mButtonSheet=null;
            mButtonSheet = new BottomSheetDialog(ActManagePiCi.this);
            View contentView = LayoutInflater.from(ActManagePiCi.this).inflate(R.layout.buttom_sheets_manage_pici_layout, null);
            View cancel = contentView.findViewById(R.id.buttom_sheets_jia_gong_cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mButtonSheet.dismiss();
                }
            });
            View start = contentView.findViewById(R.id.buttom_sheets_manage_pici_start);
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPrePiCi.updateProductionBatchType(1+"",checkedData.getId()+"");
                    mButtonSheet.dismiss();
                }
            });
            View stop = contentView.findViewById(R.id.buttom_sheets_manage_pici_stop);
            stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPrePiCi.updateProductionBatchType(2+"",checkedData.getId()+"");
                    mButtonSheet.dismiss();
                }
            });
            switch (status) {
                case 0://未启动
                case 2://已停止
                    stop.setVisibility(View.GONE);
                    start.setVisibility(View.VISIBLE);
                    break;
                case 1://已启用
                    start.setVisibility(View.GONE);
                    stop.setVisibility(View.VISIBLE);
                    break;
            }
            mButtonSheet.setContentView(contentView);

        mButtonSheet.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==99&&resultCode==200){
            Debug.info(TAG,"创建批次成功刷新列表");
            if (mActive==0){
                mTempPullablePresenterI.requestRefresh();
            }else{
                customTabView.selectedRight();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
