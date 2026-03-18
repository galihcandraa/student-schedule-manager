package app.model;

import java.time.LocalTime;

public class Jadwal {
    
    private String id;
    private String namaMatkul;
    private String namaRuang;
    private String hari;
    private LocalTime jamMulai;
    private LocalTime jamSelesai;
    
    
    public Jadwal(String id, String namaMatkul, String namaRuang, String hari, LocalTime jamMulai, LocalTime jamSelesai) {
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
    
    public String getHari() {
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
        return "Jadwal [namaMatkul=" + namaMatkul + ", namaRuang=" + namaRuang + ", hari=" + hari + ", jam mulai=" + jamMulai + ", jam selesai=" + jamSelesai + "]";
    }
}