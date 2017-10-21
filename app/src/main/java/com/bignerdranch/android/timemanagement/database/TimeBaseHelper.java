package com.bignerdranch.android.timemanagement.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bignerdranch.android.timemanagement.database.TimeDbSchema.TimeTable;

/**
 * Created by mitchellhayward on 16/10/17.
 */

public class TimeBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "timeBase.db";

    public TimeBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        // Activities Table

       db.execSQL("create table " + TimeTable.NAME + "(" +
               " _id integer primary key autoincrement, " + TimeTable.Cols.UUID + ", " + TimeTable.Cols.TITLE +
               ", " + TimeTable.Cols.DATE + ", " + TimeTable.Cols.SPINNERID +
               ", " + TimeTable.Cols.HOUR + ", " + TimeTable.Cols.MINUTE +
               ", " + TimeTable.Cols.LAT + ", " + TimeTable.Cols.LONG +
               ", " + TimeTable.Cols.FULLADDRESS + ", " + TimeTable.Cols.COMMENT + ")");

        // Settings Table

        db.execSQL("create table " + TimeTable.SETTINGSNAME +
                "(" + " _id integer primary key autoincrement, " + TimeTable.settingsCols.SID +
                ", " + TimeTable.settingsCols.NAME + ", " + TimeTable.settingsCols.EMAIL +
                ", " + TimeTable.settingsCols.IDENTITY + ", " + TimeTable.settingsCols.SETTINGSCOMMENT +
                ", " + TimeTable.settingsCols.SETTINGSSPINNER + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TimeTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TimeTable.SETTINGSNAME);
        onCreate(db);
    }
}
