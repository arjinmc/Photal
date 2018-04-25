package com.arjinmc.photal.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.arjinmc.photal.Photal;
import com.arjinmc.photal.R;
import com.arjinmc.photal.config.Constant;
import com.arjinmc.photal.config.PhotalConfig;
import com.arjinmc.photal.exception.ConfigException;
import com.arjinmc.photal.util.ImageLoader;
import com.arjinmc.photal.widget.CropGuideView;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;

/**
 * Crop Image
 * Created by Eminem Lo on 2018/4/18.
 * email: arjinmc@hotmail.com
 */
public class CropImageActivity extends FragmentActivity implements View.OnClickListener {

    private ImageButton mBtnBack;
    private TextView mTvTitle;
    private Button mBtnDone;

    private PhotoView mPvPhoto;
    private CropGuideView mCvView;
    private File mOriginalFile;

    private String mResultKey;
    private int mResultCode;
    private String mOriginalFilePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photal_activity_crop_image);

        mBtnBack = findViewById(R.id.btn_back);
        mBtnBack.setOnClickListener(this);
        mTvTitle = findViewById(R.id.tv_head_title);
        mBtnDone = findViewById(R.id.btn_done);
        mBtnDone.setOnClickListener(this);

        mPvPhoto = findViewById(R.id.pv_image);
        mCvView = findViewById(R.id.iv_crop);

        mResultKey = getIntent().getStringExtra(Constant.BUNDLE_KEY_RESULT_KEY);
        mResultCode = getIntent().getIntExtra(Constant.BUNDLE_KEY_RESULT_CODE, 0);
        mOriginalFilePath = getIntent().getStringExtra(Constant.BUNDLE_KEY_ORIGINAL_FILE_PATH);

        initConfig();
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

        mCvView.setBorder(Color.YELLOW,2);
        mCvView.setShape(CropGuideView.SHAPE_CIRCLE);

        mOriginalFile = new File(mOriginalFilePath);
        ImageLoader.load(this, mOriginalFilePath, mPvPhoto);
    }

    @Override
    public void onClick(View view) {

        if (R.id.btn_back == view.getId()) {
            finish();
        } else if (R.id.btn_done == view.getId()) {

        }
    }
}
