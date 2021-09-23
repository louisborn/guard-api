package com.guard.restservice;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@org.springframework.stereotype.Service
public class LocalCalculation {

    public String calculateLocalTime() {
        //LocalTime localTime = LocalTime.now(ZoneId.of("ECT"));
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        //return localTime.format(formatter);
        return ZonedDateTime
                .now( ZoneId.systemDefault() )
                .format( DateTimeFormatter.ofPattern( "HH.mm.ss" ) );
    }

    public String calculateLocalDate() {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return localDate.format(formatter);
    }
}
