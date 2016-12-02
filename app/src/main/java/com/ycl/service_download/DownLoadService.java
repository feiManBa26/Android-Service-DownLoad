package com.ycl.service_download;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;

/**
 * 作者：yaochangliang on 2016/5/28 19:11
 * 邮箱：yaochangliang159@sina.com
 */
public class DownLoadService extends Service {
    String download_url;
    String savePath= Environment.getExternalStorageDirectory()+"/liulan.apk";
    private int requestCode = (int) SystemClock.uptimeMillis();
    private NotifyUtil currentNotify;
    File mFile;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mFile=new File(savePath);
        download_url=intent.getStringExtra("download_url");
        Log.e("test","执行onStartCommand");
        //设置想要展示的数据内容
        Intent intent_noti = new Intent();
        intent_noti.setAction(Intent.ACTION_VIEW);
        //文件的类型，从tomcat里面找
        intent_noti.setDataAndType(Uri.fromFile(mFile), "application/vnd.android.package-archive");
        PendingIntent rightPendIntent = PendingIntent.getActivity(this,
                requestCode, intent_noti, PendingIntent.FLAG_UPDATE_CURRENT);
        int smallIcon = R.drawable.xc_smaillicon;
        String ticker = "正在更新快图浏览";
        //实例化工具类，并且调用接口
        currentNotify= new NotifyUtil(this, 7);
        currentNotify.notify_progress(rightPendIntent, smallIcon, ticker, "快图浏览升级程序", "正在下载中",
                false, false, false, download_url, savePath, new NotifyUtil.DownLoadListener() {
                    @Override
                    public void OnSuccess(File file) {
                        mFile=file;
                        DownLoadService.this.stopSelf();
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString(MyBroadCast.DOWNLOAD_TYPE,"下载");
                        bundle.putSerializable(MyBroadCast.DOWNLOAD_FILE_TYPE,mFile);
                        intent.putExtras(bundle);
                        intent.setAction("com.ycl.service_download.MyBroadCast");
                        getApplication().sendBroadcast(intent);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {

                    }
                });

        return super.onStartCommand(intent, flags, startId);

    }





}
