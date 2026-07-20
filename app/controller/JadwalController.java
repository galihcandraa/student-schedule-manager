package app.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import app.model.*;
import app.service.JadwalService;
import app.util.JadwalUtil;

public class JadwalController {
    static JadwalService service = new JadwalService();
    static JadwalUtil validation = new JadwalUtil();

    public List<Jadwal> showJadwal() {
        return service.showJadwal();
    }

    public Jadwal searchJadwal(SearchType type, String value) {
        return service.searchByCondition(type, value);
    }

    public Jadwal findById(String id) {
        return service.findById(id);
    }

    public List<Jadwal> sortJadwal(SortType type, SortOrder order) {
        return service.sortByCondition(type, order);
    }

    public String deleteById(String id) {
        try {
            String msg = service.deleteById(id) ? "Berhasil menghapus jadwal!\n" : "Jadwal dengan id: " + id + " tidak ditemukan\n";
            return msg;
        } catch (IOException e) {
            return "Gagal menghapus jadwal!\n";
        }
    }

    public String reset() {
        try {
            return service.reset();
        } catch (IOException e) {
            return "Gagal menghapus semua jadwal!\n";
        }
    }

    public LocalTime parseTime(String field) {
        return service.parseTime(field);
    }

    public LocalDate parseDate(String field) {
        return service.parseDate(field);
    }

    public String validateIdEdit(String id) {
        Jadwal tmp = service.findById(id);
        return tmp == null ? "Jadwal dengan id: " + id + " tidak ditemukan\n" : null;
    }

    public String validateCategory(int choice) {
        Category kategori = Category.fromChoice(choice);
        if (kategori == null) {
            return "Pilihan tidak valid. Ulangi!";
        }
        return null;
    }

    public String validateTitle(String judul) {
        return validation.validateRequiredText("judul", judul, 3);
    }

    public String validateLocation(String lokasi) {
        return validation.validateRequiredText("lokasi", lokasi, 3);
    }

    public String validateDay(int choice) {
        Day day = Day.fromChoice(choice);
        if (day == null) {
            return "Pilihan tidak valid. Ulangi!";
        }
        return null;
    }

    public String validateFormatTimeStart(String field) {
        return validation.validateTimeFormat("jam mulai", field);
    }

    public String validateFormatTimeEnd(String field) {
        return validation.validateTimeFormat("jam selesai", field);
    }

    public String validateTimeLogic(LocalTime start, LocalTime end) {
        return service.validateTimeLogic(start, end);
    }

    public String validateAllTime(Day day, LocalTime timeStart, LocalTime timeEnd,
            String ignoreId) {
        String result = service.validateTimeLogic(timeStart, timeEnd);
        if (result != null)
            return result;

        result = service.validateTimeConflict(day, timeStart, timeEnd, ignoreId);
        return result;
    }

    public String validateFrequency(int choice) {
        Frequency frekuensi = Frequency.fromChoice(choice);
        if (frekuensi == null) {
            return "Pilihan tidak valid. Ulangi!";
        }
        return null;
    }

    public String validateFormatDateStart(String field) {
        return validation.validateDateFormat("tanggal mulai", field);
    }

    public String validateFormatDateEnd(String field) {
        return validation.validateDateFormat("tanggal selesai", field);
    }

    public String validateDateLogic(LocalDate start, LocalDate end) {
        return service.validateDateLogic(start, end);
    }

    public String validateDescription(String field) {
        return null;
    }

    public String addJadwal(Category kategori, String judul, String lokasi, Day hari, LocalTime jamMulai,
            LocalTime jamSelesai, Frequency frekuensi, LocalDate tanggalMulai, LocalDate tanggalSelesai,
            String deskripsi) {
        try {
            service.addData(kategori, judul, lokasi, hari, jamMulai, jamSelesai, frekuensi, tanggalMulai,
                    tanggalSelesai, deskripsi);
            return "Jadwal berhasil ditambahkan!\n";
        } catch (IOException e) {
            return "Gagal menyimpan jadwal " + e.getMessage();
        }
    }

    public String editJadwal(String id, Category kategori, String judul, String lokasi, Day hari, LocalTime jamMulai,
            LocalTime jamSelesai, Frequency frekuensi, LocalDate tanggalMulai, LocalDate tanggalSelesai,
            String deskripsi) {
        try {
            service.editData(id, kategori, judul, lokasi, hari, jamMulai, jamSelesai, frekuensi, tanggalMulai,
                    tanggalSelesai, deskripsi);
            return "Jadwal berhasil diedit!\n";
        } catch (IOException e) {
            return "Gagal menyimpan jadwal " + e.getMessage();
        }
    }
    
    public String loadFromFile() {
        try {
            service.loadFromFile();
            return "Berhasil dimuat!\n";
        } catch (IOException e) {
            return "Gagal memuat data " + e.getMessage();
        }
    }
}
