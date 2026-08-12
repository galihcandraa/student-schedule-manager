package com.github.galihcandraa.personal_planner.service;

import java.io.*;
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

    private List<Jadwal> listJadwal = new ArrayList<>();
    private final static String FILE_PATH = "data/data_jadwal.csv";
    int nextId = 1;

    private String generateId() {
        String id = "JS226" + nextId++;
        return id;
    }

    public List<Jadwal> showJadwal() {
        return new ArrayList<>(listJadwal);
    }

    public void addData(Category kategori, String judul, String lokasi, Day hari, LocalTime jamMulai,
            LocalTime jamSelesai, Frequency frekuensi, LocalDate tanggalMulai, LocalDate tanggalSelesai,
            String deskripsi) {
        if (deskripsi.isBlank())
            deskripsi = "-";
        Jadwal dataBaru = new Jadwal(generateId(), kategori, judul, lokasi, hari, jamMulai, jamSelesai, frekuensi,
                tanggalMulai, tanggalSelesai, deskripsi);
        listJadwal.add(dataBaru);
    }

    public boolean editData(String id, Category kategori, String judul, String lokasi, Day hari, LocalTime jamMulai,
            LocalTime jamSelesai, Frequency frekuensi, LocalDate tanggalMulai, LocalDate tanggalSelesai,
            String deskripsi) {
        for (Jadwal jadwal : listJadwal) {
            if (jadwal.getId().equals(id)) {
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
                return true;
            }
        }
        return false;
    }

    public Jadwal searchByCondition(SearchType type, String value) {
        Jadwal results = null;

        for (Jadwal jadwal : listJadwal) {
            switch (type) {
                case ID:
                    if (jadwal.getId().equalsIgnoreCase(value))
                        results = jadwal;
                    break;

                case KATEGORI:
                    if (jadwal.getKategori().toString().equalsIgnoreCase(value))
                        results = jadwal;
                    break;

                case JUDUL:
                    if (jadwal.getJudul().equalsIgnoreCase(value))
                        results = jadwal;
                    break;

                case HARI:
                    if (jadwal.getHari().toString().equalsIgnoreCase(value))
                        results = jadwal;
                    break;
            }
        }
        return results;
    }

    public Jadwal findById(String id) {
        for (Jadwal j : listJadwal) {
            if (j.getId().equals(id)) {
                return j;
            }
        }
        throw new ValidationException("Pencarian gagal, ID tidak ditemukan!");
    }

    public List<Jadwal> sortByCondition(SortType type, SortOrder order) {
        List<Jadwal> results = new ArrayList<>(listJadwal);

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

    public boolean deleteById(String id) {
        boolean results = listJadwal.removeIf(j -> j.getId().equals(id));
        if (!results)
            throw new ValidationException("Penghapusan gagal, ID tidak ditemukan!");

        return results;
    }

    public String reset() throws IOException {
        listJadwal.clear();
        nextId = 1;
        saveToFile();
        return "Berhasil menghapus semua jadwal!\n";
    }

    public void validateTimeLogic(LocalTime targetStart, LocalTime targetEnd) {
        if (targetStart.isAfter(targetEnd)) {
            throw new ValidationException("[ERROR] Jam mulai wajib sebelum jam selesai, ulangi!");
        }
    }

    public String validateTimeConflict(Day targetDay, LocalTime targetStart,
            LocalTime targetEnd, String ignoreId) {
        for (Jadwal jadwal : listJadwal) {
            boolean sameDay = jadwal.getHari() == targetDay;
            boolean shouldIgnore = ignoreId != null && jadwal.getId().equalsIgnoreCase(ignoreId);
            boolean isConflict = targetStart.isBefore(jadwal.getJamSelesai())
                    && targetEnd.isAfter(jadwal.getJamMulai());

            if (sameDay && !shouldIgnore && isConflict) {
                throw new ValidationException(
                        "[ERROR] Jadwal bentrok dengan " + jadwal.getJudul() + " (" + jadwal.getJamMulai()
                                + " - " + jadwal.getJamSelesai() + ") " + ", ulangi!");
            }
        }
        return null;
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

    public void saveToFile() throws IOException {
        File file = new File(FILE_PATH);
        File parentDir = file.getParentFile();
        if (parentDir != null) {
            parentDir.mkdirs();
        }

        try (PrintWriter writter = new PrintWriter(new FileWriter(file))) {
            for (Jadwal j : listJadwal) {
                String tglMulaiCsv = (j.getTanggalMulai() == null) ? "" : j.getTanggalMulai().toString();
                String tglSelesaiCsv = (j.getTanggalSelesai() == null) ? "" : j.getTanggalSelesai().toString();
                writter.println(
                        j.getId() + "," +
                                j.getKategori() + "," +
                                j.getJudul() + "," +
                                j.getLokasi() + "," +
                                j.getHari() + "," +
                                j.getJamMulai() + "," +
                                j.getJamSelesai() + "," +
                                j.getFrekuensi() + "," +
                                tglMulaiCsv + "," +
                                tglSelesaiCsv + "," +
                                j.getDeskripsi() + "," +
                                LocalDateTime.now());
            }
        }
    }

    public void loadFromFile() throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists())
            return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            int maxId = 1;

            while ((line = reader.readLine()) != null) {
                String[] part = line.split(",");
                if (part.length < 11)
                    continue;

                String id = part[0];
                Category kategori = parseCategory(part[1]);
                String judul = part[2];
                String lokasi = part[3];
                Day hari = parseDay(part[4]);
                LocalTime jamMulai = parseTime(part[5]);
                LocalTime jamSelesai = parseTime(part[6]);
                Frequency frekuensi = parseFrequency(part[7]);
                LocalDate tanggalMulai = part[8].isBlank() ? null : LocalDate.parse(part[8]);
                LocalDate tanggalSelesai = part[9].isBlank() ? null : LocalDate.parse(part[9]);
                String deskripsi = part[10];

                listJadwal.add(new Jadwal(id, kategori, judul, lokasi, hari, jamMulai, jamSelesai, frekuensi,
                        tanggalMulai, tanggalSelesai, deskripsi));

                int numericId = Integer.parseInt(id.replace("JS226", ""));
                if (numericId > maxId) {
                    maxId = ++numericId;
                }
            }

            nextId = maxId + 1;
        }
    }
}
