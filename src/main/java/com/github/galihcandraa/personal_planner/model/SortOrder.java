package com.github.galihcandraa.personal_planner.model;

public enum SortOrder {
    ASCENDING, DESCENDING;

    public static SortOrder fromChoice(int choice) {
        switch (choice) {
            case 1:
                return ASCENDING;

            case 2:
                return DESCENDING;

            default:
                return null;
        }
    }

}
