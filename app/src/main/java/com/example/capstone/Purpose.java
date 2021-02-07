package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.jsoup.Jsoup;

import java.util.Locale;

public class Purpose extends AppCompatActivity {
    TextToSpeech t1;
    ImageButton backBtn;
    TextView ed1;
    ImageButton b1;
    TextView resultText;
    private static final String TAG = "Purpose";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purpose);
        ed1 = findViewById(R.id.purposeTitle);
        b1 = findViewById(R.id.purposeTTS);
        resultText = findViewById(R.id.purposeResultText);
        backBtn = findViewById(R.id.purposeBackBtn);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode


        ed1.setText(HtmlCompat.fromHtml(pref.getString("name",null), HtmlCompat.FROM_HTML_MODE_LEGACY));
        resultText.setText(HtmlCompat.fromHtml(pref.getString("purpose",null), HtmlCompat.FROM_HTML_MODE_LEGACY));
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
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (Purpose.this, Links.class);
                startActivity(i);
            }
        });

    }
    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }
}