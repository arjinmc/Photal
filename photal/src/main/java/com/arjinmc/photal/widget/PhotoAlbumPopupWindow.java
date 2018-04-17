package com.arjinmc.photal.widget;

import android.database.Cursor;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.arjinmc.photal.R;
import com.arjinmc.photal.adapter.RecyclerViewCursorAdapter;
import com.arjinmc.photal.callback.AlbumLoaderCallback;
import com.arjinmc.photal.callback.PhotalLoaderCallback;
import com.arjinmc.photal.loader.AlbumCursorLoader;
import com.arjinmc.photal.util.CommonUtil;
import com.arjinmc.photal.util.ImageLoader;
import com.arjinmc.photal.viewholder.AlbumViewHolder;
import com.arjinmc.recyclerviewdecoration.RecyclerViewItemDecoration;

/**
 * album chooser for photo grid style
 * Created by Eminem Lo on 26/5/17.
 * Email arjinmc@hotmail.com
 */

public class PhotoAlbumPopupWindow extends PopupWindow {

    private FragmentActivity mContext;

    private RecyclerView mRvAlbum;
    private AlbumAdapter mAlbumAdapter;
    private DismissRunnable mDismissRunnable;
    private Handler mHandler = new Handler();
    private int mAlbumId = -1;


    public PhotoAlbumPopupWindow(FragmentActivity context) {
        mContext = context;
        init();
    }

    private void init() {

        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = CommonUtil.getScreenHeight(mContext) * 4 / 5;
        setWidth(width);
        setHeight(height);

        LinearLayout rootView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.photal_pop_photo_grid_album, null);
        rootView.setLayoutParams(new LinearLayoutCompat.LayoutParams(width, height));
        mRvAlbum = (RecyclerView) rootView.findViewById(R.id.rv_album);
        mRvAlbum.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                , CommonUtil.getScreenHeight(mContext) * 2 / 3));
        setContentView(rootView);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mRvAlbum.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRvAlbum.addItemDecoration(new RecyclerViewItemDecoration.Builder(mContext)
                .paddingStart(50)
                .paddingEnd(50)
                .color(CommonUtil.getColor(mContext, R.color.photal_album_diver))
                .thickness(2).create());
        mAlbumAdapter = new AlbumAdapter();
        mRvAlbum.setAdapter(mAlbumAdapter);

        AlbumLoaderCallback albumLoaderCallback = new AlbumLoaderCallback(mContext, new PhotalLoaderCallback() {

            @Override
            public void onLoadFinished(Cursor cursor) {
                mAlbumAdapter.setCursor(cursor);
                mAlbumAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLoaderReset() {
                mAlbumAdapter.setCursor(null);

            }
        });
        albumLoaderCallback.load();
        mDismissRunnable = new DismissRunnable();
    }


    public void show(View view) {
        showAtLocation(view, Gravity.BOTTOM, 0, view.getHeight());
    }

    public int getChosenAlbumId() {
        return mAlbumId;
    }

    private class AlbumAdapter extends RecyclerViewCursorAdapter<AlbumViewHolder> {

        private int currentPosition = 0;

        @Override
        public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AlbumViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.photal_item_photo_grid_album, parent, false));
        }

        @Override
        public void onBindViewHolder(AlbumViewHolder holder, final int position) {
            if (mCusor != null) {
                mCusor.moveToPosition(position);
                final int albumId = mCusor.getInt(mCusor.getColumnIndex(AlbumCursorLoader.ALBUM_ID));
                holder.tvName.setText(mCusor.getString(mCusor.getColumnIndex(AlbumCursorLoader.ALBUM_NAME)));
                holder.tvCount.setText(
                        "(" + mCusor.getInt(mCusor.getColumnIndex(AlbumCursorLoader.ALBUM_PHOTO_COUNT)) + ")");
                ImageLoader.loadThumbnail(mContext
                        , mCusor.getString(mCusor.getColumnIndex(AlbumCursorLoader.ALBUM_IMAGE_DATA))
                        , holder.ivPhoto);
                holder.rbChoose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentPosition = position;
                        mAlbumId = albumId;
                        notifyDataSetChanged();
                        mHandler.postDelayed(mDismissRunnable, 300);

                    }
                });
                if (currentPosition == position) {
                    holder.rbChoose.setChecked(true);
                } else {
                    holder.rbChoose.setChecked(false);
                }
            }
        }

    }

    private class DismissRunnable implements Runnable {

        @Override
        public void run() {
            dismiss();
        }
    }

}
