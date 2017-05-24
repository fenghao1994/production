package com.qdxy.app.lhjh.adapters;

import android.content.Context;
import android.view.View;

import com.lf.tempcore.tempAdapter.TempListAdapter;
import com.qdxy.app.lhjh.adapters.holders.ActEquJudgementHolder;

import java.util.List;

/**
 * Created by KY on 2016/11/10.
 */

public class ActEquJudgementAdapter extends TempListAdapter<String,ActEquJudgementHolder> {
    public ActEquJudgementAdapter(List<String> data, Context context, int layoutId) {
        super(data, context, layoutId);
    }

    @Override
    protected ActEquJudgementHolder createHolder() {
        return new ActEquJudgementHolder();
    }

    @Override
    protected void initHolder(int position, View v, ActEquJudgementHolder holder) {

    }

    @Override
    public void bunldHolderValue(int position, ActEquJudgementHolder holder, String item) {

    }
}
