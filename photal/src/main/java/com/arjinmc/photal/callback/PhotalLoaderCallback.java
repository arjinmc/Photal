package com.arjinmc.photal.callback;

import android.database.Cursor;

/**
 * Album callback for loader
 * Created by Eminem Lu on 25/5/17.
 * Email arjinmc@hotmail.com
 */

public interface PhotalLoaderCallback {

    public void onLoadFinished(Cursor cursor);

    public void onLoaderReset();
}
