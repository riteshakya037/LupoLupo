package com.lupolupo.android.common;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * @author Ritesh Shakya
 */
public class DialogUtils {
    private static AlertDialog dialog;

    public static void showDialog(Context context, String title, String message) {
        if (dialog != null) {
            if (dialog.isShowing()) {
                return;
            }
        }
        dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public static void dismissProgressDialog() {
        if (dialog != null) {
            if (dialog.isShowing()) {

                dialog.dismiss();
            }
        }
    }
}
