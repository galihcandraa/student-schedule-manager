package app.service;

import java.util.ArrayList;
import java.util.List;

import app.model.Jadwal;
import app.model.SearchType;

public class JadwalService {

    private List<Jadwal> listJadwal = new ArrayList<>();

    public void addData(String namaMatkul, String namaRuang, String hari, String jamStart, String jamEnd) {
        Jadwal dataBaru = new Jadwal(namaMatkul, namaRuang, hari, jamStart, jamEnd);
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
                    if (jadwal.getNamaMatkul().equals(value))
                        results.add(jadwal);
                    break;

                case RUANGAN:
                    if (jadwal.getNamaRuang().equals(value))
                        results.add(jadwal);
                    break;

                case HARI:
                    if (jadwal.getHari().equals(value))
                        results.add(jadwal);
                    break;

                case JAM_START:
                    if (jadwal.getJamStart().equals(value))
                        results.add(jadwal);
                    break;
                case JAM_END:
                    if (jadwal.getJamEnd().equals(value))
                        results.add(jadwal);
                    break;
            }
        }
        return results;
    }

    public boolean deleteDataByNameMatkul(String name) {
        return listJadwal.removeIf(j -> j.getNamaMatkul().equalsIgnoreCase(name));
    }

    public boolean deleteDataByNomor(int number) {
        int index = number - 1;
        if (index < 0 || index > listJadwal.size()) {
            return false;
        }
        listJadwal.remove(index);
        return true;
    }

    public void reset() {
        listJadwal.clear();
    }
}
