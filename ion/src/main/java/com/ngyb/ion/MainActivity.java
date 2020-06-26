package com.ngyb.ion;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Icon;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView tv;
    private static String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        ActivityCompat.requestPermissions(this, permissions, 1);
    }

    public void click1(View view) {
        Ion
                .with(this)
                .load(API.TEST)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e == null) {
                            String name = Thread.currentThread().getName();
                            Log.e(TAG, "onCompleted: " + name);
                            tv.setText(result);
                        } else {
                            Log.e(TAG, "onCompleted: " + e.getMessage());
                        }
                    }
                });
    }

    public void click2(View view) {
        Ion
                .with(this)
                .load(API.LOGIN)
                .setBodyParameter("username", "nangongyibin")
                .setBodyParameter("password", "123456")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e == null) {
                            tv.setText(result);
                        }
                    }
                });
    }

    public void click3(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "dog.jpg");
        Log.e(TAG, "click3: " + file.getPath());
        Ion
                .with(this)
                .load(API.UPLOAD)
                .uploadProgress(new ProgressCallback() {
                    @Override
                    public void onProgress(long downloaded, long total) {
                        Log.e(TAG, "onProgress: " + (downloaded * 100 / total));
                    }
                })
                .setMultipartFile("file", file)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (e == null) {
                            tv.setText(result);
                        }
                    }
                });
    }

    public void click4(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "a.jpg");
        Log.e(TAG, "click3: " + file.getPath());
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Ion
                .with(this)
                .load(API.IMAGE)
                .progress(new ProgressCallback() {
                    @Override
                    public void onProgress(long downloaded, long total) {
                        Log.e(TAG, "onProgress: " + (downloaded * 100 / total));
                    }
                })
                .write(file)
                .setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File result) {
                        if (e == null) {
                            Bitmap bitmap = BitmapFactory.decodeFile(result.getAbsolutePath());
                            tv.setBackgroundDrawable(new BitmapDrawable(bitmap));
                            Log.e(TAG, "onCompleted: " + result);
                        } else {
                            Log.e(TAG, "onCompleted: " + e.getMessage());
                        }
                    }
                });
    }
}
