package com.ngyb.oldnetlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.HttpGet;
import com.loopj.android.http.RequestParams;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntityEnclosingRequest;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText mUsername;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUsername = findViewById(R.id.et_username);
        mPassword = findViewById(R.id.et_password);
    }

    public void get(View view) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                HttpClientGet();
//            }
//        }).start();
        AsyncHttpClientGet();
    }

    public void Post(View view) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                HttpClientPost();
//            }
//        }).start();
        AsyncHttpClientPost();
    }

    public void AsyncHttpClientPost() {
        String username = mUsername.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String path = "http://192.168.0.103:8080/login/loginServlet";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("username", username);
        params.add("password", password);
        client.post(path, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                showToast(new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(TAG, "onFailure: " + error.toString() + "===" + statusCode);
            }
        });
    }

    public void HttpClientPost() {
        try {
            String username = mUsername.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            String path = "http://192.168.0.103:8080/login/loginServlet";
            HttpClient client = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(path);
            List<BasicNameValuePair> pairs = new ArrayList<>();
            BasicNameValuePair pair1 = new BasicNameValuePair("username", username);
            BasicNameValuePair pair2 = new BasicNameValuePair("password", password);
            pairs.add(pair1);
            pairs.add(pair2);
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs);
            httpPost.setEntity(entity);
            HttpResponse response = client.execute(httpPost);
            InputStream content = response.getEntity().getContent();
            String s = StreamUtils.StreamToString(content);
            showToast(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void HttpUrlConnectPost() {
        try {
            String username = mUsername.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            String path = "http://192.168.0.103:8080/login/loginServlet";
            String data = "username=" + username + "&&password=" + password;
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setDoOutput(true);
            connection.getOutputStream().write(data.getBytes());
            int code = connection.getResponseCode();
            if (code == 200) {
                InputStream is = connection.getInputStream();
                String s = StreamUtils.StreamToString(is);
                showToast(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void AsyncHttpClientGet() {
        String username = mUsername.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String path = "http://192.168.0.103:8080/login/loginServlet?username=" + username + "&&password=" + password;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(path, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                showToast(new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(TAG, "onFailure: " + error.toString() + "===" + statusCode);
            }
        });
    }

    public void HttpClientGet() {
        try {
            String username = mUsername.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            String path = "http://192.168.0.103:8080/login/loginServlet?username=" + username + "&&password=" + password;
            HttpGet httpGet = new HttpGet(path);
            HttpClient client = new DefaultHttpClient();
            HttpResponse res = client.execute(httpGet);
            InputStream stream = res.getEntity().getContent();
            String s = StreamUtils.StreamToString(stream);
            showToast(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void HttpUrlConnectGet() {
        try {
            String username = mUsername.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            String path = "http://192.168.0.103:8080/login/loginServlet?username=" + username + "&&password=" + password;
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            int code = connection.getResponseCode();
            Log.e(TAG, "HttpUrlConnectGet: " + code);
            if (code == 200) {
                InputStream is = connection.getInputStream();
                String s = StreamUtils.StreamToString(is);
                showToast(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showToast(final String data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
