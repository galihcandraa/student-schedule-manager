package app.model;

import java.time.*;

import app.util.DateTimeFormatters;

public class Jadwal {

    private String id;
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

    public Jadwal() {
    };

    public Jadwal(String id, Category kategori, String judul, String lokasi, Day hari, LocalTime jamMulai,
            LocalTime jamSelesai, Frequency frekuensi, LocalDate tanggalMulai, LocalDate tanggalSelesai,
            String deskripsi) {
        this.id = id;
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

    public String getId() {
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