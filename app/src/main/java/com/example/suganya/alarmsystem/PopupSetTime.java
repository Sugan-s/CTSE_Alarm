package com.example.suganya.alarmsystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class PopupSetTime extends Activity{


    DatabaseHelper myDb;
    MainActivity mainActivity;

    TextView name;
    TimePicker timePicker;
    Spinner spinner;
    Button addData,cancel;
    Context context;
    int mHour,mMin;
    MediaPlayer player;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup_window);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width *.8) ,(int)(height*.8));

        spinner = findViewById(R.id.alarm_tone);
        player = new MediaPlayer();
        final ArrayList<String> tones = new ArrayList<String>();
        tones.add("tone1");
        tones.add("tone2");
        tones.add("tone3");
        tones.add("tone4");
        tones.add("tone5");
        ArrayAdapter toneAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tones);
        spinner.setAdapter(toneAdapter);
        spinner.setSelection(0, false);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String toneName = tones.get(position);
                player.reset();

                String path = "android.resource://"+ getPackageName() +"/raw/"+toneName;
                try {

                    player.setDataSource(PopupSetTime.this, Uri.parse(path));
                    player.prepare();
                    player.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        myDb = new DatabaseHelper(this);

       timePicker = findViewById(R.id.timePicker);
       timePicker.setIs24HourView(true);
        name = findViewById(R.id.al_name);
//        tone = findViewById(R.id.alarm_tone);
        addData = findViewById(R.id.save_alarm);
        cancel = findViewById(R.id.cancel_alarm);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mHour = hourOfDay;
                mMin = minute;
            }
        });
        AddData();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
                player.stop();
            }
        });

    }

    public void AddData(){
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = mHour+":"+mMin;
                boolean isInserted = myDb.insertData(time,name.getText().toString(),
                        spinner.getSelectedItem().toString(), 1);

                if(isInserted = true) {
                    Log.e("check", time);
                    Toast.makeText(PopupSetTime.this, "Data Inserted", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(PopupSetTime.this,"Data  not Inserted", Toast.LENGTH_LONG).show();

                openMainActivity();
                player.stop();



            }
        });
    }

    public void openMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


}
