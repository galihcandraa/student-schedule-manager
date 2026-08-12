package com.github.galihcandraa.personal_planner.model;

public enum Day {
    SENIN, SELASA, RABU, KAMIS, JUMAT, SABTU, MINGGU;

    public static Day fromChoice(int choice) {
        switch (choice) {
            case 1:
                return SENIN;

            case 2:
                return SELASA;

            case 3:
                return RABU;

            case 4:
                return KAMIS;

            case 5:
                return JUMAT;

            case 6:
                return SABTU;

            case 7:
                return MINGGU;

            default:
                return null;
        }
    }
}
