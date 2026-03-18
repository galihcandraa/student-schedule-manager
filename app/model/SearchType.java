package app.model;

public enum SearchType {
    MATKUL, RUANGAN, HARI, JAM_MULAI, JAM_SELESAI;

    public static SearchType fromChoice(int choice) {
        switch (choice) {
            case 1:
                return MATKUL;

            case 2:
                return RUANGAN;

            case 3:
                return HARI;

            case 4:
                return JAM_MULAI;

            case 5:
                return JAM_SELESAI;
                
            default:
                return null;
        }
    }
}