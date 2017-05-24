package com.lf.tempcore.tempModule.tempUtils;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.lf.tempcore.R;

/**
 * Created by KY on 2016/11/18.
 */

public class TempColoredSnackbar {
    private static final int red = 0xfff44336;
    private static final int green = 0xff4caf50;
    private static final int blue = 0xff2195f3;
    private static final int orange = 0xffffc107;

    private static View getSnackBarLayout(Snackbar snackbar) {
        if (snackbar != null) {
            return snackbar.getView();
        }
        return null;
    }

    private static Snackbar colorSnackBar(Snackbar snackbar, int colorId) {
        View snackBarView = getSnackBarLayout(snackbar);

        if (snackBarView != null) {
            snackBarView.setBackgroundColor(colorId);
            TextView tv = (TextView) snackBarView.findViewById(R.id.snackbar_text);
            tv.setTextColor(Color.WHITE);

        }
       /* snackbar.setActionTextColor(Color.BLACK);
        snackbar.setAction("好的", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        return snackbar;
    }

    public static Snackbar info(Snackbar snackbar) {
        return colorSnackBar(snackbar, blue);
    }

    public static Snackbar warning(Snackbar snackbar) {
        return colorSnackBar(snackbar, orange);
    }

    public static Snackbar alert(Snackbar snackbar) {
        return colorSnackBar(snackbar, red);
    }

    public static Snackbar confirm(Snackbar snackbar) {
        return colorSnackBar(snackbar, green);
    }
}
