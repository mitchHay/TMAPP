package com.bignerdranch.android.timemanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bignerdranch.android.timemanagement.database.TimeBaseHelper;
import com.bignerdranch.android.timemanagement.database.TimeCursorWrapper;
import com.bignerdranch.android.timemanagement.database.TimeDbSchema.TimeTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by mitchellhayward on 3/10/17.
 */

public class TimeLab {

    private static TimeLab sTimeLab;

    private Time settingsTime;

    //private List<Time> mTime;

    private List<Time> mSettings;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private SQLiteDatabase mSettingsDatabase;

    public static TimeLab get(Context context){

        if(sTimeLab == null){
            sTimeLab = new TimeLab(context);
        }
        return sTimeLab;
    }

    private TimeLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new TimeBaseHelper(mContext).getWritableDatabase();

        //mTime = new ArrayList<>();
        mSettings = new ArrayList<>();
    }

    public void addActivity(Time t){
        //mTime.add(t);
        ContentValues values = getContentValues(t);

        mDatabase.insert(TimeTable.NAME, null, values);
    }

    public int deleteActivity(Time t){
        String uuidString = t.getId().toString();
        return mDatabase.delete(
                TimeTable.NAME,
                TimeTable.Cols.UUID + " = ?",
                new String[] { uuidString }
        );
    }

    public void addSettings(Time t){
        mSettings.add(t);
        //ContentValues values = getSettingsValues(t);

        //mDatabase.insert(TimeTable.SETTINGSNAME, null, values);
    }

    public List<Time> getActivity(){
        //return new ArrayList<>();

        List<Time> time = new ArrayList<>();

        TimeCursorWrapper cursor = queryTime(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                time.add(cursor.getTime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return time;
    }

    public Time getTime(UUID id){

        //return null;

        TimeCursorWrapper cursor = queryTime(TimeTable.Cols.UUID + " = ?", new String[] {id.toString()});

        try {
            if (cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getTime();

        } finally {
            cursor.close();
        }

    }

    public Time getSettings(UUID id){

        TimeCursorWrapper cursor = querySettings(TimeTable.settingsCols.SID + " = ?", new String[] {id.toString()});

        try {
            if (cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getSettings();

        } finally {
            cursor.close();
        }
    }

    public File getPhotoFile(Time time){
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, time.getPhotoFilename());
    }

    public void updateTime(Time time){

        String uuidString = time.getId().toString();
        ContentValues values = getContentValues(time);

        mDatabase.update(TimeTable.NAME, values, TimeTable.Cols.UUID + " = ?", new String[] {uuidString});

    }

    public void updateSettings(Time time){

        String uuidString = time.getNewId().toString();
        ContentValues values = getSettingsValues(time);

        mDatabase.update(TimeTable.SETTINGSNAME, values, TimeTable.settingsCols.SID + " = ?", new String[] {uuidString});

    }

    private TimeCursorWrapper queryTime(String whereClause, String[] whereArgs){

        Cursor cursor = mDatabase.query(TimeTable.NAME, null, whereClause, whereArgs, null, null, null);

        return new TimeCursorWrapper(cursor);

    }

    private TimeCursorWrapper querySettings(String whereClause, String[] whereArgs){

        Cursor cursor = mDatabase.query(TimeTable.SETTINGSNAME, null, whereClause, whereArgs, null, null, null);

        return new TimeCursorWrapper(cursor);

    }

    public UUID saveTime(UUID id){
        return id;
    }

    private static ContentValues getContentValues(Time time){

        ContentValues values = new ContentValues();
        values.put(TimeTable.Cols.UUID, time.getId().toString());
        values.put(TimeTable.Cols.TITLE, time.getTitle());
        values.put(TimeTable.Cols.DATE, time.getDate().getTime());
        values.put(TimeTable.Cols.SPINNERID, time.getSpinnerId());
        values.put(TimeTable.Cols.HOUR, time.getHour());
        values.put(TimeTable.Cols.MINUTE, time.getMin());
        values.put(TimeTable.Cols.LAT, time.getLat());
        values.put(TimeTable.Cols.LONG, time.getLong());
        values.put(TimeTable.Cols.FULLADDRESS, time.getFullAddress());
        values.put(TimeTable.Cols.COMMENT, time.getComment());

        return values;

    }

    private static ContentValues getSettingsValues(Time time){

        ContentValues values = new ContentValues();
        values.put(TimeTable.settingsCols.SID, time.getNewId().toString());
        values.put(TimeTable.settingsCols.NAME, time.getName());
        values.put(TimeTable.settingsCols.EMAIL, time.getEmail());
        values.put(TimeTable.settingsCols.IDENTITY, time.getIdentifier());
        values.put(TimeTable.settingsCols.SETTINGSCOMMENT, time.getSettingsComment());
        values.put(TimeTable.settingsCols.SETTINGSSPINNER, time.getSettingsSpinner());

        return values;
    }


}