package com.arjinmc.photal.util;

import android.content.Context;
import android.widget.ImageView;

import com.arjinmc.photal.GlideApp;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * ImageLoader
 * Created by Eminem Lo on 28/4/17.
 * Email arjinmc@hotmail.com
 */

public final class ImageLoader {

    public static void loadThumbnail(Context context, String uri, ImageView imageView) {
        GlideApp.get(context).clearMemory();
        GlideApp.with(context).asBitmap().load(uri)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .apply(new RequestOptions().centerCrop()).thumbnail(0.1f).into(imageView);
    }

    public static void load(Context context, String uri, ImageView imageView) {
        GlideApp.with(context).asBitmap().load(uri).into(imageView);
    }

    public static void clearMemory(Context context) {
        GlideApp.get(context).clearMemory();
    }

}
