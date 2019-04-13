package com.example.bookmanager.util;

import android.support.design.widget.Snackbar;

import com.example.bookmanager.ui.base.BaseActivity;

/**
 * display Snackbar utility class
 */
public class SnackUtil {

    /**
     * display Snackbar with specified string content
     *
     * @param activity
     * @param content
     */
    public static void snack(BaseActivity activity, String content) {
        Snackbar.make(activity.getWindow().getDecorView(), content, Snackbar.LENGTH_SHORT);
    }

    /**
     * display Snackbar with specified string resource id
     *
     * @param activity
     * @param contentId
     */
    public static void snack(BaseActivity activity, int contentId) {
        Snackbar.make(activity.getWindow().getDecorView(), contentId, Snackbar.LENGTH_SHORT);
    }
}
