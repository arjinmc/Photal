package com.arjinmc.photal.loader;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import androidx.loader.content.CursorLoader;

import com.arjinmc.photal.R;
import com.arjinmc.photal.config.Constant;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Loader cursor for photo album
 * Created by Eminem Lo on 28/4/17.
 * Email arjinmc@hotmail.com
 */

public class AlbumCursorLoader extends CursorLoader {

    public static final String ID = MediaStore.Files.FileColumns._ID;
    public static final String ALBUM_ID = MediaStore.Files.FileColumns.BUCKET_ID;
    public static final String ALBUM_NAME = MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME;
    public static final String ALBUM_IMAGE_DATA = MediaStore.Files.FileColumns.DATA;
    public static final String ALBUM_ORDER_BY = MediaStore.Files.FileColumns.DATE_TAKEN + " DESC";
    public static final String ALBUM_PHOTO_COUNT = MediaStore.Files.FileColumns.SIZE;

    private static final String[] PROJECTION = new String[]{
            ALBUM_ID
            , ALBUM_NAME
            , ALBUM_IMAGE_DATA
            , "COUNT(*) AS " + ALBUM_PHOTO_COUNT};
    private static final String SELECTION = MediaStore.Files.FileColumns.MEDIA_TYPE + "=? AND " + MediaStore.MediaColumns.SIZE + ">0"
            + ") GROUP BY (bucket_id";
    private static final String[] SELECTION_ARGS = new String[]{String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE)};

    //for api 29
    private static final String[] PROJECTION_API29 = new String[]{
            ALBUM_ID
            , ALBUM_NAME
            , ALBUM_IMAGE_DATA
            , ALBUM_PHOTO_COUNT};
    private static final String SELECTION_API29 = MediaStore.Files.FileColumns.MEDIA_TYPE + "=? AND " + MediaStore.MediaColumns.SIZE + ">0";
    private static final String[] SELECTION_ARGS_API29 = new String[]{String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE)};


    private Context mContext;

    public AlbumCursorLoader(Context context) {
        super(context, Constant.URI_MEDIA
                , isAboveAPI29() ? PROJECTION_API29 : PROJECTION
                , isAboveAPI29() ? SELECTION_API29 : SELECTION
                , isAboveAPI29() ? SELECTION_ARGS_API29 : SELECTION_ARGS, ALBUM_ORDER_BY);
        mContext = context;
    }

    @Override
    public Cursor loadInBackground() {
        Cursor cur = super.loadInBackground();
        MatrixCursor allCur = new MatrixCursor(new String[]{ALBUM_ID, ALBUM_NAME, ALBUM_IMAGE_DATA, ALBUM_PHOTO_COUNT});
        int totalCount = 0;
        String totalData = null;

        if (isAboveAPI29()) {


        } else {
            while (cur.moveToNext()) {
                if (cur.getPosition() == 0) {
                    totalData = cur.getString(cur.getColumnIndex(ALBUM_IMAGE_DATA));
                }
                totalCount += cur.getInt(cur.getColumnIndex(MediaStore.MediaColumns.SIZE));
            }
        }
        allCur.addRow(new String[]{"-1"
                , mContext.getString(R.string.photal_all_photos)
                , totalData
                , String.valueOf(totalCount)});
        return new MergeCursor(new Cursor[]{allCur, cur});
    }

    private static Uri getUri(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID));
        Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
        return uri;
    }

    private static boolean isAboveAPI29() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    }
}
