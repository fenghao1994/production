package com.qdxy.app.lhjh.views;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.lf.tempcore.tempModule.tempDebuger.Debug;
import com.qdxy.app.lhjh.R;

/**
 * Created by KY on 2016/10/11.
 */

public class TouLiaoNestedLayout extends LinearLayout implements NestedScrollingParent {
    private final String TAG = "TouLiaoNestedLayout";
    private View mTop;
    private View mBottom;

    private int mTopViewHeight;
    private OverScroller mScroller;

    public TouLiaoNestedLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        if (isInEditMode()) {
            return;
        }

        mScroller = new OverScroller(context);
    }


    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {

    }

    @Override
    public void onStopNestedScroll(View target) {

    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (isInEditMode()) {
            return;
        }
        boolean hiddenTop = dy > 0 && getScrollY() < mTopViewHeight;
        boolean showTop = dy < 0 && getScrollY() >= 0 && !ViewCompat.canScrollVertically(target, -1);

        if (hiddenTop || showTop) {
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        Log.e(TAG, "onNestedPreFling");
        //down - //up+
        if (getScrollY() >= mTopViewHeight) return false;
        fling((int) velocityY);
        return true;
    }

    public void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight);
        invalidate();
    }

    @Override
    public int getNestedScrollAxes() {
        return 0;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (isInEditMode()) {
            return;
        }
        mTop = findViewById(R.id.body_id_touliao_nestedlayout_topview);
        mBottom = findViewById(R.id.body_id_touliao_nestedlayout_recycler_view);
//        if (!(view instanceof RecyclerView)) {
//            throw new RuntimeException(
//                    "id_touliao_nestedlayout_viewpager show used by RecyclerView !");
//        }
//        mBottom = ( view;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isInEditMode()) {
            return;
        }
        getChildAt(0).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        getChildAt(1).measure(widthMeasureSpec, heightMeasureSpec);

//        Debug.info(TAG, "onMeasure MeasuredHeight"  + getMeasuredHeight());

        mTopViewHeight = mTop.getMeasuredHeight();
//        Debug.info(TAG, " top height =" + mTop.getMeasuredHeight());
        ViewGroup.LayoutParams params = getChildAt(1).getLayoutParams();
        params.height = getMeasuredHeight();
//        Debug.info(TAG, " bottom height=" + mBottom.getMeasuredHeight());
//        mRecyclerView.setLayoutParams(recyclerViewParams);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (isInEditMode()) {
            return;
        }
//        Debug.info(TAG, "onLayout" + l + "||" + t + "||" + r + "||" + b);


    }


    @Override
    public void scrollTo(int x, int y) {

        if (isInEditMode()) {
            return;
        }
        Debug.info(TAG, "scrollto=" + x + "|" + y);
        if (y < 0) {
            y = 0;
        }
        if (y > mTopViewHeight) {
            y = mTopViewHeight;
        }
        if (y != getScrollY()) {
            super.scrollTo(x, y);
        }
    }

    @Override
    public void computeScroll() {
        if (isInEditMode()) {
            return;
        }
        if (mScroller.computeScrollOffset()) {
            Debug.info(TAG, "computeScroll=" + 0 + "|" + mScroller.getCurrY());
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }
}
