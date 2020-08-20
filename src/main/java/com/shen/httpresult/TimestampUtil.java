package com.shen.httpresult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimestampUtil {

    private TimestampUtil(){}

    private static final SimpleDateFormat DATA_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    public static String getTimestamp() {
        return DATA_FORMAT.format(new Date());
    }

}
