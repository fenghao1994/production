package com.qdxy.app.lhjh.adapters.holders;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by KY on 2016/11/25.
 */

public class HolderJiaGong {
    public TextView gongXu;
    public View devider;
    public TextView checkButton;
    public TextView commitButton;
    public ImageView operationImage;
    public TextView num;
    public TextView pici;
    public TextView checkCommit;
    public TextView repairCommit;
    public LinearLayout repairFrame;
    private CardView cardView;

    public CardView getCardView() {
        return cardView;
    }

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }
}
