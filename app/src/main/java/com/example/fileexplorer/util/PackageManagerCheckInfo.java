package com.example.fileexplorer.util;

import android.content.Context;
import android.content.pm.PackageManager;

public class PackageManagerCheckInfo {
    public boolean isAppInstalled(String packageName, Context context) {

        PackageManager pm = context.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}
