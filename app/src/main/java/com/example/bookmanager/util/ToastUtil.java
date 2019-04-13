package com.example.bookmanager.util;

import android.content.Context;
import android.widget.Toast;

/**
 * display Toast utility class
 */
public class ToastUtil {

    /**
     * display Toast with specified string
     * @param context
     * @param content
     */
    public static void tost(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    /**
     * display Toast with specified string resource id
     * @param context
     * @param contentId
     */
    public static void tost(Context context, int contentId) {
        Toast.makeText(context, contentId, Toast.LENGTH_SHORT).show();
    }
}
