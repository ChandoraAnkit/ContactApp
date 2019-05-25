package com.testapp.chandora.androidy.contactApp.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

/**
 * Created by chandora on 24-May-2019
 */
public class PermissionUtils {

    private PermissionUtils() {
        //Prevent direct instantiation
    }

    public static void requestPermission(Activity context, String permission, int requestCode) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(context, new String[]{permission}, requestCode);
            }
        }
    }

    public static boolean checkPermissions(Activity context, String permission) {

        if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
            return false;

        return true;

    }
}


