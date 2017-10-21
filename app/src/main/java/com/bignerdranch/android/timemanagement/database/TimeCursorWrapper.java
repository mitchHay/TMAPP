package com.bignerdranch.android.timemanagement.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.timemanagement.Time;
import com.bignerdranch.android.timemanagement.database.TimeDbSchema.TimeTable;

import java.util.Date;
import java.util.UUID;

/**
 * Created by mitchellhayward on 16/10/17.
 */

public class TimeCursorWrapper extends CursorWrapper {

    public TimeCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Time getTime(){
        String uuidString = getString(getColumnIndex(TimeTable.Cols.UUID));
        String title = getString(getColumnIndex(TimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(TimeTable.Cols.DATE));
        int spinnerId = getInt(getColumnIndex(TimeTable.Cols.SPINNERID));
        int hour = getInt(getColumnIndex(TimeTable.Cols.HOUR));
        int minute = getInt(getColumnIndex(TimeTable.Cols.MINUTE));
        Double lat = getDouble(getColumnIndex(TimeTable.Cols.LAT));
        Double lon = getDouble(getColumnIndex(TimeTable.Cols.LONG));
        String fullAddress = getString(getColumnIndex(TimeTable.Cols.FULLADDRESS));
        String comment = getString(getColumnIndex(TimeTable.Cols.COMMENT));

        Time time = new Time(UUID.fromString(uuidString));
        time.setTitle(title);
        time.setDate(new Date(date));
        time.setSpinnerId(spinnerId);
        time.setHour(hour);
        time.setMin(minute);
        time.setLat(lat);
        time.setLong(lon);
        time.setFullAddress(fullAddress);
        time.setComment(comment);

        return time;
    }

}
