package com.arjinmc.photal.widget;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;

import androidx.annotation.ColorInt;

/**
 * back button background
 * Created by Eminem Lo on 2018/4/23.
 * email: arjinmc@hotmail.com
 */
public class PressSelectorDrawable extends StateListDrawable {

    private int mUnpressColor;
    private int mPressColor;

    public PressSelectorDrawable(@ColorInt int unpressColor, @ColorInt int pressColor) {
        mUnpressColor = unpressColor;
        mPressColor = pressColor;
        init();
    }

    private void init() {
        addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(mPressColor));
        addState(new int[]{-android.R.attr.state_pressed}, new ColorDrawable(mUnpressColor));
    }
}
