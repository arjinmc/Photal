package com.arjinmc.photal.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;

import com.arjinmc.photal.R;

/**
 * preview Activity with edit mode
 * Created by Eminem Lu on 11/5/17.
 * Email arjinmc@hotmail.com
 */

public class PreviewActivity extends Activity {

    private ImageButton mBtnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        mBtnBack = (ImageButton) findViewById(R.id.btn_back);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
