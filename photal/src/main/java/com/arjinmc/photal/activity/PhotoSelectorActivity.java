package com.arjinmc.photal.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.arjinmc.photal.R;
import com.arjinmc.photal.adapter.RecyclerViewCursorAdapter;
import com.arjinmc.photal.callback.PhotalLoaderCallback;
import com.arjinmc.photal.callback.PhotoLoaderCallback;
import com.arjinmc.photal.config.Config;
import com.arjinmc.photal.config.Constant;
import com.arjinmc.photal.loader.PhotoCursorLoader;
import com.arjinmc.photal.util.CommonUtil;
import com.arjinmc.photal.util.ImageLoader;
import com.arjinmc.photal.viewholder.PhotoViewHolder;
import com.arjinmc.photal.widget.PhotoAlbumPopupWindow;
import com.arjinmc.photal.widget.SelectBox;
import com.arjinmc.recyclerviewdecoration.RecyclerViewItemDecoration;

import java.util.ArrayList;

/**
 * photo selector
 * Created by Eminem Lo on 11/5/17.
 * Email arjinmc@hotmail.com
 */

public class PhotoSelectorActivity extends FragmentActivity implements View.OnClickListener {

    private ImageButton mbtnBack;
    private PhotoAlbumPopupWindow mPopAlbum;
    private RecyclerView mRvPhoto;
    private PhotoGridSelectorAdapter mPhotoAdapter;
    private ArrayMap<String, String> mPhotoList;
    private PhotoLoaderCallback mPhotoLoaderCallback;
    private TextView mTvAlbum;
    private TextView mTvPreview;
    private Button mBtnSend;

    private int mCurrentAlbumId = -1;
    private String mCurrentAction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photal_activity_photo_grid_selector);

        mbtnBack = findViewById(R.id.btn_back);
        mbtnBack.setOnClickListener(this);

        mBtnSend = findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(this);
        mBtnSend.setEnabled(false);

        mPopAlbum = new PhotoAlbumPopupWindow(this);
        mPopAlbum.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                int albumId = mPopAlbum.getChosenAlbumId();
                if (mCurrentAlbumId != albumId) {
                    mCurrentAlbumId = albumId;
                    mPhotoLoaderCallback.destroyLoader();
                    mPhotoLoaderCallback.load(albumId);
                }
            }
        });
        mTvAlbum = findViewById(R.id.tv_album);
        mTvAlbum.setOnClickListener(this);

        mTvPreview = findViewById(R.id.tv_preview);
        mTvPreview.setOnClickListener(this);

        mRvPhoto = findViewById(R.id.rv_photo);
        mRvPhoto.setLayoutManager(new GridLayoutManager(this, 3));
        mRvPhoto.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
                .color(CommonUtil.getColor(this, R.color.photal_black))
                .thickness(2).create());
        mPhotoAdapter = new PhotoGridSelectorAdapter();
        mRvPhoto.setAdapter(mPhotoAdapter);

        mPhotoLoaderCallback = new PhotoLoaderCallback(this, new PhotalLoaderCallback() {
            @Override
            public void onLoadFinished(Cursor cursor) {
                mPhotoAdapter.setCursor(cursor);
                mPhotoAdapter.notifyDataSetChanged();
                if (cursor != null && cursor.getCount() != 0) {
                    int cursorCount = cursor.getCount();
                    mPhotoList = new ArrayMap<>(cursorCount);
                    for (int i = 0; i < cursorCount; i++) {
                        cursor.moveToPosition(i);
                        String key = cursor.getString(cursor.getColumnIndex(PhotoCursorLoader.PHOTO_DATA));
                        mPhotoList.put(key, key);
                    }
                }

            }

            @Override
            public void onLoaderReset() {
                mPhotoAdapter.setCursor(null);
            }
        });

        mCurrentAction = getIntent().getAction();
        if (mCurrentAction == null) mCurrentAction = Constant.ACTION_CHOOSE_MULTIPLE;
        if (mCurrentAction.equals(Constant.ACTION_CHOOSE_SINGLE)) {
            mBtnSend.setVisibility(View.GONE);
            mTvPreview.setVisibility(View.GONE);
        }
        mPhotoLoaderCallback.load(null);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_album) {
            if (!mPopAlbum.isShowing()) {
                mPopAlbum.show(findViewById(R.id.rl_bottom));
            } else {
                mPopAlbum.dismiss();
            }
        } else if (i == R.id.btn_back) {
            if (mPopAlbum != null && mPopAlbum.isShowing()) {
                mPopAlbum.dismiss();
            } else {
                finish();
            }
        } else if (i == R.id.btn_send) {
            dispatchImages();
        } else if (i == R.id.tv_preview) {
            Intent previewIntent = new Intent(PhotoSelectorActivity.this, PreviewActivity.class);
            previewIntent.setAction(mCurrentAction);
            previewIntent.putIntegerArrayListExtra(Constant.BUNDLE_KEY_SELECTED, mPhotoAdapter.getChosenImagePosition());
            previewIntent.putExtra(Constant.BUNDLE_KEY_ALL, CommonUtil.toStrings(mPhotoList));
            startActivity(previewIntent);
        }

    }

    /**
     * dispatch images back to preview
     */
    private void dispatchImages() {
        Intent intent = new Intent();

        String[] selectedPath = null;
        if (mPhotoAdapter.getChosenImagePosition() != null && mPhotoAdapter.getChosenImagePosition().size() != 0) {
            int selectedSize = mPhotoAdapter.getChosenImagePosition().size();
            selectedPath = new String[selectedSize];
            for (int i = 0; i < selectedSize; i++) {
                selectedPath[i] = mPhotoList.get(mPhotoList.keyAt(mPhotoAdapter.getChosenImagePosition().get(i)));
            }
        }
        intent.putExtra(Constant.BUNDLE_KEY_SELECTED, selectedPath);
        setResult(Config.SELECTOR_RESULT_CODE, intent);
        finish();
    }

    /**
     * update button send status
     */
    private void updateBtnSend() {
        if (mPhotoAdapter.getChosenImagePosition().size() != 0) {
            if (!mBtnSend.isEnabled()) mBtnSend.setEnabled(true);
            mBtnSend.setText(String.format(getString(R.string.photal_send_number)
                    , mPhotoAdapter.getChosenImagePosition().size(), Constant.getMaxChoosePhotoCount()));
        } else {
            mBtnSend.setEnabled(false);
            mBtnSend.setText(getString(R.string.photal_send));
        }

    }

    private class PhotoGridSelectorAdapter extends RecyclerViewCursorAdapter<PhotoViewHolder> {

        private ArrayMap<Integer, String> chosenImagesPaths;

        public PhotoGridSelectorAdapter() {
            chosenImagesPaths = new ArrayMap<>();
        }

        @Override
        public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new PhotoViewHolder(LayoutInflater.from(getBaseContext())
                    .inflate(R.layout.photal_item_photo_grid_photo, parent, false));
        }

        @Override
        public void onBindViewHolder(final PhotoViewHolder holder, final int position) {
            final String dataPath = mPhotoList.get(mPhotoList.keyAt(position));
            ImageLoader.loadThumbnail(getBaseContext()
                    , dataPath, holder.ivPhoto);
            if (mCurrentAction.equals(Constant.ACTION_CHOOSE_MULTIPLE)) {
                if (chosenImagesPaths.containsKey(position)) {
                    holder.sbCheck.setChecked(true);
                } else {
                    holder.sbCheck.setChecked(false);
                }
                holder.sbCheck.setOnCheckChangeListener(new SelectBox.OnCheckChangeListener() {
                    @Override
                    public void onChange(boolean change) {
                        if (chosenImagesPaths.size() >= Constant.getMaxChoosePhotoCount() && change) {
                            holder.sbCheck.setChecked(!change);
                            Toast.makeText(getBaseContext()
                                    , String.format(getString(R.string.photal_chosen_max)
                                            , Constant.getMaxChoosePhotoCount()), Toast.LENGTH_SHORT).show();
                        } else {
                            if (change) {
                                chosenImagesPaths.put(position, dataPath);
                            } else {
                                chosenImagesPaths.remove(position);
                            }
                        }
                        updateBtnSend();
                    }
                });
            } else {
                holder.sbCheck.setVisibility(View.GONE);
                holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chosenImagesPaths.put(position, dataPath);
                        dispatchImages();
                    }
                });
            }
        }

        public ArrayList<Integer> getChosenImagePosition() {
            if (chosenImagesPaths != null && chosenImagesPaths.size() != 0) {
                ArrayList<Integer> paths = new ArrayList<>();
                int chosenSize = chosenImagesPaths.size();
                for (int i = 0; i < chosenSize; i++) {
                    paths.add(chosenImagesPaths.keyAt(i));
                }
                return paths;
            }
            return null;
        }

        public void setChoosenImagePaths(Integer[] paths) {
            if (paths != null && paths.length != 0) {
                chosenImagesPaths.clear();
                for (int i = 0; i < paths.length; i++) {
                    chosenImagesPaths.put(paths[i], mPhotoList.get(mPhotoList.keyAt(paths[i])));
                }
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && mPopAlbum != null && mPopAlbum.isShowing()) {
            mPopAlbum.dismiss();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

}
