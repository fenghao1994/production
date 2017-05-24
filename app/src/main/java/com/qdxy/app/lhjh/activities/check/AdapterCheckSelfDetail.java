package com.qdxy.app.lhjh.activities.check;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lf.tempcore.tempAdapter.TempListAdapter;
import com.qdxy.app.lhjh.R;

import java.util.List;

/**自检记录适配器
 * Created by KY on 2016/11/30.
 */

public class AdapterCheckSelfDetail extends TempListAdapter<RespCheckPrdDetail.DataBean.SelfCheckListBean, HolderCheckDetail> {

    public AdapterCheckSelfDetail(List<RespCheckPrdDetail.DataBean.SelfCheckListBean> data, Context context, int layoutId) {

        super(data, context, layoutId);
    }

    @Override
    protected HolderCheckDetail createHolder() {
        return new HolderCheckDetail();
    }

    @Override
    protected void initHolder(int position, View v, HolderCheckDetail holder) {
        holder.statuImage = (ImageView) v.findViewById(R.id.item_check_prd_detail_status_image);
        holder.statusText = (TextView) v.findViewById(R.id.item_check_prd_detail_status_text);
        holder.index0 = (TextView) v.findViewById(R.id.item_check_prd_detail_index0_text);
        holder.index1 = (TextView) v.findViewById(R.id.item_check_prd_detail_index1_text);
        holder.index2 = (TextView) v.findViewById(R.id.item_check_prd_detail_index2_text);
    }

    @Override
    public void bunldHolderValue(int position, HolderCheckDetail holder, RespCheckPrdDetail.DataBean.SelfCheckListBean item) {
            switch (item.getResult()){
                case 0://正常
                holder.statuImage.setImageResource(R.mipmap.icon_ok);

                    break;
                case 1://异常
                    holder.statuImage.setImageResource(R.mipmap.icon_exception);
                    break;
            }
        holder.statusText.setText(item.getResultString());
        holder.index0.setText(String.format("自检人：%s", TextUtils.isEmpty(item.getCheckerName())?"无":item.getCheckerName()));
        holder.index1.setText(String.format("工序：%s", TextUtils.isEmpty(item.getProcedureName())?"无":item.getProcedureName()));
        holder.index2.setText(String.format("自检时间：%s", TextUtils.isEmpty(item.getCreationTime())?"无":item.getCreationTime()));
    }
}
