package com.arjinmc.photal.loader;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;

import com.arjinmc.photal.R;
import com.arjinmc.photal.config.Constant;

/**
 * Loader cursor for photo album
 * Created by Eminem Lu on 28/4/17.
 * Email arjinmc@hotmail.com
 */

public class AlbumCursorLoader extends CursorLoader {

    public static final String ID = MediaStore.Images.Media._ID;
    public static final String ALBUM_ID = MediaStore.Images.Media.BUCKET_ID;
    public static final String ALBUM_NAME = MediaStore.Images.Media.BUCKET_DISPLAY_NAME;
    public static final String ALBUM_IMAGE_DATA = MediaStore.Images.Media.DATA;
    public static final String ALBUM_ORDER_BY = MediaStore.Images.Media.DATE_ADDED + " DESC";
    public static final String ALBUM_PHOTO_COUNT = "count";

    private Context mContext;

    public AlbumCursorLoader(Context context) {
        super(context, Constant.URI_IMAGE_MEDIA
                , new String[]{ALBUM_ID, ALBUM_NAME, ALBUM_IMAGE_DATA, "COUNT(*) AS " + ALBUM_PHOTO_COUNT}
                , "1=1) GROUP BY 1,(1", null, ALBUM_ORDER_BY);
        mContext = context;
    }

    @Override
    public Cursor loadInBackground() {
        Cursor cur = super.loadInBackground();
        MatrixCursor allCur = new MatrixCursor(new String[]{ALBUM_ID, ALBUM_NAME, ALBUM_IMAGE_DATA, ALBUM_PHOTO_COUNT});
        int totalCount = 0;
        String totalData = null;
        while (cur.moveToNext()) {
            if (cur.getPosition() == 0)
                totalData = cur.getString(cur.getColumnIndex(ALBUM_IMAGE_DATA));
            totalCount += cur.getInt(cur.getColumnIndex(ALBUM_PHOTO_COUNT));
        }
        allCur.addRow(new String[]{"-1"
                , mContext.getString(R.string.photal_all_photos)
                , totalData
                , String.valueOf(totalCount)});
        return new MergeCursor(new Cursor[]{allCur, cur});
    }
}
