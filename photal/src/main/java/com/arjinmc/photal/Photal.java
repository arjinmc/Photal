package com.arjinmc.photal;

import android.app.Activity;
import android.content.Intent;

import com.arjinmc.photal.activity.CropImageActivity;
import com.arjinmc.photal.activity.PhotoSelectorActivity;
import com.arjinmc.photal.config.Constant;
import com.arjinmc.photal.config.PhotalConfig;
import com.arjinmc.photal.exception.ConfigException;
import com.arjinmc.photal.util.CommonUtil;

import java.io.File;

/**
 * Photal入口
 * Created by Eminem Lo on 2018/4/19.
 * email: arjinmc@hotmail.com
 */
public final class Photal {


    private static Photal mPhotal;
    private PhotalConfig mConfig;

    public static Photal getInstance() {
        if (mPhotal == null) {
            mPhotal = new Photal();
        }
        return mPhotal;
    }

    public void setConfig(PhotalConfig config) {
        mConfig = config;
    }

    public PhotalConfig getConfig() {
        return mConfig;
    }

    public void startMultipleSelector(Activity activity, int resultCode, String imageArrayKey
            , int selectMaxCount, boolean useCamera) {

        if (selectMaxCount <= 1) {
            try {
                throw new IllegalAccessException("selectMaxCount must above 2");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return;
            }
        }
        if (!isSetConfig()) {
            try {
                throw new ConfigException();
            } catch (ConfigException e) {
                e.printStackTrace();
                return;
            }
        }

        Intent intent = new Intent(activity, PhotoSelectorActivity.class);
        intent.setAction(Constant.ACTION_CHOOSE_MULTIPLE);
        intent.putExtra(Constant.BUNDLE_KEY_MAX_COUNT, selectMaxCount);
        intent.putExtra(Constant.BUNDLE_KEY_RESULT_KEY, imageArrayKey);
        intent.putExtra(Constant.BUNDLE_KEY_RESULT_CODE, resultCode);
        activity.startActivityForResult(intent, resultCode);
    }

    public void startSingleSelector(Activity activity, int resultCode, String imageKey
            , boolean useCamera) {

        if (!isSetConfig()) {
            try {
                throw new ConfigException();
            } catch (ConfigException e) {
                e.printStackTrace();
                return;
            }
        }

        Intent intent = new Intent(activity, PhotoSelectorActivity.class);
        intent.setAction(Constant.ACTION_CHOOSE_SINGLE);
        intent.putExtra(Constant.BUNDLE_KEY_RESULT_KEY, imageKey);
        intent.putExtra(Constant.BUNDLE_KEY_RESULT_CODE, resultCode);
        activity.startActivityForResult(intent, resultCode);
    }

    public void capture(Activity activity, int requestCode, File file) {

        if (!isSetConfig()) {
            try {
                throw new ConfigException();
            } catch (ConfigException e) {
                e.printStackTrace();
                return;
            }
        }

        Intent intent = CommonUtil.newCaptureIntent(activity, getConfig().getFileProviderAuthorities(), file);
        activity.startActivityForResult(intent, requestCode);

    }

    public void crop(Activity activity,int resultCode,String imageKey,String originFilePath){

        if (!isSetConfig()) {
            try {
                throw new ConfigException();
            } catch (ConfigException e) {
                e.printStackTrace();
                return;
            }
        }

        Intent intent = new Intent(activity, CropImageActivity.class);
        intent.putExtra(Constant.BUNDLE_KEY_RESULT_CODE,resultCode);
        intent.putExtra(Constant.BUNDLE_KEY_RESULT_KEY,imageKey);
        intent.putExtra(Constant.BUNDLE_KEY_ORIGINAL_FILE_PATH,originFilePath);
        activity.startActivityForResult(intent,resultCode);

    }

    private boolean isSetConfig() {
        if (mConfig != null || mConfig.getFileProviderAuthorities() != null) {
            return true;
        }
        return false;
    }

    /**
     * release resource
     */
    public void release() {
        if (mPhotal != null) {
            mConfig = null;
            mPhotal = null;
        }
    }
}
