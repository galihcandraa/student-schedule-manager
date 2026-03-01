package app.util;

import java.time.LocalTime;
import java.time.format.*;

public class InputValidation {
    public static String validateText(String fieldName, String field, int minChar) {
        if (field == null || field.isBlank()) {
            return "  " + fieldName + " tidak boleh kosong, ulangi!";
        }
        if (field.length() < minChar) {
            return "  " + fieldName + " minimal " + minChar + " karakter, ulangi!";
        }
        return null;
    }

    public static String validateDay(String field) {
        field = field.toLowerCase();
        String error = validateText("hari", field, 3);
        if (error != null) return error;

        String[] days = { "senin", "selasa", "rabu", "kamis", "jumat" };

        for (String day : days) {
            if (field.equals(day)) {
                return null;
            }
        }
        return "  Bukan hari kerja, ulangi!";
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm");
    public static String validateTime(String timeName, String field) {
        String error = validateText(timeName, field, 5);
        if (error != null) return error;

        try {
            LocalTime.parse(field, formatter);
        } catch (DateTimeParseException e) {
            return "  Format jam harus HH.mm, cth: 07.00. Ulangi!";
        }
        return null;
    }
}
