package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import org.jsoup.Jsoup;

public class Links extends AppCompatActivity{

    EditText ed1;
//    TextToSpeech t1;
    Button purposeBtn;
    Button analogyBtn;
    Button howItWorksBtn;
    Button linksBtn;
//    ImageButton b1;
//    TextView resultText;
    ImageButton backBtn;
//    RecyclerView recyclerView;
    private static final String TAG = "links";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_links);
        ed1 = findViewById(R.id.purposeTitle);
        purposeBtn = findViewById(R.id.purposeText);
        analogyBtn = findViewById(R.id.analogyText);
        howItWorksBtn = findViewById(R.id.howItWorksText);
        linksBtn = findViewById(R.id.linksText);
//        b1 = findViewById(R.id.b1);
//        resultText = findViewById(R.id.resultText);
        backBtn = findViewById(R.id.analogyBackBtn);
//        recyclerView = findViewById(R.id.links);


        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Links.this, result.class));
            }
        });
        ed1.setText(html2text(pref.getString("name",null)));
        purposeBtn.setText("Purpose");
        analogyBtn.setText("Analogy");
        howItWorksBtn.setText("How It Works");
        linksBtn.setText("Links");


        purposeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (Links.this, Purpose.class);
                startActivity(i);
            }
        });

        analogyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (Links.this, Analogy.class);
                startActivity(i);
            }
        });

        howItWorksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (Links.this, HowItWorks.class);
                startActivity(i);
            }
        });

        linksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent (Links.this, conceptLinks.class);
                startActivity(i);
            }
        });
//        resultText.setText(definition);
//        MyAdapter adapter = new MyAdapter(this, value, key, (MyAdapter.onClickListener) this);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int status) {
//                if (status != TextToSpeech.ERROR) {
//                    t1.setLanguage(Locale.UK);
//                }
//            }
//        });
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String toSpeak = resultText.getText().toString();
//                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
//            }
//        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Links.this, MainActivity.class));
    }

//    public void onPause() {
//        if (t1 != null) {
//            t1.stop();
//            t1.shutdown();
//        }
//        super.onPause();
//    }


//    @Override
//    public void onClick(int position) {
//        Bundle bundle = getIntent().getExtras();
//        ArrayList<String> key = bundle.getStringArrayList("key");
//        Log.e(TAG, key.get(position));
//        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(key.get(position))));
//    }
    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }
}