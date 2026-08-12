package com.github.galihcandraa.personal_planner.dto;

public class JadwalRequest {
    private String judul;
    private String lokasi;
    private String kategori;
    private String hari;
    private String jamMulai;
    private String jamSelesai;
    private String frekuensi;
    private String tglMulai;
    private String tglSelesai;
    private String deskripsi;

    // WAJIB: Constructor kosong yang dicari oleh Jackson
    public JadwalRequest() {
    }

    // Getter dan Setter
    public String getJudul() { return judul; }
    public void setJudul(String judul) { this.judul = judul; }

    public String getLokasi() { return lokasi; }
    public void setLokasi(String lokasi) { this.lokasi = lokasi; }

    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }

    public String getHari() { return hari; }
    public void setHari(String hari) { this.hari = hari; }

    public String getJamMulai() { return jamMulai; }
    public void setJamMulai(String jamMulai) { this.jamMulai = jamMulai; }

    public String getJamSelesai() { return jamSelesai; }
    public void setJamSelesai(String jamSelesai) { this.jamSelesai = jamSelesai; }

    public String getFrekuensi() { return frekuensi; }
    public void setFrekuensi(String frekuensi) { this.frekuensi = frekuensi; }

    public String getTglMulai() { return tglMulai; }
    public void setTglMulai(String tglMulai) { this.tglMulai = tglMulai; }

    public String getTglSelesai() { return tglSelesai; }
    public void setTglSelesai(String tglSelesai) { this.tglSelesai = tglSelesai; }

    public String getDeskripsi() { return deskripsi; }
    public void setDeskripsi(String deskripsi) { this.deskripsi = deskripsi; }
}
