package com.example.suganya.alarmsystem;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class CheckAlarm extends Service {
    DatabaseHelper databaseHelper;
    Context context;



    public CheckAlarm() {

    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
//        Calendar currentTime = Calendar.getInstance();
//        int currentHourIn24 = currentTime.get(Calendar.HOUR_OF_DAY);
//        String time = String.valueOf(currentHourIn24);
//
//        Log.i("current time", time);

        databaseHelper = new DatabaseHelper(this);


        boolean isAlarmTriggered = false;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = databaseHelper.getReadableDatabase();

                Cursor alarmsList = databaseHelper.getSeleAlarm(1);
                Calendar currentTime = Calendar.getInstance();
                int HOUR = currentTime.get(Calendar.HOUR_OF_DAY);
                int MINUTE = currentTime.get(Calendar.MINUTE);
                String curTime = HOUR + ":"+MINUTE;

                boolean serviceStarted = false;
                if(alarmsList == null)
                {
                    Toast.makeText(CheckAlarm.this,"noo Data  Inserted", Toast.LENGTH_LONG).show();
                }
                else {
                    while (alarmsList.moveToNext()) {
                        String alarmTime = alarmsList.getString(alarmsList.getColumnIndex(DatabaseHelper.TIME));
                        String alarmTone = alarmsList.getString(alarmsList.getColumnIndex(DatabaseHelper.TONE));
                        String alarmName = alarmsList.getString(alarmsList.getColumnIndex(DatabaseHelper.NAME));
//                for(Alarms alarms: alarmsList){
                        Log.i("alarm check", alarmTime);
                        if (alarmTime.equals(curTime)) {
                            Log.i("alarm check", "stopped at" + curTime);

                            Intent ringAlarmIntent = new Intent(CheckAlarm.this, RingAlarm.class);
                            ringAlarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                            ringAlarmIntent.putExtra("tone",alarmTone);
                            ringAlarmIntent.putExtra("alarmName",alarmName);
                            ringAlarmIntent.putExtra("alarmTime",alarmTime);
                            ringAlarmIntent.putExtra("condition","start");
                            startActivity(ringAlarmIntent);

                            stopSelf();
                            serviceStarted = true;

                        }
                    }
                }
//                }

                if(!serviceStarted){
                    handler.postDelayed(this,1000);
                }

            }


        };

        handler.postDelayed(runnable,1000);
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static String getClassName(Context context) {
        return context.getApplicationContext().getPackageName() + "." + "CheckAlarm";
    }
}
