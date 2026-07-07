package app.util;

import java.time.LocalTime;
import java.time.format.*;
import java.util.List;

import app.model.Day;
import app.model.Jadwal;
import app.service.JadwalService;

public class InputValidation {
    public String validateRequiredText(String fieldName, String field, int minChar) {
        if (field == null || field.isBlank()) {
            return fieldName + " tidak boleh kosong, ulangi!";
        }
        if (field.length() < minChar) {
            return fieldName + " minimal " + minChar + " karakter, ulangi!";
        }
        return null;
    }

    public String validateDay(String field) {
        field = field.toUpperCase();
        String error = validateRequiredText("hari", field, 3);
        if (error != null)
            return error;

        for (Day day : Day.values()) {
            if (day.toString().equals(field)) {
                return null;
            }
        }
        return "Bukan hari kerja, ulangi! cth:Senin - Jumat";
    }

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public String validateTimeFormat(String timeName, String timeField) {
        String error = validateRequiredText(timeName, timeField, 4);
        if (error != null)
            return error;

        try {
            LocalTime.parse(timeField, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return "[ERROR] Format jam harus HH.mm, cth: 07:00, ulangi!";
        }
        return null;
    }

    public String validateTimeLogic(LocalTime targetStart, LocalTime targetEnd) {
        if (!targetStart.isBefore(targetEnd)) {
            return "[ERROR] Jam mulai wajib sebelum jam selesai, ulangi!";
        }
        return null;
    }

    public String validateTimeConflict(List<Jadwal> data, String targetDay, LocalTime targetStart,
            LocalTime targetEnd, String ignoreId) {
        for (Jadwal jadwal : data) {
            boolean sameDay = jadwal.getHari().toString().equalsIgnoreCase(targetDay);
            boolean shouldIgnore = ignoreId != null && jadwal.getId().equalsIgnoreCase(ignoreId);
            boolean isConflict = targetStart.isBefore(jadwal.getJamSelesai())
                    && targetEnd.isAfter(jadwal.getJamMulai());

            if (sameDay && !shouldIgnore && isConflict) {
                return "[ERROR] Jadwal bentrok dengan " + jadwal.getNamaMatkul() + " (" + jadwal.getJamMulai()
                        + " - " + jadwal.getJamSelesai() + ") " + ", ulangi!";
            }
        }
        return null;
    }

    public String validateIdEdit(String id, JadwalService service) {
        Jadwal j = service.searchById(id);

        if (j == null) {
            return "ID tidak ada!\n";
        }
        return null;
    }
}
