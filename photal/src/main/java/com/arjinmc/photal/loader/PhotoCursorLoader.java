package com.arjinmc.photal.loader;

import android.content.Context;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;

import com.arjinmc.photal.config.Constant;

/**
 * loader for photo
 * Created by Eminem Lo on 28/4/17.
 * Email arjinmc@hotmail.com
 */

public class PhotoCursorLoader extends CursorLoader {

    public static final String PHOTO_ID = MediaStore.Images.Media._ID;
    public static final String PHOTO_DISPLAY_NAME = MediaStore.Images.Media.DISPLAY_NAME;
    public static final String PHOTO_DATA = MediaStore.Images.Media.DATA;
    public static final String PHOTO_TYPE = MediaStore.Images.Media.MIME_TYPE;

    public static final String SELECTION_ALBUM = AlbumCursorLoader.ALBUM_ID + "=?";

    public PhotoCursorLoader(Context context) {

        super(context, Constant.URI_IMAGE_MEDIA
                , new String[]{PHOTO_ID, PHOTO_DISPLAY_NAME, PHOTO_DATA, PHOTO_TYPE}
                , null, null, MediaStore.Images.Media.DATE_ADDED + " DESC");

    }

    public PhotoCursorLoader(Context context, Integer albumId) {

        super(context, Constant.URI_IMAGE_MEDIA
                , new String[]{PHOTO_ID, PHOTO_DISPLAY_NAME, PHOTO_DATA, PHOTO_TYPE}
                , SELECTION_ALBUM, new String[]{String.valueOf(albumId)}, MediaStore.Images.Media.DATE_ADDED + " DESC");

    }

}
