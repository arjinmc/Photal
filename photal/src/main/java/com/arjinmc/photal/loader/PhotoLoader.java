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

public class PhotoLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private FragmentActivity mContext;
    private PhotalLoaderCallback mPhotalLoaderCallback;


    public PhotoLoader(FragmentActivity context, PhotalLoaderCallback photalLoaderCallback) {
        mContext = context;
        mPhotalLoaderCallback = photalLoaderCallback;
    }

    public void load(Integer albumId) {
        if (albumId == null) albumId = -1;
        Bundle bundle = new Bundle();
        bundle.putInt("id", albumId);
        mContext.getSupportLoaderManager().initLoader(Constant.LOADER_ID_PHOTO, bundle, this);
    }

    public void destroyLoader() {
        mContext.getSupportLoaderManager().destroyLoader(Constant.LOADER_ID_PHOTO);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        int albumId = args.getInt("id", -1);

        if (albumId != -1)
            return new PhotoCursorLoader(mContext, albumId);
        return new PhotoCursorLoader(mContext);
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
