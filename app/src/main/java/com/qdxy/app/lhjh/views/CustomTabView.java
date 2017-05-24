package com.qdxy.app.lhjh.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lf.tempcore.tempModule.tempUtils.TempDensityUtil;

/**
 * Created by KY on 2016/11/11.
 */

public class CustomTabView extends View {
    private String leftTabDesc, rightTabDesc;
    private float cornerSize = 5;
//    private RectF leftTAab, rightTab;
//    private float storkeWidth=2f;
    private Paint paint;
    float[] leftOuterR = new float[]{10, 10, 0, 0, 0,0, 10, 10};
    float[] rightOuterR = new float[]{0, 0, 10, 10, 10,10, 0, 0};

    private int strokeWidth =2; // 3px not dp
//    int roundRadius = 12; // 8px not dp
    int strokeColor = Color.parseColor("#FFFFFF");
    int fillColor = Color.parseColor("#FFFFFF");
//    RoundRectShape rightRectShape;
//    ShapeDrawable leftTab,rightTab;
    private boolean rightSelected;
    private  GradientDrawable leftRectShape,rightRectShape;
    private int selectedBackGround, unSelectedBackground, selectedTextcolor, unSelectedTextColor;
    private String leftText,rightText;
    private Rect textBounds;
    private OnTabSelectedListener mOnTabSelectedListener;

    //    private
    public CustomTabView(Context context) {
        super(context);
    }

    public CustomTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
//        TypedValue typedValue = new TypedValue();
//        if (context.getTheme().resolveAttribute(com.lf.tempcore.R.style.AppTheme_Base,typedValue,true)){
//
//        }
//        leftRectShape = new RoundRectShape(leftOuterR, null, null);
//        rightRectShape = new RoundRectShape(rightOuterR, null, null);
//         leftTab = new ShapeDrawable(leftRectShape);
//        rightTab = new ShapeDrawable(rightRectShape);
        leftText = "已激活";
        rightText = "未激活";
        textBounds = new Rect();
//        selectedBackGround = Color.parseColor("#00A5BB");
        selectedBackGround = Color.parseColor("#FFFFFF");
        unSelectedTextColor =Color.parseColor("#FFFFFF");
        unSelectedBackground =Color.parseColor("#3F51B5");
        selectedTextcolor =Color.parseColor("#3F51B5");
        leftRectShape = new GradientDrawable();
        rightRectShape = new GradientDrawable();
    }
    public CustomTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        leftTAab = new RectF(0, 0, getWidth() / 2, getHeight());
//        rightTab = new RectF(getWidth() / 2, 0, getWidth(), getHeight());


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightSize = MeasureSpec.getSize(widthMeasureSpec);
//        if
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }



    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        canvas.drawRoundRect(leftTAab,);

    if (!rightSelected){
    //绘制左边tab
    leftRectShape.setColor(selectedBackGround);
//        leftRectShape.setCornerRadius(roundRadius);
    leftRectShape.setCornerRadii(leftOuterR);
//    leftRectShape.setBounds(strokeWidth/2, strokeWidth/2, getWidth()/2, (getHeight()-strokeWidth/2));
    leftRectShape.setBounds(strokeWidth/2, strokeWidth/2, getWidth()/2, (getHeight()-strokeWidth/2));
    leftRectShape.setStroke(strokeWidth, strokeColor);
    leftRectShape.draw(canvas);
    //绘制内容
        paint.setColor(selectedTextcolor);
        paint.setTextSize(TempDensityUtil.dip2px(getContext(),15f));
        paint.getTextBounds(leftText,0,leftText.length(),textBounds);
        canvas.drawText(leftText,(getWidth()/2-textBounds.width())/2,(getHeight()+textBounds.height())/2-strokeWidth,paint);
    //绘制右边tab
    rightRectShape.setColor(unSelectedBackground);
//        leftRectShape.setCornerRadius(roundRadius);
    rightRectShape.setCornerRadii(rightOuterR);
    rightRectShape.setBounds(getWidth()/2, strokeWidth/2, getWidth()-strokeWidth/2, (getHeight()-strokeWidth/2));
    rightRectShape.setStroke(strokeWidth, strokeColor);
    rightRectShape.draw(canvas);
        //绘制文字
        paint.setColor(unSelectedTextColor);
        paint.setTextSize(TempDensityUtil.dip2px(getContext(),15f));
        paint.getTextBounds(rightText,0,leftText.length(),textBounds);
        canvas.drawText(rightText,(getWidth()+getWidth()/2-textBounds.width())/2,(getHeight()+textBounds.height())/2-strokeWidth,paint);
    }else{
        //绘制左边tab
        leftRectShape.setColor(unSelectedBackground);
//        leftRectShape.setCornerRadius(roundRadius);
        leftRectShape.setCornerRadii(leftOuterR);
        leftRectShape.setBounds(strokeWidth/2, strokeWidth/2, getWidth()/2, (getHeight()-strokeWidth/2));
        leftRectShape.setStroke(strokeWidth, strokeColor);
        leftRectShape.draw(canvas);
        //绘制内容
        paint.setColor(unSelectedTextColor);
        paint.setTextSize(TempDensityUtil.dip2px(getContext(),15f));
        paint.getTextBounds(leftText,0,leftText.length(),textBounds);
        canvas.drawText(leftText,(getWidth()/2-textBounds.width())/2,(getHeight()+textBounds.height())/2-strokeWidth,paint);
        //绘制右边tab
        rightRectShape.setColor(selectedBackGround);
//        leftRectShape.setCornerRadius(roundRadius);
        rightRectShape.setCornerRadii(rightOuterR);
        rightRectShape.setBounds(getWidth()/2, strokeWidth/2, getWidth()-strokeWidth/2, (getHeight()-strokeWidth/2));
        rightRectShape.setStroke(strokeWidth, strokeColor);
        rightRectShape.draw(canvas);
        //绘制文字
        paint.setColor(selectedTextcolor);
        paint.setTextSize(TempDensityUtil.dip2px(getContext(),15f));
        paint.getTextBounds(rightText,0,leftText.length(),textBounds);
        canvas.drawText(rightText,(getWidth()+getWidth()/2-textBounds.width())/2,(getHeight()+textBounds.height())/2-strokeWidth,paint);
    }




       /* leftTab.getPaint().setColor(Color.WHITE);
        leftTab.getPaint().setStrokeWidth(storkeWidth);
        leftTab.setBounds((int)(rightTab.getPaint().getStrokeWidth()/2), (int)(rightTab.getPaint().getStrokeWidth()/2), (int)(getWidth()/2-(leftTab.getPaint().getStrokeWidth()/2)), (int)(getHeight()-(leftTab.getPaint().getStrokeWidth()/2)));
        leftTab.draw(canvas);*/
        //绘制右边tab
//        rightTab.getPaint().setColor(Color.GREEN);
//        rightTab.getPaint().setStrokeWidth(5);
//        rightTab.getPaint().setStyle(Paint.Style.STROKE);
//        rightTab.setBounds(getWidth()/2+(int)(rightTab.getPaint().getStrokeWidth()/2), (int)(rightTab.getPaint().getStrokeWidth()/2), (int)(getWidth()-(rightTab.getPaint().getStrokeWidth()/2)), (int)(getHeight()-(rightTab.getPaint().getStrokeWidth()/2)));
//        rightTab.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Debug.info("touch");
//        Debug.info("width="+getWidth());

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
//                Debug.info("down");
                break;
            case MotionEvent.ACTION_UP:
//
//                Debug.info("touchX="+event.getX());
//
                if (!rightSelected&&event.getX()>(getWidth()/2+strokeWidth)){
//                    Debug.info("点击右边");
                    //点击右边
                    selectedRight();
//                    rightSelected=true;
//                    invalidate();

                }else if (rightSelected&&event.getX()<(getWidth()/2+strokeWidth)){
                    //点击左边
//                    Debug.info("点击左边");
                    selectedLeft();
                }
        }
        return true;
    }
    public void selectedLeft(){
        rightSelected=false;
        invalidate();
        if (mOnTabSelectedListener!=null){
            mOnTabSelectedListener.onTabLeftSelected(leftText);
        }
    }
    public void selectedRight(){
        rightSelected=true;
        invalidate();
        if (mOnTabSelectedListener!=null){
            mOnTabSelectedListener.onTabRightSelected(rightText);
        }
    }
    public interface OnTabSelectedListener{
       void onTabLeftSelected(String content);
       void onTabRightSelected(String content);
    }


    public void setmOnTabSelectedListener(OnTabSelectedListener mOnTabSelectedListener) {
        this.mOnTabSelectedListener = mOnTabSelectedListener;
    }
}
