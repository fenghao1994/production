package com.qdxy.app.lhjh.adapters;

import android.content.Context;
import android.text.TextUtils;

import com.lf.tempcore.tempViews.tempRecyclerView.TempRVCommonAdapter;
import com.lf.tempcore.tempViews.tempRecyclerView.TempRVHolder;
import com.qdxy.app.lhjh.R;
import com.qdxy.app.lhjh.activities.storageRoom.RespStorageList;
import com.qdxy.app.lhjh.emues.CommonType;

/**
 * Created by mac on 2016/12/28.
 */

public class AdapterCommmonList extends TempRVCommonAdapter<RespStorageList.DataBean.DatasBean> {
    private CommonType mtype;

    public AdapterCommmonList(Context context, CommonType type) {
        super(context, R.layout.item_common_list_layout);
        mtype = type;
    }


    @Override
    public void bindItemValues(TempRVHolder holder, RespStorageList.DataBean.DatasBean datasBean) {
        if (datasBean.getStatusString().equals("已入库")) {
            holder.setImageResource(R.id.item_common_list_image, R.mipmap.icon_maopi_yrk);
        } else if (datasBean.getStatusString().equals("待抽检")) {
            holder.setImageResource(R.id.item_common_list_image, R.mipmap.icon_maopi_dcj);
        } else if (datasBean.getStatusString().equals("已退货")) {
            holder.setImageResource(R.id.item_common_list_image, R.mipmap.icon_maopi_yth);
        } else if (datasBean.getStatusString().equals("待审批")) {
            holder.setImageResource(R.id.item_common_list_image, R.mipmap.icon_maopi_dsp);
        } else if (datasBean.getStatusString().equals("已抽检")) {
            holder.setImageResource(R.id.item_common_list_image, R.mipmap.icon_maopi_ycj);
        } else if (datasBean.getStatusString().equals("已通过")) {
            holder.setImageResource(R.id.item_common_list_image, R.mipmap.icon_maopi_ytg);
        } else if (datasBean.getStatusString().equals("未通过")) {
            holder.setImageResource(R.id.item_common_list_image, R.mipmap.icon_maopi_wtg);
        } else if (datasBean.getStatusString().equals("已取消")) {
            holder.setImageResource(R.id.item_common_list_image, R.mipmap.icon_maopi_yqx);
        } else if (datasBean.getStatusString().equals("已发货")) {
            holder.setImageResource(R.id.item_common_list_image, R.mipmap.icon_maopi_yfh);
        } else if (datasBean.getStatusString().equals("已完成")) {
            holder.setImageResource(R.id.item_common_list_image, R.mipmap.icon_maopi_ytg);
        } else if (datasBean.getStatusString().equals("已同意")) {
            holder.setImageResource(R.id.item_common_list_image, R.mipmap.icon_maopi_ytg);
        } else if (datasBean.getStatusString().equals("已拒绝")) {
            holder.setImageResource(R.id.item_common_list_image, R.mipmap.icon_maopi_wtg);
        } else if (datasBean.getStatusString().equals("待发货")) {
            holder.setImageResource(R.id.item_common_list_image, R.mipmap.icon_maopi_dfh);
        } else if (datasBean.getStatusString().equals("未清点")||datasBean.getStatusString().equals("装箱完成")) {
            holder.setImageResource(R.id.item_common_list_image, R.mipmap.icon_maopi_wqd);
        } else if (datasBean.getStatusString().equals("已清点")) {
            holder.setImageResource(R.id.item_common_list_image, R.mipmap.icon_maopi_yqd);
        }

        switch (mtype) {
            case MAO_PI_STORAGE_CJ:
            case MAO_PI_STORAGE_SP:
            case MAO_PI_STORAGE_SQ:

                holder.setText(R.id.item_common_list_title, datasBean.getStatusString());
                holder.setText(R.id.item_common_list_time, datasBean.getTime());
                holder.setText(R.id.item_common_list_text0, "型号：" + datasBean.getModel());
                holder.setText(R.id.item_common_list_text1, "厂家：" + datasBean.getManufactor());
                holder.setText(R.id.item_common_list_text2, "数量：" + datasBean.getCount());
                holder.setVisible(R.id.item_common_list_text3, false);
                break;
            case MAO_PI_LY:
            case MAO_PI_STORAGE_LY:

                holder.setText(R.id.item_common_list_title, datasBean.getStatusString());
                holder.setText(R.id.item_common_list_time, datasBean.getTime());
                holder.setText(R.id.item_common_list_text0, "申请人：" + datasBean.getApplicant());
                holder.setText(R.id.item_common_list_text1, "厂家：" + datasBean.getManufactor());
                holder.setText(R.id.item_common_list_text2, "数量：" + datasBean.getCount());
//                holder.setVisible(R.id.item_common_list_text2, false);
                holder.setVisible(R.id.item_common_list_text3, false);
                break;
            case MATERIAL_SQ://辅料申请
            case MATERIAL_SP://审批
            case MATERIAL_FH://发货
            case DEVICE_TOOL_SQ://刀具申请
            case DEVICE_TOOL_SP://审批
            case DEVICE_TOOL_FH://发货
                holder.setText(R.id.item_common_list_title, datasBean.getStatusString());
                holder.setText(R.id.item_common_list_time, datasBean.getTime());
                holder.setText(R.id.item_common_list_text0, "申请人：" + datasBean.getApplicant());
                holder.setText(R.id.item_common_list_text1, "生产线：" + datasBean.getLineName());
                if (!datasBean.getStatusString().equals("待审批")) {

                    holder.setText(R.id.item_common_list_text2, "审批人：" + (TextUtils.isEmpty(datasBean.getApprover()) ? "暂无人审批" : datasBean.getApprover()));
                } else {

                    holder.setVisible(R.id.item_common_list_text2, false);
                }
//                holder.setVisible(R.id.item_common_list_text2, false);
                holder.setVisible(R.id.item_common_list_text3, false);
                break;
            case PRD_QD://产品清点
                holder.setText(R.id.item_common_list_title, datasBean.getStatusString());
                holder.setText(R.id.item_common_list_time, datasBean.getCreationTime());
                holder.setText(R.id.item_common_list_text0, "装箱编号：" + datasBean.getName());
                holder.setText(R.id.item_common_list_text1, "批次：" + datasBean.getBatchCode());

                holder.setText(R.id.item_common_list_text2, "数量：" + datasBean.getCount()+" / "+datasBean.getSize());
//                holder.setVisible(R.id.item_common_list_text2, false);
                holder.setVisible(R.id.item_common_list_text3, false);
                break;

        }
    }

}
