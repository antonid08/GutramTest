package com.gurtam.antonenkoid.test.utils;

import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

/**
 * Contains static helper method for displaying general purpose dialogs such as information, warning, confirmation etc.
 *
 * @author antonenkoid
 */
public class Dialogs {

    /**
     * Shows information dialog with Ok button.
     *
     * @param context Context to use
     * @param title CharSequence title
     * @param text CharSequence text
     */
    public static void showOkDialog(Context context, CharSequence title, CharSequence text) {
        new AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(text)
            .setPositiveButton("Ok", null)
            .show();
    }

    /**
     * Shows information dialog with Ok button.
     *
     * @param context Context to use
     * @param title int title string resource
     * @param text int text string resource
     */
    public static void showOkDialog(Context context, @StringRes int title, @StringRes int text) {
        new AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(text)
            .setPositiveButton("Ok", null)
            .show();
    }

    /**
     * Shows information dialog with Ok button.
     *
     * @param context Context to use
     * @param textId resource id to display as a message
     */
    public static void showOkDialog(Context context, @StringRes int textId) {
        new AlertDialog.Builder(context)
            .setMessage(textId)
            .setPositiveButton("Ok", null)
            .show();
    }

    /**
     * Shows information dialog with Ok button.
     *
     * @param context Context to use
     * @param text message
     */
    public static void showOkDialog(Context context, String text) {
        new AlertDialog.Builder(context)
            .setMessage(text)
            .setPositiveButton("Ok", null)
            .show();
    }

    /**
     * Shows confirmation dialog with two buttons YES and NO.
     *
     * @param context Context to use
     * @param message resource id message to display
     * @param okStringId resource id of the text to display in the positive button
     * @param noStringId resource id of the text to display in the negative button
     * @param okClick click listener for the button Yes
     */
    public static void showOkNoDialog(Context context, @StringRes int message, @StringRes int okStringId,
        @StringRes int noStringId, DialogInterface.OnClickListener okClick) {

        new AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton(okStringId, okClick)
            .setNegativeButton(noStringId, null)
            .show();
    }

}
