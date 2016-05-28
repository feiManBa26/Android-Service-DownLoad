package com.ycl.service_download;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    String download_url="http://shouji.360tpcdn.com/160329/a9037075b8d3aa98fbf6115c54a5b895/com.alensw.PicFolder_4722404.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void bt_start_service(View view){
        Intent intent=new Intent(this,DownLoadService.class);
        intent.putExtra("download_url",download_url);
        startService(intent);
    }
}
