package com.arjinmc.photal.util;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

/**
 * Created by Eminem Lu on 28/4/17.
 * Email arjinmc@hotmail.com
 */

public final class ImageLoader {

    private static final int MODE_GLIDE = 1;
    private static final int MODE_PICASSO = 2;

    //change this mode for use different image framework
    private static int mode = MODE_GLIDE;


    public static void loadThumbnail(Context context,Uri uri,ImageView imageView){
        switch (mode){
            case MODE_GLIDE:
                Glide.with(context).load(uri).centerCrop().into(imageView);
                break;
            case MODE_PICASSO:
                Picasso.with(context).load(uri).centerCrop().into(imageView);
                break;
        }
    }

}
