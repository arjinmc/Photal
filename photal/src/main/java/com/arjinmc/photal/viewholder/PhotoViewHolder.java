package com.arjinmc.photal.viewholder;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.arjinmc.photal.R;
import com.arjinmc.photal.widget.SelectBox;

/**
 * PhotoViewHolder
 * Created by Eminem Lo on 11/5/17.
 * Email arjinmc@hotmail.com
 */

public class PhotoViewHolder extends RecyclerView.ViewHolder {

    public ImageView ivPhoto;
    public SelectBox sbCheck;

    public PhotoViewHolder(View itemView) {
        super(itemView);
        ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
        sbCheck = (SelectBox) itemView.findViewById(R.id.cb_check);
    }
}
