package com.testapp.chandora.androidy.contactApp.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

/**
 * Created by chandora on 25-May-2019
 */
public class DrawableUtils {

    private DrawableUtils(){
        //Prevent direct instantiation
    }

    public static Drawable changeDrawableColor(Context context, int id,int color){

        Drawable drawable = ContextCompat.getDrawable(context, id).mutate();
        drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
        return drawable;
    }
}
