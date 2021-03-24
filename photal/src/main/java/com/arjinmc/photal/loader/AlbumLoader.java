package com.arjinmc.photal.loader;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.arjinmc.photal.callback.PhotalLoaderCallback;
import com.arjinmc.photal.config.Constant;

/**
 * Created by Eminem Lo on 25/5/17.
 * Email arjinmc@hotmail.com
 */

public class AlbumLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private FragmentActivity mContext;
    private PhotalLoaderCallback mPhotalLoaderCallback;


    public AlbumLoader(FragmentActivity context, PhotalLoaderCallback photalLoaderCallback) {
        mContext = context;
        mPhotalLoaderCallback = photalLoaderCallback;
    }

    public void load() {
        mContext.getSupportLoaderManager().initLoader(Constant.LOADER_ID_ALBUM, null, this);
    }

    public void destroyLoader() {
        mContext.getSupportLoaderManager().destroyLoader(Constant.LOADER_ID_ALBUM);
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
