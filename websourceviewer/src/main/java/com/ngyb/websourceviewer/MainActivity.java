package com.ngyb.websourceviewer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText mEt;
    private TextView mTv;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String str = (String) msg.obj;
            mTv.setText(str);
            super.handleMessage(msg);
        }
    };
    private ImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEt = findViewById(R.id.et);
        mTv = findViewById(R.id.tv);
        mIv = findViewById(R.id.iv);
    }

    public void look(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = mEt.getText().toString().trim();
                Log.e(TAG, "look: " + path);
                if (!TextUtils.isEmpty(path)) {
                    Log.e(TAG, "look: 111");
                    try {
                        URL url = new URL(path);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        int code = connection.getResponseCode();
                        Log.e(TAG, "look: " + code);
                        if (code == 200) {
                            InputStream is = connection.getInputStream();
                            String str = StreamUtils.StreamToString(is);
                            Log.e(TAG, "look: " + str);
                            Message message = mHandler.obtainMessage();
                            message.obj = str;
                            mHandler.sendMessage(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Log.e(TAG, "look: 222");
            }
        }).start();
    }

    public void lookPhoto(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = mEt.getText().toString().trim();
                Log.e(TAG, "look: " + path);
                if (!TextUtils.isEmpty(path)) {
                    Log.e(TAG, "look: 111");
                    try {
                        URL url = new URL(path);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        int code = connection.getResponseCode();
                        Log.e(TAG, "look: " + code);
                        if (code == 200) {
                            InputStream is = connection.getInputStream();
                            final Bitmap bitmap = BitmapFactory.decodeStream(is);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                   mIv.setImageBitmap(bitmap);
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Log.e(TAG, "look: 222");
            }
        }).start();
    }
}
