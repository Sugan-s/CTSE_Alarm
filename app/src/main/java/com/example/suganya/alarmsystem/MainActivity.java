package com.example.suganya.alarmsystem;

import android.app.ActivityManager;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btnOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("get_info");


        toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        if(!isAlarmServiceRunning()){
            Intent serviceIntent = new Intent(MainActivity.this,CheckAlarm.class);
            startService(serviceIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu,menu);
        return true;
    }


    public void setTime(MenuItem item) {
      startActivity(new Intent(MainActivity.this,PopupSetTime.class));
    }

    public boolean isAlarmServiceRunning(){
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo serviceInfo : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if(CheckAlarm.getClassName(this).equals(serviceInfo.service.getClassName()))
            {
                return true;
            }
        }
        return false;
    }
    


}
