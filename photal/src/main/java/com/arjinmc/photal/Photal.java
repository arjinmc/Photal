package com.arjinmc.photal;

import android.app.Activity;
import android.content.Intent;

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
            , boolean useCamera, boolean useCrop) {

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

    public void capture(Activity activity, int resultCode, String imageKey, boolean useCrop, File file) {

        if (!isSetConfig()) {
            try {
                throw new ConfigException();
            } catch (ConfigException e) {
                e.printStackTrace();
                return;
            }
        }

        activity.startActivityForResult(
                CommonUtil.newCaptureIntent(activity, getConfig().getFileProviderAuthorities(), file)
                , resultCode);

    }

    private boolean isSetConfig() {
        if (mConfig != null) {
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
