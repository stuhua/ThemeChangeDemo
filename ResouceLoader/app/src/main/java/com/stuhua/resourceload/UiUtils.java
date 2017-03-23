package com.stuhua.resourceload;

import android.content.Context;
import android.util.Log;

/**
 * Created by liulh on 2017/3/23.
 */

public class UiUtils {
    public static String getTextString(Context ctx) {
        return ctx.getResources().getString(R.string.app_name);
    }

    public static int getDayTheme(Context ctx) {
        return R.style.MyTheme;
    }


}
