package com.arjinmc.photal.selector;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.arjinmc.photal.R;

/**
 * photo selector with a preview of current selected photo
 * Created by Eminem Lu on 11/5/17.
 * Email arjinmc@hotmail.com
 */

public class PhotoSwitchGallerySelectorActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_switch_gallery_selector);
    }
}
