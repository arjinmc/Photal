package com.arjinmc.photal.util;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

/**
 * Created by Eminem Lu on 26/5/17.
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
}
