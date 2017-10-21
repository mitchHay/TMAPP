package com.bignerdranch.android.timemanagement.database;

/**
 * Created by mitchellhayward on 21/10/17.
 */

public class SettingsDbSchema {

    public static final class SettingsTable{
        public static final String NAME = "settings";

        public static final class Cols {
            public static final String NAME = "name";
            public static final String SETTINGSID = "settingsid";
            public static final String EMAIL = "email";
            public static final String SETTINGSCOMMENT = "settingscomment";
            public static final String SETTINGSSPINNER = "settingsspinner";
            public static final String sID = "sid";
        }
    }

}
