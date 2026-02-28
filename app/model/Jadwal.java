package app.model;

public class Jadwal {
    
    private String namaMatkul;
    private String namaRuang;
    private String hari;
    private String jamStart;
    private String jamEnd;
    
    
    public Jadwal(String namaMatkul, String namaRuang, String hari, String jamStart, String jamEnd) {
        this.namaMatkul = namaMatkul;
        this.namaRuang = namaRuang;
        this.hari = hari;
        this.jamStart = jamStart;
        this.jamEnd = jamEnd;
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
    
    public String getJamStart() {
        return jamStart;
    }

    public String getJamEnd() {
        return jamEnd;
    }

    @Override
    public String toString() {
        return "Jadwal [namaMatkul=" + namaMatkul + ", namaRuang=" + namaRuang + ", hari=" + hari + ", jam mulai=" + jamStart + ", jam selesai=" + jamEnd
                + "]";
    }
}