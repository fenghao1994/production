package com.qdxy.app.lhjh.activities.storageRoom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lf.tempcore.tempAdapter.TempListAdapter;
import com.lf.tempcore.tempEnum.TempErrorCode;
import com.lf.tempcore.tempEnum.TempNetType;
import com.lf.tempcore.tempFragment.TempFragment;
import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.lf.tempcore.tempModule.tempUtils.TempDensityUtil;
import com.qdxy.app.lhjh.MyApplication;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.activities.exceptions.HolderExceptionDeal;
import com.qdxy.app.lhjh.emues.CommonType;
import com.qdxy.app.lhjh.views.TempMyListView;

import butterknife.Bind;

/**通用详情页面
 * Created by mac on 2017/2/20.
 */

public class FragTempDetail extends TempFragment {
    private final String TAG = "FragTempDetail";
    private PreStorageRoom mPre;
    private CommonType mType;
    private int mId;//详情id（列表页item id）
    @Bind(R.id.parent_view)
    CoordinatorLayout parentView;
    @Bind(R.id.body_common_detail_linearFrame)
    LinearLayout mLinearFrame;
    @Bind(R.id.body_common_detail_text0)
    TextView body_common_detail_text0;
    @Bind(R.id.body_common_detail_text1)
    TextView body_common_detail_text1;
    @Bind(R.id.body_common_detail_text2)
    TextView body_common_detail_text2;
    @Bind(R.id.body_common_detail_text3)
    TextView body_common_detail_text3;
    @Bind(R.id.body_common_detail_text4)
    TextView body_common_detail_text4;
    @Bind(R.id.body_common_detail_EditText4)
    AppCompatEditText body_common_detail_EditText4;
    @Bind(R.id.body_common_detail_EditFrame4)
    LinearLayout body_common_detail_EditFrame4;
    @Bind(R.id.body_common_detail_frame4)
    LinearLayout body_common_detail_frame4;

    @Bind(R.id.body_common_detail_text5)
    TextView body_common_detail_text5;

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.body_common_detail_layout, container, false);
    }

    @Override
    protected void setListeners(View view, @Nullable Bundle savedInstanceState) {
//        mType = (CommonType) getArguments().getSerializable("type");

        mPre = new PreStorageRoom(new ViewStorageRoom() {

            @Override
            public <RESULT> void onSucceed(RESULT result, int type) {
                switch (type) {
                    case 1:
                        RespStorageDetail data = (RespStorageDetail) result;
                        Debug.info(TAG, "返回详情数据成功");
                        if (data.getData() != null) {
                            initDetailInfo(data.getData());
                        } else {
                            superViewError(parentView, TempErrorCode.ERROR_FAILED, "详情数据为空");
                        }
                        break;
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                    case 18:
//                        Debug.info(TAG,"返回详情数据成功");
                        getActivity().setResult(200);
                        getActivity().finish();
                        break;
                    case 2:
                        RespMaterialDetail respMaterialDetail = (RespMaterialDetail) result;
                        if (respMaterialDetail.getData() != null) {
                            initMaterialDetailInfo(respMaterialDetail);
                        } else {
                            superViewError(parentView, TempErrorCode.ERROR_FAILED, "详情数据为空");
                        }

                        Debug.info(TAG, "辅料领用详情数据返回");
                        break;
                    case 3:
                        RespMaterialDetail respDeviceToolDetail = (RespMaterialDetail) result;
                        if (respDeviceToolDetail.getData() != null) {
                            initMaterialDetailInfo(respDeviceToolDetail);
                        } else {
                            superViewError(parentView, TempErrorCode.ERROR_FAILED, "详情数据为空");
                        }

                        Debug.info(TAG, "刀具领用详情数据返回");
                        break;
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
                superViewMessage(parentView, message);
            }

            @Override
            public void viewError(TempErrorCode code, String message) {
                superViewError(parentView, code, message);
            }
        });
    }

    @Override
    protected void bundleValues(View view, @Nullable Bundle savedInstanceState) {
        // TODO: 2017/2/20 通过type判断操作类型
        mType = (CommonType) getArguments().getSerializable("type");
        mId = getArguments().getInt("id");
        if (mId == -1 || mType == null) {
            showSnackBar(parentView, "数据有误", 2, Snackbar.LENGTH_LONG);
        }
        if (mType == CommonType.MAO_PI_LY || mType == CommonType.MAO_PI_STORAGE_LY) {

            Debug.info(TAG, "毛坯领用详情详情id=" + mId);
            mPre.orderGet(mId);

        } else if (mType == CommonType.MATERIAL_SQ || mType == CommonType.MATERIAL_SP || mType == CommonType.MATERIAL_FH) {
            Debug.info(TAG, "辅料领用详情=" + mId);
            mPre.orderGet_auxiliary(mId);
        } else if(mType == CommonType.DEVICE_TOOL_SQ || mType == CommonType.DEVICE_TOOL_SP || mType == CommonType.DEVICE_TOOL_FH){
            Debug.info(TAG, "刀具领用详情=" + mId);
            mPre.orderGet_deviceTool(mId);
        }else {
            Debug.info(TAG, "详情id=" + mId);
            mPre.applyGet(mId);
        }
    }

    private void initMaterialDetailInfo(RespMaterialDetail detail) {
        switch (mType) {
            case MATERIAL_SQ:
            case DEVICE_TOOL_SQ:
                Debug.info(TAG, "type类型为辅料/刀具领用申请");
                body_common_detail_text0.setText(String.format("申请人：%s", detail.getData().getApplicant()));
                body_common_detail_text1.setText(String.format("申请时间：%s", detail.getData().getApplyTime()));
                body_common_detail_text2.setText(String.format("生产线：%s", detail.getData().getLineName()));
//            body_common_detail_text3.setText(String.format("数量：%s", detail.getData().get));
//            body_common_detail_text4.setText(String.format("不合格数量：%s", detail.getUnqualifiedCount()));
                body_common_detail_text3.setVisibility(View.GONE);
                body_common_detail_text4.setVisibility(View.GONE);
                body_common_detail_text5.setVisibility(View.GONE);
                body_common_detail_EditFrame4.setVisibility(View.GONE);
                View itemsView = LayoutInflater.from(getActivity()).inflate(R.layout.body_material_items_layout, null);
                mLinearFrame.addView(itemsView);
                TempMyListView listView = (TempMyListView) itemsView.findViewById(R.id.body_items_tempMyListView);
                TempListAdapter<RespMaterialDetail.DataBean.ItemsBean, HolderMaterialItems> itemAdapter = new TempListAdapter<RespMaterialDetail.DataBean.ItemsBean, HolderMaterialItems>(detail.getData().getItems(), getActivity(), R.layout.item_material_create_layout) {

                    @Override
                    protected HolderMaterialItems createHolder() {
                        return new HolderMaterialItems();
                    }

                    @Override
                    protected void initHolder(int position, View v, HolderMaterialItems holder) {
                        holder.statusImage = (ImageView) v.findViewById(R.id.item_material_image_status);
                        holder.name = (TextView) v.findViewById(R.id.item_material_text_name);
                        holder.count = (TextView) v.findViewById(R.id.item_material_text_count);
                    }

                    @Override
                    public void bunldHolderValue(int position, HolderMaterialItems holder, RespMaterialDetail.DataBean.ItemsBean item) {
                        holder.statusImage.setVisibility(View.GONE);
                        holder.name.setText(item.getModel());
                        holder.count.setText("数量：" + item.getCount());
                    }
                };
                listView.setAdapter(itemAdapter);

                if (detail.getData().getStatusString().equals("待审批")) {
                    //如果是待抽检状态，则显示取消申请按钮
                    View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.body_commit_btn_layout, null);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 50), TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 16));
                    rootView.setLayoutParams(lp);
                    AppCompatButton commitBtn = (AppCompatButton) rootView.findViewById(R.id.commit_btn);
                    commitBtn.setText("取消申请");
                    commitBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Debug.info(TAG, "入库抽检取消申请按钮点击");
                            if (mType==CommonType.DEVICE_TOOL_SQ){
                                mPre.orderStatus_deviceTool(mId, 5);
                            }else if(mType==CommonType.MATERIAL_SQ){
                                mPre.orderStatus_auxiliary(mId, 5);
                            }

                        }
                    });
                    mLinearFrame.addView(rootView);
                } else if (detail.getData().getStatusString().equals("已发货")) {
                    //如果是待抽检状态，则显示取消申请按钮
                    View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.body_commit_btn_layout, null);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 50), TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 16));
                    rootView.setLayoutParams(lp);
                    AppCompatButton commitBtn = (AppCompatButton) rootView.findViewById(R.id.commit_btn);
                    commitBtn.setText("确认收货");
                    commitBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mType==CommonType.DEVICE_TOOL_SQ){
                                mPre.orderStatus_deviceTool(mId, 4);
                            }else if(mType==CommonType.MATERIAL_SQ){
                                Debug.info(TAG, "入库抽检确认收货按钮点击");
                                mPre.orderStatus_auxiliary(mId, 4);
                            }

                        }
                    });
                    mLinearFrame.addView(rootView);
                }
                break;
            case MATERIAL_SP:
            case DEVICE_TOOL_SP:
                Debug.info(TAG, "type类型为辅料/刀具领用审批");
                body_common_detail_text0.setText(String.format("申请人：%s", detail.getData().getApplicant()));
                body_common_detail_text1.setText(String.format("申请时间：%s", detail.getData().getApplyTime()));
                body_common_detail_text2.setText(String.format("生产线：%s", detail.getData().getLineName()));
//            body_common_detail_text3.setText(String.format("数量：%s", detail.getData().get));
//            body_common_detail_text4.setText(String.format("不合格数量：%s", detail.getUnqualifiedCount()));
                body_common_detail_text3.setVisibility(View.GONE);
                body_common_detail_text4.setVisibility(View.GONE);
                body_common_detail_text5.setVisibility(View.GONE);
                body_common_detail_EditFrame4.setVisibility(View.GONE);
                //添加辅料列表数据
                View itemsView_approve = LayoutInflater.from(getActivity()).inflate(R.layout.body_material_items_layout, null);

                TempMyListView listView_approve = (TempMyListView) itemsView_approve.findViewById(R.id.body_items_tempMyListView);
                TempListAdapter<RespMaterialDetail.DataBean.ItemsBean, HolderMaterialItems> itemAdapter_approve = new TempListAdapter<RespMaterialDetail.DataBean.ItemsBean, HolderMaterialItems>(detail.getData().getItems(), getActivity(), R.layout.item_material_create_layout) {

                    @Override
                    protected HolderMaterialItems createHolder() {
                        return new HolderMaterialItems();
                    }

                    @Override
                    protected void initHolder(int position, View v, HolderMaterialItems holder) {
                        holder.statusImage = (ImageView) v.findViewById(R.id.item_material_image_status);
                        holder.name = (TextView) v.findViewById(R.id.item_material_text_name);
                        holder.count = (TextView) v.findViewById(R.id.item_material_text_count);
                    }

                    @Override
                    public void bunldHolderValue(int position, HolderMaterialItems holder, RespMaterialDetail.DataBean.ItemsBean item) {
                        holder.statusImage.setVisibility(View.GONE);
                        holder.name.setText(item.getModel());
                        holder.count.setText("数量：" + item.getCount());
                    }
                };
                listView_approve.setAdapter(itemAdapter_approve);
                mLinearFrame.addView(itemsView_approve);
                //添加审批意见数据
                View resultView_approve = LayoutInflater.from(getActivity()).inflate(R.layout.body_material_results_layout, null);

                TempMyListView resultListView_approve = (TempMyListView) resultView_approve.findViewById(R.id.body_result_tempMyListView);
                TempListAdapter<RespMaterialDetail.DataBean.ResultsBean, HolderExceptionDeal> resultAdapter_approve = new TempListAdapter<RespMaterialDetail.DataBean.ResultsBean, HolderExceptionDeal>(detail.getData().getResults(), getActivity(), R.layout.item_exception_deal_layout) {

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
                resultListView_approve.setAdapter(resultAdapter_approve);
                mLinearFrame.addView(resultView_approve);
                if (detail.getData().getStatusString().equals("待审批")) {
                    //如果是待抽检状态，则显示取消申请按钮
                    View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.body_commit_btn_2_layout, null);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 50), TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 16));
                    rootView.setLayoutParams(lp);
                    AppCompatButton commitBtnLeft = (AppCompatButton) rootView.findViewById(R.id.commit_btn_left);
                    commitBtnLeft.setText("拒绝");
                    commitBtnLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(mType==CommonType.DEVICE_TOOL_SP){
                                Debug.info(TAG, "刀具领用审批不通过按钮点击");
                                mPre.orderApprove_deviceTool(mId, 1);
                            }else if(mType==CommonType.MATERIAL_SP){

                                Debug.info(TAG, "辅料领用审批不通过按钮点击");
                                mPre.orderApprove_auxiliary(mId, 1);
                            }

                        }
                    });
                    AppCompatButton commitBtnRight = (AppCompatButton) rootView.findViewById(R.id.commit_btn_right);
                    commitBtnRight.setText("同意");
                    commitBtnRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(mType==CommonType.DEVICE_TOOL_SP){
                                Debug.info(TAG, "刀具领用审批通过按钮点击");
                                mPre.orderApprove_deviceTool(mId, 2);
                            }else if(mType==CommonType.MATERIAL_SP){

                                Debug.info(TAG, "辅料领用审批通过按钮点击");
                                mPre.orderApprove_auxiliary(mId, 2);
                            }
//                            Debug.info(TAG, "辅料领用审批通过按钮点击");
//                            mPre.orderApprove_auxiliary(mId, 2);
                        }
                    });
                    mLinearFrame.addView(rootView);
                }
                break;
            case MATERIAL_FH:
            case DEVICE_TOOL_FH:
                Debug.info(TAG, "type类型为辅料领用发货");
                body_common_detail_text0.setText(String.format("申请人：%s", detail.getData().getApplicant()));
                body_common_detail_text1.setText(String.format("申请时间：%s", detail.getData().getApplyTime()));
                body_common_detail_text2.setText(String.format("生产线：%s", detail.getData().getLineName()));
//            body_common_detail_text3.setText(String.format("数量：%s", detail.getData().get));
//            body_common_detail_text4.setText(String.format("不合格数量：%s", detail.getUnqualifiedCount()));
                body_common_detail_text3.setVisibility(View.GONE);
                body_common_detail_text4.setVisibility(View.GONE);
                body_common_detail_text5.setVisibility(View.GONE);
                body_common_detail_EditFrame4.setVisibility(View.GONE);
                View itemsView_send = LayoutInflater.from(getActivity()).inflate(R.layout.body_material_items_layout, null);
                mLinearFrame.addView(itemsView_send);
                TempMyListView listView_send = (TempMyListView) itemsView_send.findViewById(R.id.body_items_tempMyListView);
                TempListAdapter<RespMaterialDetail.DataBean.ItemsBean, HolderMaterialItems> itemAdapter_send = new TempListAdapter<RespMaterialDetail.DataBean.ItemsBean, HolderMaterialItems>(detail.getData().getItems(), getActivity(), R.layout.item_material_create_layout) {

                    @Override
                    protected HolderMaterialItems createHolder() {
                        return new HolderMaterialItems();
                    }

                    @Override
                    protected void initHolder(int position, View v, HolderMaterialItems holder) {
                        holder.statusImage = (ImageView) v.findViewById(R.id.item_material_image_status);
                        holder.name = (TextView) v.findViewById(R.id.item_material_text_name);
                        holder.count = (TextView) v.findViewById(R.id.item_material_text_count);
                    }

                    @Override
                    public void bunldHolderValue(int position, HolderMaterialItems holder, RespMaterialDetail.DataBean.ItemsBean item) {
                        holder.statusImage.setVisibility(View.GONE);
                        holder.name.setText(item.getModel());
                        holder.count.setText("数量：" + item.getCount());
                    }
                };
                listView_send.setAdapter(itemAdapter_send);

                if (detail.getData().getStatusString().equals("待发货")) {
                    //如果是待抽检状态，则显示取消申请按钮
                    View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.body_commit_btn_layout, null);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 50), TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 16));
                    rootView.setLayoutParams(lp);
                    AppCompatButton commitBtn = (AppCompatButton) rootView.findViewById(R.id.commit_btn);
                    commitBtn.setText("发货");
                    commitBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                          /*  if(mType==CommonType.DEVICE_TOOL_FH){
                                Debug.info(TAG, "刀具领用审批不通过按钮点击");
                                mPre.orderStatus_deviceTool(mId, 3);
                            }else if(mType==CommonType.MATERIAL_SP){

                                Debug.info(TAG, "辅料领用审批不通过按钮点击");
                                mPre.orderStatus_auxiliary(mId, 3);
                            }*/
                            if (mType==CommonType.MATERIAL_FH){
                                mPre.orderStatus_auxiliary(mId,3);
                            }else {
                                mPre.orderStatus_deviceTool(mId,3);
                            }



//                            Debug.info(TAG, "入库抽检取消申请按钮点击");
//                            mPre.orderStatus_auxiliary(mId, 3);
                        }
                    });
                    mLinearFrame.addView(rootView);
                }
                break;
        }
    }

    private void initDetailInfo(RespStorageDetail.DataBean detail) {
        switch (mType) {
            case MAO_PI_STORAGE_SQ:
                Debug.info(TAG, "type类型为毛坯入库申请");
                body_common_detail_text0.setText(String.format("申请时间：%s", detail.getApplyTime()));
                body_common_detail_text1.setText(String.format("型号：%s", detail.getModel()));
                body_common_detail_text2.setText(String.format("厂家：%s", detail.getManufactor()));
                body_common_detail_text3.setText(String.format("数量：%s", detail.getCount()));
                body_common_detail_text4.setText(String.format("不合格数量：%s", detail.getUnqualifiedCount()));
                body_common_detail_text5.setVisibility(View.GONE);
                body_common_detail_EditFrame4.setVisibility(View.GONE);
                if (detail.getStatusString().equals("待抽检")) {
                    //如果是待抽检状态，则显示取消申请按钮
                    View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.body_commit_btn_layout, null);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 50), TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 16));
                    rootView.setLayoutParams(lp);
                    AppCompatButton commitBtn = (AppCompatButton) rootView.findViewById(R.id.commit_btn);
                    commitBtn.setText("取消申请");
                    commitBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Debug.info(TAG, "入库抽检取消申请按钮点击");
                            mPre.applyCancel(mId);
                        }
                    });
                    mLinearFrame.addView(rootView);
                }

                break;
            case MAO_PI_STORAGE_CJ:
                Debug.info(TAG, "type类型为毛坯入库抽检");
                body_common_detail_text0.setText(String.format("申请时间：%s", detail.getApplyTime()));
                body_common_detail_text1.setText(String.format("型号：%s", detail.getModel()));
                body_common_detail_text2.setText(String.format("厂家：%s", detail.getManufactor()));
                body_common_detail_text3.setText(String.format("数量：%s", detail.getCount()));

                if (detail.getStatusString().equals("待抽检")) {
                    body_common_detail_EditText4.setHint("请输入不合格数量");
                    body_common_detail_EditFrame4.setVisibility(View.VISIBLE);
                    body_common_detail_frame4.setVisibility(View.GONE);
                    body_common_detail_text5.setVisibility(View.GONE);
                    //如果是待抽检状态，则显示取消申请按钮
                    View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.body_commit_btn_layout, null);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 50), TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 16));
                    rootView.setLayoutParams(lp);
                    AppCompatButton commitBtn = (AppCompatButton) rootView.findViewById(R.id.commit_btn);
                    commitBtn.setText("提交");
                    commitBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Debug.info(TAG, "入库抽检按钮点击");
                            if (body_common_detail_EditText4.getText().toString().trim().equals("")) {
                                showSnackBar(parentView, "请输入不合格产品数量后，再提交", 2, Snackbar.LENGTH_LONG);
                            } else {

                                mPre.applyRandomCheck(mId, Integer.valueOf(body_common_detail_EditText4.getText().toString().trim()));
                            }
                        }
                    });
                    mLinearFrame.addView(rootView);
                } else {
                    body_common_detail_text4.setText(String.format("不合格数量：%s", detail.getUnqualifiedCount()));
                    body_common_detail_text5.setVisibility(View.GONE);
                    body_common_detail_EditFrame4.setVisibility(View.GONE);
                }
                break;
            case MAO_PI_STORAGE_SP:
                Debug.info(TAG, "type类型为毛坯入库审批");
                body_common_detail_text0.setText(String.format("申请时间：%s", detail.getApplyTime()));
                body_common_detail_text1.setText(String.format("型号：%s", detail.getModel()));
                body_common_detail_text2.setText(String.format("厂家：%s", detail.getManufactor()));
                body_common_detail_text3.setText(String.format("数量：%s", detail.getCount()));
                body_common_detail_text4.setText(String.format("不合格数量：%s", detail.getUnqualifiedCount()));
                body_common_detail_text5.setVisibility(View.GONE);
                body_common_detail_EditFrame4.setVisibility(View.GONE);
                if (detail.getStatusString().equals("待审批")) {
                    //如果是待抽检状态，则显示取消申请按钮
                    View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.body_commit_btn_2_layout, null);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 50), TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 16));
                    rootView.setLayoutParams(lp);
                    AppCompatButton commitBtnLeft = (AppCompatButton) rootView.findViewById(R.id.commit_btn_left);
                    commitBtnLeft.setText("不通过");
                    commitBtnLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Debug.info(TAG, "入库审批不通过按钮点击");
                            mPre.applyApprove(mId, false);

                        }
                    });
                    AppCompatButton commitBtnRight = (AppCompatButton) rootView.findViewById(R.id.commit_btn_right);
                    commitBtnRight.setText("通过");
                    commitBtnRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Debug.info(TAG, "入库审批通过按钮点击");
                            mPre.applyApprove(mId, true);
                        }
                    });
                    mLinearFrame.addView(rootView);
                }

                break;
            case MAO_PI_LY:
                body_common_detail_text0.setText(String.format("申请人：%s", detail.getApplicant()));
                body_common_detail_text1.setText(String.format("申请时间：%s", detail.getApplyTime()));
                body_common_detail_text2.setText(String.format("生产线：%s", TextUtils.isEmpty(detail.getLineName()) ? "无生产线" : detail.getLineName()));
                body_common_detail_text3.setText(String.format("厂家：%s", detail.getManufactor()));
                body_common_detail_text4.setText(String.format("数量：%s", detail.getCount()));
                body_common_detail_text5.setText(String.format("备注：%s", TextUtils.isEmpty(detail.getRemark()) ? "无备注内容" : detail.getRemark()));
//                body_common_detail_text5.setVisibility(View.GONE);

                body_common_detail_EditFrame4.setVisibility(View.GONE);
                if (detail.getStatusString().equals("待发货")) {
                    //如果是待抽检状态，则显示取消申请按钮
                    View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.body_commit_btn_2_layout, null);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 50), TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 16));
                    rootView.setLayoutParams(lp);
                    AppCompatButton commitBtnLeft = (AppCompatButton) rootView.findViewById(R.id.commit_btn_left);
                    commitBtnLeft.setText("取消申请");
                    commitBtnLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Debug.info(TAG, "毛坯领用取消");
                            mPre.orderStatus(mId, 3);

                        }
                    });
                    AppCompatButton commitBtnRight = (AppCompatButton) rootView.findViewById(R.id.commit_btn_right);
                    commitBtnRight.setVisibility(View.GONE);
                   /* commitBtnRight.setText("确认收货");
                    commitBtnRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Debug.info(TAG, "毛坯领用完成");
                            mPre.orderStatus(mId, 2);
                        }
                    });*/
                    mLinearFrame.addView(rootView);
                }else if (detail.getStatusString().equals("已发货")){

                    View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.body_commit_btn_2_layout, null);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 50), TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 16));
                    rootView.setLayoutParams(lp);
                    AppCompatButton commitBtnLeft = (AppCompatButton) rootView.findViewById(R.id.commit_btn_left);
                    commitBtnLeft.setVisibility(View.GONE);
                    AppCompatButton commitBtnRight = (AppCompatButton) rootView.findViewById(R.id.commit_btn_right);
                    commitBtnRight.setText("确认收货");
                    commitBtnRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Debug.info(TAG, "毛坯领用完成");
                            mPre.orderStatus(mId, 2);
                        }
                    });
                    mLinearFrame.addView(rootView);

            }


                break;
            case MAO_PI_STORAGE_LY:
                body_common_detail_text0.setText(String.format("申请人：%s", detail.getApplicant()));
                body_common_detail_text1.setText(String.format("申请时间：%s", detail.getApplyTime()));
                body_common_detail_text2.setText(String.format("生产线：%s", TextUtils.isEmpty(detail.getLineName()) ? "无生产线" : detail.getLineName()));
                body_common_detail_text3.setText(String.format("厂家：%s", detail.getManufactor()));
                body_common_detail_text4.setText(String.format("数量：%s", detail.getCount()));
                body_common_detail_text5.setText(String.format("备注：%s", TextUtils.isEmpty(detail.getRemark()) ? "无备注内容" : detail.getRemark()));
//                body_common_detail_text5.setVisibility(View.GONE);
                body_common_detail_EditFrame4.setVisibility(View.GONE);
                if (detail.getStatusString().equals("待发货")){
                    View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.body_commit_btn_layout, null);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 50), TempDensityUtil.dip2px(getActivity(), 16), TempDensityUtil.dip2px(getActivity(), 16));
                    rootView.setLayoutParams(lp);
                    AppCompatButton commitBtn = (AppCompatButton) rootView.findViewById(R.id.commit_btn);
                    commitBtn.setText("发货");
                    commitBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Debug.info(TAG, "毛坯领用发货");
                            mPre.orderSend(mId, Integer.valueOf(body_common_detail_text4.getText().toString().trim().split("：")[1]));

                        }
                    });
                    mLinearFrame.addView(rootView);
                }

                break;
        }

    }

    @Override
    protected void OnViewClicked(View v) {

    }
}
