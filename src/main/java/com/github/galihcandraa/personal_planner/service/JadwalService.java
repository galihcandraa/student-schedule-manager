package com.github.galihcandraa.personal_planner.service;

import com.github.galihcandraa.personal_planner.repository.JadwalRepository;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.ValidationException;

import com.github.galihcandraa.personal_planner.model.*;

@Service
public class JadwalService {

    private final JadwalRepository jadwalRepository;

    JadwalService(JadwalRepository jadwalRepository) {
        this.jadwalRepository = jadwalRepository;
    }

    public List<Jadwal> showJadwal() {
        return jadwalRepository.findAll();
    }

    public void addJadwal(Jadwal jadwal) {
        validateTimeLogic(jadwal.getJamMulai(), jadwal.getJamSelesai());
        validateTimeConflict(jadwal.getHari(), jadwal.getJamMulai(), jadwal.getJamSelesai(), 0);
        validateDateLogic(jadwal.getTanggalMulai(), jadwal.getTanggalSelesai());

        if (jadwal.getFrekuensi() == Frequency.SELALU) {
            jadwal.setTanggalSelesai(null);
        }
        
        if (jadwal.getDeskripsi() == null || jadwal.getDeskripsi().isBlank())
            jadwal.setDeskripsi("-");

        jadwalRepository.save(jadwal);
    }

    public void editJadwal(Jadwal jadwal, long id) {
        Jadwal oldJadwal = jadwalRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Jadwal dengan ID: " + id + " tidak ditemukan!"));
        
        validateTimeLogic(jadwal.getJamMulai(), jadwal.getJamSelesai());
        validateTimeConflict(jadwal.getHari(), jadwal.getJamMulai(), jadwal.getJamSelesai(), id);
        validateDateLogic(jadwal.getTanggalMulai(), jadwal.getTanggalSelesai());

        oldJadwal.setKategori(jadwal.getKategori());
        oldJadwal.setJudul(jadwal.getJudul());
        oldJadwal.setLokasi(jadwal.getLokasi());
        oldJadwal.setHari(jadwal.getHari());
        oldJadwal.setJamMulai(jadwal.getJamMulai());
        oldJadwal.setJamSelesai(jadwal.getJamSelesai());
        oldJadwal.setFrekuensi(jadwal.getFrekuensi());
        oldJadwal.setTanggalMulai(jadwal.getTanggalMulai());
        oldJadwal.setTanggalSelesai(jadwal.getTanggalSelesai());
        oldJadwal.setDeskripsi(jadwal.getDeskripsi());
        
        if (oldJadwal.getFrekuensi() == Frequency.SELALU) {
            oldJadwal.setTanggalSelesai(null);
        }

        if (oldJadwal.getDeskripsi() == null || oldJadwal.getDeskripsi().isBlank())
            oldJadwal.setDeskripsi("-");

        jadwalRepository.save(oldJadwal);
    }

    public Jadwal findById(long id) {
        return jadwalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Pencarian gagal, ID " + id + " tidak ditemukan!"));
    }

    public List<Jadwal> searchByCondition(SearchType type, String value) {
        if (value == null || value.isBlank()) {
            return jadwalRepository.findAll();
        }

        value = value.trim();

        switch (type) {
            case ID:
                try {
                    Long id = Long.parseLong(value);
                    return jadwalRepository.findById(id).map(List::of).orElseGet(ArrayList::new);
                } catch (NumberFormatException e) {
                    throw new ValidationException("[ERROR] ID bukan termasuk angka, ulangi!");
                }

            case KATEGORI:
                try {
                    Category kategori = Category.valueOf(value.toUpperCase());
                    return jadwalRepository.findByKategori(kategori);
                } catch (IllegalArgumentException e) {
                    return new ArrayList<>();
                }

            case JUDUL:
                return jadwalRepository.findByJudulContainingIgnoreCase(value);

            case HARI:
                try {
                    Day hari = Day.valueOf(value.toUpperCase());
                    return jadwalRepository.findByHari(hari);
                } catch (IllegalArgumentException e) {
                    return new ArrayList<>();
                }

            default:
                return new ArrayList<>();
        }
    }

    public List<Jadwal> sortByCondition(SortType type, SortOrder order) {
        Sort.Direction direction = (order == SortOrder.DESC) ? Sort.Direction.DESC : Sort.Direction.ASC;

        if (type == null)
            type = SortType.JUDUL;

        Sort sort;

        switch (type) {
            case KATEGORI:
                sort = Sort.by(direction, "kategori");
                break;

            case JUDUL:
                sort = Sort.by(direction, "judul");
                break;

            case HARI:
                sort = Sort.by(direction, "hari");
                break;

            case JAM_MULAI:
                sort = Sort.by(direction, "jamMulai");
                break;

            case TANGGAL_MULAI:
                sort = Sort.by(direction, "tanggalMulai", "jamMulai");
                break;

            default:
                sort = Sort.by(direction, "id");
        }
        return jadwalRepository.findAll(sort);
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
        if (targetEnd == null)
            return;

        if (targetStart.isAfter(targetEnd)) {
            throw new ValidationException("[ERROR] tanggal mulai wajib sebelum tanggal selesai, ulangi!");
        }
    }
}
