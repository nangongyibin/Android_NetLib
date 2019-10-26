package com.ngyb.filedownload;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText mEt;
    private ProgressBar mPb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEt = findViewById(R.id.et);
        mPb = findViewById(R.id.pb);
        init();
    }

    private void init() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, 7219);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.MOUNT_FORMAT_FILESYSTEMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MOUNT_FORMAT_FILESYSTEMS}, 7219);
        }
    }


    public void download(View view) {
        String path = mEt.getText().toString().trim();
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.download(path, getFileName(path), true,new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                Toast.makeText(MainActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                Log.e(TAG, "onLoading: total:"+total+"==current:"+current );
                mPb.setMax((int)total);
                mPb.setMax((int)current);
                super.onLoading(total, current, isUploading);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {
            if (permissions[i] == Manifest.permission.WRITE_EXTERNAL_STORAGE) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "读写文件的权限获取成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "读写文件的权限获取失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public String getFileName(String url) {
        String[] filenames = url.split("/");
        int length = filenames.length;
        String path = getFilesDir().getPath();
        String localPath = path + "/" + filenames[length - 1];
        Log.e(TAG, "getFileName: " + localPath);
        return localPath;
    }
}
