package com.qdxy.app.lhjh.activities.storageRoom;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lf.tempcore.tempActivity.TempActivity;
import com.lf.tempcore.tempAdapter.TempListAdapter;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.lf.tempcore.tempModule.tempUtils.TempDensityUtil;
import com.qdxy.app.lhjh.MyApplication;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.activities.exceptions.HolderExceptionDeal;
import com.qdxy.app.lhjh.activities.product.ActInBoxDetail;
import com.qdxy.app.lhjh.views.TempMyListView;

import butterknife.Bind;

/**
 * 产品入库详情
 * Created by mac on 2017/2/28.
 */

public class ActApplyPrdDetail extends TempActivity {
    private final String TAG = "ActApplyPrdDetail";
    private PreStorageRoom mPreStorageRoom;
    private RespMaterialDetail mDataDetail;
    private int mId,mType;

    @Bind(R.id.body_items_title)
    TextView body_items_title;
    @Bind(R.id.body_items_tempMyListView)
    TempMyListView body_items_tempMyListView;
    @Bind(R.id.body_result_tempMyListView)
    TempMyListView body_result_tempMyListView;
    @Bind(R.id.body_result_rootView)
    LinearLayout body_result_rootView;
    @Bind(R.id.body_items_toolView)
    LinearLayout body_items_toolView;
    @Bind(R.id.act_apply_prd_detail_rootView)
    LinearLayout act_apply_prd_detail_rootView;

    @Override
    protected void initContentView(Bundle savedInstanceState) {
        setContentView(R.layout.act_apply_prd_detail_layout);
    }

    @Override
    protected void findViews() {
        mType = getIntent().getIntExtra("type",1);
        if (mType==2){
            initToolbar("产品入库确认详情");
        }else {
            initToolbar("产品入库审批详情");
        }
    }

    @Override
    protected void setListeners() {
        mPreStorageRoom = new PreStorageRoom(new ViewStorageRoom() {
            @Override
            public <RESULT> void onSucceed(RESULT result, int type) {
//                showSnackBar("提交成功", 0, Snackbar.LENGTH_LONG);
                if (type == 14) {
                    setResult(200);
                    finish();
                } else if (type == 19) {

                    Debug.info(TAG, "获取到审批详情数据");
                    mDataDetail = (RespMaterialDetail) result;

                    if (null == mDataDetail.getData()) {
                        showSnackBar("数据为空！", 0, Snackbar.LENGTH_LONG);
                    } else {
                        //处理领用列表显示
                        if (mDataDetail.getData().getItems() == null || mDataDetail.getData().getItems().isEmpty()) {
                            body_items_toolView.setVisibility(View.GONE);
                        } else {
                            initAtemAdapter(mDataDetail);
                        }

                        //处理意见列表显示
                        if (mDataDetail.getData().getResults() == null || mDataDetail.getData().getResults().isEmpty()) {
                            body_result_rootView.setVisibility(View.GONE);
                        } else {
                            initResultAdapter(mDataDetail);
                        }
                        //显示处理按钮
                        upDateButtonStatu(mDataDetail);
                    }
                }
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
        });
    }

    private void initAtemAdapter(RespMaterialDetail detail) {
        final TempListAdapter<RespMaterialDetail.DataBean.ItemsBean, HolderMaterialItems> itemAdapter = new TempListAdapter<RespMaterialDetail.DataBean.ItemsBean, HolderMaterialItems>(detail.getData().getItems(), getTempContext(), R.layout.item_apply_prd_items_layout) {

            @Override
            protected HolderMaterialItems createHolder() {
                return new HolderMaterialItems();
            }

            @Override
            protected void initHolder(int position, View v, HolderMaterialItems holder) {
                holder.statusImage = (ImageView) v.findViewById(R.id.item_apply_prd_items_arrow);
                holder.name = (TextView) v.findViewById(R.id.item_apply_prd_items_title);
                holder.time = (TextView) v.findViewById(R.id.item_apply_prd_items_time);
                holder.text0 = (TextView) v.findViewById(R.id.item_apply_prd_items_text0);
                holder.text1 = (TextView) v.findViewById(R.id.item_apply_prd_items_text1);
            }

            @Override
            public void bunldHolderValue(int position, HolderMaterialItems holder, RespMaterialDetail.DataBean.ItemsBean item) {
                holder.statusImage.setVisibility(View.VISIBLE);
                holder.name.setText(item.getModel());
                holder.time.setText(item.getTime());
                holder.text0.setText(String.format("批次：%s", item.getBatchCode()));
                holder.text1.setText(String.format("数量：%s", item.getCount()) + " / " + item.getSize());
            }
        };
        body_items_tempMyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent tempIntent = new Intent(ActApplyPrdDetail.this,ActInBoxDetail.class);
//                tempIntent.putExtra("type",mType);
                tempIntent.putExtra("id",itemAdapter.getItem(position).getId()+"");
               tempIntent.putExtra("isInbox",true);
                startActivity(tempIntent);
            }
        });
        body_items_tempMyListView.setAdapter(itemAdapter);
    }

    private void initResultAdapter(RespMaterialDetail detail) {
        TempListAdapter<RespMaterialDetail.DataBean.ResultsBean, HolderExceptionDeal> resultAdapter_approve = new TempListAdapter<RespMaterialDetail.DataBean.ResultsBean, HolderExceptionDeal>(detail.getData().getResults(), getTempContext(), R.layout.item_exception_deal_layout) {

            @Override
            protected HolderExceptionDeal createHolder() {
                return new HolderExceptionDeal();
            }

            @Override
            protected void initHolder(int position, View v, HolderExceptionDeal holder) {
                holder.statusImage = (ImageView) v.findViewById(R.id.item_exception_deal_stauts_image);
                holder.status = (TextView) v.findViewById(R.id.item_exception_deal_stauts_text);
                holder.content0 = (TextView) v.findViewById(R.id.item_exception_deal_index0_text);
                holder.content1 = (TextView) v.findViewById(R.id.item_exception_deal_index1_text);
                holder.content2 = (TextView) v.findViewById(R.id.item_exception_deal_index2_text);
            }

            @Override
            public void bunldHolderValue(int position, HolderExceptionDeal holder, RespMaterialDetail.DataBean.ResultsBean item) {
                switch (item.getStatusString()) {
                    case "已同意":
                    case "通过":
                        holder.statusImage.setImageResource(R.mipmap.icon_exception_daoju_ok);
                        break;
                    case "已拒绝":
                    case "不通过":
                        holder.statusImage.setImageResource(R.mipmap.icon_maopi_wtg);
                        break;

                }
                holder.status.setText(item.getStatusString());
                holder.content0.setText(String.format("审批人:%s", item.getApprover()));
//                        holder.content1.setText(String.format("备注：%s", item.getRemark()));
                holder.content1.setVisibility(View.GONE);
                holder.content2.setVisibility(View.GONE);
            }
        };
        body_result_tempMyListView.setAdapter(resultAdapter_approve);
    }

    private void upDateButtonStatu(RespMaterialDetail detail) {
        if (mType==1){
            //产品入库审批操作


            View rootView = LayoutInflater.from(getTempContext()).inflate(R.layout.body_commit_btn_2_layout, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(TempDensityUtil.dip2px(getTempContext(), 16), TempDensityUtil.dip2px(getTempContext(), 50), TempDensityUtil.dip2px(getTempContext(), 16), 0);
            rootView.setLayoutParams(lp);
            AppCompatButton commitBtnLeft = (AppCompatButton) rootView.findViewById(R.id.commit_btn_left);
            commitBtnLeft.setText("拒绝");
            commitBtnLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Debug.info(TAG, "产品入库审批不通过按钮点击");
                    mPreStorageRoom.applyApprove_productBox(mId, 1);

                }
            });
            AppCompatButton commitBtnRight = (AppCompatButton) rootView.findViewById(R.id.commit_btn_right);
            commitBtnRight.setText("同意");
            commitBtnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Debug.info(TAG, "产品入库审批通过按钮点击");
                    mPreStorageRoom.applyApprove_productBox(mId, 2);
//                            Debug.info(TAG, "辅料领用审批通过按钮点击");
//                            mPre.orderApprove_auxiliary(mId, 2);
                }
            });
            act_apply_prd_detail_rootView.addView(rootView);
        } else if (mType==2) {
            //产品入库确认操作
            View rootView = LayoutInflater.from(getTempContext()).inflate(R.layout.body_commit_btn_layout, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(TempDensityUtil.dip2px(getTempContext(), 16), TempDensityUtil.dip2px(getTempContext(), 50), TempDensityUtil.dip2px(getTempContext(), 16), 0);
            rootView.setLayoutParams(lp);
            AppCompatButton commitBtn = (AppCompatButton) rootView.findViewById(R.id.commit_btn);
            commitBtn.setText("确认入库");
            commitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Debug.info(TAG, "确认入库");
                    mPreStorageRoom.applyStatus_productBox(mId);


                }
            });
            act_apply_prd_detail_rootView.addView(rootView);
        }
    }

    @Override
    protected void bindValues() {
        mId = getIntent().getIntExtra("id", -1);
        if (mId == -1) {
            showSnackBar("没有获取到id", 2, Snackbar.LENGTH_LONG);
        } else {
            mPreStorageRoom.productBoxApplyDetail(mId);
        }
    }

    @Override
    protected void OnViewClicked(View v) {

    }
}
