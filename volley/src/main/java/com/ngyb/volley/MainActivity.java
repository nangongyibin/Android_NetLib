package com.ngyb.volley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView tv;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        queue = Volley.newRequestQueue(this);
    }

    public void click1(View view) {
        StringRequest stringRequest = new StringRequest(API.TEST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tv.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error.getLocalizedMessage());
            }
        });
        queue.add(stringRequest);
    }

    public void click2(View view) {
        MyQuest myQuest = new MyQuest(Request.Method.POST, API.LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tv.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: " + error.getLocalizedMessage());
            }
        });
        HashMap<String, String> map = new HashMap<>();
        map.put("username", "nangongyibin");
        map.put("password", "123456");
        myQuest.setParam(map);
        queue.add(myQuest);
    }

    private class MyQuest extends StringRequest {
        private Map<String, String> param = null;

        public MyQuest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }

        public MyQuest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(url, listener, errorListener);
        }

        public Map<String, String> getParam() throws AuthFailureError {
            return param;
        }

        public void setParam(Map<String, String> param) {
            this.param = param;
        }
    }
}
