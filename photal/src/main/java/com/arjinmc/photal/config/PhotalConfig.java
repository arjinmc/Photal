package com.arjinmc.photal.config;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import com.arjinmc.photal.R;
import com.arjinmc.photal.util.ImageLoader;

/**
 * Created by Eminem Lo on 28/4/17.
 * Email arjinmc@hotmail.com
 */

public final class PhotalConfig {

    @IntDef({ImageLoader.MODE_GLIDE, ImageLoader.MODE_PICASSO})
    @interface ImageLoaderType {
    }

    private static final int UNEFFECTED_COLOR = 1;

    private Context mContext;

    private int themeColor = UNEFFECTED_COLOR;
    private int themeDarkColor = UNEFFECTED_COLOR;
    private int textTitleColor = UNEFFECTED_COLOR;
    private int textTitleSize = -1;
    private int btnBackIcon = -1;
    private int btnDoneBackground = -1;
    private int btnDoneTextColor = UNEFFECTED_COLOR;
    private int btnDoneTextSize = -1;
    private int albumBackgroundColor = UNEFFECTED_COLOR;
    private int albumTextSize = -1;
    private int albumTextColor = UNEFFECTED_COLOR;
    private int albumCheckBox = -1;
    private RecyclerView.ItemDecoration albumDiver;
    private RecyclerView.ItemDecoration galleryDiver;
    private int galleryColumnCount = -1;
    private int galleryCheckboxColor = UNEFFECTED_COLOR;
    private int galleryBackgroundColor = UNEFFECTED_COLOR;
    private int previewCheckbox = -1;
    private int previewTextColor = UNEFFECTED_COLOR;
    private int previewTextSize = -1;
    private String fileProviderAuthorities;
    private int cropDoneIcon = -1;
    private int imageLoaderType = -1;

    public PhotalConfig(Context context) {
        mContext = context;
    }

    public int getThemeColor() {
        if (themeColor == UNEFFECTED_COLOR) {
            return ContextCompat.getColor(mContext, R.color.photal_theme);
        }
        return themeColor;
    }

    public void setThemeColor(@ColorInt int themeColor) {
        this.themeColor = themeColor;
    }

    public int getThemeDarkColor() {
        if (themeColor == UNEFFECTED_COLOR && themeDarkColor == UNEFFECTED_COLOR) {
            return ContextCompat.getColor(mContext, R.color.photal_theme_dark);
        }
        if (themeDarkColor == UNEFFECTED_COLOR) {
            return themeColor;
        }
        return themeDarkColor;
    }

    public void setThemeDarkColor(@ColorInt int themeDarkColor) {
        this.themeDarkColor = themeDarkColor;
    }

    public int getTextTitleColor() {
        if (textTitleColor == UNEFFECTED_COLOR) {
            return ContextCompat.getColor(mContext, R.color.photal_title_text);
        }
        return textTitleColor;
    }

    public void setTextTitleColor(@ColorInt int textTitleColor) {
        this.textTitleColor = textTitleColor;
    }

    public int getTextTitleSize() {
        if (textTitleSize == -1) {
            return mContext.getResources().getDimensionPixelSize(R.dimen.photal_txt_normal);
        }
        return textTitleSize;
    }

    public void setTextTitleSize(@DimenRes int textTitleSize) {
        this.textTitleSize = mContext.getResources().getDimensionPixelSize(textTitleSize);
    }

    public int getBtnBackIcon() {
        if (btnBackIcon == -1) {
            return R.drawable.photal_ic_arrow_back;
        }
        return btnBackIcon;
    }

    public void setBtnBackIcon(@DrawableRes int btnBackIcon) {
        this.btnBackIcon = btnBackIcon;
    }

    public int getBtnDoneBackground() {
        if (btnDoneBackground == -1) {
            return R.drawable.photal_send_btn_bg_selector;
        }
        return btnDoneBackground;
    }

    public void setBtnDoneBackground(@DrawableRes int btnDoneBackground) {
        this.btnDoneBackground = btnDoneBackground;
    }

    public int getBtnDoneTextColor() {
        if (btnDoneTextColor == UNEFFECTED_COLOR) {
            return ContextCompat.getColor(mContext, R.color.photal_title_text);
        }
        return btnDoneTextColor;
    }

    public void setBtnDoneTextColor(@ColorInt int btnDoneTextColor) {
        this.btnDoneTextColor = btnDoneTextColor;
    }

    public int getBtnDoneTextSize() {
        if (btnDoneTextSize == -1) {
            return mContext.getResources().getDimensionPixelSize(R.dimen.photal_txt_small);
        }
        return btnDoneTextSize;
    }

    public void setBtnDoneTextSize(@DimenRes int btnDoneTextSize) {
        this.btnDoneTextSize = mContext.getResources().getDimensionPixelSize(btnDoneTextSize);
    }

    public int getAlbumBackgroundColor() {
        if (albumBackgroundColor == UNEFFECTED_COLOR) {
            return ContextCompat.getColor(mContext, R.color.photal_white);
        }
        return albumBackgroundColor;
    }

    public void setAlbumBackgroundColor(@ColorInt int albumBackgroundColor) {
        this.albumBackgroundColor = albumBackgroundColor;
    }

    public int getAlbumTextSize() {
        if (albumTextSize == -1) {
            return mContext.getResources().getDimensionPixelSize(R.dimen.photal_txt_small);
        }
        return albumTextSize;
    }

    public void setAlbumTextSize(@DimenRes int albumTextSize) {
        this.albumTextSize = mContext.getResources().getDimensionPixelSize(albumTextSize);
    }

    public int getAlbumTextColor() {
        if (albumTextColor == UNEFFECTED_COLOR) {
            return ContextCompat.getColor(mContext, R.color.photal_black);
        }
        return albumTextColor;
    }

    public void setAlbumTextColor(@ColorInt int albumTextColor) {
        this.albumTextColor = albumTextColor;
    }

    public int getAlbumCheckBox() {
        return albumCheckBox;
    }

    public void setAlbumCheckBox(@DrawableRes int albumCheckBox) {
        this.albumCheckBox = albumCheckBox;
    }

    public RecyclerView.ItemDecoration getAlbumDiver() {
        return albumDiver;
    }

    public void setAlbumDiver(RecyclerView.ItemDecoration albumDiver) {
        this.albumDiver = albumDiver;
    }

    public RecyclerView.ItemDecoration getGalleryDiver() {
        return galleryDiver;
    }

    public void setGalleryDiver(RecyclerView.ItemDecoration galleryDiver) {
        this.galleryDiver = galleryDiver;
    }

    public int getGalleryColumnCount() {
        if (galleryColumnCount == -1) {
            return galleryColumnCount = 3;
        }
        return galleryColumnCount;
    }

    public void setGalleryColumnCount(int galleryColumnCount) {
        this.galleryColumnCount = galleryColumnCount;
    }

    public int getGalleryCheckboxColor() {
        if (galleryCheckboxColor == UNEFFECTED_COLOR) {
            return Color.BLUE;
        }
        return galleryCheckboxColor;
    }

    public void setGalleryCheckboxColor(@ColorInt int galleryCheckboxColor) {
        this.galleryCheckboxColor = galleryCheckboxColor;
    }

    public int getGalleryBackgroundColor() {
        if (galleryBackgroundColor == UNEFFECTED_COLOR) {
            return ContextCompat.getColor(mContext, R.color.photal_black);
        }
        return galleryBackgroundColor;
    }

    public void setGalleryBackgroundColor(@ColorInt int galleryBackgroundColor) {
        this.galleryBackgroundColor = galleryBackgroundColor;
    }

    public int getPreviewCheckbox() {
        return previewCheckbox;
    }

    public void setPreviewCheckbox(@DrawableRes int previewCheckbox) {
        this.previewCheckbox = previewCheckbox;
    }

    public int getPreviewTextColor() {
        if (previewTextColor == UNEFFECTED_COLOR) {
            return ContextCompat.getColor(mContext, R.color.photal_white);
        }
        return previewTextColor;
    }

    public void setPreviewTextColor(@ColorInt int previewTextColor) {
        this.previewTextColor = previewTextColor;
    }

    public int getPreviewTextSize() {
        if (previewTextSize == -1) {
            return mContext.getResources().getDimensionPixelSize(R.dimen.photal_txt_small);
        }
        return previewTextSize;
    }

    public void setPreviewTextSize(int previewTextSize) {
        this.previewTextSize = mContext.getResources().getDimensionPixelSize(previewTextSize);
    }

    public String getFileProviderAuthorities() {
        return fileProviderAuthorities;
    }

    public void setFileProviderAuthorities(String fileProviderAuthorities) {
        this.fileProviderAuthorities = fileProviderAuthorities;
    }

    public int getCropDoneIcon() {
        if (cropDoneIcon == -1) {
            return R.drawable.photal_ic_crop_done;
        }
        return cropDoneIcon;
    }

    public void setCropDoneIcon(@DrawableRes int cropDoneIcon) {
        this.cropDoneIcon = cropDoneIcon;
    }

    public int getImageLoaderType() {
        return imageLoaderType;
    }

    public void setImageLoaderType(int imageLoaderType) {
        this.imageLoaderType = imageLoaderType;
    }

}
