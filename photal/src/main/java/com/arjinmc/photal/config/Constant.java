package com.arjinmc.photal.config;

import android.os.Environment;

import com.arjinmc.photal.util.FileUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Eminem Lu on 28/4/17.
 * Email arjinmc@hotmail.com
 */

public final class Constant {

    public static final String MODE_CHOOSE_SINGLE = "MODE_CHOOSE_SINGLE";
    public static final String MODE_CHOOSE_MULTIPLE = "MODE_CHOOSE_MULTIPLE";

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


}
