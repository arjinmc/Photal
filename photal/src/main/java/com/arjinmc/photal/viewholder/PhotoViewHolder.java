package com.arjinmc.photal.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.arjinmc.photal.R;

/**
 * PhotoViewHolder
 * Created by Eminem Lu on 11/5/17.
 * Email arjinmc@hotmail.com
 */

public class PhotoViewHolder extends RecyclerView.ViewHolder {

    private ImageView ivPhoto;

    public PhotoViewHolder(View itemView) {
        super(itemView);
        ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
    }
}
