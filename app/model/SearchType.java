package app.model;

public enum SearchType {
    ID, KATEGORI, JUDUL, HARI;

    public static SearchType fromChoice(int choice) {
        switch (choice) {
            case 1:
                return ID;

            case 2:
                return KATEGORI;

            case 3:
                return JUDUL;

            case 4:
                return HARI;
                
            default:
                return null;
        }
    }
}