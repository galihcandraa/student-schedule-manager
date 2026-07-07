package app.service;

import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import app.model.*;
import app.util.InputValidation;

public class JadwalService {

    private List<Jadwal> listJadwal = new ArrayList<>();
    private final static String FILE_PATH = "data_jadwal.csv";
    int nextId = 1;

    private String generateId() {
        String id = "JS226" + nextId++;
        return id;
    }

    public void addData(String namaMatkul, String namaRuang, Day hari, LocalTime jamMulai,
            LocalTime jamSelesai) {
        Jadwal dataBaru = new Jadwal(generateId(), namaMatkul, namaRuang, hari, jamMulai, jamSelesai);
        listJadwal.add(dataBaru);
        saveToFile();
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
                saveToFile();
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
                    if (jadwal.getHari().toString().equalsIgnoreCase(value))
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
        boolean results = listJadwal.removeIf(j -> j.getId().equals(id));
        if (results) saveToFile();
        return results;
    }

    public void reset() {
        listJadwal.clear();
        nextId = 1;
        saveToFile();
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
    
    public void saveToFile() {
        try (PrintWriter writter = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Jadwal j : listJadwal) {
                writter.println(
                    j.getId() + "," + 
                    j.getNamaMatkul() + "," +
                    j.getNamaRuang() + "," +
                    j.getHari() + "," +
                    j.getJamMulai() + "," +
                    j.getJamSelesai() 
                );
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data " + e.getMessage());
        }
    } 

    public void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            int maxId = 1;

            while ((line = reader.readLine()) != null) {
                String[] part = line.split(",");
                if (part.length < 6) continue;

                String id = part[0];
                String namaMatkul = part[1];
                String namaRuang = part[2];
                Day hari = Day.valueOf(part[3]);
                LocalTime jamMulai = parseTime(part[4]);
                LocalTime jamSelesai = parseTime(part[5]);

                listJadwal.add(new Jadwal(id, namaMatkul, namaRuang, hari, jamMulai, jamSelesai));

                int numericId = Integer.parseInt(id.replace("JS226", ""));
                if (numericId > maxId) {
                    maxId = numericId++;
                }
            }

            nextId = maxId;
        } catch (IOException e) {
            System.out.println("Gagal memuat data " + e.getMessage());
        }
    }
} 
