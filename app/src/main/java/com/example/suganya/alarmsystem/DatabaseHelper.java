package com.example.suganya.alarmsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "AlarmTest.db";
    public static final String TABLE_NAME = "alarm_detail_table";
    public static final String ID = "ID";
    public static final String TIME = "TIME";
    public static final String NAME = "NAME";
    public static final String TONE = "TONE";
    public static final String STATUS = "STATUS";
    Context context;



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " +TABLE_NAME+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT , TIME TEXT ,NAME TEXT , TONE TEXT , STATUS INTEGER) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(String time , String name , String tone , Integer status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TIME,time);
        contentValues.put(NAME,name);
        contentValues.put(TONE,tone);
        contentValues.put(STATUS,status);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;



    }

    public Cursor getInformations(SQLiteDatabase db)
    {
        String[] projections = {TIME , NAME,TONE,STATUS};
        Cursor cursor = db.query(TABLE_NAME,projections,null,null,null,null,null);
        return cursor;
    }

    public Cursor getSeleAlarm(Integer status){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] coloumns = {TIME , NAME,TONE,STATUS};
        Cursor cursor = db.query(TABLE_NAME,coloumns,STATUS+"="+status,null,null,null,null);
        return  cursor;

    }

    public Cursor getSelectedAlarms(SQLiteDatabase db){

//        SQLiteDatabase db = this.getReadableDatabase();
        String[] coloumns = {TIME , NAME,TONE,STATUS};
        Cursor cursor = db.query(TABLE_NAME , coloumns , null , null,null,null,null);
        return cursor;
    }


    public boolean updateStatus(String time , String name , Integer status , String tone){

        Log.e("in db helper","updatestatus");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TIME,time);
        contentValues.put(NAME,name);
        contentValues.put(TONE,tone);
        contentValues.put(STATUS,status);

        int result = db.update(TABLE_NAME, contentValues, NAME + "=? AND " + TIME + "=?",new String[] {time , name});
        String ans = String.valueOf(result);
        Log.e("result", ans);
        db.close();
        if(result == -1)
            return false;
        else
            return true;
    }
}
