package app.ui;

import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.time.LocalTime;
import java.time.LocalDate;

import app.controller.JadwalController;
import app.model.*;

public class JadwalApp {
    static JadwalController controller = new JadwalController();

    public static void showMenu() {
        System.out.println("===== MANAJEMEN JADWAL MAHASISWA  =====");
        System.out.println("1. Tampil Jadwal");
        System.out.println("2. Tambah Jadwal");
        System.out.println("3. Edit Jadwal");
        System.out.println("4. Hapus Jadwal");
        System.out.println("0. Keluar");
        System.out.println("===============================");
        System.out.print("Masukkan pilihan (0 - 4): ");
    }

    public static void displayShowMenu() {
        System.out.println("Pilih Menu:");
        System.out.println("1. Lihat Jadwal");
        System.out.println("2. Cari Jadwal");
        System.out.println("3. Urutkan Jadwal");
        System.out.println("0. Kembali");
        System.out.println("===============================");
        System.out.print("Masukkan pilihan (0 - 3): ");
    }

    public static void showAllJadwal(List<Jadwal> listData) {
        if (listData.isEmpty()) {
            System.out.println("Tidak ada jadwal!\n");
        } else {
            System.out.println("\n=== LIST JADWAL ===");
            System.out.println(
                    "-------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("| %-6s | %-10s | %-25s | %-10s | %-6s | %-9s | %-11s | %-10s | %-10s | %-11s |%n",
                    "ID", "Kategori", "Judul", "Lokasi", "Hari", "Jam Mulai", "Jam Selesai", "Frekuensi", "Tgl Mulai",
                    "Tgl Selesai");
            for (int i = 0; i < listData.size(); i++) {
                System.out.println(
                        "-------------------------------------------------------------------------------------------------------------------------------------------");
                listData.get(i).print();
            }
            System.out.println(
                    "-------------------------------------------------------------------------------------------------------------------------------------------\n");
        }
    }

    public static void showDetailJadwal(Jadwal data) {
        System.out.println("----- Jadwal -----");
        System.out.println("ID                : " + data.getId());
        System.out.println("Kategori          : " + data.getKategori());
        System.out.println("Judul             : " + data.getJudul());
        System.out.println("Lokasi            : " + data.getLokasi());
        System.out.println("Hari              : " + data.getHari());
        System.out.println("Jam Mulai         : " + data.getJamMulai());
        System.out.println("Jam Selesai       : " + data.getJamSelesai());
        System.out.println("Frekuensi         : " + data.getFrekuensi());
        System.out.println("Tanggal Mulai     : " + data.getTanggalMulai());
        System.out.println("Tanggal Selesai   : " + data.getTanggalSelesai());
        System.out.println("Deskripsi         : " + data.getDeskripsi());
        System.out.println("------------------\n");
    }

    public static void showMenuCategory() {
        System.out.println("- kategori ");
        System.out.println("  1. Kuliah");
        System.out.println("  2. Organisasi");
        System.out.println("  3. Pribadi");
        System.out.println("  4. Kerja");
    }

    public static void showMenuDay() {
        System.out.println("- hari");
        System.out.println("  1. Senin");
        System.out.println("  2. Selasa");
        System.out.println("  3. Rabu");
        System.out.println("  4. Kamis");
        System.out.println("  5. Jum'at");
        System.out.println("  6. Sabtu");
        System.out.println("  7. Minggu");
    }

    public static void showMenuFrequency() {
        System.out.println("- frekuensi: ");
        System.out.println("  1. Selalu");
        System.out.println("  2. Sekali");
        System.out.println("  3. Rentang");
    }

    private static String promptUntilValid(Scanner sc, String prompt, Function<String, String> validator) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
            String error = validator.apply(input);
            if (error == null)
                return input;
            System.out.println(error);
        }
    }

    private static int choicePromptUntilValid(Scanner sc, String prompt, Function<Integer, String> validator) {
        while (true) {
            System.out.print(prompt);
            int input = sc.nextInt();
            sc.nextLine();
            String error = validator.apply(input);
            if (error == null)
                return input;
            System.out.println(error);
        }
    }

    public static void inputOrEditData(Scanner sc, boolean isEdit) {
        Category kategori = null;
        Day hari = null;
        LocalTime parsedTimeStart = null, parsedTimeEnd = null;
        LocalDate parsedDateStart = null, parsedDateEnd = null;

        Frequency frekuensi = null;
        int pilKategori, pilHari, pilFrekuensi;
        String judul = null, lokasi = null, jamMulai, jamSelesai, tglMulai = null, tglSelesai = null, deskripsi = null;

        String error, msg;
        String idEdit = null;

        while (true) {
            if (isEdit) {
                System.out.print("Masukkan ID target: ");
                idEdit = sc.nextLine();

                error = controller.validateIdEdit(idEdit);
                if (error != null) {
                    System.out.println(error);
                    break;
                }

                showDetailJadwal(controller.findById(idEdit));
            }

            showMenuCategory();
            pilKategori = choicePromptUntilValid(sc, "Masukkan pilihan (1 - 4): ", controller::validateCategory);
            kategori = Category.fromChoice(pilKategori);

            judul = promptUntilValid(sc, "- Masukkan judul: ", controller::validateTitle);
            lokasi = promptUntilValid(sc, "- Masukkan lokasi: ", controller::validateLocation);

            showMenuDay();
            pilHari = choicePromptUntilValid(sc, "Masukkan pilihan (1 - 7): ", controller::validateDay);
            hari = Day.fromChoice(pilHari);

            while (true) {
                jamMulai = promptUntilValid(sc, "- Masukkan jam mulai: ", controller::validateFormatTimeStart);
                jamSelesai = promptUntilValid(sc, "- Masukkan jam selesai: ", controller::validateFormatTimeEnd);

                parsedTimeStart = controller.parseTime(jamMulai);
                parsedTimeEnd = controller.parseTime(jamSelesai);

                String ignoreId = isEdit ? idEdit : null;
                error = controller.validateAllTime(hari, parsedTimeStart, parsedTimeEnd, ignoreId);
                if (error != null) {
                    System.out.println(error);
                    continue;
                }
                break;
            }

            showMenuFrequency();
            pilFrekuensi = choicePromptUntilValid(sc, "Masukkan pilihan (1 - 3): ", controller::validateFrequency);
            frekuensi = Frequency.fromChoice(pilFrekuensi);
            if (pilFrekuensi != 1) {
                while (true) {
                    if (pilFrekuensi == 2) {
                        tglMulai = promptUntilValid(sc, "- Masukkan tanggal mulai: ", controller::validateFormatDateStart);
                        tglSelesai = tglMulai;
                        
                        parsedDateStart = controller.parseDate(tglMulai);
                        parsedDateEnd = controller.parseDate(tglSelesai);
                        break;
                    }
                    tglMulai = promptUntilValid(sc, "- Masukkan tanggal mulai: ", controller::validateFormatDateStart);
                    tglSelesai = promptUntilValid(sc, "- Masukkan tanggal selesai: ", controller::validateFormatDateEnd);

                    parsedDateStart = controller.parseDate(tglMulai);
                    parsedDateEnd = controller.parseDate(tglSelesai);

                    error = controller.validateDateLogic(parsedDateStart, parsedDateEnd);
                    if (error != null) {
                        System.out.println(error);
                        continue;
                    }
                    break;
                }
            }

            deskripsi = promptUntilValid(sc, "- Masukkan deskripsi (opsional): ", controller::validateDescription);
            break;
        }

        if (!isEdit) {
            msg = controller.addJadwal(kategori, judul, lokasi, hari, parsedTimeStart, parsedTimeEnd, frekuensi,
                    parsedDateStart, parsedDateEnd, deskripsi);
            System.out.println(msg);
        } else {
            msg = controller.editJadwal(idEdit, kategori, judul, lokasi, hari, parsedTimeStart, parsedTimeEnd,
                    frekuensi,
                    parsedDateStart, parsedDateEnd, deskripsi);
            System.out.println(msg);
        }
    }

    public static void inputData(Scanner sc) {
        System.out.println("\n=== TAMBAH JADWAL ===");
        inputOrEditData(sc, false);
    }

    public static void editData(Scanner sc) {
        System.out.println("\n=== EDIT JADWAL ===");
        inputOrEditData(sc, true);
    }

    public static void showMenuSearch() {
        System.out.println("\n=== CARI JADWAL ===");
        System.out.println("1. Cari berdasarkan ID");
        System.out.println("2. Cari berdasarkan kategori");
        System.out.println("3. Cari berdasarkan judul");
        System.out.println("4. Cari berdasarkan hari");
        System.out.println("0. Kembali");
        System.out.print("Masukkan pilihan (0 - 4): ");
    }

    public static void showMenuSort() {
        System.out.println("\n=== URUTAN JADWAL ===");
        System.out.println("1. Urutan berdasarkan kategori");
        System.out.println("2. Urutan berdasarkan judul");
        System.out.println("3. Urutan berdasarkan hari");
        System.out.println("4. Urutan berdasarkan jam");
        System.out.println("5. Urutan berdasarkan tanggal");
        System.out.println("0. Kembali");
        System.out.print("Masukkan pilihan (0 - 5): ");
    }

    public static void showSortType() {
        System.out.println("Pilih urutan");
        System.out.println("1. Ascending");
        System.out.println("2. Descending");
        System.out.println("0. Kembali");
        System.out.print("Masukkan pilihan (0 - 2): ");
    }

    public static void showMenuRemove() {
        System.out.println("\n=== REMOVE JADWAL ===");
        System.out.println("1. Hapus berdasarkan ID");
        System.out.println("2. Hapus semua");
        System.out.println("0. Kembali");
        System.out.print("Masukkan pilihan (0 - 2): ");
    }

    public static void main(String[] args) {
        controller.loadFromFile();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            showMenu();
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 0:
                    System.out.println("Keluar dari program.");
                    break;

                case 1:
                    displayShowMenu();
                    int choiceShowMenu = sc.nextInt();
                    sc.nextLine();

                    if (choiceShowMenu == 0) {
                        System.out.println("Kembali\n");
                        break;
                    }

                    switch (choiceShowMenu) {
                        case 1:
                            showAllJadwal(controller.showJadwal());
                            break;

                        case 2:
                            showMenuSearch();
                            int choiceSearch = sc.nextInt();
                            sc.nextLine();

                            if (choiceSearch == 0) {
                                System.out.println("Kembali");
                                break;
                            }

                            SearchType searchType = SearchType.fromChoice(choiceSearch);
                            if (searchType == null) {
                                System.out.println("Pilihan tidak valid!");
                                continue;
                            }

                            System.out.print("Masukkan " + searchType.toString().toLowerCase() + ": ");
                            String searchValue = sc.nextLine();

                            Jadwal searchResults = controller.searchJadwal(searchType, searchValue);
                            if (searchResults == null) {
                                System.out.println(
                                        "Jadwal dengan " + searchType + ": " + searchValue + " tidak ditemukan\n");
                            } else {
                                showDetailJadwal(searchResults);
                            }
                            ;
                            break;

                        case 3:
                            showMenuSort();
                            int choiceSort = sc.nextInt();
                            sc.nextLine();

                            if (choiceSort == 0) {
                                System.out.println("Kembali\n");
                                break;
                            }

                            SortType sortType = SortType.fromChoice(choiceSort);
                            if (sortType == null) {
                                System.out.println("Pilihan tidak valid!\n");
                                continue;
                            }

                            showSortType();
                            int choiceSortType = sc.nextInt();
                            sc.nextLine();

                            if (choiceSortType == 0) {
                                System.out.println("Kembali\n");
                                break;
                            }

                            SortOrder sortOrder = SortOrder.fromChoice(choiceSortType);
                            if (sortOrder == null) {
                                System.out.println("Pilihan tidak valid!\n");
                                continue;
                            }

                            List<Jadwal> sortResults = controller.sortJadwal(sortType, sortOrder);
                            if (sortResults.isEmpty()) {
                                System.out.println("Tidak ada data!\n");
                            } else {
                                showAllJadwal(sortResults);
                            }
                            break;

                        default:
                            System.out.println("Pilihan tidak valid!\n");
                            break;
                    }
                    break;

                case 2:
                    inputData(sc);
                    break;

                case 3:
                    editData(sc);
                    break;

                case 4:
                    showMenuRemove();
                    int choiceDelete = sc.nextInt();
                    sc.nextLine();

                    if (choiceDelete == 0) {
                        System.out.println("Kembali\n");
                        break;
                    }

                    switch (choiceDelete) {
                        case 1:
                            String msg;
                            System.out.print("Masukkan ID: ");
                            String deleteId = sc.nextLine();

                            msg = controller.deleteById(deleteId);
                            System.out.println(msg);
                            break;

                        case 2:
                            System.out.print("Apakah anda yakin? (y/n): ");
                            String DelAll = sc.nextLine();

                            switch (DelAll) {
                                case "y", "Y":
                                    msg = controller.reset();
                                    System.out.println(msg);
                                    break;

                                case "n", "N":
                                    System.out.println("Aksi dibatalkan\n");
                                    break;

                                default:
                                    System.out.println("Pilihan tidak valid!\n");
                                    break;
                            }
                            break;

                        default:
                            System.out.println("Pilihan tidak valid!\n");
                            break;
                    }
                    break;

                default:
                    System.out.println("Pilihan tidak valid!\n");
                    break;
            }

        } while (choice != 0);

        sc.close();
    }
}