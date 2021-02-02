package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Locale;

import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class result extends AppCompatActivity {
    TextToSpeech t1;
    EditText ed1;
    ImageButton b1;
    TextView resultText;
    ImageButton backBtn;
    Button seeMoreBtn;
    private static final String TAG = "result";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new NukeSSLCerts().nuke();

        setContentView(R.layout.activity_result);
        ed1 = findViewById(R.id.ed1);
        b1 = findViewById(R.id.b1);
        resultText = findViewById(R.id.resultText);
        backBtn = findViewById(R.id.backBtn);
        seeMoreBtn = findViewById(R.id.seeMoreBtn);

        final ArrayList<String> data1 = new ArrayList<String>();
        final ArrayList<String> data2 = new ArrayList<String>();


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(result.this, MainActivity.class));
            }
        });

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        Bundle bundle = getIntent().getExtras();
        final String query = bundle.getString("query");
        final String url = String.format("https://hannah-cloris.com/?Category=&ConceptName=%s", query);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            Document doc = Jsoup.parse(response);
                            Element x = doc;
                            ed1.setText(query);
                            resultText.setText(x.select("dd").get(1).text());

                            data1.add(x.select("dd").get(2).text());
                            data1.add(x.select("dd").get(3).text());
                            data1.add(x.select("dd").get(4).text());


                            data2.add(x.select("dt").get(2).text());
                            data2.add(x.select("dt").get(3).text());
                            data2.add(x.select("dt").get(4).text());

                            if(resultText.getText() == null){
                                seeMoreBtn.setVisibility(View.GONE);
                            }
                            else {
                                seeMoreBtn.setVisibility(View.VISIBLE);
                            }
                            Log.e(TAG, x.select("dt").get(2).text());
                            Log.e(TAG, x.select("dd").get(2).text());
                            Log.e(TAG, x.select("dd").get(3).text());
                            Log.e(TAG, x.select("dd").get(4).text());


                            seeMoreBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent (result.this, Links.class);
                                    i.putExtra("name", query);
                                    i.putExtra("definition",resultText.getText());
                                    i.putExtra("data1",data1);
                                    i.putExtra("data2",data2);
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(browserIntent);
                                }
                            });



                        }catch (Exception exception){
                            ed1.setText(query);
                            resultText.setText(String.format("Concept \"%s\" not found", query));
                            seeMoreBtn.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ",error );
                ed1.setText("Concept not found");
                resultText.setText("Concept not found");
                seeMoreBtn.setVisibility(View.GONE);
            }

        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = resultText.getText().toString();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(result.this, MainActivity.class));
    }
    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }
}