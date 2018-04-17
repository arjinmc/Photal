package com.arjinmc.photal.util;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v4.util.ArrayMap;
import android.view.WindowManager;

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

    public static int getColor(Context context, @ColorRes int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(colorId);
        } else {
            return context.getResources().getColor(colorId);
        }
    }
}
