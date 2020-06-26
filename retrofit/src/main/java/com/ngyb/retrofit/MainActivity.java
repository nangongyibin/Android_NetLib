package com.ngyb.retrofit;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView tv;
    private static String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private RetrofitApi retrofitApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        ActivityCompat.requestPermissions(this, permissions, 1);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.SEVER_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitApi = retrofit.create(RetrofitApi.class);
    }

    public void click1(View view) {
        Call<People> call = retrofitApi.getTest();
        call.enqueue(new Callback<People>() {
            @Override
            public void onResponse(Call<People> call, Response<People> response) {
                People body = response.body();
                tv.setText(body.getNickname());
            }

            @Override
            public void onFailure(Call<People> call, Throwable t) {

            }
        });
    }

    public void click2(View view) {
        Call<User> call = retrofitApi.login("nangongyibin", "123456");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                tv.setText(response.body().getUsertoken());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public void click3(View view) {
        Call<ResponseBody> download = retrofitApi.download();
        download.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ResponseBody body = response.body();
                InputStream inputStream = body.byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                tv.setBackgroundDrawable(new BitmapDrawable(bitmap));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void click4(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "a.jpg");
        File file1 = new File(Environment.getExternalStorageDirectory(), "dog.jpg");
        RequestBody responseBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
        RequestBody responseBody1 = RequestBody.create(MediaType.parse("image/jpeg"), file1);
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("file\";filename=\"" + file.getName(), responseBody);
        map.put("file\";filename=\"" + file1.getName(), responseBody1);
        Call<ResponseBody> call = retrofitApi.upload(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String string = response.body().string();
                    tv.setText(string);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
}
