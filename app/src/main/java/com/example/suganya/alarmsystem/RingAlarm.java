package com.example.suganya.alarmsystem;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import static com.example.suganya.alarmsystem.AudioPlay.PlayAudio;

public class RingAlarm extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    TextView alarmTime,alarmName;
    ImageView stopAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_alarm);
        Intent serviceIntent = new Intent(RingAlarm.this,CheckAlarm.class);
        stopService(serviceIntent);

        final String toneName = getIntent().getExtras().getString("tone");
        final String alaTime = getIntent().getExtras().getString("alarmTime");
        final String alaName = getIntent().getExtras().getString("alarmName");

        alarmTime = findViewById(R.id.alarmTime);
        alarmName = findViewById(R.id.alarmName);
        stopAlarm = findViewById(R.id.stopAlarm);

        alarmTime.setText(alaTime);
        alarmName.setText(alaName);

        Log.e("tone :::::::",toneName);

        String path = "android.resource://"+ getPackageName() +"/raw/"+toneName;

        PlayAudio(RingAlarm.this,path);

        stopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent alarmQuiz = new Intent(RingAlarm.this,AlarmQuiz.class);
                alarmQuiz.putExtra("alName",alaName);
                alarmQuiz.putExtra("alTime",alaTime);
                alarmQuiz.putExtra("alTone",toneName);
                startActivity(alarmQuiz);
            }
        });
    }
}
