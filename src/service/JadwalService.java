package src.service;

import java.util.ArrayList;
import java.util.List;

import src.model.Jadwal;
import src.model.SearchType;

public class JadwalService {

    private List<Jadwal> listJadwal = new ArrayList<>();

    public void addData(String namaMatkul, String namaRuang, String hari, String jam) {
        Jadwal dataBaru = new Jadwal(namaMatkul, namaRuang, hari, jam);
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

                case JAM:
                    if (jadwal.getJam().equalsIgnoreCase(value))
                        results.add(jadwal);
                    break;
            }
        }
        return results;
    }

    public boolean deleteDataByNameMatkul(String name) {
        return listJadwal.removeIf(j -> j.getNamaMatkul().equalsIgnoreCase(name));
    }

    public void deleteDataByIndex(int index) {
        if (index > 0 && index < listJadwal.size()) {
            listJadwal.remove(index);
        }
    }

    public void reset() {
        listJadwal.clear();
    }
}
