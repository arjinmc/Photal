package com.arjinmc.photal.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.arjinmc.photal.R;

/**
 * crop image guide
 * Created by Eminem Lo on 2018/4/25.
 * email: arjinmc@hotmail.com
 */
public class CropGuideView extends View {

    public static final int SHAPE_CIRCLE = 1;
    public static final int SHAPE_SQUARE = 2;

    @IntDef(value = {SHAPE_CIRCLE, SHAPE_SQUARE})
    @interface CropShapeType {
    }

    private int mBackgroundColor;
    private int mShapeBorderColor = Color.WHITE;
    private int mShapeThickness = 2;
    private int mCurrentShape = SHAPE_SQUARE;
    private int mPadding;

    private Paint mPaint;

    public CropGuideView(Context context) {
        super(context);
        init();
    }

    public CropGuideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CropGuideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CropGuideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

        mBackgroundColor = ContextCompat.getColor(getContext(), R.color.photal_translucent);
        mPadding = getContext().getResources().getDimensionPixelSize(R.dimen.photal_margin_super_huge);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setShape(@CropShapeType int shape) {
        mCurrentShape = shape;
    }

    public void setBorder(@ColorInt int borderColor, int thickness) {
        mShapeBorderColor = borderColor;
        mShapeThickness = thickness;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        mPadding = (width <= height ? width : height) / 3;
        //center point
        int cX = width / 2;
        int cY = height / 2;

        mPaint.setStrokeWidth(mShapeThickness);
        //draw background
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(mBackgroundColor);
        Path path = new Path();
        path.addRect(0, 0, width, height, Path.Direction.CW);

        if (mCurrentShape == SHAPE_SQUARE) {

            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mBackgroundColor);
            mPadding = mPadding - mShapeThickness - 1;
            path.addRect(cX - mPadding, cY - mPadding, cX + mPadding, cY + mPadding, Path.Direction.CCW);
            canvas.drawPath(path, mPaint);

            //draw square border
            mPaint.setColor(mShapeBorderColor);
            mPaint.setStyle(Paint.Style.STROKE);
            mPadding = mPadding + mShapeThickness / 2 + 1;
            canvas.drawRect(cX - mPadding, cY - mPadding, cX + mPadding, cY + mPadding, mPaint);

        } else {

            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setColor(mBackgroundColor);
            mPadding = mPadding - mShapeThickness - 1;
            path.addCircle(cX, cY, mPadding, Path.Direction.CCW);
            canvas.drawPath(path, mPaint);

            //draw circle border
            mPaint.setColor(mShapeBorderColor);
            mPaint.setStyle(Paint.Style.STROKE);
            mPadding = mPadding + mShapeThickness / 2 + 1;
            canvas.drawCircle(cX, cY, mPadding, mPaint);

        }


    }
}
