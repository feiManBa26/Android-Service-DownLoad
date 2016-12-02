package com.ycl.service_download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.File;

/**
 * Created by ejiang on 2016-12-02.
 */
public class MyBroadCast extends BroadcastReceiver {
    public static final String DOWNLOAD_TYPE = "downloadType";
    public static final String DOWNLOAD_FILE_TYPE ="downloadFileType";
    private goToInstallAppListener mGoToInstallAppListener;



    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            String type = bundle.getString(DOWNLOAD_TYPE);
            File file = (File) bundle.getSerializable(DOWNLOAD_FILE_TYPE);
            if(type!=null){
                switch (type){
                    case "下载":
                        if(mGoToInstallAppListener!=null){
                            mGoToInstallAppListener.goToInstall(file);
                        }
                        break;
                }
            }
        }

    }
    public void setGoToInstallAppListener(goToInstallAppListener goToInstallAppListener) {
        mGoToInstallAppListener = goToInstallAppListener;
    }

    public interface  goToInstallAppListener{
        void goToInstall(File mFile);
    }

}
