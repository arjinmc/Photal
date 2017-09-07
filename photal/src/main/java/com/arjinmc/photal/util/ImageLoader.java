package com.arjinmc.photal.util;

import android.content.Context;
import android.widget.ImageView;

import com.arjinmc.photal.config.Config;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

/**
 * Created by Eminem Lo on 28/4/17.
 * Email arjinmc@hotmail.com
 */

public final class ImageLoader {

    public static final int MODE_GLIDE = 1;
    public static final int MODE_PICASSO = 2;

    private static int mode = Config.IMAGE_MODE;

    public static void loadThumbnail(Context context, String uri, ImageView imageView) {
        switch (mode) {
            case MODE_GLIDE:
                Glide.with(context).load(uri).centerCrop().into(imageView);
                break;
            case MODE_PICASSO:
                Picasso.with(context).load(uri).centerCrop().into(imageView);
                break;
        }
    }



}
