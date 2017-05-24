package com.qdxy.app.lhjh.activities.exceptions;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lf.tempcore.tempAdapter.TempListAdapter;
import com.qdxy.app.lhjh.R;

import java.util.List;

/**
 * 处理意见adapter
 * Created by KY on 2016/12/13.
 */

public class AdapterExceptionDeal extends TempListAdapter<RespProblemDetail.DataBean.HandleResultListBean, HolderExceptionDeal> {
    public AdapterExceptionDeal(List<RespProblemDetail.DataBean.HandleResultListBean> data, Context context, int layoutId) {
        super(data, context, layoutId);
    }

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
    public void bunldHolderValue(int position, HolderExceptionDeal holder, RespProblemDetail.DataBean.HandleResultListBean item) {
        switch (item.getHandleType()) {
            case "正常":
                holder.statusImage.setImageResource(R.mipmap.icon_exception_daoju_ok);
                break;
            case "更换":
                holder.statusImage.setImageResource(R.mipmap.icon_exception_daoju_change);
                break;
            case "维修完成":
                holder.statusImage.setImageResource(R.mipmap.icon_weixiu);
                break;
            case "无法维修":
                holder.statusImage.setImageResource(R.mipmap.icon_exception);
                break;
            case "无异常":
                holder.statusImage.setImageResource(R.mipmap.icon_exception_daoju_ok);
                break;
            case "工废":
                holder.statusImage.setImageResource(R.mipmap.icon_gongfei);
                break;
            case "料废":
                holder.statusImage.setImageResource(R.mipmap.icon_liaofei);
                break;
            case "工二级":
                holder.statusImage.setImageResource(R.mipmap.icon_gongerji);
                break;
            case "料二级":
                holder.statusImage.setImageResource(R.mipmap.icon_liaoerji);
                break;
            case "返工":
                holder.statusImage.setImageResource(R.mipmap.icon_fangong);
                break;

        }
        holder.status.setText(item.getHandleType());
        holder.content0.setText(String.format("处理人:%s", item.getHandlerName()));
        holder.content1.setText(String.format("备注：%s", item.getRemark()));
        holder.content2.setText(String.format("责任人：%s", TextUtils.isEmpty(item.getResponsiblePersonName())?"无":item.getResponsiblePersonName()));
    }

}
