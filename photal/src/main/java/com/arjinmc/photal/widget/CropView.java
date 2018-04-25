package com.arjinmc.photal.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;

/**
 * Crop View
 * scale,move the source image then crop
 * Created by Eminem Lo on 2018/4/25.
 * email: arjinmc@hotmail.com
 */
@SuppressLint("AppCompatCustomView")
public class CropView extends ImageView{

    private PhotoView mPvImage;


    public CropView(Context context) {
        super(context);
    }

    public CropView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CropView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CropView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
