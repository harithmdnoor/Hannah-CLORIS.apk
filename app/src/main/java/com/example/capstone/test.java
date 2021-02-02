package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.VideoView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class test extends AppCompatActivity {
//    private TextView text_view_result;
    private VideoView videoView;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new NukeSSLCerts().nuke();
        setContentView(R.layout.activity_test);
//        text_view_result = findViewById(R.id.text_view_result);
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://hannah-cloris.com/?Category=&ConceptName=trojan";

//        // Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        Document doc = Jsoup.parse(response);
////                        Log.e(TAG, response);
//                        Element x = doc.select("dd").get(1);
//                        text_view_result.setText("Response is: "+ x.text());
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                text_view_result.setText("That didn't work!"+error);
//            }
//        });
//
//        // Add the request to the RequestQueue.
//        queue.add(stringRequest);
        VideoView videoView = findViewById(R.id.videoView);
        videoView.setVideoPath("https://www.youtube.com/watch?v=5smN3t1dlZA");
        videoView.start();
    }
}