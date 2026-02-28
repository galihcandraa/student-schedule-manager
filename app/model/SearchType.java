package app.model;

public enum SearchType {
    MATKUL, RUANGAN, HARI, JAM_START, JAM_END;

    public static SearchType fromChoice(int choice) {
        switch (choice) {
            case 1:
                return MATKUL;
            case 2:
                return RUANGAN;
            case 3:
                return HARI;
            case 4:
                return JAM_START;
            case 5:
                return JAM_END;
            default:
                return null;
        }
    }
}