package com.arjinmc.photal.util;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

/**
 * Created by Eminem Lu on 28/4/17.
 * Email arjinmc@hotmail.com
 */

public class AlbumLoader extends CursorLoader {


    public AlbumLoader(Context context) {
        super(context);
    }

    public AlbumLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }
}
