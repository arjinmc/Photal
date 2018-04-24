package com.arjinmc.photal.config;

import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.arjinmc.photal.util.FileUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Eminem Lo on 28/4/17.
 * Email arjinmc@hotmail.com
 */

public final class Constant {

    public static final String ACTION_CHOOSE_SINGLE = "ACTION_CHOOSE_SINGLE";
    public static final String ACTION_CHOOSE_MULTIPLE = "ACTION_CHOOSE_MULTIPLE";

    public static final String BUNDLE_KEY_SELECTED = "photal_selected";
    public static final String BUNDLE_KEY_RESULT_KEY = "photal_result_key";
    public static final String BUNDLE_KEY_RESULT_CODE = "photal_result_code";
    public static final String BUNDLE_KEY_MAX_COUNT = "photal_max_count";
    public static final String BUNDLE_KEY_USE_CAMERA = "photal_use_camera";
    public static final String BUNDLE_KEY_USE_CROP = "photal_use_crop";

    public static final Uri URI_IMAGE_MEDIA = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    public static final int LOADER_ID_ALBUM = 0xff10001;
    public static final int LOADER_ID_PHOTO = 0xff10002;
    public static final int SELECTOR_REQUEST_CODE = 1;
    public static final int SELECTOR_RESULT_CODE = 0xff10004;
    public static final int SELECTOR_PREVIEW_RESULT_CODE = 0xff10005;

    public static String getCameraPhotoPath() {

        String path = null;
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            path = Environment.getExternalStorageDirectory().getPath() + File.separator + PhotalConfig.CAMERA_FILE_DIRECTORY;
        } else {
            path = Environment.getDataDirectory().getPath() + File.separator + PhotalConfig.CAMERA_FILE_DIRECTORY;
        }
        FileUtils.create(path);
        return path;

    }

    public static String createImageName() {
        String imageName = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMDDhhmmsss");
        imageName = PhotalConfig.IMAGE_NAME_PREFIX + simpleDateFormat.format(new Date());
        return imageName;
    }

}
