package com.arjinmc.photal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.arjinmc.expandrecyclerview.adapter.RecyclerViewAdapter;
import com.arjinmc.expandrecyclerview.adapter.RecyclerViewSingleTypeProcessor;
import com.arjinmc.expandrecyclerview.adapter.RecyclerViewViewHolder;
import com.arjinmc.expandrecyclerview.style.RecyclerViewStyleHelper;
import com.arjinmc.photal.R;
import com.arjinmc.photal.config.Config;
import com.arjinmc.photal.config.Constant;
import com.arjinmc.photal.util.ImageLoader;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.Arrays;

/**
 * preview Activity with edit mode
 * Created by Eminem Lo on 11/5/17.
 * Email arjinmc@hotmail.com
 */

public class PreviewActivity extends FragmentActivity implements View.OnClickListener {

    private ImageButton mBtnBack;
    private Button mBtnSend;
    private RelativeLayout mRlBottom;
    private CheckBox mCbSelected;
    private RecyclerView mRvImages;
    private RecyclerViewAdapter mImageAdapter;

    private String mCurrentAction;
    private ArrayMap<String, String> mChosenImagePathMap;
    private String[] mChosenImagePaths;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photal_activity_preview);

        mBtnBack = findViewById(R.id.btn_back);
        mBtnBack.setOnClickListener(this);

        mBtnSend = findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(this);
        mRlBottom = findViewById(R.id.rl_bottom);
        mCbSelected = findViewById(R.id.cb_check);
        mCbSelected.setOnClickListener(this);

        mRvImages = findViewById(R.id.rv_image);
        RecyclerViewStyleHelper.toViewPager(mRvImages, LinearLayoutManager.HORIZONTAL);

        if (getIntent().getAction() == null) {
            mCurrentAction = Constant.ACTION_CHOOSE_MULTIPLE;
        } else {
            mCurrentAction = getIntent().getAction();
        }

        if (mCurrentAction == Constant.ACTION_CHOOSE_MULTIPLE) {
            mChosenImagePaths = getIntent().getStringArrayExtra(Constant.BUNDLE_KEY_SELECTED);

            if (mChosenImagePaths == null || mChosenImagePaths.length == 0) {
                return;
            }
            mRlBottom.setVisibility(View.VISIBLE);

            if (mChosenImagePaths != null && mChosenImagePaths.length != 0) {
                int chosenSize = mChosenImagePaths.length;
                mChosenImagePathMap = new ArrayMap<>(chosenSize);
                for (int i = 0; i < chosenSize; i++) {
                    mChosenImagePathMap.put(mChosenImagePaths[i], mChosenImagePaths[i]);
                }
            } else {
                mChosenImagePathMap = new ArrayMap<>();
            }
        } else {
            mRlBottom.setVisibility(View.GONE);
        }

        mImageAdapter = new RecyclerViewAdapter<>(this, Arrays.asList(mChosenImagePaths)
                , R.layout.photal_item_scale_image
                , new RecyclerViewSingleTypeProcessor<String>() {
            @Override
            public void onBindViewHolder(RecyclerViewViewHolder holder, final int position, String uri) {

                PhotoView photoView = holder.getView(R.id.pv_image);
                photoView.setZoomable(true);
                ImageLoader.load(PreviewActivity.this, uri, photoView);

                if (mChosenImagePathMap != null && !mChosenImagePathMap.isEmpty()
                        && mChosenImagePathMap.containsKey(mChosenImagePaths[position])) {
                    mCbSelected.setChecked(true);
                } else {
                    mCbSelected.setChecked(false);
                }

            }
        });
        mRvImages.setAdapter(mImageAdapter);
        mRvImages.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mChosenImagePathMap != null && !mChosenImagePathMap.isEmpty()
                            && getCurrentPosition() != -1
                            && mChosenImagePathMap.containsKey(mChosenImagePaths[getCurrentPosition()])) {
                        mCbSelected.setChecked(true);
                    } else {
                        mCbSelected.setChecked(false);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        updateBtnSend();

        mCbSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getCurrentPosition();
                boolean checked = mChosenImagePathMap.containsKey(mChosenImagePaths[position]);
                if (mChosenImagePathMap.size() >= Constant.getMaxChoosePhotoCount() && checked) {
                    mCbSelected.setChecked(!checked);
                    Toast.makeText(getBaseContext()
                            , String.format(getString(R.string.photal_chosen_max)
                                    , Constant.getMaxChoosePhotoCount()), Toast.LENGTH_SHORT).show();
                } else {
                    if (checked) {
                        mChosenImagePathMap.remove(mChosenImagePaths[position]);
                    } else {
                        mChosenImagePathMap.put(mChosenImagePaths[position], mChosenImagePaths[position]);
                    }
                }
                updateBtnSend();
            }
        });

    }

    /**
     * update button send status
     */
    private void updateBtnSend() {
        if (mChosenImagePathMap != null && !mChosenImagePathMap.isEmpty()) {
            if (!mBtnSend.isEnabled()) mBtnSend.setEnabled(true);
            mBtnSend.setText(String.format(getString(R.string.photal_send_number)
                    , mChosenImagePathMap.size(), Constant.getMaxChoosePhotoCount()));
        } else {
            mBtnSend.setEnabled(false);
            mBtnSend.setText(getString(R.string.photal_send));
        }

    }

    /**
     * get current view position
     *
     * @return
     */
    private int getCurrentPosition() {
        return ((LinearLayoutManager) mRvImages.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }

    /**
     * dispatch images back to preview
     */
    private void dispatchImages(int resultCode) {
        Intent intent = new Intent();

        String[] selectedPosition = null;
        if (mChosenImagePathMap != null && !mChosenImagePathMap.isEmpty()) {
            int selectedSize = mChosenImagePathMap.size();
            selectedPosition = new String[selectedSize];
            for (int i = 0; i < selectedSize; i++) {
                selectedPosition[i] = mChosenImagePathMap.keyAt(i);
            }
        }
        intent.putExtra(Constant.BUNDLE_KEY_SELECTED, selectedPosition);
        setResult(resultCode, intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_back) {
            dispatchImages(Config.SELECTOR_PREVIEW_RESULT_CODE);
        } else if (id == R.id.btn_send) {
            dispatchImages(Config.SELECTOR_RESULT_CODE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dispatchImages(Config.SELECTOR_PREVIEW_RESULT_CODE);
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

}
