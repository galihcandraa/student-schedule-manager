package src.model;

public class Jadwal {

    
    private String namaMatkul;
    private String namaRuang;
    private String hari;
    private String jam;
    
    public Jadwal(String namaMatkul, String namaRuang, String hari, String jam) {
        this.namaMatkul = namaMatkul;
        this.namaRuang = namaRuang;
        this.hari = hari;
        this.jam = jam;
    }

    public String getNamaMatkul() {
        return namaMatkul;
    }

    public void setNamaMatkul(String namaMatkul) {
        this.namaMatkul = namaMatkul;
    }

    public String getNamaRuang() {
        return namaRuang;
    }

    public void setNamaRuang(String namaRuang) {
        this.namaRuang = namaRuang;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    @Override
    public String toString() {
        return "Jadwal [namaMatkul=" + namaMatkul + ", namaRuang=" + namaRuang + ", hari=" + hari + ", jam=" + jam
                + "]";
    }

}