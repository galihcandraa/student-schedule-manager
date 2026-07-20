package app.model;

public enum Frequency {
    SELALU, SEKALI, RENTANG;

    public static Frequency fromChoice(int choice) {
        switch (choice) {
            case 1:
                return SELALU;

            case 2:
                return SEKALI;

            case 3:
                return RENTANG;

            default:
                return null;
        }
    }
}
