
package com.github.mobile.api;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.ToJson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateAdapter {
    private final DateFormat[] formats = new DateFormat[3];

    public DateAdapter() {
        formats[0] = new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss\'Z\'");
        formats[1] = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z");
        formats[2] = new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss");
        final TimeZone timeZone = TimeZone.getTimeZone("Zulu");
        for (DateFormat format : formats) {
            format.setTimeZone(timeZone);
        }
    }

    @ToJson
    String toJson(Date date) {
        return formats[0].format(date);
    }

    @FromJson
    Date fromJson(String date) {
        for (DateFormat format : formats) {
            try {
                return format.parse(date);
            } catch (ParseException e) {
            }
        }

        throw new JsonDataException();
    }
}
