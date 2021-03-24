package com.arjinmc.photal.loader;

import android.content.Context;
import android.provider.MediaStore;

import androidx.loader.content.CursorLoader;

import com.arjinmc.photal.config.Constant;

/**
 * loader for photo
 * Created by Eminem Lo on 28/4/17.
 * Email arjinmc@hotmail.com
 */

public class PhotoCursorLoader extends CursorLoader {

    public static final String PHOTO_ID = MediaStore.Files.FileColumns._ID;
    public static final String PHOTO_DISPLAY_NAME = MediaStore.Files.FileColumns.DISPLAY_NAME;
    public static final String PHOTO_DATA = MediaStore.Files.FileColumns.DATA;
    public static final String PHOTO_TYPE = MediaStore.Files.FileColumns.MIME_TYPE;
    public static final String PHOTO_DATE_TOKEN = MediaStore.Files.FileColumns.DATE_TAKEN;

    public static final String SELECTION_ALBUM = AlbumCursorLoader.ALBUM_ID + "=?";
    public static final String SELECTION_MEDIA_TYPE = MediaStore.Files.FileColumns.MEDIA_TYPE + "=?";

    public PhotoCursorLoader(Context context) {

        super(context, Constant.URI_MEDIA
                , new String[]{PHOTO_ID, PHOTO_DISPLAY_NAME, PHOTO_DATA, PHOTO_TYPE, PHOTO_DATE_TOKEN}
                , SELECTION_MEDIA_TYPE
                , new String[]{String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE)}
                , PHOTO_DATE_TOKEN + " DESC");

    }

    public PhotoCursorLoader(Context context, Integer albumId) {

        super(context, Constant.URI_MEDIA
                , new String[]{PHOTO_ID, PHOTO_DISPLAY_NAME, PHOTO_DATA, PHOTO_TYPE, PHOTO_DATE_TOKEN}
                ,  SELECTION_ALBUM + " AND " + SELECTION_MEDIA_TYPE
                , new String[]{String.valueOf(albumId), String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE)}
                , PHOTO_DATE_TOKEN + " DESC");

    }

}