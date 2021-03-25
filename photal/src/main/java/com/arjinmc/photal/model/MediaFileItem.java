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
    private String uriOriginalPath;
    private long size;
    private long duration;
    private long dateTaken;

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

    public String getUriOriginalPath() {
        return uriOriginalPath;
    }

    public void setUriOriginalPath(String uriOriginalPath) {
        this.uriOriginalPath = uriOriginalPath;
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

    public long getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(long dateTaken) {
        this.dateTaken = dateTaken;
    }

    public MediaFileItem() {

    }

    protected MediaFileItem(Parcel in) {
        displayName = in.readString();
        mimeType = in.readString();
        path = in.readString();
        uriPath = in.readString();
        uriOriginalPath = in.readString();
        size = in.readLong();
        duration = in.readLong();
        dateTaken = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(displayName);
        dest.writeString(mimeType);
        dest.writeString(path);
        dest.writeString(uriPath);
        dest.writeString(uriOriginalPath);
        dest.writeLong(size);
        dest.writeLong(duration);
        dest.writeLong(dateTaken);
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
