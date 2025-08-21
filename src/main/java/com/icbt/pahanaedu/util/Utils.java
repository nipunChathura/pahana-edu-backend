package com.icbt.pahanaedu.util;

import com.icbt.pahanaedu.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;

public class Utils {
    private static final Logger log = LoggerFactory.getLogger(Utils.class);

    private static final String PREFIX = "CUST";
    private static final AtomicInteger counter = new AtomicInteger(0);

    public static Date getCurrentDateByTimeZone(String timeZone) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        SimpleDateFormat dateFormatConf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        dateFormatConf.setTimeZone(TimeZone.getTimeZone(timeZone));


        Date date = new Date();
        Date confTime = null;
        try {
            confTime = dateFormat.parse(dateFormatConf.format(date));
        } catch (ParseException exception) {
            log.warn("Unable to obtain TimeZone date using local date, defaulting to local date", exception);
            confTime = date;
        }
        return confTime;
    }

    public static String convertMobileNumber(String number) {
        number = number.replaceAll("[^\\d+]", "");

        if (number.startsWith("+94")) {
            return number;
        }
        if (number.startsWith("94")) {
            return "+" + number;
        }
        if (number.startsWith("0")) {
            return "+94" + number.substring(1);
        }
        return number;
    }

    public static String generateCustomerRegNumber(Long sequence) {
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        return PREFIX + "-" + date + "-" + String.format("%03d", sequence + 1);
    }

    public static Date convetPromotionDate(Date date) {
        Instant instant = date.toInstant();

        ZoneId sriLankaZone = ZoneId.of(Constants.TIME_ZONE);
        ZonedDateTime zonedDateTime = instant.atZone(sriLankaZone);

        return Date.from(zonedDateTime.toInstant());
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }
}
