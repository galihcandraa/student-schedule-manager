package com.github.galihcandraa.personal_planner.util;

import java.time.format.DateTimeFormatter;

public class DateTimeFormatters {
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
}
