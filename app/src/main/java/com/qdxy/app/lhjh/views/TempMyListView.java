package com.qdxy.app.lhjh.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by longf on 2016/7/28.
 */
public class TempMyListView extends ListView{
    public TempMyListView(Context context) {
        super(context);
    }

    public TempMyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TempMyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
