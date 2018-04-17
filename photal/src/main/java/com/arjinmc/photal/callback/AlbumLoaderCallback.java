package com.arjinmc.photal.callback;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.arjinmc.photal.config.Config;
import com.arjinmc.photal.loader.AlbumCursorLoader;

/**
 * Created by Eminem Lo on 25/5/17.
 * Email arjinmc@hotmail.com
 */

public class AlbumLoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {

    private FragmentActivity mContext;
    private PhotalLoaderCallback mPhotalLoaderCallback;


    public AlbumLoaderCallback(FragmentActivity context, PhotalLoaderCallback photalLoaderCallback) {
        mContext = context;
        mPhotalLoaderCallback = photalLoaderCallback;
    }

    public void load() {
        mContext.getSupportLoaderManager().initLoader(Config.LOADER_ID_ALBUM, null, this);
    }

    public void destroyLoader() {
        mContext.getSupportLoaderManager().destroyLoader(Config.LOADER_ID_ALBUM);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AlbumCursorLoader(mContext);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (mPhotalLoaderCallback != null) {
            mPhotalLoaderCallback.onLoadFinished(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (mPhotalLoaderCallback != null) {
            mPhotalLoaderCallback.onLoaderReset();
        }
    }

}