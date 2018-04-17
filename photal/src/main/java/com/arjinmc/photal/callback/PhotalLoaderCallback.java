package com.arjinmc.photal.callback;

import android.database.Cursor;

/**
 * Album callback for loader
 * Created by Eminem Lo on 25/5/17.
 * Email arjinmc@hotmail.com
 */

public interface PhotalLoaderCallback {

    void onLoadFinished(Cursor cursor);

    void onLoaderReset();
}
