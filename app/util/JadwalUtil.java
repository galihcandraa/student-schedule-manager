package app.util;

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.*;

public class JadwalUtil {
    public String validateRequiredText(String fieldName, String field, int minChar) {
        if (field == null || field.isBlank()) {
            return fieldName + " tidak boleh kosong, ulangi!";
        }
        if (field.length() < minChar) {
            return fieldName + " minimal " + minChar + " karakter, ulangi!";
        }
        return null;
    }

    public String validateTimeFormat(String timeName, String timeField) {
        String error = validateRequiredText(timeName, timeField, 4);
        if (error != null)
            return error;

        try {
            LocalTime.parse(timeField, DateTimeFormatters.TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return "[ERROR] Format jam harus HH.mm, cth: 07:00, ulangi!";
        }
        return null;
    }

    public String validateDateFormat(String dateName, String dateField) {
        try {
            LocalDate.parse(dateField, DateTimeFormatters.DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            return "[ERROR] Format tanggal harus dd-MM-yyyy, cth: 12-05-2026, ulangi!";
        }
        return null;
    }
}
