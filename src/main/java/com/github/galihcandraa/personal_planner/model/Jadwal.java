package com.github.galihcandraa.personal_planner.model;

import java.time.*;

import com.github.galihcandraa.personal_planner.util.DateTimeFormatters;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Jadwal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category kategori;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String judul;

    @Column(length = 100)
    private String lokasi;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Day hari;
    
    @NotNull
    @Column(nullable = false)
    private LocalTime jamMulai;

    @NotNull
    @Column(nullable = false)
    private LocalTime jamSelesai;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Frequency frekuensi;
    
    private LocalDate tanggalMulai;
    private LocalDate tanggalSelesai;
    private String deskripsi;

    public Jadwal() {
    };

    public Jadwal(Category kategori, String judul, String lokasi, Day hari, LocalTime jamMulai,
            LocalTime jamSelesai, Frequency frekuensi, LocalDate tanggalMulai, LocalDate tanggalSelesai,
            String deskripsi) {
        this.kategori = kategori;
        this.judul = judul;
        this.lokasi = lokasi;
        this.hari = hari;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.frekuensi = frekuensi;
        this.tanggalMulai = tanggalMulai;
        this.tanggalSelesai = tanggalSelesai;
        this.deskripsi = deskripsi;
    }

    public long getId() {
        return id;
    }

    public Category getKategori() {
        return kategori;
    }

    public String getJudul() {
        return judul;
    }

    public String getLokasi() {
        return lokasi;
    }

    public Day getHari() {
        return hari;
    }

    public LocalTime getJamMulai() {
        return jamMulai;
    }

    public LocalTime getJamSelesai() {
        return jamSelesai;
    }

    public Frequency getFrekuensi() {
        return frekuensi;
    }

    public LocalDate getTanggalMulai() {
        return tanggalMulai;
    }

    public LocalDate getTanggalSelesai() {
        return tanggalSelesai;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setKategori(Category kategori) {
        this.kategori = kategori;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public void setHari(Day hari) {
        this.hari = hari;
    }

    public void setJamMulai(LocalTime jamMulai) {
        this.jamMulai = jamMulai;
    }

    public void setJamSelesai(LocalTime jamSelesai) {
        this.jamSelesai = jamSelesai;
    }

    public void setFrekuensi(Frequency frekuensi) {
        this.frekuensi = frekuensi;
    }

    public void setTanggalMulai(LocalDate tanggalMulai) {
        this.tanggalMulai = tanggalMulai;
    }

    public void setTanggalSelesai(LocalDate tanggalSelesai) {
        this.tanggalSelesai = tanggalSelesai;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public void print() {
        String tglMulaiStr = (getTanggalMulai() == null) ? "-" : getTanggalMulai().format(DateTimeFormatters.DATE_FORMATTER);
        String tglSelesaiStr = (getTanggalSelesai() == null) ? "-" : getTanggalSelesai().format(DateTimeFormatters.DATE_FORMATTER);

        System.out.printf("| %-6s | %-10s | %-25s | %-10s | %-6s | %-9s | %-11s | %-10s | %-10s | %-11s |%n",
            getId(),
            getKategori(),
            getJudul(),
            getLokasi(),
            getHari(),
            getJamMulai(),
            getJamSelesai(),
            getFrekuensi(),
            tglMulaiStr,
            tglSelesaiStr);
    }
}