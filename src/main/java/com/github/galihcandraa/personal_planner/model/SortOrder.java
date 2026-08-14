package com.github.galihcandraa.personal_planner.model;

public enum SortOrder {
    ASC, DESC;

    public static SortOrder fromChoice(int choice) {
        switch (choice) {
            case 1:
                return ASC;

            case 2:
                return DESC;

            default:
                return null;
        }
    }

}
