package com.arjinmc.photal.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.arjinmc.photal.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Request permission assistant
 * Created by Eminem Lu on 6/4/17.
 * Email arjinmc@hotmail.com
 */

public final class PermissionAssistant {


    private static Context mContext;
    private static Map<String, String> mRequestPermissionMap = new HashMap<>();
    private static List<String> mUngrantedPermissionList = new ArrayList<>();
    private static AlertDialog mAlerDialog;
    private static boolean mForceGrantAllPermissions;

    private static PermissionCallback mCallback;

    /**
     * check to request permissions
     *
     * @param context
     */
    public static void requestPermissions(Context context) {
        mContext = context;
        mUngrantedPermissionList.clear();
        boolean useDialog = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : mRequestPermissionMap.keySet()) {

                int permissionResult = ContextCompat.checkSelfPermission(context, permission);
                if (permissionResult != PackageManager.PERMISSION_GRANTED) {
                    mUngrantedPermissionList.add(permission);
                }

                //judge if need to use our own dialog to request permission
                if (!useDialog) {
                    boolean shouldRequest = ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission);
                    if (shouldRequest == false && permissionResult == PackageManager.PERMISSION_DENIED) {
                        useDialog = true;
                    }
                    Log.e("permissionResult", permissionResult + ":" + shouldRequest);
                }
            }
            Log.e("mUngranted", mUngrantedPermissionList.size() + "");
            if (mUngrantedPermissionList.size() != 0) {
                if (useDialog) {
                    showDialog(context);
                } else {
                    ActivityCompat.requestPermissions((Activity) context
                            , mUngrantedPermissionList.toArray(new String[mUngrantedPermissionList.size()]), 1);
                }
            }
        }

    }


    /**
     * response callback for request permission result
     *
     * @param permissions
     * @param grantResults
     */
    public static void onRequestPermissionResult(@NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mCallback != null) {
            int permissionSize = permissions.length;
            List<String> grandtedList = new ArrayList<>();
            List<String> denyList = new ArrayList<>();
            for (int i = 0; i < permissionSize; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                    grandtedList.add(permissions[i]);
                else if (grantResults[i] == PackageManager.PERMISSION_DENIED)
                    denyList.add(permissions[i]);
            }

            if (grandtedList.size() != 0)
                mCallback.onAllow(grandtedList.toArray(new String[grandtedList.size()]));
            if (denyList.size() != 0) {
                mCallback.onDeny(denyList.toArray(new String[denyList.size()]));
            }

        }

    }

    /**
     * return for is granted all permmission which would be requested
     *
     * @return
     */
    public static boolean isGrantedAllPermissions(Context context) {
        int permissionSize = mRequestPermissionMap.size();
        for (String permission : mRequestPermissionMap.keySet()) {
            int permissionResult = ContextCompat.checkSelfPermission(context, permission);
            if (permissionResult == PackageManager.PERMISSION_GRANTED) {
                permissionSize--;
            }
        }
        if (permissionSize == 0)
            return true;
        return false;
    }

    /**
     * add permission for request
     *
     * @param permission
     */
    public static void addPermission(String permission) {
        mRequestPermissionMap.put(permission, permission);
    }

    /**
     * add permisions for request
     *
     * @param permission
     */
    public static void addPermission(String[] permission) {
        if (permission != null && permission.length != 0) {
            int permissionSize = permission.length;
            for (int i = 0; i < permissionSize; i++) {
                mRequestPermissionMap.put(permission[i], permission[i]);
            }
        }
    }

    /**
     * remove permission for request
     *
     * @param permission
     */
    public static void removePermission(String permission) {
        mRequestPermissionMap.remove(permission);
    }

    /**
     * clear all permissions for request
     */
    public static void clearPermission() {
        mRequestPermissionMap.clear();
    }

    /**
     * set permission callback
     *
     * @param callback
     */
    public static void setCallback(PermissionCallback callback) {
        mCallback = callback;
    }


    /**
     * set force to grant all permission
     *
     * @param foreceGrantPermission
     */
    public static void setForceGrantAllPermissions(boolean foreceGrantPermission) {
        mForceGrantAllPermissions = foreceGrantPermission;
    }

    /**
     * open this setting of current application
     *
     * @param context
     */
    public static void openSystemPermissionSettting(Context context) {

        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    /**
     * if the system has deny request permission by clicked "never ask again"
     *
     * @param context
     */
    public static void showDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.photal_permission_setting))
                .setMessage(context.getString(R.string.photal_permission_setting_tips))
                .setPositiveButton(context.getString(R.string.photal_permission_setting_yes)
                        , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                openSystemPermissionSettting(context);
                            }
                        }).setNegativeButton(context.getString(R.string.photal_permission_setting_no)
                        , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (mForceGrantAllPermissions) {
                                    showDialog(mContext);
                                }
                            }
                        });

        mAlerDialog = builder.create();
        if (mForceGrantAllPermissions) {
            mAlerDialog.setCancelable(false);
            mAlerDialog.setCanceledOnTouchOutside(false);
        }
        mAlerDialog.show();
    }


    /**
     * the callback for requesting permission
     */
    public interface PermissionCallback {

        public void onAllow(String[] permission);

        public void onDeny(String[] permission);

    }


}
