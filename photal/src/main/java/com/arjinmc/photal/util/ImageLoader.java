package com.arjinmc.photal.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.arjinmc.photal.Photal;
import com.arjinmc.photal.config.PhotalConfig;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

/**
 * ImageLoader
 * Created by Eminem Lo on 28/4/17.
 * Email arjinmc@hotmail.com
 */

public final class ImageLoader {

    public static final int MODE_GLIDE = 1;
    public static final int MODE_PICASSO = 2;


    private static int mCurrentMode = -1;

    private static void init() {
        PhotalConfig photalConfig = Photal.getInstance().getConfig();
        if (mCurrentMode == -1 &&
                photalConfig != null
                && (photalConfig.getImageLoaderType() == 1 || photalConfig.getImageLoaderType() == 2)) {
            mCurrentMode = photalConfig.getImageLoaderType();
        } else if (mCurrentMode == -1) {
            mCurrentMode = MODE_GLIDE;
        }
    }

    public static void loadThumbnail(Context context, String uri, ImageView imageView) {
        init();
        switch (mCurrentMode) {
            case MODE_GLIDE:
                Glide.with(context).load(uri).centerCrop().into(imageView);
                break;
            case MODE_PICASSO:
                Picasso.with(context).load("file:" + uri)
                        .config(Bitmap.Config.RGB_565)
                        .fit().centerCrop().into(imageView);
                break;
        }
    }

    public static void load(Context context, String uri, ImageView imageView) {
        init();
        switch (mCurrentMode) {
            case MODE_GLIDE:
                Glide.with(context).load(uri).into(imageView);
                break;
            case MODE_PICASSO:
                Picasso.with(context).load("file:" + uri).config(Bitmap.Config.RGB_565).into(imageView);
                break;
        }
    }

}
