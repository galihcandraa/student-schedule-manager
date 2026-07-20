package app.model;

public enum Category {
    KULIAH, ORGANISASI, PRIBADI, KERJA;

    public static Category fromChoice(int choice) {
        switch (choice) {
            case 1:
                return KULIAH;

            case 2:
                return ORGANISASI;

            case 3:
                return PRIBADI;

            case 4:
                return KERJA;
                
            default:
                return null;
        }
    }
}
