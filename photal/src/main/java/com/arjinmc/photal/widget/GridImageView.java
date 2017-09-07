package com.arjinmc.photal.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Eminem Lo on 26/5/17.
 * Email arjinmc@hotmail.com
 */

public class GridImageView extends android.support.v7.widget.AppCompatImageView {


    public GridImageView(Context context) {
        super(context);
    }

    public GridImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

}
