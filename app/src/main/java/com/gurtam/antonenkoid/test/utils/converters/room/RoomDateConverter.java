package com.gurtam.antonenkoid.test.utils.converters.room;

import java.util.Date;

import androidx.room.TypeConverter;

public class RoomDateConverter {

    @TypeConverter
    public static Date toDate(Long dateLong) {
        return dateLong == null ? null : new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date) {
        return date == null ? null : date.getTime();
    }
}