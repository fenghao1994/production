package com.qdxy.app.lhjh.activities.count;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qdxy.app.lhjh.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fenghao on 2017/5/27.
 */

public class ExpandableListViewaAdapter extends BaseExpandableListAdapter {
    Context context;
    private ArrayList<List<String>> chileList;
    private ArrayList<String> titleList;
    private LayoutInflater inflate;

    public ExpandableListViewaAdapter(Context context, ArrayList<String> titleList, ArrayList<List<String>> chileList) {
        this.context = context;
        this.titleList = titleList;
        this.chileList = chileList;
        inflate = ((ProductionCountMoreInfoActivity) context).getLayoutInflater();
    }

    /*-----------------Child */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return chileList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ItemHolder itemHolder = null;
        if (convertView == null) {
            convertView = inflate.from(context).inflate(R.layout.item_production_count_more_info_child, null);
            itemHolder = new ItemHolder();
            itemHolder.name = (TextView) convertView.findViewById(R.id.chile_item);
            itemHolder.layout = (LinearLayout) convertView.findViewById(R.id.item_child_layout);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }
        ExpandableListView.LayoutParams params = new ExpandableListView.LayoutParams(ExpandableListView.LayoutParams.MATCH_PARENT, 100);//设置宽度和高度
        itemHolder.layout.setLayoutParams(params);

        itemHolder.name.setText(chileList.get(groupPosition).get(childPosition));
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return chileList.get(groupPosition).size();
    }

    /* ----------------------------Group */
    @Override
    public Object getGroup(int groupPosition) {
        return titleList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return titleList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        TitleHolder titleHolder = null;
        if (convertView == null) {
            convertView = inflate.from(context).inflate(R.layout.item_production_count, null);
            titleHolder = new TitleHolder();
            titleHolder.name = (TextView) convertView.findViewById(R.id.production_name);
            titleHolder.number = (TextView) convertView.findViewById(R.id.production_count);
            titleHolder.img = (ImageView) convertView.findViewById(R.id.title_img);
            titleHolder.layout = (RelativeLayout) convertView.findViewById(R.id.item_production_count_layout);

            ExpandableListView.LayoutParams params = new ExpandableListView.LayoutParams(ExpandableListView.LayoutParams.MATCH_PARENT, 200);//设置宽度和高度
            titleHolder.layout.setLayoutParams(params);
            titleHolder.name.setText(titleList.get(groupPosition));
            titleHolder.number.setText(chileList.get(groupPosition).size() + "");
//            titleHolder.img.setVisibility(View.GONE);

            convertView.setTag(titleHolder);
            return convertView;
        } else {
            titleHolder = (TitleHolder) convertView.getTag();
        }
        ExpandableListView.LayoutParams params = new ExpandableListView.LayoutParams(ExpandableListView.LayoutParams.MATCH_PARENT, 200);//设置宽度和高度
        titleHolder.layout.setLayoutParams(params);
        titleHolder.name.setText(titleList.get(groupPosition));
        titleHolder.number.setText(chileList.get(groupPosition).size() + "");
        if (isExpanded){
            titleHolder.img.setRotation(90);
        }else {
            titleHolder.img.setRotation(0);
        }
//        titleHolder.img.setVisibility(View.GONE);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class ItemHolder {
        public LinearLayout layout;
        public TextView name;
    }

    static class TitleHolder {
        public RelativeLayout layout;
        public TextView name;
        public TextView number;
        public ImageView img;
    }
}

