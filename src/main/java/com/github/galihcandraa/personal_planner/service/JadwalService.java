package com.github.galihcandraa.personal_planner.service;

import com.github.galihcandraa.personal_planner.repository.JadwalRepository;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;
import jakarta.validation.ValidationException;

import com.github.galihcandraa.personal_planner.model.*;
import com.github.galihcandraa.personal_planner.util.*;

@Service
public class JadwalService {

    private final JadwalRepository jadwalRepository;

    JadwalService(JadwalRepository jadwalRepository) {
        this.jadwalRepository = jadwalRepository;
    }

    public List<Jadwal> showJadwal() {
        return jadwalRepository.findAll();
    }

    public void addJadwal(Category kategori, String judul, String lokasi, Day hari, LocalTime jamMulai,
            LocalTime jamSelesai, Frequency frekuensi, LocalDate tanggalMulai, LocalDate tanggalSelesai,
            String deskripsi) {
        if (deskripsi.isBlank())
            deskripsi = "-";

        Jadwal dataBaru = new Jadwal(kategori, judul, lokasi, hari, jamMulai, jamSelesai, frekuensi,
                tanggalMulai, tanggalSelesai, deskripsi);
        jadwalRepository.save(dataBaru);
    }

    public void editJadwal(long id, Category kategori, String judul, String lokasi, Day hari, LocalTime jamMulai,
            LocalTime jamSelesai, Frequency frekuensi, LocalDate tanggalMulai, LocalDate tanggalSelesai,
            String deskripsi) {
        Jadwal jadwal = findById(id);

        jadwal.setKategori(kategori);
        jadwal.setJudul(judul);
        jadwal.setLokasi(lokasi);
        jadwal.setHari(hari);
        jadwal.setJamMulai(jamMulai);
        jadwal.setJamSelesai(jamSelesai);
        jadwal.setFrekuensi(frekuensi);
        jadwal.setTanggalMulai(tanggalMulai);
        jadwal.setTanggalSelesai(tanggalSelesai);
        jadwal.setDeskripsi(deskripsi);
        jadwalRepository.save(jadwal);
    }

    public Jadwal findById(long id) {
        return jadwalRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Pencarian gagal, ID tidak ditemukan!"));
    }

    public List<Jadwal> searchByCondition(SearchType type, String value) {
        List<Jadwal> results = new ArrayList<>();

        for (Jadwal jadwal : jadwalRepository.findAll()) {
            switch (type) {
                case ID:
                    try {
                        if (jadwal.getId() == Long.parseLong(value))
                            results.add(jadwal);
                    } catch (ValidationException e) {
                        throw new ValidationException("[ERROR] ID bukan termasuk angka, ulangi!");
                    }
                    break;

                case KATEGORI:
                    if (jadwal.getKategori().toString().equalsIgnoreCase(value))
                        results.add(jadwal);
                    break;

                case JUDUL:
                    if (jadwal.getJudul().contains(value))
                        results.add(jadwal);
                    break;

                case HARI:
                    if (jadwal.getHari().toString().equalsIgnoreCase(value))
                        results.add(jadwal);
                    break;
            }
        }
        return results;
    }

    public List<Jadwal> sortByCondition(SortType type, SortOrder order) {
        List<Jadwal> results = new ArrayList<>(jadwalRepository.findAll());

        Comparator<Jadwal> comparator = null;

        switch (type) {
            case KATEGORI:
                comparator = Comparator.comparing(Jadwal::getKategori);
                break;

            case JUDUL:
                comparator = Comparator.comparing(Jadwal::getJudul);
                break;

            case HARI:
                comparator = Comparator.comparing(Jadwal::getHari);
                break;

            case JAM_MULAI:
                comparator = Comparator.comparing(Jadwal::getJamMulai);
                break;

            case TANGGAL_MULAI:
                comparator = Comparator.comparing(Jadwal::getTanggalMulai)
                        .thenComparing(Jadwal::getJamMulai);
                break;
        }
        if (order == SortOrder.DESCENDING) {
            comparator = comparator.reversed();
        }
        results.sort(comparator);

        return results;
    }

    public void deleteById(long id) {
        if (!jadwalRepository.existsById(id))
            throw new ValidationException("Penghapusan gagal, ID tidak ditemukan!");

        jadwalRepository.deleteById(id);
    }

    public String reset() {
        jadwalRepository.deleteAll();
        jadwalRepository.resetAutoIncrement();
        return "Berhasil menghapus semua jadwal!\n";
    }

    public void validateTimeLogic(LocalTime targetStart, LocalTime targetEnd) {
        if (targetStart.isAfter(targetEnd)) {
            throw new ValidationException("[ERROR] Jam mulai wajib sebelum jam selesai, ulangi!");
        }
    }

    public void validateTimeConflict(Day targetDay, LocalTime targetStart,
            LocalTime targetEnd, long ignoreId) {
        for (Jadwal jadwal : jadwalRepository.findAll()) {
            boolean sameDay = jadwal.getHari() == targetDay;
            boolean shouldIgnore = ignoreId != 0 && jadwal.getId() == ignoreId;
            boolean isConflict = targetStart.isBefore(jadwal.getJamSelesai())
                    && targetEnd.isAfter(jadwal.getJamMulai());

            if (sameDay && !shouldIgnore && isConflict) {
                throw new ValidationException(
                        "[ERROR] Jadwal bentrok dengan " + jadwal.getJudul() + " (" + jadwal.getJamMulai()
                                + " - " + jadwal.getJamSelesai() + ") " + ", ulangi!");
            }
        }
    }

    public void validateDateLogic(LocalDate targetStart, LocalDate targetEnd) {
        if (targetStart.isAfter(targetEnd)) {
            throw new ValidationException("[ERROR] tanggal mulai wajib sebelum tanggal selesai, ulangi!");
        }
    }

    public Category parseCategory(String field) {
        try {
            return Category.valueOf(field.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tidak berhasil konversi tipe data");
        }
    }

    public Day parseDay(String field) {
        try {
            return Day.valueOf(field.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tidak berhasil konversi tipe data");
        }
    }

    public Frequency parseFrequency(String field) {
        try {
            return Frequency.valueOf(field.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tidak berhasil konversi tipe data");
        }
    }

    public LocalTime parseTime(String field) {
        try {
            return LocalTime.parse(field, DateTimeFormatters.TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Format jam harus HH:mm");
        }
    }

    public LocalDate parseDate(String field) {
        try {
            return LocalDate.parse(field, DateTimeFormatters.DATE_FORMATTER);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Format tanggal harus dd-MM-yyyy");
        }
    }
}
