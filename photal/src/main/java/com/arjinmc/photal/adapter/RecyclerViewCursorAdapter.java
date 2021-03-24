package com.arjinmc.photal.adapter;

import android.database.Cursor;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Eminem Lo on 25/5/17.
 * Email arjinmc@hotmail.com
 */

public class RecyclerViewCursorAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected Cursor mCusor;

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (mCusor != null)
            return mCusor.getCount();
        return 0;
    }

    public void setCursor(Cursor cursor) {
        mCusor = cursor;
    }
}
