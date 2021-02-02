package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class Links extends AppCompatActivity implements MyAdapter.onClickListener {

    EditText ed1;
    TextToSpeech t1;
    ImageButton b1;
    TextView resultText;
    ImageButton backBtn;
    RecyclerView recyclerView;
    private static final String TAG = "links";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_links);
        ed1 = findViewById(R.id.ed1);
        b1 = findViewById(R.id.b1);
        resultText = findViewById(R.id.resultText);
        backBtn = findViewById(R.id.backBtn);
        recyclerView = findViewById(R.id.links);

        Bundle bundle = getIntent().getExtras();
        String definition = bundle.getString("definition");
        String name = bundle.getString("name");
        ArrayList<String> data1 = bundle.getStringArrayList("data1");
        ArrayList<String> data2 = bundle.getStringArrayList("data2");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Links.this, MainActivity.class));
            }
        });

        ed1.setText(name);
        resultText.setText(definition);
        MyAdapter adapter = new MyAdapter(this, data1, data2, (MyAdapter.onClickListener) this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
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
        startActivity(new Intent(Links.this, MainActivity.class));
    }

    public void onPause() {
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }


    @Override
    public void onClick(int position) {
        Bundle bundle = getIntent().getExtras();
        ArrayList<String> data1 = bundle.getStringArrayList("data1");
        Log.e(TAG, data1.get(position));
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(data1.get(position))));
    }
}