package com.sethtomy.util;

import java.time.Instant;

public class TimeUtil {

    public static long instantToEpochSeconds(Instant instant) {
        return instant.toEpochMilli() / 1000;
    }

}
