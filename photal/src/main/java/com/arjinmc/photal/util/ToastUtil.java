package com.arjinmc.photal.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast Util
 * Created by Eminem Lo on 2018/4/18.
 * email: arjinmc@hotmail.com
 */
public class ToastUtil {

    private static Toast mToast;

    public static void show(Context context, String msg) {

        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

}
