package com.arjinmc.photal.selector;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.arjinmc.photal.R;

/**
 * photo selector grid style
 * Created by Eminem Lu on 11/5/17.
 * Email arjinmc@hotmail.com
 */

public class PhotoGridSelectorActivity extends Activity {


    private RecyclerView mRecyclerView;
    private int mCurrentMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_grid_selector);

    }

}
