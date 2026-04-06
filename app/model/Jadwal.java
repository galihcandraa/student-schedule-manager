package app.model;

import java.time.LocalTime;

public class Jadwal {
    
    private String id;
    private String namaMatkul;
    private String namaRuang;
    private Day hari;
    private LocalTime jamMulai;
    private LocalTime jamSelesai;
    
    
    public Jadwal(String id, String namaMatkul, String namaRuang, Day hari, LocalTime jamMulai, LocalTime jamSelesai) {
        this.id = id;
        this.namaMatkul = namaMatkul;
        this.namaRuang = namaRuang;
        this.hari = hari;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
    }

    public String getId() {
        return id;
    }

    public String getNamaMatkul() {
        return namaMatkul;
    }

    public String getNamaRuang() {
        return namaRuang;
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

    @Override
    public String toString() {
        return "Jadwal [ID=" + id + ", namaMatkul=" + namaMatkul + ", namaRuang=" + namaRuang + ", hari=" + hari + ", jam mulai=" + jamMulai + ", jam selesai=" + jamSelesai + "]";
    }

    public void setNamaMatkul(String namaMatkul) {
        this.namaMatkul = namaMatkul;
    }

    public void setNamaRuang(String namaRuang) {
        this.namaRuang = namaRuang;
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
}