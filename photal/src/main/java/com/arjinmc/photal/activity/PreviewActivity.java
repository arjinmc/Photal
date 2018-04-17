package com.arjinmc.photal.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.arjinmc.expandrecyclerview.adapter.RecyclerViewAdapter;
import com.arjinmc.expandrecyclerview.adapter.RecyclerViewSingleTypeProcessor;
import com.arjinmc.expandrecyclerview.adapter.RecyclerViewViewHolder;
import com.arjinmc.expandrecyclerview.style.RecyclerViewStyleHelper;
import com.arjinmc.photal.R;
import com.arjinmc.photal.config.Constant;
import com.arjinmc.photal.util.CommonUtil;
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
    private String[] mAllImagePaths;

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
        mCbSelected.setChecked(true);
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
            mAllImagePaths = getIntent().getStringArrayExtra(Constant.BUNDLE_KEY_ALL);
            mRlBottom.setVisibility(View.VISIBLE);
            if (mChosenImagePaths != null && mChosenImagePaths.length != 0) {
                mChosenImagePathMap = CommonUtil.toArrayMap(mChosenImagePaths);
            }
        } else {
            mRlBottom.setVisibility(View.GONE);
        }

        mImageAdapter = new RecyclerViewAdapter<>(this, Arrays.asList(mAllImagePaths)
                , R.layout.photal_item_scale_image
                , new RecyclerViewSingleTypeProcessor<String>() {
            @Override
            public void onBindViewHolder(RecyclerViewViewHolder holder, int position, String uri) {

                PhotoView photoView = holder.getView(R.id.pv_image);
                photoView.setZoomable(true);
                photoView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                photoView.setImageURI(Uri.parse(uri));

                if (mChosenImagePathMap.containsKey(mAllImagePaths[position])) {
                    mCbSelected.setChecked(true);
                } else {
                    mCbSelected.setChecked(false);
                }
            }
        });
        mRvImages.setAdapter(mImageAdapter);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_back) {
            finish();
        } else if (id == R.id.btn_send) {

        } else if (id == R.id.cb_check) {
            String key = mChosenImagePaths[
                    ((LinearLayoutManager) mRvImages.getLayoutManager()).findFirstCompletelyVisibleItemPosition()];
            if (mChosenImagePathMap.containsKey(key)) {
                mChosenImagePathMap.remove(key);
            } else {
                mChosenImagePathMap.put(key, key);
            }
        }
    }

}
