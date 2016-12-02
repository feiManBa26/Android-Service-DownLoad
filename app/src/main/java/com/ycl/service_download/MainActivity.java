package com.ycl.service_download;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    String download_url="http://shouji.360tpcdn.com/160329/a9037075b8d3aa98fbf6115c54a5b895/com.alensw.PicFolder_4722404.apk";
    private MyBroadCast mMyBroadCast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerBroadCast();
    }

    private void registerBroadCast() {
        mMyBroadCast = new MyBroadCast();
        IntentFilter intentFilter = new IntentFilter("com.ycl.service_download.MyBroadCast");
        registerReceiver(mMyBroadCast,intentFilter);

        mMyBroadCast.setGoToInstallAppListener(new MyBroadCast.goToInstallAppListener() {
            @Override
            public void goToInstall(File mFile) {
                installApp(mFile);
            }
        });

    }

    public void bt_start_service(View view){
        Intent intent=new Intent(this,DownLoadService.class);
        intent.putExtra("download_url",download_url);
        startService(intent);
    }

    private void installApp(File appFile){
        //创建URI
        Uri uri = Uri.fromFile(appFile);
        //创建Intent意图
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //设置Uri和类型
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        //执行意图进行安装
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBroadCast();
    }

    private void unBroadCast() {
        if(mMyBroadCast!=null){
            unregisterReceiver(mMyBroadCast);
        }
    }
}
