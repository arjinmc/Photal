package com.arjinmc.photal.util;

import java.io.File;
import java.io.IOException;

/**
 * Created by Eminem Lu on 28/4/17.
 * Email arjinmc@hotmail.com
 */

public final class FileUtils {

    public static boolean isExisted(String filePath){
        File file = new File(filePath);
        if(file.exists())
            return true;
        return false;
    }

    public static void create(String filePath){
        if(!isExisted(filePath)){
            File file = new File(filePath);
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
