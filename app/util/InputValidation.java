package app.util;

import java.time.LocalTime;
import java.time.format.*;
import java.util.List;

import app.model.Jadwal;

public class InputValidation {
    public static String validateRequiredText(String fieldName, String field, int minChar) {
        if (field == null || field.isBlank()) {
            return fieldName + " tidak boleh kosong, ulangi!";
        }
        if (field.length() < minChar) {
            return fieldName + " minimal " + minChar + " karakter, ulangi!";
        }
        return null;
    }

    public static String validateDay(String field) {
        field = field.toLowerCase();
        String error = validateRequiredText("hari", field, 3);
        if (error != null)
            return error;

        String[] days = { "senin", "selasa", "rabu", "kamis", "jumat" };

        for (String day : days) {
            if (field.equals(day)) {
                return null;
            }
        }
        return "Bukan hari kerja, ulangi!";
    }

    public static LocalTime parseTime(String field) {
        try {
            return LocalTime.parse(field, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Format jam harus HH.mm");
        }
    }

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH.mm");

    public static String validateTimeFormat(String timeName, String timeField) {
        String error = InputValidation.validateRequiredText(timeName, timeField, 3);
        if (error != null)
            return error;

        try {
            LocalTime.parse(timeField, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return "[ERROR] Format jam harus HH.mm, cth: 07.00, ulangi!";
        }
        return null;
    }

    public static String validateTimeLogic(LocalTime targetStart, LocalTime targetEnd) {
        if (!targetStart.isBefore(targetEnd)) {
            return "[ERROR] Jam mulai wajib sebelum jam selesai, ulangi!";
        }
        return null;
    }

    public static String validateTimeConflict(List<Jadwal> data, String targetDay, LocalTime targetStart, LocalTime targetEnd) {
        for (Jadwal jadwal : data) {
            if (jadwal.getHari().equalsIgnoreCase(targetDay)) {
                if (targetStart.isBefore(jadwal.getJamSelesai()) && targetEnd.isAfter(jadwal.getJamMulai())) {
                    return "[ERROR] Jadwal bentrok dengan " + jadwal.getNamaMatkul() + " (" + jadwal.getJamMulai() + " - " + jadwal.getJamSelesai() + ") " + ", ulangi!";
                }
            }
        }
        return null;
    }
}
