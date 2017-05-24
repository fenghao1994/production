package com.qdxy.app.lhjh.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.qdxy.app.lhjh.R;

/**
 * Created by KY on 2016/10/14.
 */

public class CustomImageView extends View {
    public static final int IMAGE_SCALE_FITXY=0,IMAGE_SCALE_CENTER=1;
    private String mTitle;
    private Bitmap mImage;
    private int mImageScale, mTextColor, mTextSize, mWidth, mHeight;
    private Rect rect;
    private Paint mPaint,mPaint1,mPaint2;
    private Rect mTextBound;
    private int mCircleWidth=30;

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomImageView, defStyleAttr, 0);
        int mMargin =10;
        mMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                mMargin, getResources().getDisplayMetrics());
        for (int i = 0; i < array.getIndexCount(); i++) {
            int attr = array.getIndex(i);

            switch (attr) {
                case R.styleable.CustomImageView_image:
                    mImage = BitmapFactory.decodeResource(getResources(), array.getResourceId(attr, 0));
                    break;
                case R.styleable.CustomImageView_imageScaleType:
                    mImageScale = array.getInt(attr, 0);
                    break;
                case R.styleable.CustomImageView_titleText:
                    mTitle = array.getString(attr);
                    break;
                case R.styleable.CustomImageView_titleTextColor:
                    mTextColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.CustomImageView_titleTextSize:
                    mTextSize = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                            16, getResources().getDisplayMetrics()));
                    break;

            }
        }
        array.recycle();
        rect = new Rect();
        mPaint = new Paint();
        mPaint2=new Paint();
        mTextBound = new Rect();
        mPaint.setTextSize(mTextSize);
        // 计算了描绘字体需要的范围
        mPaint.getTextBounds(mTitle, 0, mTitle.length(), mTextBound);
        mPaint1 = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(widthMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            // 由图片决定的宽
            int desireByImg = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
            // 由字体决定的宽
            int desireByTitle = getPaddingLeft() + getPaddingRight() + mTextBound.width();

            if (widthMode == MeasureSpec.AT_MOST)// wrap_content
            {
                int desire = Math.max(desireByImg, desireByTitle);
                mWidth = Math.min(desire, widthSize);
                Log.e("xxx", "AT_MOST");
            }
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            // 由图片决定的宽
            int desireByImg = getPaddingTop() + getPaddingBottom() + mImage.getWidth();
            // 由字体决定的宽
            int desireByTitle = getPaddingTop() + getPaddingBottom() + mTextBound.width();

            if (widthMode == MeasureSpec.AT_MOST)// wrap_content
            {
                int desire = Math.max(desireByImg, desireByTitle);
                mHeight = Math.min(desire, widthSize);
                Log.e("xxx", "AT_MOST");
            }
        }
        setMeasuredDimension(mWidth,mHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0,0,getWidth(),getHeight(),mPaint2);
        boolean isNext=false;
        int mProgress=45;
        int center = getWidth() / 2; // 获取圆心的x坐标
        int radius = center-(mCircleWidth/2);// 半径
        mPaint.setStrokeWidth(mCircleWidth); // 设置圆环的宽度
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius); // 用于定义的圆弧的形状和大小的界限
        if (!isNext)
        {// 第一颜色的圈完整，第二颜色跑
            mPaint.setColor(Color.GREEN); // 设置圆环的颜色
            canvas.drawCircle(center, center, radius, mPaint); // 画出圆环
            mPaint.setColor(Color.RED); // 设置圆环的颜色
            canvas.drawArc(oval, -90, mProgress, false, mPaint); // 根据进度画圆弧
        } /*else
        {
            mPaint.setColor(mSecondColor); // 设置圆环的颜色
            canvas.drawCircle(centre, centre, radius, mPaint); // 画出圆环
            mPaint.setColor(mFirstColor); // 设置圆环的颜色
            canvas.drawArc(oval, -90, mProgress, false, mPaint); // 根据进度画圆弧
        }*/
      /*  *//**
         * 边框
         *//*
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.CYAN);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        rect.left = getPaddingLeft();
        rect.right = mWidth - getPaddingRight();
        rect.top = getPaddingTop();
        rect.bottom = mHeight - getPaddingBottom();

        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);
        *//**
         * 当前设置的宽度小于字体需要的宽度，将字体改为xxx...
         *//*
        if (mTextBound.width() > mWidth)
        {
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mTitle, paint, (float) mWidth - getPaddingLeft() - getPaddingRight(),
                    TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);

        } else
        {
            //正常情况，将字体居中
            canvas.drawText(mTitle, mWidth / 2 - mTextBound.width() * 1.0f / 2, mHeight - getPaddingBottom(), mPaint);
        }

        //取消使用掉的快
        rect.bottom -= mTextBound.height();

        if (mImageScale == IMAGE_SCALE_FITXY)
        {
            canvas.drawBitmap(mImage, null, rect, mPaint);
        } else
        {
            //计算居中的矩形范围
            rect.left = mWidth / 2 - mImage.getWidth() / 2;
            rect.right = mWidth / 2 + mImage.getWidth() / 2;
            rect.top = (mHeight - mTextBound.height()) / 2 - mImage.getHeight() / 2;
            rect.bottom = (mHeight - mTextBound.height()) / 2 + mImage.getHeight() / 2;

            canvas.drawBitmap(mImage, null, rect, mPaint);
        }*/
    }
}
