package com.arjinmc.photal.activity;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arjinmc.photal.Photal;
import com.arjinmc.photal.R;
import com.arjinmc.photal.adapter.RecyclerViewCursorAdapter;
import com.arjinmc.photal.callback.PhotalLoaderCallback;
import com.arjinmc.photal.config.Constant;
import com.arjinmc.photal.config.PhotalConfig;
import com.arjinmc.photal.exception.ConfigException;
import com.arjinmc.photal.loader.PhotoCursorLoader;
import com.arjinmc.photal.loader.PhotoLoader;
import com.arjinmc.photal.model.MediaFileItem;
import com.arjinmc.photal.util.CommonUtil;
import com.arjinmc.photal.util.ImageLoader;
import com.arjinmc.photal.util.ToastUtil;
import com.arjinmc.photal.viewholder.PhotoViewHolder;
import com.arjinmc.photal.widget.PhotoAlbumPopupWindow;
import com.arjinmc.photal.widget.PressSelectorDrawable;
import com.arjinmc.photal.widget.SelectBox;
import com.arjinmc.recyclerviewdecoration.RecyclerViewLinearItemDecoration;

import java.util.ArrayList;

/**
 * photo selector
 * Created by Eminem Lo on 11/5/17.
 * Email arjinmc@hotmail.com
 */

public class PhotoSelectorActivity extends FragmentActivity implements View.OnClickListener {

    private ImageButton mBtnBack;
    private PhotoAlbumPopupWindow mPopAlbum;
    private RecyclerView mRvPhoto;
    private PhotoGridSelectorAdapter mPhotoAdapter;
    private SparseArray<MediaFileItem> mPhotoList;
    private PhotoLoader mPhotoLoader;
    private TextView mTvAlbum;
    private TextView mTvPreview;
    private Button mBtnSend;

    private int mCurrentAlbumId = -1;
    private String mCurrentAction;

    private PhotalConfig mPhotalConfig;

    private String mResultKey;
    private int mMaxCount = 1;
    private int mResultCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photal_activity_photo_grid_selector);

        CommonUtil.setStatusBarColor(this, ContextCompat.getColor(this, R.color.photal_theme_dark));

        mBtnBack = findViewById(R.id.btn_back);
        ViewCompat.setBackground(mBtnBack
                , new PressSelectorDrawable(
                        ContextCompat.getColor(this, R.color.photal_theme)
                        , ContextCompat.getColor(this, R.color.photal_theme_dark)));
        mBtnBack.setOnClickListener(this);

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

        initConfig();

        mRvPhoto.setLayoutManager(new GridLayoutManager(this
                , mPhotalConfig == null ? 3 : mPhotalConfig.getGalleryColumnCount()));
        if (mPhotalConfig != null && mPhotalConfig.getGalleryDiver() != null) {
            mRvPhoto.addItemDecoration(mPhotalConfig.getGalleryDiver());
        } else {
            mRvPhoto.addItemDecoration(new RecyclerViewLinearItemDecoration.Builder(this)
                    .color(ContextCompat.getColor(this, R.color.photal_black))
                    .thickness(2).create());
        }
        mPhotoAdapter = new PhotoGridSelectorAdapter();
        mRvPhoto.setAdapter(mPhotoAdapter);

        mPhotoLoader = new PhotoLoader(this, new PhotalLoaderCallback() {
            @Override
            public void onLoadFinished(Cursor cursor) {
                if (cursor != null && cursor.getCount() != 0) {
                    int cursorCount = cursor.getCount();
                    mPhotoList = new SparseArray<>(cursorCount);
                    for (int i = 0; i < cursorCount; i++) {
                        cursor.moveToPosition(i);
                        MediaFileItem mediaFileItem = new MediaFileItem();

                        String key = cursor.getString(cursor.getColumnIndex(PhotoCursorLoader.PHOTO_DATA));
                        mediaFileItem.setPath(key);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            int idColumn = cursor.getColumnIndexOrThrow(PhotoCursorLoader.PHOTO_ID);
                            long id = cursor.getLong(idColumn);
                            Uri contentUri = ContentUris.withAppendedId(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
                            mediaFileItem.setUriPath(contentUri.toString());
                            //check if need access gps for original path
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
                                    && Photal.getInstance().getConfig().isAccessGPS()) {
                                contentUri = MediaStore.setRequireOriginal(contentUri);
                                mediaFileItem.setUriOriginalPath(contentUri.toString());
                            }
                        }
                        mediaFileItem.setDisplayName(cursor.getString(cursor.getColumnIndex(PhotoCursorLoader.PHOTO_DISPLAY_NAME)));
                        mPhotoList.put(i, mediaFileItem);
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
        } else {
            mMaxCount = getIntent().getIntExtra(Constant.BUNDLE_KEY_MAX_COUNT, 9);
        }
        mResultKey = getIntent().getStringExtra(Constant.BUNDLE_KEY_RESULT_KEY);
        mResultCode = getIntent().getIntExtra(Constant.BUNDLE_KEY_RESULT_CODE, 0);

        mPhotoLoader.load(null);

    }

    private void initConfig() {

        mPhotalConfig = Photal.getInstance().getConfig();
        if (mPhotalConfig == null) {
            try {
                throw new ConfigException();
            } catch (ConfigException e) {
                e.printStackTrace();
                return;
            }
        }

        RelativeLayout rlHead = findViewById(R.id.rl_head);
        rlHead.setBackgroundColor(mPhotalConfig.getThemeColor());
        RelativeLayout rlBottom = findViewById(R.id.rl_bottom);
        rlBottom.setBackgroundColor(mPhotalConfig.getThemeColor());
        ViewCompat.setBackground(mBtnBack
                , new PressSelectorDrawable(mPhotalConfig.getThemeColor(), mPhotalConfig.getThemeDarkColor()));
        TextView tvHeadTitle = findViewById(R.id.tv_head_title);
        tvHeadTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mPhotalConfig.getTextTitleSize());
        tvHeadTitle.setTextColor(mPhotalConfig.getTextTitleColor());
        mBtnBack.setImageResource(mPhotalConfig.getBtnBackIcon());
        mBtnSend.setBackgroundResource(mPhotalConfig.getBtnDoneBackground());
        mBtnSend.setTextColor(mPhotalConfig.getBtnDoneTextColor());
        mBtnSend.setTextSize(TypedValue.COMPLEX_UNIT_PX, mPhotalConfig.getBtnDoneTextSize());
        mRvPhoto.setBackgroundColor(mPhotalConfig.getGalleryBackgroundColor());
        mTvPreview.setTextColor(mPhotalConfig.getPreviewTextColor());
        mTvPreview.setTextSize(TypedValue.COMPLEX_UNIT_PX, mPhotalConfig.getPreviewTextSize());
        mTvAlbum.setTextColor(mPhotalConfig.getPreviewTextColor());
        mTvAlbum.setTextSize(TypedValue.COMPLEX_UNIT_PX, mPhotalConfig.getPreviewTextSize());

        CommonUtil.setStatusBarColor(this, mPhotalConfig.getThemeDarkColor());
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_album) {
            if (!mPopAlbum.isShowing()) {
                mPopAlbum.show(findViewById(R.id.rl_parent));
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
            if (mPhotoList == null || mPhotoList.size() == 0
                    || mPhotoAdapter.getChosenImagePosition() == null) {
                return;
            }
            Intent previewIntent = new Intent(PhotoSelectorActivity.this, PreviewActivity.class);
            previewIntent.setAction(mCurrentAction);
            previewIntent.putParcelableArrayListExtra(Constant.BUNDLE_KEY_SELECTED, mPhotoAdapter.getChosenImagePosition());
            previewIntent.putExtra(Constant.BUNDLE_KEY_MAX_COUNT, mMaxCount);

            startActivityForResult(previewIntent, Constant.SELECTOR_REQUEST_CODE);
        }

    }

    /**
     * dispatch images back to preview
     */
    private void dispatchImages() {
        Intent intent = new Intent();

        if (mPhotoAdapter.getChosenImagePosition() != null && mPhotoAdapter.getChosenImagePosition().size() != 0) {
            intent.putExtra(mResultKey, mPhotoAdapter.getChosenImagePosition());
        }
        setResult(mResultCode, intent);
        finish();
    }

    /**
     * update button send status
     */
    private void updateBtnSend() {
        if (mPhotoAdapter.getChosenImagePosition() != null
                && mPhotoAdapter.getChosenImagePosition().size() != 0) {
            if (!mBtnSend.isEnabled()) mBtnSend.setEnabled(true);
            mBtnSend.setText(String.format(getString(R.string.photal_send_number)
                    , mPhotoAdapter.getChosenImagePosition().size(), mMaxCount));
        } else {
            mBtnSend.setEnabled(false);
            mBtnSend.setText(getString(R.string.photal_send));
        }

    }

    private class PhotoGridSelectorAdapter extends RecyclerViewCursorAdapter<PhotoViewHolder> {

        public ArrayMap<String, MediaFileItem> chosenImagesPaths;

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
            final MediaFileItem mediaFileItem = mPhotoList.get(mPhotoList.keyAt(position));
            final String dataPath = mediaFileItem.getPath();
            ImageLoader.loadThumbnail(getBaseContext()
                    , dataPath, holder.ivPhoto);
            if (mCurrentAction.equals(Constant.ACTION_CHOOSE_MULTIPLE)) {
                if (mPhotalConfig != null) {
                    holder.sbCheck.setColor(mPhotalConfig.getGalleryCheckboxColor());
                }
                if (chosenImagesPaths.containsKey(mediaFileItem.getPath())) {
                    holder.sbCheck.setChecked(true);
                } else {
                    holder.sbCheck.setChecked(false);
                }
                holder.sbCheck.setOnCheckChangeListener(new SelectBox.OnCheckChangeListener() {
                    @Override
                    public void onChange(boolean change) {
                        if (chosenImagesPaths.size() >= mMaxCount && change) {
                            holder.sbCheck.setChecked(!change);
                            ToastUtil.show(getBaseContext()
                                    , String.format(getString(R.string.photal_chosen_max)
                                            , mMaxCount));
                        } else {
                            if (change) {
                                chosenImagesPaths.put(dataPath, mediaFileItem);
                            } else {
                                chosenImagesPaths.remove(dataPath);
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
                        chosenImagesPaths.put(dataPath, mediaFileItem);
                        dispatchImages();
                    }
                });
            }
        }

        public ArrayList<MediaFileItem> getChosenImagePosition() {
            if (chosenImagesPaths != null && chosenImagesPaths.size() != 0) {
                int chosenSize = chosenImagesPaths.size();
                ArrayList<MediaFileItem> chosenItems = new ArrayList<>(chosenSize);
                for (int i = 0; i < chosenSize; i++) {
                    chosenItems.add(chosenImagesPaths.get(chosenImagesPaths.keyAt(i)));
                }
                return chosenItems;
            }
            return null;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constant.SELECTOR_RESULT_CODE ||
                resultCode == Constant.SELECTOR_PREVIEW_RESULT_CODE) {
            ArrayList<MediaFileItem> newSelectedImages = data.getParcelableArrayListExtra(Constant.BUNDLE_KEY_SELECTED);
            mPhotoAdapter.chosenImagesPaths.clear();
            if (newSelectedImages != null && !newSelectedImages.isEmpty()) {
                int selectedSize = newSelectedImages.size();
                for (int i = 0; i < selectedSize; i++) {
                    mPhotoAdapter.chosenImagesPaths.put(newSelectedImages.get(i).getPath(), newSelectedImages.get(i));
                }
            }
            mPhotoAdapter.notifyDataSetChanged();
            updateBtnSend();
        }
        if (resultCode == Constant.SELECTOR_RESULT_CODE) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageLoader.clearMemory(this);
    }
}
