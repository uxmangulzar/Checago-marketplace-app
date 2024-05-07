package com.softarena.checagocoffee.Helper;


import android.app.Activity;


import androidx.multidex.MultiDexApplication;

import dmax.dialog.SpotsDialog;

public class GlobalClass extends MultiDexApplication {

    public static SpotsDialog dialog;
    private static boolean isLoading = false;
    public static void showLoading(Activity activity, String message) {
        if (!isLoading) {
            isLoading = true;
            dialog = new SpotsDialog(activity, message);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
        }
    }

    public static void dismissLoading() {
        try {
            if (isLoading) {
                isLoading = false;
                dialog.dismiss();
            }
        } catch (Exception e) {
            isLoading = true;
        }
    }
}
