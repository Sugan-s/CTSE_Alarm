package com.example.suganya.alarmsystem;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

public class BackgroundTask extends AsyncTask<String,Alarms,String> {

    Context context;
    AlarmAdapter alarmAdapter;
    Activity activity;
    ListView listView;
    BackgroundTask(Context context)
    {
        this.context = context;
        activity = (Activity) context;
    }

    @Override
    protected String doInBackground(String... params) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        String methode = params[0];
        if(methode.equals("get_info"))
        {
            listView = activity.findViewById(R.id.display_alarm_list);
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor cursor = databaseHelper.getInformations(db);

            alarmAdapter = new AlarmAdapter(context,R.layout.display_alarm_row);
            String time,name,tone;
            Integer status;

            while (cursor.moveToNext())
            {
                time = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TIME));
                name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME));
                tone = cursor.getString(cursor.getColumnIndex(DatabaseHelper.TONE));
                status = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.STATUS));
                Alarms alarms = new Alarms(time,name,tone,status);
                publishProgress(alarms);

            }
                return "get_info";

        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Alarms... values) {
        alarmAdapter.add(values[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("get_info"))
        {
            listView.setAdapter(alarmAdapter);
        }
        else
        {
            Toast.makeText(context,result, Toast.LENGTH_SHORT).show();
        }
    }
}
