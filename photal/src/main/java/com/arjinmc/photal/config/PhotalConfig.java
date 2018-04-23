package com.arjinmc.photal.config;

import android.content.Context;
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

    public static final String CAMERA_FILE_DIRECTORY = "photal";
    public static final String IMAGE_NAME_PREFIX = "IMAGE";

    public static final int MAX_CHOOSE_PHOTO = 9;
    //change this mode for use different image framework
    public static final int IMAGE_MODE = ImageLoader.MODE_GLIDE;
//    public static final int IMAGE_MODE = ImageLoader.MODE_PICASSO;

    @IntDef({ImageLoader.MODE_GLIDE, ImageLoader.MODE_PICASSO})
    @interface ImageLoaderType {
    }

    private Context mContext;

    private int themeColor = -1;
    private int themeDarkColor = -1;
    private int textTitleColor = -1;
    private int textTitleSize = -1;
    private int btnBackIcon = -1;
    private int btnDoneBackground = -1;
    private int btnDoneTextColor = -1;
    private int btnDoneTextSize = -1;
    private int albumBackgroundColor = -1;
    private int albumTextSize = -1;
    private int albumTextColor = -1;
    private int albumCheckBox = -1;
    private RecyclerView.ItemDecoration albumDiver;
    private RecyclerView.ItemDecoration galleryDiver;
    private int galleryColumnCount = -1;
    private int galleryCheckbox = -1;
    private int previewCheckbox = -1;
    private int previewTextColor = -1;
    private int previewTextSize = -1;
    private int headerHeight = -1;
    private int bottomHeight = -1;
    private String fileProviderAuthorities;
    private String saveFileRegex;
    private int imageLoaderType = -1;
    private int choosePhotoMaxCount = -1;

    public PhotalConfig(Context context) {
        mContext = context;
    }

    public int getThemeColor() {
        if (themeColor == -1) {
            return ContextCompat.getColor(mContext, R.color.photal_theme);
        }
        return themeColor;
    }

    public void setThemeColor(@ColorInt int themeColor) {
        this.themeColor = themeColor;
    }

    public int getThemeDarkColor() {
        if (themeColor == -1 && themeDarkColor == -1) {
            return ContextCompat.getColor(mContext, R.color.photal_theme_dark);
        }
        if (themeDarkColor == -1) {
            return themeColor;
        }
        return themeDarkColor;
    }

    public void setThemeDarkColor(@ColorInt int themeDarkColor) {
        this.themeDarkColor = themeDarkColor;
    }

    public int getTextTitleColor() {
        if (textTitleColor == -1) {
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
        if (btnDoneTextColor == -1) {
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
        if (albumBackgroundColor == -1) {
            return R.color.photal_white;
        }
        return albumBackgroundColor;
    }

    public void setAlbumBackgroundColor(@ColorInt int albumBackgroundColor) {
        this.albumBackgroundColor = albumBackgroundColor;
    }

    public int getAlbumTextSize() {
        return albumTextSize;
    }

    public void setAlbumTextSize(@DimenRes int albumTextSize) {
        this.albumTextSize = albumTextSize;
    }

    public int getAlbumTextColor() {
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
        return galleryColumnCount;
    }

    public void setGalleryColumnCount(int galleryColumnCount) {
        this.galleryColumnCount = galleryColumnCount;
    }

    public int getGalleryCheckbox() {
        return galleryCheckbox;
    }

    public void setGalleryCheckbox(@DrawableRes int galleryCheckbox) {
        this.galleryCheckbox = galleryCheckbox;
    }

    public int getPreviewCheckbox() {
        return previewCheckbox;
    }

    public void setPreviewCheckbox(@DrawableRes int previewCheckbox) {
        this.previewCheckbox = previewCheckbox;
    }

    public int getPreviewTextColor() {
        return previewTextColor;
    }

    public void setPreviewTextColor(int previewTextColor) {
        this.previewTextColor = previewTextColor;
    }

    public int getPreviewTextSize() {
        return previewTextSize;
    }

    public void setPreviewTextSize(int previewTextSize) {
        this.previewTextSize = previewTextSize;
    }

    public int getHeaderHeight() {
        return headerHeight;
    }

    public void setHeaderHeight(int headerHeight) {
        this.headerHeight = headerHeight;
    }

    public int getBottomHeight() {
        return bottomHeight;
    }

    public void setBottomHeight(int bottomHeight) {
        this.bottomHeight = bottomHeight;
    }

    public String getFileProviderAuthorities() {
        return fileProviderAuthorities;
    }

    public void setFileProviderAuthorities(String fileProviderAuthorities) {
        this.fileProviderAuthorities = fileProviderAuthorities;
    }

    public String getSaveFileRegex() {
        return saveFileRegex;
    }

    public void setSaveFileRegex(String saveFileRegex) {
        this.saveFileRegex = saveFileRegex;
    }

    public int getImageLoaderType() {
        return imageLoaderType;
    }

    public void setImageLoaderType(int imageLoaderType) {
        this.imageLoaderType = imageLoaderType;
    }

    public int getChoosePhotoMaxCount() {
        return choosePhotoMaxCount;
    }

    public void setChoosePhotoMaxCount(int choosePhotoMaxCount) {
        this.choosePhotoMaxCount = choosePhotoMaxCount;
    }
}
