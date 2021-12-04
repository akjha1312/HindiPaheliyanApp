package com.aj.pehliyan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

public class AppRater {

    //    private final static int DAYS_UNTIL_PROMPT = 3;//Min number of days
    private final static int LAUNCHES_UNTIL_PROMPT = 3;//Min number of launches

    public static void app_launched(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
        if (prefs.getBoolean("dontshowagain", false)) {
            return;
        }

        SharedPreferences.Editor editor = prefs.edit();

        // Increment launch counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        // Get date of first launch
        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }

        // Wait at least n days before opening
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch +
                    (24 * 60 * 60 * 1000)) {
                showRateDialog(mContext, editor);
            }
        }

        editor.apply();
    }

    private static void showRateDialog(final Context mContext, final SharedPreferences.Editor editor) {

        String APP_TITLE = mContext.getResources().getString(R.string.app_name);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
        alertBuilder.setCancelable(false);

        alertBuilder.setMessage("If you enjoy using " + APP_TITLE + ", please take a moment to rate it. Thanks for your support!");
        alertBuilder.setPositiveButton("Rate now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editor != null) {
                    editor.putBoolean("dontshowagain", true);
                    editor.commit();
                }
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + mContext.getPackageName())));
                dialog.dismiss();
            }
        });

        alertBuilder.setNeutralButton("Remind later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertBuilder.setNegativeButton("Never", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (editor != null) {
                    editor.putBoolean("dontshowagain", true);
                    editor.commit();
                }
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }
}