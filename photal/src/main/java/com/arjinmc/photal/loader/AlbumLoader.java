package com.arjinmc.photal.loader;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;

import com.arjinmc.photal.config.Constant;

/**
 * Loader for photo album
 * Created by Eminem Lu on 28/4/17.
 * Email arjinmc@hotmail.com
 */

public class AlbumLoader extends CursorLoader {

    public static final String ALBUM_ID = MediaStore.Images.Media.BUCKET_ID;
    public static final String ALBUM_NAME = MediaStore.Images.Media.BUCKET_DISPLAY_NAME;
    public static final String ALBUM_ORDER_BY = "MAX(" + MediaStore.Images.Media.DATE_TAKEN + ") DESC";
    public static final String ALBUM_PHOTO_COUNT = "count";

    public AlbumLoader(Context context) {
        super(context,Constant.URI_IMAGE_MEDIA
                ,new String[]{ALBUM_ID,ALBUM_NAME,"COUNT(*) AS "+ALBUM_PHOTO_COUNT},null,null, ALBUM_ORDER_BY);
    }

    public AlbumLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context, uri,projection, selection, selectionArgs, sortOrder);
    }

}
