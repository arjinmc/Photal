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
    public static final String BUNDLE_KEY_ALL = "photal_all";
    public static final String BUNDLE_USE_CAMERA = "use_camera";

    public static final Uri URI_IMAGE_MEDIA = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    public static String getCameraPhotoPath() {

        String path = null;
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            path = Environment.getExternalStorageDirectory().getPath() + File.separator + Config.CAMERA_FILE_DIRECTORY;
        } else {
            path = Environment.getDataDirectory().getPath() + File.separator + Config.CAMERA_FILE_DIRECTORY;
        }
        FileUtils.create(path);
        return path;

    }

    public static String createImageName() {
        String imageName = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMDDhhmmsss");
        imageName = Config.IMAGE_NAME_PREFIX + simpleDateFormat.format(new Date());
        return imageName;
    }

    public static int getMaxChoosePhotoCount() {
        int count = Config.MAX_CHOOSE_PHOTO;
        if (count < 1)
            return 1;
        return count;
    }


}
