package com.arjinmc.photal.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.arjinmc.photal.R;
import com.github.chrisbanes.photoview.PhotoView;

/**
 * fragment for scalable image
 * Created by Eminem Lu on 14/6/17.
 * Email arjinmc@hotmail.com
 */

public class ScaleImageFragment extends Fragment {

    private View mRootView;
    private PhotoView mPhotoView;
    private String mUri;

    public static ScaleImageFragment newInstance(String uri){

        ScaleImageFragment scaleImageFragment = new ScaleImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("uri",uri);
        scaleImageFragment.setArguments(bundle);
        return  scaleImageFragment;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        mUri = args.getString("uri",null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mRootView == null){
            mRootView = inflater.inflate(R.layout.fragment_scale_image,null);
        }
        return mRootView;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPhotoView = (PhotoView) mRootView.findViewById(R.id.pv_image);
        mPhotoView.setZoomable(true);
        mPhotoView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mPhotoView.setImageURI(Uri.parse(mUri));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRootView = null;
        mPhotoView = null;
    }
}
