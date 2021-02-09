package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.w3c.dom.Text;

import java.util.Locale;

public class Analogy extends AppCompatActivity {
    TextToSpeech t1;
    ImageButton backBtn;
    TextView ed1;
    ImageButton b1;
    WebView resultText;
    private static final String TAG = "Analogy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analogy);
        ed1 = findViewById(R.id.analogyTitle);
        b1 = findViewById(R.id.analogyTTS);
        resultText = findViewById(R.id.analogyResultText);
        backBtn = findViewById(R.id.analogyBackBtn);

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode


        ed1.setText("Analogy");
        resultText.loadData(pref.getString("analogy",null),"text/html", "UTF-8");
        resultText.setHorizontalScrollBarEnabled(false);
        resultText.setOnTouchListener(new View.OnTouchListener() {
            float m_downX;
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getPointerCount() > 1) {
                    //Multi touch detected
                    return true;
                }

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        // save the x
                        m_downX = event.getX();
                        break;
                    }
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP: {
                        // set x so that it doesn't move
                        event.setLocation(m_downX, event.getY());
                        break;
                    }

                }
                return false;
            }
        });        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
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
                String toSpeak = html2text(pref.getString("analogy",null));
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (Analogy.this, Links.class);
                startActivity(i);
            }
        });

    }
    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }
}