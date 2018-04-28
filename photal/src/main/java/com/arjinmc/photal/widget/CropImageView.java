package com.arjinmc.photal.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.arjinmc.photal.entity.ShapePoint;
import com.github.chrisbanes.photoview.PhotoView;

/**
 * Crop View
 * scale,move the source image then crop
 * Created by Eminem Lo on 2018/4/25.
 * email: arjinmc@hotmail.com
 */
@SuppressLint("AppCompatCustomView")
public class CropImageView extends ImageView {

    // zoom status
    private final int STATUS_ZOOM_NORMAIL = 1;
    private final int STATUS_ZOOM_FULL_IMAGE = 2;
    private final int STATUS_ZOOM_BIG = 3;

    private Matrix mLastSuppMatrix;
    private Matrix mCurrentMatrix;
    private Matrix mDrawMatrix;

    private float mLastDownX, mLastDownY;
    private float mLastMoveX, mLastMoveY;
    private int mLastAction;
    private int mOldPositionL, mOldPositionT, mOldPositionR, mOldPositionB;

    private ShapePoint mShapePoint;

    private PhotoView mPvImage;


    public CropImageView(Context context) {
        super(context);
        init();
    }

    public CropImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CropImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CropImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setShapePoint(ShapePoint shapePoint) {
        mShapePoint = shapePoint;
    }

    private void init() {

        super.setScaleType(ImageView.ScaleType.MATRIX);
        mLastSuppMatrix = getMatrix();
        mCurrentMatrix = getMatrix();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

//        ViewParent parent = getParent();
//        // First, disable the Parent from intercepting the touch
//        // event
//        if (parent != null) {
//            parent.requestDisallowInterceptTouchEvent(true);
//        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("onTouchEvent", "ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                int pointerCount = event.getPointerCount();
                Log.d("onTouchEvent", "ACTION_MOVE:" + pointerCount);
                // pointer count is 1 for move
                if (pointerCount == 1) {
                    float eventX = event.getX();
                    float eventY = event.getY();
                    mCurrentMatrix.postTranslate(event.getX() - mLastMoveX, event.getY() - mLastMoveY);

                    mCurrentMatrix.postConcat(mLastSuppMatrix);
                    Log.e("matrix",mCurrentMatrix.toShortString());
                    setImageMatrix(mCurrentMatrix);
                    mLastSuppMatrix = mCurrentMatrix;

                    mLastMoveX = eventX;
                    mLastMoveY = eventY;
                    // pointer count is 2 for zoom
                } else if (pointerCount == 2) {

                }
                break;
            case MotionEvent.ACTION_UP:
                Log.d("onTouchEvent", "ACTION_UP");
                mLastMoveX = 0;
                mLastMoveY = 0;

                break;
        }


        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("onLayout","onLayout");
        if (mOldPositionL != left
                || mOldPositionT != top
                || mOldPositionR != right
                || mOldPositionB != bottom) {

            updateMatrix();
            mOldPositionL = left;
            mOldPositionT = top;
            mOldPositionR = right;
            mOldPositionB = bottom;
        }

    }

    private  void updateMatrix(){
        Drawable drawable = getDrawable();

    }

}
