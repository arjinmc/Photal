package com.arjinmc.photal.loader;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;

import com.arjinmc.photal.config.Constant;

/**
 * loader for photo
 * Created by Eminem Lu on 28/4/17.
 * Email arjinmc@hotmail.com
 */

public class PhotoLoader extends CursorLoader {

    public static final String PHOTO_ID = MediaStore.Images.Media._ID;
    public static final String PHOTO_DISPLAY_NAME = MediaStore.Images.Media.DISPLAY_NAME;
    public static final String PHOTO_DATA = MediaStore.Images.Media.DATA;
    public static final String PHOTO_TYPE = MediaStore.Images.Media.MIME_TYPE;
    public static final String PHOTO_ORIENTATION = MediaStore.Images.Media.ORIENTATION;

    public PhotoLoader(Context context) {
        super(context, Constant.URI_IMAGE_MEDIA
                ,new String[]{PHOTO_ID,PHOTO_DISPLAY_NAME,PHOTO_DATA,PHOTO_TYPE,PHOTO_ORIENTATION}
                ,null,null, MediaStore.Images.Media.DATE_ADDED+" DESC");
    }

    public PhotoLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }
}
