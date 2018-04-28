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
import com.arjinmc.photal.widget.CropMaskView;
import com.arjinmc.photal.widget.CropImageView;

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

    private CropImageView mCvImage;
    private CropMaskView mCgView;
    private File mOriginalFile;

    private String mResultKey;
    private int mResultCode;
    private String mOriginalFilePath;

    private float mTranslationX, mTranslationY;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photal_activity_crop_image);

        mBtnBack = findViewById(R.id.btn_back);
        mBtnBack.setOnClickListener(this);
        mTvTitle = findViewById(R.id.tv_head_title);
        mBtnDone = findViewById(R.id.btn_done);
        mBtnDone.setOnClickListener(this);

        mCvImage = findViewById(R.id.pv_image);
        mCgView = findViewById(R.id.iv_crop);

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

        mCgView.setBorder(Color.YELLOW, 2);
        mCgView.setShape(CropMaskView.SHAPE_CIRCLE);

        mOriginalFile = new File(mOriginalFilePath);
        ImageLoader.load(this, mOriginalFilePath, mCvImage);
        mCvImage.setShapePoint(mCgView.getShapePosition());

    }

    @Override
    public void onClick(View view) {

        if (R.id.btn_back == view.getId()) {
            finish();
        } else if (R.id.btn_done == view.getId()) {

        }
    }
}
