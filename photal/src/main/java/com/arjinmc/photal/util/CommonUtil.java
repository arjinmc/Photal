package com.arjinmc.photal.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v4.util.ArrayMap;
import android.view.WindowManager;

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

    public static Intent newCaptureIntent(Context context, String authority, File file) {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, authority, file);
        } else {
            uri = Uri.parse("file:////"+file.getAbsolutePath());
        }
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        return captureIntent;
    }
}
