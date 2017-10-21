package com.bignerdranch.android.timemanagement.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.timemanagement.Time;
import com.bignerdranch.android.timemanagement.database.SettingsDbSchema.SettingsTable;

import java.util.UUID;

/**
 * Created by mitchellhayward on 21/10/17.
 */

public class SettingsCursorWrapper extends CursorWrapper {

    public SettingsCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Time getTime(){
        String uuidString = getString(getColumnIndex(SettingsTable.Cols.sID));
        String name = getString(getColumnIndex(SettingsTable.Cols.NAME));
        String email = getString(getColumnIndex(SettingsTable.Cols.EMAIL));
        int settingsId = getInt(getColumnIndex(SettingsTable.Cols.SETTINGSSPINNER));
        String identifier = getString(getColumnIndex(SettingsTable.Cols.SETTINGSID));
        String comments = getString(getColumnIndex(SettingsTable.Cols.SETTINGSCOMMENT));

        Time time = new Time(UUID.fromString(uuidString));
        time.setName(name);
        time.setEmail(email);
        time.setSettingsSpinner(settingsId);
        time.setIdentifier(identifier);
        time.setSettingsComment(comments);

        return time;
    }

}
