package com.upgradedsoftware.android.chat.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class TimeParser {

    public String timeParser(Long date){
        final SimpleDateFormat NOTIFICATION_TIME_FORMAT;
        final SimpleDateFormat DAY_OF_THE_WEEK;
        final SimpleDateFormat DATE_FORMAT;

        NOTIFICATION_TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.getDefault());
        DAY_OF_THE_WEEK = new SimpleDateFormat("EEE", Locale.getDefault());
        DATE_FORMAT = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());

        long currentTime = System.currentTimeMillis() / 1000L;

        if (date == null || date == 0) {
            return "-";
        }

        if(currentTime - 24*60*60 < date){ // today
            NOTIFICATION_TIME_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
            return NOTIFICATION_TIME_FORMAT.format(date * 1000);
        }
        else if(currentTime - 24*60*60*7 < date){ // on week
            DAY_OF_THE_WEEK.setTimeZone(TimeZone.getTimeZone("UTC"));
            return DAY_OF_THE_WEEK.format(date * 1000);
        }
        else if(currentTime < date){
            return "FUTURE";
        }
        else {
            DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
            return DATE_FORMAT.format(date * 1000);
        }

    }
}
