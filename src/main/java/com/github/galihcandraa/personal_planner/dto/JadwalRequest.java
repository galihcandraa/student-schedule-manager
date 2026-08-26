package com.github.galihcandraa.personal_planner.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.github.galihcandraa.personal_planner.model.*;
import com.github.galihcandraa.personal_planner.util.DateTimeFormatters;

public class JadwalRequest {
    private String kategori;
    private String judul;
    private String lokasi;
    private String hari;
    private String jamMulai;
    private String jamSelesai;
    private String frekuensi;
    private String tanggalMulai;
    private String tanggalSelesai;
    private String deskripsi;

    public JadwalRequest() {
    }

    public Jadwal toEntity() {
        Jadwal jadwal = new Jadwal();
        jadwal.setKategori(Category.valueOf(this.kategori.toUpperCase()));
        jadwal.setJudul(this.judul);
        jadwal.setLokasi(this.lokasi);
        jadwal.setHari(Day.valueOf(this.hari.toUpperCase()));
        jadwal.setJamMulai(LocalTime.parse(this.jamMulai, DateTimeFormatters.TIME_FORMATTER));
        jadwal.setJamSelesai(LocalTime.parse(this.jamSelesai, DateTimeFormatters.TIME_FORMATTER));
        jadwal.setFrekuensi(Frequency.valueOf(this.frekuensi.toUpperCase()));
        jadwal.setTanggalMulai(parseDateOrNull(tanggalMulai));
        jadwal.setTanggalSelesai(parseDateOrNull(tanggalSelesai));
        jadwal.setDeskripsi(deskripsi);
        return jadwal;
    }

    private LocalDate parseDateOrNull(String value) {
        if (value == null || value.isBlank()) 
            return null;
        return LocalDate.parse(value, DateTimeFormatters.DATE_FORMATTER);
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getJamMulai() {
        return jamMulai;
    }

    public void setJamMulai(String jamMulai) {
        this.jamMulai = jamMulai;
    }

    public String getJamSelesai() {
        return jamSelesai;
    }

    public void setJamSelesai(String jamSelesai) {
        this.jamSelesai = jamSelesai;
    }

    public String getFrekuensi() {
        return frekuensi;
    }

    public void setFrekuensi(String frekuensi) {
        this.frekuensi = frekuensi;
    }

    public String getTanggalMulai() {
        return tanggalMulai;
    }

    public void setTanggalMulai(String tanggalMulai) {
        this.tanggalMulai = tanggalMulai;
    }

    public String getTanggalSelesai() {
        return tanggalSelesai;
    }

    public void setTanggalSelesai(String tanggalSelesai) {
        this.tanggalSelesai = tanggalSelesai;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
