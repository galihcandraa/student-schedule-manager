package app.service;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import app.model.*;
import app.util.InputValidation;

public class JadwalService {

    private List<Jadwal> listJadwal = new ArrayList<>();

    int nextId = 1;

    private String generateId() {
        String id = "JS226" + nextId++;
        return id;
    }

    public void addData(String namaMatkul, String namaRuang, Day hari, LocalTime jamMulai,
            LocalTime jamSelesai) {
        Jadwal dataBaru = new Jadwal(generateId(), namaMatkul, namaRuang, hari, jamMulai, jamSelesai);
        listJadwal.add(dataBaru);
    }

    public List<Jadwal> showJadwal() {
        return new ArrayList<>(listJadwal);
    }

    public boolean editData(String id, String matkul, String ruang, Day hari, LocalTime jamMulai,
            LocalTime jamSelesai) {
        for (Jadwal jadwal : listJadwal) {
            if (jadwal.getId().equals(id)) {
                jadwal.setNamaMatkul(matkul);
                jadwal.setNamaRuang(ruang);
                jadwal.setHari(hari);
                jadwal.setJamMulai(jamMulai);
                jadwal.setJamSelesai(jamSelesai);
                return true;
            }
        }
        return false;
    }

    public List<Jadwal> searchByCondition(SearchType type, String value) {
        List<Jadwal> results = new ArrayList<>();

        for (Jadwal jadwal : listJadwal) {
            switch (type) {
                case MATKUL:
                    if (jadwal.getNamaMatkul().equalsIgnoreCase(value))
                        results.add(jadwal);
                    break;

                case HARI:
                    if (jadwal.getHari().toString().equals(value))
                        results.add(jadwal);
                    break;
                case ID:
                    if (jadwal.getId().equalsIgnoreCase(value))
                        results.add(jadwal);
            }
        }
        return results;
    }

    public Jadwal searchById(String id) {
        for (Jadwal j : listJadwal) {
            if (j.getId().equals(id)) {
                return j;
            }
        }
        return null;
    }

    public List<Jadwal> sortByCondition(SortType type, SortOrder order) {
        List<Jadwal> results = new ArrayList<>(listJadwal);

        Comparator<Jadwal> comparator = null;

        switch (type) {
            case MATKUL:
                comparator = Comparator.comparing(Jadwal::getNamaMatkul);
                break;

            case HARI:
                comparator = Comparator.comparing(Jadwal::getHari);
                break;

            case JAM_MULAI:
                comparator = Comparator.comparing(Jadwal::getJamMulai);
                break;
        }
        if (order == SortOrder.DESCENDING) {
            comparator = comparator.reversed();
        }
        results.sort(comparator);

        return results;
    }

    public boolean deleteDataById(String id) {
        return listJadwal.removeIf(j -> j.getId().equals(id));
    }

    public void reset() {
        listJadwal.clear();
        nextId = 1;
    }

    public Day parseDay(String field) {
        Day result;

        try {
            result = Day.valueOf(field.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tidak berhasil konversi tipe data");
        }
        return result;
    }

    public LocalTime parseTime(String field) {
        try {
            return LocalTime.parse(field, InputValidation.TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Format jam harus HH:mm");
        }
    }

}
