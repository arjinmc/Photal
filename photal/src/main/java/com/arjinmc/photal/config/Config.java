package com.arjinmc.photal.config;

import com.arjinmc.photal.util.ImageLoader;

/**
 * Created by Eminem Lo on 28/4/17.
 * Email arjinmc@hotmail.com
 */

public final class Config {

    public static final String CAMERA_FILE_DIRECTORY = "photal";
    public static final String IMAGE_NAME_PREFIX = "IMAGE";

    public static final int MAX_CHOOSE_PHOTO = 9;
    //change this mode for use different image framework
    public static final int IMAGE_MODE = ImageLoader.MODE_GLIDE;

    public static final int LOADER_ID_ALBUM = 0xff10001;
    public static final int LOADER_ID_PHOTO = 0xff10002;
    public static final int SELECTOR_REQUEST_CODE = 0xff10003;
    public static final int SELECTOR_RESULT_CODE = 0xff10004;

}
