package com.arjinmc.photal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arjinmc.expandrecyclerview.adapter.RecyclerViewAdapter;
import com.arjinmc.expandrecyclerview.adapter.RecyclerViewSingleTypeProcessor;
import com.arjinmc.expandrecyclerview.adapter.RecyclerViewViewHolder;
import com.arjinmc.expandrecyclerview.style.RecyclerViewStyleHelper;
import com.arjinmc.photal.Photal;
import com.arjinmc.photal.R;
import com.arjinmc.photal.config.Constant;
import com.arjinmc.photal.config.PhotalConfig;
import com.arjinmc.photal.exception.ConfigException;
import com.arjinmc.photal.model.MediaFileItem;
import com.arjinmc.photal.util.CommonUtil;
import com.arjinmc.photal.util.ImageLoader;
import com.arjinmc.photal.widget.PressSelectorDrawable;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

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
    private ArrayList<MediaFileItem> mChosenImagePaths;
    private ArrayList<MediaFileItem> mOriginalChosenImagePaths;
    private int mMaxCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photal_activity_preview);

        CommonUtil.setStatusBarColor(this, ContextCompat.getColor(this, R.color.photal_theme_dark));

        mBtnBack = findViewById(R.id.btn_back);
        ViewCompat.setBackground(mBtnBack
                , new PressSelectorDrawable(
                        ContextCompat.getColor(this, R.color.photal_theme)
                        , ContextCompat.getColor(this, R.color.photal_theme_dark)));
        mBtnBack.setOnClickListener(this);

        mBtnSend = findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(this);
        mRlBottom = findViewById(R.id.rl_bottom);
        mCbSelected = findViewById(R.id.cb_check);
        mCbSelected.setOnClickListener(this);

        mRvImages = findViewById(R.id.rv_image);

        initConfig();

        RecyclerViewStyleHelper.toViewPager(mRvImages, RecyclerView.HORIZONTAL);

        if (getIntent().getAction() == null) {
            mCurrentAction = Constant.ACTION_CHOOSE_MULTIPLE;
        } else {
            mCurrentAction = getIntent().getAction();
        }

        if (mCurrentAction == Constant.ACTION_CHOOSE_MULTIPLE) {
            mOriginalChosenImagePaths = getIntent().getParcelableArrayListExtra(Constant.BUNDLE_KEY_SELECTED);
            mMaxCount = getIntent().getIntExtra(Constant.BUNDLE_KEY_MAX_COUNT, 1);

            if (mOriginalChosenImagePaths == null || mOriginalChosenImagePaths.isEmpty()) {
                return;
            }
            mRlBottom.setVisibility(View.VISIBLE);

            if (mOriginalChosenImagePaths != null && !mOriginalChosenImagePaths.isEmpty()) {
                int chosenSize = mOriginalChosenImagePaths.size();
                mChosenImagePaths = new ArrayList<>(chosenSize);
                for (int i = 0; i < chosenSize; i++) {
                    mChosenImagePaths.add(mOriginalChosenImagePaths.get(i));
                }
            } else {
                mChosenImagePaths = new ArrayList<>();
            }
        } else {
            mRlBottom.setVisibility(View.GONE);
        }

        mImageAdapter = new RecyclerViewAdapter<>(this, mOriginalChosenImagePaths
                , R.layout.photal_item_scale_image
                , new RecyclerViewSingleTypeProcessor<MediaFileItem>() {
            @Override
            public void onBindViewHolder(RecyclerViewViewHolder holder, final int position, MediaFileItem mediaFileItem) {

                PhotoView photoView = holder.getView(R.id.pv_image);
                photoView.setZoomable(true);
                ImageLoader.load(PreviewActivity.this, CommonUtil.getImagePath(mediaFileItem), photoView);

                if (mChosenImagePaths != null && !mChosenImagePaths.isEmpty()
                        && mChosenImagePaths.contains(mOriginalChosenImagePaths.get(position))) {
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
                    if (mChosenImagePaths != null && !mChosenImagePaths.isEmpty()
                            && getCurrentPosition() != -1
                            && mChosenImagePaths.contains(mOriginalChosenImagePaths.get(getCurrentPosition()))) {
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
                boolean checked = mChosenImagePaths.contains(mOriginalChosenImagePaths.get(position));

                if (checked) {
                    mChosenImagePaths.remove(mOriginalChosenImagePaths.get(position));
                } else {
                    mChosenImagePaths.add(mOriginalChosenImagePaths.get(position));
                }

                updateBtnSend();
            }
        });

    }

    private void initConfig() {

        PhotalConfig photalConfig = Photal.getInstance().getConfig();
        if (photalConfig == null) {
            try {
                throw new ConfigException();
            } catch (ConfigException e) {
                e.printStackTrace();
                return;
            }
        }

        RelativeLayout rlHead = findViewById(R.id.rl_head);
        rlHead.setBackgroundColor(photalConfig.getThemeColor());
        RelativeLayout rlBottom = findViewById(R.id.rl_bottom);
        rlBottom.setBackgroundColor(photalConfig.getThemeColor());
        ViewCompat.setBackground(mBtnBack
                , new PressSelectorDrawable(photalConfig.getThemeColor(), photalConfig.getThemeDarkColor()));
        TextView tvHeadTitle = findViewById(R.id.tv_head_title);
        tvHeadTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, photalConfig.getTextTitleSize());
        tvHeadTitle.setTextColor(photalConfig.getTextTitleColor());
        mBtnBack.setImageResource(photalConfig.getBtnBackIcon());
        mBtnBack.setPadding(photalConfig.getBtnBackIconPaddingLeft(),photalConfig.getBtnBackIconPaddingTop()
                ,photalConfig.getBtnBackIconPaddingRight(),photalConfig.getBtnBackIconPaddingBottom());
        mBtnSend.setBackgroundResource(photalConfig.getBtnDoneBackground());
        mBtnSend.setTextColor(photalConfig.getBtnDoneTextColor());
        mBtnSend.setTextSize(TypedValue.COMPLEX_UNIT_PX, photalConfig.getBtnDoneTextSize());

        if (photalConfig.getPreviewCheckbox() != -1) {
            mCbSelected.setButtonDrawable(photalConfig.getPreviewCheckbox());
        }
        CommonUtil.setStatusBarColor(this, photalConfig.getThemeDarkColor());
    }

    /**
     * update button send status
     */
    private void updateBtnSend() {
        if (mChosenImagePaths != null && !mChosenImagePaths.isEmpty()) {
            if (!mBtnSend.isEnabled()) mBtnSend.setEnabled(true);
            mBtnSend.setText(String.format(getString(R.string.photal_send_number)
                    , mChosenImagePaths.size(), mMaxCount));
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
     * dispatch images back to result
     */
    private void dispatchImages(int resultCode) {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(Constant.BUNDLE_KEY_SELECTED, mChosenImagePaths);
        setResult(resultCode, intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_back) {
            dispatchImages(Constant.SELECTOR_PREVIEW_RESULT_CODE);
        } else if (id == R.id.btn_send) {
            dispatchImages(Constant.SELECTOR_RESULT_CODE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dispatchImages(Constant.SELECTOR_PREVIEW_RESULT_CODE);
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

}
