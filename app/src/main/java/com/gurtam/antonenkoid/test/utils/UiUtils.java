package com.gurtam.antonenkoid.test.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Utility methods for UI.
 *
 * @author antonenkoid
 */
public class UiUtils {

    /**
     * Inflates view by ViewGroup
     *
     * @param layoutId id of layout for view
     * @param root     ViewGroup к которой будет добавлен View.
     */
    public static View inflate(ViewGroup root, int layoutId) {
        return LayoutInflater.from(root.getContext()).inflate(layoutId, root, false);
    }

    /**
     * Inflates view
     *
     * @param context  android context
     * @param layoutId id of layout for view
     */
    public static <T extends View> T inflate(Context context, int layoutId) {
        return (T) LayoutInflater.from(context).inflate(layoutId, null);
    }

    /**
     * Sets visibility of views.
     *
     * @param visible boolean <code>true</code> if views should be visible
     * @param views   View[] views
     */
    public static void setVisibility(boolean visible, View... views) {
        for (View view : views) {
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

}
