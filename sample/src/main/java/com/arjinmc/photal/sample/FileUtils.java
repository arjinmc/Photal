package com.arjinmc.photal.sample;

import java.io.File;
import java.io.IOException;

/**
 * Created by Eminem Lo on 28/4/17.
 * Email arjinmc@hotmail.com
 */

public final class FileUtils {

    public static void createDir(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.isDirectory()) {
            return;
        }
        file.mkdir();
    }

    public static File createFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file;
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

}
