package com.thephoenixit.getjobsapp.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created by root on 18/03/29.
 */

public class TypefaceUtil {
    public TypefaceUtil()
    {
    }

    public static void overrideFont(Context context, String s, String s1)
    {
        try
        {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), s1);
            Field field = android.graphics.Typeface.class.getDeclaredField(s);
            field.setAccessible(true);
            field.set(null, typeface);
            return;
        }
        catch(Exception exception)
        {
            Log.d("font matters : ", (new StringBuilder()).append("Can not set custom font ").append(s1).append(" instead of ").append(s).toString());
        }
    }
}
