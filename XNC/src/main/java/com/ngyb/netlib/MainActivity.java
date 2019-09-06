package com.ngyb.netlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView mLv;
    private List<News> mNews;
    private MyAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLv = findViewById(R.id.lv);
        initData();
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://it.nangongyibin.com:8080/resource/news.xml");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    int code = conn.getResponseCode();
                    Log.e(TAG, "initData: "+code );
                    if (code == 200) {
                        InputStream is = conn.getInputStream();
                        mNews = NewsXmlUtils.parseXml(is);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (myadapter ==null){
                                    myadapter = new MyAdapter();
                                    mLv.setAdapter(myadapter);
                                }else{
                                    myadapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                } catch (IOException e) {
                    Log.e(TAG, "initData: "+e.getLocalizedMessage().toString() );
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mNews != null) {
                return mNews.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this, R.layout.item, null);
            }
            TextView tv1 = convertView.findViewById(R.id.tv1);
            TextView tv2 = convertView.findViewById(R.id.tv2);
            News news = mNews.get(position);
            Log.e(TAG, "getView: "+news.toString() );
            SmartImageView siv = convertView.findViewById(R.id.siv);
            siv.setImageUrl(news.getImage());
            tv1.setText(news.getTitle());
            tv2.setText(news.getDescription());
            return convertView;
        }
    }
}
