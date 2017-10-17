package com.bignerdranch.android.timemanagement.database;

/**
 * Created by mitchellhayward on 16/10/17.
 */

public class TimeDbSchema {

    public static final class TimeTable {

        public static final String NAME = "activities";

        public static final class Cols {

            // All Elements Within the TimeFragment Class must be here in order to display within the TimeDbSchema

            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SPINNERID = "spinnerid";
            public static final String HOUR = "hour";
            public static final String MINUTE = "minute";
            public static final String LAT = "lat";
            public static final String LONG = "long";

        }

    }

}
