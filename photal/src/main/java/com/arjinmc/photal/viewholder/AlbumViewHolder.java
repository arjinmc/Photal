package com.arjinmc.photal.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arjinmc.photal.R;

/**
 * AlbumViewHolder
 * Created by Eminem Lu on 11/5/17.
 * Email arjinmc@hotmail.com
 */

public class AlbumViewHolder extends RecyclerView.ViewHolder {

    public RelativeLayout rlRoot;
    public TextView tvName;
    public TextView tvCount;
    public ImageView ivPhoto;
    public RadioButton rbChoose;

    public AlbumViewHolder(View itemView) {
        super(itemView);
        rlRoot = (RelativeLayout) itemView;
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        tvCount = (TextView) itemView.findViewById(R.id.tv_count);
        ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
        rbChoose = (RadioButton) itemView.findViewById(R.id.rb_current);
    }
}
