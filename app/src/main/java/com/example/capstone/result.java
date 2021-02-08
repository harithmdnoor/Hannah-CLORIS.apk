package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Locale;

import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

public class result extends AppCompatActivity {
    TextToSpeech t1;
    TextView ed1;
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
        ed1 = findViewById(R.id.purposeTitle);
        b1 = findViewById(R.id.analogyTTS);
        resultText = findViewById(R.id.analogyResultText);
        backBtn = findViewById(R.id.analogyBackBtn);
        seeMoreBtn = findViewById(R.id.seeMoreBtn);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();

        final ArrayList<String> key = new ArrayList<String>();
        final ArrayList<String> value = new ArrayList<String>();


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(result.this, MainActivity.class));
            }
        });

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String tempString = pref.getString("query",null);
        final String query = tempString.substring(0, 1).toUpperCase() + tempString.substring(1).toLowerCase();

        final String url = String.format("https://hannah-cloris.azurewebsites.net/api/Concepts");
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i=0; i<jsonArray.length();i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                if ((object.get("name").toString().toUpperCase()).contains(query.toUpperCase())) {
                                    ed1.setText(HtmlCompat.fromHtml(object.getString("name"), HtmlCompat.FROM_HTML_MODE_LEGACY));
                                    Log.d(TAG, "onResponse: " + object.toString());
                                    key.add("Name");
                                    key.add("Description");
                                    key.add("Purpose");
                                    key.add("Analogy");
                                    key.add("How It Works");
                                    key.add("Links");

                                    value.add(object.getString("name"));
                                    value.add(object.getString("description"));
                                    value.add(object.getString("purpose"));
                                    value.add(object.getString("analogy"));
                                    value.add(object.getString("howItWorks"));
                                    value.add(object.getString("links"));

                                    resultText.setText(HtmlCompat.fromHtml(object.getString("description"), HtmlCompat.FROM_HTML_MODE_LEGACY));

                                    seeMoreBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {


                                            editor.putString("name",value.get(0));
                                            editor.putString("description",value.get(1));
                                            editor.putString("purpose",value.get(2));
                                            editor.putString("analogy",value.get(3));
                                            editor.putString("howItWorks",value.get(4));
                                            editor.putString("links",value.get(5));
                                            Log.e(TAG, value.get(5) );
                                            editor.commit();
                                            Intent i = new Intent (result.this, Links.class);
                                            startActivity(i);
                                        }
                                    });
                                    if(resultText.getText() == null){
                                        seeMoreBtn.setVisibility(View.GONE);
                                    }
                                    else {
                                        seeMoreBtn.setVisibility(View.VISIBLE);
                                    }
                                }
//                                else{
//                                    ed1.setText("Concept not found");
//                                    resultText.setText("Concept not found");
//                                    seeMoreBtn.setVisibility(View.GONE);
//                                }

                            }
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
    public static String html2text(String html) {
        return Jsoup.parse(html).text();
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