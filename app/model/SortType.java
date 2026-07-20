package app.model;

public enum SortType {
    KATEGORI, JUDUL, HARI, JAM_MULAI, TANGGAL_MULAI;

    public static SortType fromChoice(int choice) {
        switch (choice) {
            case 1:
                return KATEGORI;

            case 2:
                return JUDUL;

            case 3:
                return HARI;

            case 4:
                return JAM_MULAI;
        
            case 5:
                return TANGGAL_MULAI;
                
            default:
                return null;
        }
    }
}
