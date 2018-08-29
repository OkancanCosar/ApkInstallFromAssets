package com.okan.assettenapkyukletme;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class ApkInstallerHelper {

    private String TAG = ":::ApkInstallerHelper";

    private ArrayList<ApkItem> apklist = new ArrayList<ApkItem>();
    private Context ctx = null;

    ApkInstallerHelper(Context ctx) {
        this.ctx = ctx;

        AssetManager assetManager = ctx.getAssets();
        try {
            String[] apkNameList = assetManager.list(ctx.getString(R.string.asset_apk_folder));

            for (String apkName : apkNameList) {
                diskeYaz(apkName);
            }
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    private void diskeYaz(String apkName) {
        InputStream inpt = null;
        OutputStream out = null;
        AssetManager assetManager = ctx.getAssets();

        try {
            inpt = assetManager.open(ctx.getString(R.string.asset_apk_folder) + "/" + apkName);
            out = new FileOutputStream(Environment.getExternalStorageDirectory() + ctx.getString(R.string.temp_apk_folder) + "." + apkName);

            byte[] buffer = new byte[9999];

            int read;
            while ((read = inpt.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }

            inpt.close();
            inpt = null;

            out.flush();
            out.close();
            out = null;
            Log.d(TAG, apkName + " isimli apk diske yazıldı.");

            File apkFile = new File(Environment.getExternalStorageDirectory() + ctx.getString(R.string.temp_apk_folder) + "." + apkName);

            if (apkFile.exists()) {
                Log.d(TAG, apkName + "/" + getPkgcNme(apkFile) + " ekliyorum.");
                apklist.add(new ApkItem(apkName, getPkgcNme(apkFile), false));
            }

        } catch (FileNotFoundException e) {
            Log.d(TAG, e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    private String getPkgcNme(File apkFile) {
        return ctx.getPackageManager().getPackageArchiveInfo(apkFile.getAbsolutePath(), 0).packageName;
    }

    private Boolean isApkInstalled(String pkgcName) {
        PackageManager pm = ctx.getPackageManager();
        try {
            pm.getPackageInfo(pkgcName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.toString());
        }
        return false;
    }

    public Boolean allAppInstalled() {
        Boolean state = true;

        for (ApkItem item : apklist) {
            if (!isApkInstalled(item.getPkgcName())) {
                state = false;
                Log.d(TAG, item.getApkName() + " yüklenmemiş.");
                break;
            } else {
                item.setInstalled(true);
                File pk = new File(Environment.getExternalStorageDirectory() + ctx.getString(R.string.temp_apk_folder) + "." + item.getApkName());
                Log.d(TAG, item.getApkName() + "yüklü siliyorum.");
                pk.delete();
            }
        }

        return state;
    }


    public ArrayList<ApkItem> getListe() {
        return apklist;
    }


}
