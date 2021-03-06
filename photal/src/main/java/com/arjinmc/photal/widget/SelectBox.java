package com.arjinmc.photal.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * select box function like checkbox with custom style
 * Created by Eminem Lo on 15/5/17.
 * Email arjinmc@hotmail.com
 */

public class SelectBox extends View implements View.OnClickListener {

    private int mFullWidth = 1;
    private Paint mPaint;
    private Paint mTextPaint;
    private int mBorderColor = Color.WHITE;
    private int mBackgroundColor = Color.BLUE;
    private int mIconColor = Color.WHITE;
    private int mThickness;
    private boolean isChecked;
    private int mTextNumber = 1;

    private OnCheckChangeListener mOnCheckChangeListener;

    public SelectBox(Context context) {
        super(context);
        init();
    }

    public SelectBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SelectBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SelectBox(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setColor(@ColorInt int color) {
        mBackgroundColor = color;
    }

    public void setTextNumber(int number) {
        mTextNumber = number;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(mBorderColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mThickness);
        canvas.drawCircle(mFullWidth / 2, mFullWidth / 2, mFullWidth / 2 - mThickness, mPaint);
        if (isChecked) {
            mPaint.setColor(mBackgroundColor);
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(mFullWidth / 2, mFullWidth / 2, mFullWidth / 2 - (int) (mThickness * 1.5), mPaint);

            String drawText = mTextNumber + "";
            mTextPaint.setColor(mIconColor);
            mTextPaint.setStrokeWidth(1);
            mTextPaint.setTextSize(mThickness * 3.5f);
            mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            float textWidth = mTextPaint.measureText(drawText);
            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
            float baselineY = mFullWidth / 2 + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
            canvas.drawText(drawText, mFullWidth / 2 - textWidth / 2, baselineY, mTextPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(mFullWidth, mFullWidth);
    }

    private void init() {
        initFullWidth();
        mThickness = mFullWidth / 8 >= 1 ? mFullWidth / 8 : 1;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        setOnClickListener(this);
    }

    public void initFullWidth() {
        Point point = new Point();

        ((WindowManager) (getContext().getSystemService(Context.WINDOW_SERVICE)))
                .getDefaultDisplay().getSize(point);
        mFullWidth = point.x;
        mFullWidth = mFullWidth / 12;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public void setOnCheckChangeListener(OnCheckChangeListener onCheckChangeListener) {
        mOnCheckChangeListener = onCheckChangeListener;
    }

    @Override
    public void onClick(View v) {
        setChecked(!isChecked);
        postInvalidate();
        if (mOnCheckChangeListener != null) {
            mOnCheckChangeListener.onChange(isChecked);
        }
    }

    public interface OnCheckChangeListener {

        void onChange(boolean change);
    }
}

