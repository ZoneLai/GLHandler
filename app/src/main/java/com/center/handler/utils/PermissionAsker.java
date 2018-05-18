package com.center.handler.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

public class PermissionAsker {

    private Runnable mOkRun = RUN;
    private Runnable mDeniRun = RUN;
    private int mReqCode = 1;

    public PermissionAsker() {
    }

    public PermissionAsker(int code, Runnable ok, Runnable deni) {
        this.mReqCode = code;
        this.mOkRun = ok;
        this.mDeniRun = deni;
    }

    public void setReqCode(int code) {
        this.mReqCode = code;
    }

    public void setSuccedCallback(Runnable run) {
        this.mOkRun = run;
    }

    public void setFailedCallback(Runnable run) {
        this.mDeniRun = run;
    }

    public PermissionAsker askPermission(Activity context, String... permission) {
        int result = 0;
        for (String p : permission) {
            result += ActivityCompat.checkSelfPermission(context, p);
        }
        if (result == 0) {
            mOkRun.run();
        } else {
            ActivityCompat.requestPermissions(context, permission, mReqCode);
        }
        return this;
    }

    public void onRequestPermissionsResult(int[] grantResults) {
        boolean b = true;
        for (int a : grantResults) {
            b &= (a == PackageManager.PERMISSION_GRANTED);
        }
        if (grantResults.length > 0 && b) {
            mOkRun.run();
        } else {
            mDeniRun.run();
        }
    }


    private static final Runnable RUN = new Runnable() {
        @Override
        public void run() {

        }
    };


}
