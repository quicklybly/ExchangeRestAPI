package com.quicklybly.exchangerestapi.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private DateUtils() {
    }

    public static LocalDate getFormattedNow() {
        return getFormattedDate(LocalDate.now());
    }

    public static LocalDate getFormattedDate(LocalDate date) {
        return LocalDate.parse(date.format(formatter), formatter);
    }
}
