package app.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import app.model.Jadwal;
import app.model.SearchType;

public class JadwalService {

    private List<Jadwal> listJadwal = new ArrayList<>();

    int nextId = 1;
    private String generateId() {
        String id = "JS226" + nextId++;
        return id;
    }

    public void addData(String namaMatkul, String namaRuang, String hari, LocalTime jamMulai,
            LocalTime jamSelesai) {
        Jadwal dataBaru = new Jadwal(generateId(), namaMatkul, namaRuang, hari, jamMulai, jamSelesai);
        listJadwal.add(dataBaru);
    }

    public List<Jadwal> showJadwal() {
        return new ArrayList<>(listJadwal);
    }

    public List<Jadwal> searchByCondition(SearchType type, String value) {
        List<Jadwal> results = new ArrayList<>();

        for (Jadwal jadwal : listJadwal) {
            switch (type) {
                case MATKUL:
                    if (jadwal.getNamaMatkul().equalsIgnoreCase(value))
                        results.add(jadwal);
                    break;

                case RUANGAN:
                    if (jadwal.getNamaRuang().equalsIgnoreCase(value))
                        results.add(jadwal);
                    break;

                case HARI:
                    if (jadwal.getHari().equalsIgnoreCase(value))
                        results.add(jadwal);
                    break;

                case JAM_MULAI:
                    if (jadwal.getJamMulai().equals(LocalTime.parse(value)))
                        results.add(jadwal);
                    break;
                    
                case JAM_SELESAI:
                    if (jadwal.getJamSelesai().equals(LocalTime.parse(value)))
                        results.add(jadwal);
                    break;
            }
        }
        return results;
    }

    public boolean deleteDataById(String id) {
        boolean found = false;

        for (Jadwal j : listJadwal) {
            if (j.getId().equals(id)) {
                found = true;
            }
        }

        if (!found) {
            return false;
        } else {
            return listJadwal.removeIf(j -> j.getId().equals(id));
        }
    }

    public void reset() {
        listJadwal.clear();
    }
}
