package com.arjinmc.photal.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
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
import com.arjinmc.photal.util.ImageLoader;
import com.arjinmc.photal.viewholder.PhotoViewHolder;
import com.arjinmc.photal.widget.PhotoAlbumPopupWindow;
import com.arjinmc.photal.widget.RecyclerViewItemDecoration;
import com.arjinmc.photal.widget.SelectBox;

import java.util.HashMap;
import java.util.Map;

/**
 * photo selector
 * Created by Eminem Lu on 11/5/17.
 * Email arjinmc@hotmail.com
 */

public class PhotoSelectorActivity extends FragmentActivity implements View.OnClickListener {

    private ImageButton mbtnBack;
    private PhotoAlbumPopupWindow mPopAlbum;
    private RecyclerView mRvPhoto;
    private PhotoGridSelctorAdapter mPhotoAdapter;
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

        mbtnBack = (ImageButton) findViewById(R.id.btn_back);
        mbtnBack.setOnClickListener(this);

        mBtnSend = (Button) findViewById(R.id.btn_send);
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
        mTvAlbum = (TextView) findViewById(R.id.tv_album);
        mTvAlbum.setOnClickListener(this);

        mTvPreview = (TextView) findViewById(R.id.tv_preview);
        mTvPreview.setOnClickListener(this);

        mRvPhoto = (RecyclerView) findViewById(R.id.rv_photo);
        mRvPhoto.setLayoutManager(new GridLayoutManager(this, 3));
        mRvPhoto.addItemDecoration(new RecyclerViewItemDecoration.Builder(this)
                .mode(RecyclerViewItemDecoration.MODE_GRID)
                .color(ContextCompat.getColor(this, R.color.photal_black))
                .thickness(2).create());
        mPhotoAdapter = new PhotoGridSelctorAdapter();
        mRvPhoto.setAdapter(mPhotoAdapter);

        mPhotoLoader = new PhotoLoader(this, new PhotalLoaderCallback() {
            @Override
            public void onLoadFinished(Cursor cursor) {
                mPhotoAdapter.setCursor(cursor);
                mPhotoAdapter.notifyDataSetChanged();
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
            Intent previewIntent = new Intent(PhotoSelectorActivity.this, PreviewActivity.class);
            previewIntent.setAction(mCurrentAction);
            previewIntent.putExtra(Constant.BUNDLE_KEY, mPhotoAdapter.getChosenImagePaths());
            startActivity(previewIntent);
        }

    }

    /**
     * dispatch images back to preview
     */
    private void dispatchImages() {
        Intent intent = new Intent();
        intent.putExtra(Constant.BUNDLE_KEY, mPhotoAdapter.getChosenImagePaths());
        setResult(Config.SELECTOR_RESULT_CODE, intent);
        finish();
    }

    /**
     * update button send status
     */
    private void updateBtnSend() {
        if (mPhotoAdapter.getChosenImagePaths().length != 0) {
            if (!mBtnSend.isEnabled()) mBtnSend.setEnabled(true);
            mBtnSend.setText(String.format(getString(R.string.photal_send_number)
                    , mPhotoAdapter.getChosenImagePaths().length, Constant.getMaxChoosePhotoCount()));
        } else {
            mBtnSend.setEnabled(false);
            mBtnSend.setText(getString(R.string.photal_send));
        }

    }

    private class PhotoGridSelctorAdapter extends RecyclerViewCursorAdapter<PhotoViewHolder> {

        private Map<String, String> chosenImagesPaths;

        public PhotoGridSelctorAdapter() {
            chosenImagesPaths = new HashMap<>();
        }

        @Override
        public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PhotoViewHolder(LayoutInflater.from(getBaseContext())
                    .inflate(R.layout.photal_item_photo_grid_photo, parent, false));
        }

        @Override
        public void onBindViewHolder(final PhotoViewHolder holder, int position) {
            if (mCusor != null) {
                mCusor.moveToPosition(position);
                final String dataPath = mCusor.getString(mCusor.getColumnIndex(PhotoCursorLoader.PHOTO_DATA));
                ImageLoader.loadThumbnail(getBaseContext()
                        , dataPath, holder.ivPhoto);
                if (mCurrentAction.equals(Constant.ACTION_CHOOSE_MULTIPLE)) {
                    if (chosenImagesPaths.containsKey(dataPath))
                        holder.sbCheck.setChecked(true);
                    else holder.sbCheck.setChecked(false);
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
                                    chosenImagesPaths.put(dataPath, dataPath);
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
                            chosenImagesPaths.put(dataPath, dataPath);
                            dispatchImages();
                        }
                    });
                }
            }
        }

        public String[] getChosenImagePaths() {
            String[] paths = new String[]{};
            if (chosenImagesPaths.size() != 0) {
                return chosenImagesPaths.keySet().toArray(paths);
            }
            return paths;
        }

        public void setChoosenImagePaths(String[] paths){
            if(paths!=null && paths.length!=0){
                chosenImagesPaths.clear();
                for(int i=0;i<paths.length;i++){
                    chosenImagesPaths.put(paths[i],paths[i]);
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
