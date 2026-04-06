package app.model;

public enum SortType {
    MATKUL, HARI, JAM_MULAI;

    public static SortType fromChoice(int choice) {
        switch (choice) {
            case 1:
                return MATKUL;

            case 2:
                return HARI;

            case 3:
                return JAM_MULAI;
        
            default:
                return null;
        }
    }
}
