package com.guard.restservice;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@org.springframework.stereotype.Service
public class LocalCalculation {

    public String calculateLocalTime() {
        //LocalTime localTime = LocalTime.now(ZoneId.of("ECT"));
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        //return localTime.format(formatter);
        ZoneId zoneId = ZoneId.of("Europe/Paris");
        return ZonedDateTime
                .now( zoneId )
                .format( DateTimeFormatter.ofPattern( "HH:mm" ) );
    }

    public String calculateLocalDate() {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return localDate.format(formatter);
    }
}
