package com.arjinmc.photal.util;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.Map;

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

    public static Map<String,String> toMap(String[] ts){
        if(ts!=null && ts.length!=0){
            int len = ts.length;
            Map<String,String> stringMap = new HashMap<>();
            for(int i=0;i<len;i++){
                stringMap.put(ts[i],ts[i]);
            }
            return stringMap;
        }else
            return null;
    }

    public static String[] toStrings(Map<String,String> map){
        if(map==null || map.size()==0)
            return null;
        int size = map.size();
        String[] strings = new String[size];
        return map.keySet().toArray(strings);
    }
}
