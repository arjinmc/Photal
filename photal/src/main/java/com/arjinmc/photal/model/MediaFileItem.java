package com.arjinmc.photal.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * file item for media
 * Created by Eminem Lo on 3/24/21.
 * email: arjinmc@hotmail.com
 */
public class MediaFileItem implements Parcelable {

    private String displayName;
    private String mimeType;
    private String path;
    private String uriPath;
    private long size;
    private long duration;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUriPath() {
        return uriPath;
    }

    public void setUriPath(String uriPath) {
        this.uriPath = uriPath;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public MediaFileItem(){

    }

    protected MediaFileItem(Parcel in) {
        displayName = in.readString();
        mimeType = in.readString();
        path = in.readString();
        uriPath = in.readString();
        size = in.readLong();
        duration = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(displayName);
        dest.writeString(mimeType);
        dest.writeString(path);
        dest.writeString(uriPath);
        dest.writeLong(size);
        dest.writeLong(duration);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MediaFileItem> CREATOR = new Creator<MediaFileItem>() {
        @Override
        public MediaFileItem createFromParcel(Parcel in) {
            return new MediaFileItem(in);
        }

        @Override
        public MediaFileItem[] newArray(int size) {
            return new MediaFileItem[size];
        }
    };
}
