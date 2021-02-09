package com.example.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.jsoup.Jsoup;

import java.util.Locale;

public class conceptLinks extends AppCompatActivity  {
    TextView ed1;
//    TextToSpeech t1;
//    ImageButton b1;
    ImageButton backBtn;
    TextView resultText;

    private static final String TAG = "concept links";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concept_links);
        ed1 = findViewById(R.id.conceptLinksTitle);
//        b1 = findViewById(R.id.conceptLinksTTS);
        backBtn = findViewById(R.id.conceptLinksBackBtn);
        resultText =findViewById(R.id.conceptLinksText);
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode


        ed1.setText("Links");
//        resultText.loadData(pref.getString("links",null),"text/html; charset=utf-8", "utf-8");
        resultText.setText((HtmlCompat.fromHtml(pref.getString("links",null), HtmlCompat.FROM_HTML_MODE_LEGACY)));
//        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int status) {
//                if(status != TextToSpeech.ERROR) {
//                    t1.setLanguage(Locale.UK);
//                }
//            }
//        });
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String toSpeak = html2text(pref.getString("links",null));
//                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
//            }
//        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (conceptLinks.this, Links.class);
                startActivity(i);
            }
        });

    }
    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

}