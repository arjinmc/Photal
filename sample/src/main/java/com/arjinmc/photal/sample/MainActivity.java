package com.arjinmc.photal.sample;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arjinmc.photal.Photal;
import com.arjinmc.photal.config.PhotalConfig;
import com.arjinmc.photal.model.MediaFileItem;
import com.arjinmc.recyclerviewdecoration.RecyclerViewLinearItemDecoration;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String[] mSampleList;
    private RecyclerView mRecyclerView;
    private String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.CAMERA};

    private final int RESULT_CODE_MUTILPLE_SELECTED = 1;
    private final int RESULT_CODE_SINGLE_SELECTED = 2;

    private final int REQUEST_CODE_CAPURE_SELECTED = 0;
    private final String BUNDLE_KEY_IMAGE = "image_selected";

    private boolean useCrop = false;
    private File mFile;
    private File mCropFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSampleList = getResources().getStringArray(R.array.sample_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(), RecyclerView.VERTICAL, false));
        mRecyclerView.addItemDecoration(
                new RecyclerViewLinearItemDecoration.Builder(this)
                        .color(Color.BLACK).thickness(2).paddingStart(20).paddingEnd(20).create());
        mRecyclerView.setAdapter(new MyAdapter());

        //init config
        PhotalConfig photalConfig = new PhotalConfig(this);
//        photalConfig.setThemeColor(ContextCompat.getColor(this, R.color.colorAccent));
//        photalConfig.setThemeDarkColor(ContextCompat.getColor(this, R.color.red));
//        photalConfig.setTextTitleSize(R.dimen.text_title);
//        photalConfig.setTextTitleColor(ContextCompat.getColor(this, R.color.colorAccent));
//        photalConfig.setBtnBackIcon(android.R.drawable.ic_menu_more);
//        photalConfig.setBtnDoneBackground(R.drawable.btn_done);
//        photalConfig.setBtnDoneTextColor(ContextCompat.getColor(this, R.color.colorAccent));
//        photalConfig.setBtnDoneTextSize(R.dimen.text_send);
//        photalConfig.setAlbumBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
//        photalConfig.setAlbumTextSize(R.dimen.text_send);
//        photalConfig.setAlbumTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
//        photalConfig.setAlbumCheckBox(R.drawable.cb_album);
//        photalConfig.setAlbumDiver(new RecyclerViewLinearItemDecoration.Builder(this)
//                .color(ContextCompat.getColor(this, com.arjinmc.photal.R.color.photal_album_background))
//                .thickness(2).create());
//        photalConfig.setGalleryDiver(new RecyclerViewGridItemDecoration.Builder(this)
//                .color(ContextCompat.getColor(this, R.color.photal_send_disable))
//                .horizontalSpacing(2)
//                .verticalSpacing(2)
//                .create());
//        photalConfig.setGalleryColumnCount(4);
//        photalConfig.setGalleryCheckboxColor(Color.YELLOW);
//        photalConfig.setGalleryBackgroundColor(Color.WHITE);
//        photalConfig.setPreviewCheckbox(R.drawable.cb_album);
//        photalConfig.setPreviewTextColor(Color.RED);
//        photalConfig.setPreviewTextSize(R.dimen.text_send);
//        photalConfig.setCropDoneIcon(R.drawable.photal_ic_crop_done);
        photalConfig.setFileProviderAuthorities("com.arjinmc.photal.fileprovider");
//        photalConfig.setImageLoaderType(ImageLoader.MODE_PICASSO);
//        photalConfig.setAccessGPS(true);
        Photal.getInstance().setConfig(photalConfig);

        PermissionAssistant.addPermission(permissions);
        PermissionAssistant.setForceGrantAllPermissions(true);

    }

    private class MyAdapter extends RecyclerView.Adapter<ItemViewHolder> {

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemViewHolder(LayoutInflater.from(getBaseContext())
                    .inflate(R.layout.item_sample_list, parent, false));
        }

        @Override
        public void onBindViewHolder(ItemViewHolder holder, int position) {
            holder.tvName.setText(mSampleList[position]);
            holder.llParent.setOnClickListener(new ItemClickListener(position));
        }

        @Override
        public int getItemCount() {
            return mSampleList.length;
        }
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout llParent;
        private TextView tvName;

        public ItemViewHolder(View itemView) {
            super(itemView);
            llParent = (LinearLayout) itemView;
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

    private class ItemClickListener implements View.OnClickListener {

        private int position;

        public ItemClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (position) {
                case 0:
                    Photal.getInstance().startMultipleSelector(MainActivity.this
                            , RESULT_CODE_MUTILPLE_SELECTED, BUNDLE_KEY_IMAGE, 3);
                    break;
                case 1:
                    Photal.getInstance().startSingleSelector(MainActivity.this
                            , RESULT_CODE_SINGLE_SELECTED, BUNDLE_KEY_IMAGE);
                    break;
                case 2:
                    useCrop = false;
                    mFile = FileUtils.createFile(getCameraPhotoPath() + File.separator + createImageName());
                    Log.e("file", mFile.getAbsolutePath());
                    Photal.getInstance().capture(MainActivity.this, REQUEST_CODE_CAPURE_SELECTED, mFile);
                    break;
                case 3:
                    useCrop = true;
                    mFile = FileUtils.createFile(getCameraPhotoPath() + File.separator + createImageName());
                    mCropFile = FileUtils.createFile(getCameraPhotoPath() + File.separator + createImageName());
                    Log.e("file", mFile.getAbsolutePath());
                    Photal.getInstance().capture(MainActivity.this, REQUEST_CODE_CAPURE_SELECTED, mFile);
                    break;
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CODE_MUTILPLE_SELECTED
                || resultCode == RESULT_CODE_SINGLE_SELECTED) {
            if (data == null) {
                mFile.delete();
                return;
            }
            List<MediaFileItem> mediaFileItemList = data.getParcelableArrayListExtra(BUNDLE_KEY_IMAGE);
            if (mediaFileItemList == null || mediaFileItemList.isEmpty()) {
                Log.e("path", "empty");
            } else {
                for (MediaFileItem mediaFileItem : mediaFileItemList) {
                    Log.e("path", "path:" + mediaFileItem.getPath()
                            + "\nuri:" + mediaFileItem.getUriPath()
                            + "\noriginal uri:" + mediaFileItem.getUriOriginalPath()
                            + "\nmimeType:" + mediaFileItem.getMimeType()
                            + "\nsize:" + mediaFileItem.getSize()
                            + "\ndateTaken:" + mediaFileItem.getDateTaken()
                    );
                }
            }

        } else if (requestCode == REQUEST_CODE_CAPURE_SELECTED) {
            if (resultCode == Activity.RESULT_OK) {
                if (useCrop) {
                    Photal.getInstance().crop(this, mFile.getAbsolutePath()
                            , mCropFile.getAbsolutePath(), 400);
                } else {
                    Log.e("capture", "done");
                }
            } else {
                mFile.delete();
            }

        } else if (requestCode == UCrop.REQUEST_CROP) {
            if (resultCode == RESULT_OK) {
                final Uri resultUri = UCrop.getOutput(data);
                mFile.delete();
                Log.e("result uri", resultUri.getPath());
            } else {
                mFile.delete();
            }
        }
    }

    public String getCameraPhotoPath() {

        String path = getExternalFilesDir("").getPath() + File.separator + "photal";
        FileUtils.createDir(path);
        return path;

    }

    public String createImageName() {
        String imageName = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmsss" + new Random().nextInt(100));
        imageName = "IMAGE_" + simpleDateFormat.format(new Date()) + ".jpg";
        return imageName;
    }

    private String getPath(String[] path) {
        StringBuilder stringBuilder = new StringBuilder();
        int len = path.length;
        for (int i = 0; i < len; i++) {
            stringBuilder.append(path[i] + "\n");
        }
        return stringBuilder.toString();
    }


    @Override
    protected void onResume() {
        super.onResume();
        PermissionAssistant.forceRequestPermissions(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFile = null;
        mFile = null;
        Photal.getInstance().release();
    }
}
