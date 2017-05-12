package com.arjinmc.photal.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.arjinmc.photal.R;

/**
 * AlbumViewHolder
 * Created by Eminem Lu on 11/5/17.
 * Email arjinmc@hotmail.com
 */

public class AlbumViewHolder extends RecyclerView.ViewHolder {

    private TextView tvName;
    public AlbumViewHolder(View itemView) {
        super(itemView);
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
    }
}
