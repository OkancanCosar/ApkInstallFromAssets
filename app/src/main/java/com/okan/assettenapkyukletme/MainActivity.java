package com.okan.assettenapkyukletme;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    ApkInstallerHelper apkHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apkHelper = new ApkInstallerHelper(getApplicationContext());


    }

    public void BASLA(View view) {
        installOtherApks();
    }


    private void installOtherApks() {
        for (ApkItem item : apkHelper.getListe()) {
            if (!item.getInstalled()) {
                Log.d(TAG, item.getPkgcName() + " yüklenmeye başlıyor.");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(
                        new File(Environment.getExternalStorageDirectory() + getString(R.string.temp_apk_folder) + "." + item.getApkName())),
                        "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, 1234);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1234) {
            if (!apkHelper.allAppInstalled()) installOtherApks();
        }
    }
}



















