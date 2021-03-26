package com.arjinmc.photal.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.collection.ArrayMap;
import androidx.core.content.FileProvider;

import com.arjinmc.photal.model.MediaFileItem;

import java.io.File;

/**
 * Created by Eminem Lo on 26/5/17.
 * Email arjinmc@hotmail.com
 */

public final class CommonUtil {

    public static int getScreenHeight(Context context) {
        Point point = new Point();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getSize(point);
        return point.y;
    }

    public static int getScreenWidth(Context context) {
        Point point = new Point();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getSize(point);
        return point.x;
    }

    public static String[] toStrings(ArrayMap<String, String> map) {
        if (map == null || map.size() == 0)
            return null;
        int size = map.size();
        String[] strings = new String[size];
        return map.keySet().toArray(strings);
    }

    /**
     * call system capture
     *
     * @param context
     * @param authority
     * @param file
     * @return
     */
    public static Intent newCaptureIntent(Context context, String authority, File file) {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, compatFileUri(context, authority, file));
        return captureIntent;
    }

    /**
     * compat file uri
     *
     * @param context
     * @param authority
     * @param file
     * @return
     */
    public static Uri compatFileUri(Context context, String authority, File file) {
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, authority, file);
        } else {
            uri = Uri.parse("file:////" + file.getAbsolutePath());
        }
        return uri;
    }

    /**
     * Sets status-bar color for L devices.
     *
     * @param color - status-bar color
     */
    public static void setStatusBarColor(Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final Window window = activity.getWindow();
            if (window != null) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(color);
            }
        }
    }

    /**
     * get image path
     *
     * @param mediaFileItem
     * @return
     */
    public static String getImagePath(MediaFileItem mediaFileItem) {
        if (mediaFileItem == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return mediaFileItem.getUriPath();
        }
        return mediaFileItem.getPath();
    }
}
