package com.qdxy.app.lhjh.adapters;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lf.tempcore.tempAdapter.TempExpandableListAdapter;
import com.qdxy.app.lhjh.R;

import java.util.List;

/**
 * 产品加工adapter
 * Created by KY on 2016/10/9.
 */

public class FragJiaGongExpandableAdapter extends TempExpandableListAdapter<String> {
    private BottomSheetDialog mButtonSheet;
    public FragJiaGongExpandableAdapter(Context context, List<String> data) {

        super(context, data);
    }

    @Override
    public int getGroupCount() {
        return 5;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 2;
    }

    @Override
    public String getGroup(int groupPosition) {
        return null;
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.item_jia_gong_group_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.devider =convertView.findViewById(R.id.item_jia_gong_divider);
            viewHolder.operationImage = (ImageView) convertView.findViewById(R.id.item_jia_gong_operation);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.operationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOperationButtomSheets();
            }
        });
        if (getGroupCount()>0&&groupPosition==0){
            viewHolder.devider.setVisibility(View.GONE);
        }else{
            viewHolder.devider.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.item_jia_gong_child_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.checkButton = (TextView) convertView.findViewById(R.id.item_jia_gong_check_btn);
            viewHolder.commitButton = (TextView) convertView.findViewById(R.id.item_jia_gong_commit_btn);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getTempContext(), "自检点击groupPosition="+groupPosition+"||childPosition="+childPosition, Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.commitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeKnife();
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    private class ViewHolder{
        public View devider;
        public TextView checkButton;
        public TextView commitButton;
        public ImageView operationImage;
    }
    /**
     * 更换刀具对话框
     */
    private void showChangeKnife(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getTempContext());
        builder.setView(R.layout.dialog_change_knife_layout).setNegativeButton("取消",null).setPositiveButton("更换刀具",null).show();

    }
    /**
     * 显示底部导航栏
     */
    private void showOperationButtomSheets(){
        if (mButtonSheet==null){
            mButtonSheet = new BottomSheetDialog(getTempContext());
            View contentView = LayoutInflater.from(getTempContext()).inflate(R.layout.buttom_sheets_jia_gong_content_layout,null);
            View cancel = contentView.findViewById(R.id.buttom_sheets_jia_gong_cancel);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mButtonSheet.dismiss();
                }
            });
            mButtonSheet.setContentView(contentView);

        }
        mButtonSheet.show();

    }
}
