package com.ngyb.fresco;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private SimpleDraweeView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "onCreate: ");
        iv = findViewById(R.id.iv);
        Uri uri = Uri.parse("http://it.nangongyibin.com:8080/resource/a.jpg");
        iv.setImageURI(uri);
//        iv.setImageURI("http://it.nangongyibin.com:8080/resource/a.jpg");
    }
}
