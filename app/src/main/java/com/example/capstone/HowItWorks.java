package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Locale;

public class HowItWorks extends AppCompatActivity {
    TextToSpeech t1;
    ImageButton backBtn;
    TextView ed1;
    ImageButton b1;
    WebView resultText;
    private static final String TAG = "How It Works";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_it_works);
        ed1 = findViewById(R.id.howItWorksTitle);
        b1 = findViewById(R.id.howItWorksTTS);
        resultText = findViewById(R.id.howItWorksResultText);
        backBtn = findViewById(R.id.howItWorksBackBtn);

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode


        ed1.setText("How It Works");
        Log.e(TAG, pref.getString("howItWorks",null) );
        resultText.loadData(pref.getString("howItWorks",null),"text/html", "UTF-8");
//        resultText.setText(HtmlCompat.fromHtml(pref.getString("howItWorks",null), HtmlCompat.FROM_HTML_MODE_LEGACY));
//        resultText.setMovementMethod(new ScrollingMovementMethod());
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
                String toSpeak = html2text(pref.getString("howItWorks",null));
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (HowItWorks.this, Links.class);
                startActivity(i);
            }
        });

    }
    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }
}