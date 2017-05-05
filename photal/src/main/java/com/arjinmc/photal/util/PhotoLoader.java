package com.arjinmc.photal.util;

import android.content.Context;
import android.net.Uri;
import android.support.v4.content.CursorLoader;

/**
 * Created by Eminem Lu on 28/4/17.
 * Email arjinmc@hotmail.com
 */

public class PhotoLoader extends CursorLoader {
    public PhotoLoader(Context context) {
        super(context);
    }

    public PhotoLoader(Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        super(context, uri, projection, selection, selectionArgs, sortOrder);
    }
}
