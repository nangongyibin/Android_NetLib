package com.ngyb.glide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = findViewById(R.id.iv);
        Glide
                .with(this)
                .load("http://it.nangongyibin.com:8080/resource/a.jpg")
                .centerCrop()
//                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
//                .crossFade(500)
                .into(iv);
    }
}
