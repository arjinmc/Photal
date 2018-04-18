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
import com.arjinmc.photal.config.Config;
import com.arjinmc.photal.config.Constant;
import com.arjinmc.photal.loader.PhotoCursorLoader;
import com.arjinmc.photal.loader.PhotoLoader;
import com.arjinmc.photal.util.CommonUtil;
import com.arjinmc.photal.util.ImageLoader;
import com.arjinmc.photal.viewholder.PhotoViewHolder;
import com.arjinmc.photal.widget.PhotoAlbumPopupWindow;
import com.arjinmc.photal.widget.SelectBox;
import com.arjinmc.recyclerviewdecoration.RecyclerViewItemDecoration;

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
    private PhotoLoader mPhotoLoader;
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
                    mPhotoLoader.destroyLoader();
                    mPhotoLoader.load(albumId);
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

        mPhotoLoader = new PhotoLoader(this, new PhotalLoaderCallback() {
            @Override
            public void onLoadFinished(Cursor cursor) {
                if (cursor != null && cursor.getCount() != 0) {
                    int cursorCount = cursor.getCount();
                    mPhotoList = new ArrayMap<>(cursorCount);
                    for (int i = 0; i < cursorCount; i++) {
                        cursor.moveToPosition(i);
                        String key = cursor.getString(cursor.getColumnIndex(PhotoCursorLoader.PHOTO_DATA));
                        mPhotoList.put(key, key);
                    }
                    mPhotoAdapter.setCursor(cursor);
                    mPhotoAdapter.notifyDataSetChanged();
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
        mPhotoLoader.load(null);

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
            if (mPhotoList == null && mPhotoList.isEmpty()) {
                return;
            }
            Intent previewIntent = new Intent(PhotoSelectorActivity.this, PreviewActivity.class);
            previewIntent.setAction(mCurrentAction);
            previewIntent.putExtra(Constant.BUNDLE_KEY_SELECTED, mPhotoAdapter.getChosenImagePosition());
            previewIntent.putExtra(Constant.BUNDLE_KEY_ALL, CommonUtil.toStrings(mPhotoList));
            startActivityForResult(previewIntent, Config.SELECTOR_REQUEST_CODE);
        }

    }

    /**
     * dispatch images back to preview
     */
    private void dispatchImages() {
        Intent intent = new Intent();

        String[] selectedPath = null;
        if (mPhotoAdapter.getChosenImagePosition() != null && mPhotoAdapter.getChosenImagePosition().length != 0) {
            int selectedSize = mPhotoAdapter.getChosenImagePosition().length;
            selectedPath = new String[selectedSize];
            for (int i = 0; i < selectedSize; i++) {
                selectedPath[i] = mPhotoList.get(mPhotoList.keyAt(mPhotoAdapter.getChosenImagePosition()[i]));
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
        if (mPhotoAdapter.getChosenImagePosition() != null
                && mPhotoAdapter.getChosenImagePosition().length != 0) {
            if (!mBtnSend.isEnabled()) mBtnSend.setEnabled(true);
            mBtnSend.setText(String.format(getString(R.string.photal_send_number)
                    , mPhotoAdapter.getChosenImagePosition().length, Constant.getMaxChoosePhotoCount()));
        } else {
            mBtnSend.setEnabled(false);
            mBtnSend.setText(getString(R.string.photal_send));
        }

    }

    private class PhotoGridSelectorAdapter extends RecyclerViewCursorAdapter<PhotoViewHolder> {

        public ArrayMap<Integer, String> chosenImagesPaths;

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

        public int[] getChosenImagePosition() {
            if (chosenImagesPaths != null && chosenImagesPaths.size() != 0) {
                int chosenSize = chosenImagesPaths.size();
                int[] paths = new int[chosenSize];
                for (int i = 0; i < chosenSize; i++) {
                    paths[i] = chosenImagesPaths.keyAt(i);
                }
                return paths;
            }
            return null;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Config.SELECTOR_RESULT_CODE ||
                resultCode == Config.SELECTOR_PREVIEW_RESULT_CODE) {
            int[] newSeletedPosition = data.getIntArrayExtra(Constant.BUNDLE_KEY_SELECTED);
            mPhotoAdapter.chosenImagesPaths.clear();
            if (newSeletedPosition != null && newSeletedPosition.length != 0) {
                int selectedSize = newSeletedPosition.length;
                for (int i = 0; i < selectedSize; i++) {
                    mPhotoAdapter.chosenImagesPaths.put(newSeletedPosition[i], mPhotoList.get(newSeletedPosition[i]));
                }
            }
            mPhotoAdapter.notifyDataSetChanged();
        }
        if (resultCode == Config.SELECTOR_RESULT_CODE) {
            dispatchImages();
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
