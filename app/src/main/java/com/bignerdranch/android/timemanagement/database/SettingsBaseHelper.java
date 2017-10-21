package com.bignerdranch.android.timemanagement.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdranch.android.timemanagement.database.SettingsDbSchema.SettingsTable;

/**
 * Created by mitchellhayward on 21/10/17.
 */

public class SettingsBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "settingsBase.db";

    public SettingsBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + SettingsTable.NAME + "(" + " _id integer primary key autoincrement, " + SettingsTable.Cols.NAME + ", " + SettingsTable.Cols.SETTINGSID + ", " + SettingsTable.Cols.EMAIL + ", " + SettingsTable.Cols.SETTINGSCOMMENT + ", " + SettingsTable.Cols.SETTINGSSPINNER + ", " + SettingsTable.Cols.sID + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
