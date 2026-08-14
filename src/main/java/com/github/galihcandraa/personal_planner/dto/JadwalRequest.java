package com.github.galihcandraa.personal_planner.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.github.galihcandraa.personal_planner.model.*;

public class JadwalRequest {
    private Category kategori;
    private String judul;
    private String lokasi;
    private Day hari;
    private LocalTime jamMulai;
    private LocalTime jamSelesai;
    private Frequency frekuensi;
    private LocalDate tanggalMulai;
    private LocalDate tanggalSelesai;
    private String deskripsi;

    public JadwalRequest() {
    }

    public Jadwal toEntity() {
        Jadwal jadwal = new Jadwal();
        jadwal.setKategori(this.kategori);
        jadwal.setJudul(this.judul);
        jadwal.setLokasi(this.lokasi);
        jadwal.setHari(this.hari);
        jadwal.setJamMulai(this.jamMulai);
        jadwal.setJamSelesai(this.jamSelesai);
        jadwal.setFrekuensi(this.frekuensi);
        jadwal.setTanggalMulai(this.tanggalMulai);
        jadwal.setTanggalSelesai(this.tanggalSelesai);
        jadwal.setDeskripsi(deskripsi);
        return jadwal;
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

    public Category getKategori() {
        return kategori;
    }

    public void setKategori(Category kategori) {
        this.kategori = kategori;
    }

    public Day getHari() {
        return hari;
    }

    public void setHari(Day hari) {
        this.hari = hari;
    }

    public LocalTime getJamMulai() {
        return jamMulai;
    }

    public void setJamMulai(LocalTime jamMulai) {
        this.jamMulai = jamMulai;
    }

    public LocalTime getJamSelesai() {
        return jamSelesai;
    }

    public void setJamSelesai(LocalTime jamSelesai) {
        this.jamSelesai = jamSelesai;
    }

    public Frequency getFrekuensi() {
        return frekuensi;
    }

    public void setFrekuensi(Frequency frekuensi) {
        this.frekuensi = frekuensi;
    }

    public LocalDate getTglMulai() {
        return tanggalMulai;
    }

    public void setTglMulai(LocalDate tglMulai) {
        this.tanggalMulai = tglMulai;
    }

    public LocalDate getTglSelesai() {
        return tanggalSelesai;
    }

    public void setTglSelesai(LocalDate tglSelesai) {
        this.tanggalSelesai = tglSelesai;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
