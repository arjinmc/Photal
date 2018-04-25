package com.arjinmc.photal.config;

import android.net.Uri;
import android.provider.MediaStore;

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
    public static final String BUNDLE_KEY_ORIGINAL_FILE_PATH = "photal_original_file_path";
    public static final String BUNDLE_KEY_USE_CAMERA = "photal_use_camera";
    public static final String BUNDLE_KEY_USE_CROP = "photal_use_crop";

    public static final Uri URI_IMAGE_MEDIA = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    public static final int LOADER_ID_ALBUM = 0xff10001;
    public static final int LOADER_ID_PHOTO = 0xff10002;
    public static final int SELECTOR_REQUEST_CODE = 1;
    public static final int SELECTOR_RESULT_CODE = 0xff10004;
    public static final int SELECTOR_PREVIEW_RESULT_CODE = 0xff10005;

}
