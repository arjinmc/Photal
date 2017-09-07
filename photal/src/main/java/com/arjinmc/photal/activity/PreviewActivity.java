package com.arjinmc.photal.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.arjinmc.photal.R;
import com.arjinmc.photal.config.Constant;
import com.arjinmc.photal.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private ViewPager mVPImages;
    private ImagePageAdapter mImageAdapter;

    private String mCurrentAction;
    private Map<String, String> mChosenImagePathMap;
    private String[] mChosenImagePaths;
    private List<Fragment> mFragmentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photal_activity_preview);

        mBtnBack = (ImageButton) findViewById(R.id.btn_back);
        mBtnBack.setOnClickListener(this);

        mBtnSend = (Button) findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(this);
        mRlBottom = (RelativeLayout) findViewById(R.id.rl_bottom);
        mCbSelected = (CheckBox) findViewById(R.id.cb_check);
        mCbSelected.setChecked(true);
        mCbSelected.setOnClickListener(this);

        mVPImages = (ViewPager) findViewById(R.id.vp_image);

        mFragmentList = new ArrayList<>();

        if (getIntent().getAction() == null) mCurrentAction = Constant.ACTION_CHOOSE_MULTIPLE;
        else mCurrentAction = getIntent().getAction();
        if (mCurrentAction == Constant.ACTION_CHOOSE_MULTIPLE) {
            mChosenImagePaths = getIntent().getStringArrayExtra(Constant.BUNDLE_KEY);
            mRlBottom.setVisibility(View.VISIBLE);
            int lImageSize = mChosenImagePaths.length;
            if (lImageSize != 0) {
                mChosenImagePathMap = CommonUtil.toMap(mChosenImagePaths);
                for (int i = 0; i < lImageSize; i++) {
                    mFragmentList.add(ScaleImageFragment.newInstance(mChosenImagePaths[i]));
                }
            }
        } else {
            if (mChosenImagePaths != null)
                mFragmentList.add(ScaleImageFragment.newInstance(mChosenImagePaths[0]));
            mRlBottom.setVisibility(View.GONE);
        }

        mImageAdapter = new ImagePageAdapter(getSupportFragmentManager());
        mVPImages.setAdapter(mImageAdapter);
        mVPImages.addOnPageChangeListener(new ViewPagerChangeListener());

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_back) {
            finish();
        } else if (id == R.id.btn_send) {

        } else if (id == R.id.cb_check) {
            String key = mChosenImagePaths[mVPImages.getCurrentItem()];
            if (mChosenImagePathMap.containsKey(key)) {
                mChosenImagePathMap.remove(key);
            } else {
                mChosenImagePathMap.put(key, key);
            }
        }
    }

    private class ImagePageAdapter extends FragmentPagerAdapter {

        public ImagePageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

    private class ViewPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (mChosenImagePathMap.containsKey(mChosenImagePaths[position]))
                mCbSelected.setChecked(true);
            else
                mCbSelected.setChecked(false);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


}
