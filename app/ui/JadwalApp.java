package app.ui;

import java.util.List;
import java.util.Scanner;
import java.time.LocalTime;

import app.model.*;
import app.service.JadwalService;
import app.util.InputValidation;

public class JadwalApp {
    static JadwalService service = new JadwalService();

    public static void showMenu() {
        System.out.println("===== MANAJEMEN JADWAL MHS =====");
        System.out.println("1. Input data jadwal");
        System.out.println("2. Tampilkan jadwal");
        System.out.println("3. Edit jadwal");
        System.out.println("4. Cari jadwal");
        System.out.println("5. Urutkan jadwal");
        System.out.println("6. Hapus jadwal");
        System.out.println("7. Keluar");
        System.out.println("===============================");
        System.out.print("Masukkan pilihan (1 - 7): ");
    }

    public static void showAllJadwal(List<Jadwal> listData) {
        if (listData.isEmpty()) {
            System.out.println("Tidak ada data!\n");
        } else {
            System.out.println("\n=== LIST JADWAL ===");
            System.out.println(
                    "---------------------------------------------------------------------------------------------------");
            System.out.printf("| %-8s | %-30s | %-10s | %-10s | %-10s | %-12s |%n", "ID", "Nama Mata Kuliah", "Ruang",
                    "Hari", "Jam Mulai", "Jam Selesai");
            for (int i = 0; i < listData.size(); i++) {
                System.out.println(
                        "---------------------------------------------------------------------------------------------------");
                System.out.printf("| %-8s | %-30s | %-10s | %-10s | %-10s | %-12s |%n",
                        listData.get(i).getId(),
                        listData.get(i).getNamaMatkul(),
                        listData.get(i).getNamaRuang(),
                        listData.get(i).getHari(),
                        listData.get(i).getJamMulai(),
                        listData.get(i).getJamSelesai());
            }
            System.out.println(
                    "---------------------------------------------------------------------------------------------------\n");
        }
    }

    public static void showDetailJadwal(Jadwal data) {
        System.out.println("ID           : " + data.getId());
        System.out.println("Nama Matkul  : " + data.getNamaMatkul());
        System.out.println("Ruang        : " + data.getNamaRuang());
        System.out.println("Hari         : " + data.getHari());
        System.out.println("Jam Mulai    : " + data.getJamMulai());
        System.out.println("Jam Selesai  : " + data.getJamSelesai());
    }

    public static void inputOrEditData(Scanner sc, boolean isEdit) {
        String namaMatkul, namaRuang, hari, jamMulai, jamSelesai;
        LocalTime parsedTimeStart, parsedTimeEnd;
        InputValidation validation = new InputValidation();

        String error;
        String idEdit = null;

        while (true) {
            if (isEdit) {
                System.out.print("Masukkan ID target: ");
                idEdit = sc.nextLine();

                error = validation.validateIdEdit(idEdit, service);
                if (error != null) {
                    System.out.println(error);
                    break;
                }

                if (service.searchById(idEdit) != null) {
                    showDetailJadwal(service.searchById(idEdit));
                }
            }

            while (true) {
                System.out.print("- Masukkan nama mata kuliah: ");
                namaMatkul = sc.nextLine();
                error = validation.validateRequiredText("nama matkul", namaMatkul, 3);
                if (error != null) {
                    System.out.println(error);
                    continue;
                }
                break;
            }

            while (true) {
                System.out.print("- Masukkan nama ruangan: ");
                namaRuang = sc.nextLine();
                error = validation.validateRequiredText("nama ruangan", namaRuang, 3);
                if (error != null) {
                    System.out.println(error);
                    continue;
                }
                break;
            }

            while (true) {
                System.out.print("- Masukkan hari: ");
                hari = sc.nextLine();
                error = validation.validateDay(hari);
                if (error != null) {
                    System.out.println(error);
                    continue;
                }
                break;
            }

            Day parsedDay = service.parseDay(hari);

            while (true) {
                while (true) {
                    System.out.print("- Masukkan jam mulai: ");
                    jamMulai = sc.nextLine();
                    error = validation.validateTimeFormat("jam mulai", jamMulai);
                    if (error != null) {
                        System.out.println(error);
                        continue;
                    }
                    break;
                }

                while (true) {
                    System.out.print("- Masukkan jam selesai: ");
                    jamSelesai = sc.nextLine();
                    error = validation.validateTimeFormat("jam selesai", jamSelesai);
                    if (error != null) {
                        System.out.println(error);
                        continue;
                    }
                    break;
                }

                parsedTimeStart = service.parseTime(jamMulai);
                parsedTimeEnd = service.parseTime(jamSelesai);

                error = validation.validateTimeLogic(parsedTimeStart, parsedTimeEnd);
                if (error != null) {
                    System.out.println(error);
                    continue;
                }

                String ignoreId = isEdit ? idEdit : null;
                error = validation.validateTimeConflict(service.showJadwal(), hari, parsedTimeStart, parsedTimeEnd,
                        ignoreId);
                if (error != null) {
                    System.out.println(error);
                    continue;
                }
                break;
            }

            if (!isEdit) {
                service.addData(namaMatkul, namaRuang, parsedDay, parsedTimeStart, parsedTimeEnd);
                System.out.println("Data berhasil ditambahkan!\n");
            } else {
                service.editData(idEdit, namaMatkul, namaRuang, parsedDay, parsedTimeStart, parsedTimeEnd);
                System.out.println("Data berhasil diedit!\n");
            }
            break;
        }
    }

    public static void inputData(Scanner sc) {
        System.out.println("\n=== INPUT DATA ===");
        inputOrEditData(sc, false);
    }

    public static void editData(Scanner sc) {
        System.out.println("\n=== EDIT DATA ===");
        inputOrEditData(sc, true);
    }

    public static void showMenuSearch() {
        System.out.println("\n=== SEARCH DATA ===");
        System.out.println("1. Cari berdasarkan nama mata kuliah");
        System.out.println("2. Cari berdasarkan hari");
        System.out.println("3. Cari berdasarkan id");
        System.out.print("Masukkan pilihan (1 - 3): ");
    }

    public static void showMenuSort() {
        System.out.println("\n=== SORTING DATA ===");
        System.out.println("1. Urutan berdasarkan hari");
        System.out.println("2. Urutan berdasarkan hari");
        System.out.println("3. Urutan berdasarkan jam");
        System.out.print("Masukkan pilihan (1 - 3): ");
    }

    public static void showSortType() {
        System.out.println("Pilih urutan");
        System.out.println("1. Ascending");
        System.out.println("2. Descending");
        System.out.print("Masukkan pilihan (1/2): ");
    }

    public static void showMenuRemove() {
        System.out.println("\n=== REMOVE JADWAL ===");
        System.out.println("1. Remove from ID");
        System.out.println("2. Delete all");
        System.out.print("Masukkan pilihan (1-2): ");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        do {
            showMenu();
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    inputData(sc);
                    break;

                case 2:
                    showAllJadwal(service.showJadwal());
                    break;

                case 3:
                    editData(sc);
                    break;

                case 4:
                    showMenuSearch();
                    int choiceSearch = sc.nextInt();
                    sc.nextLine();

                    SearchType searchType = SearchType.fromChoice(choiceSearch);
                    if (searchType == null) {
                        System.out.println("Pilihan tidak valid!");
                        continue;
                    }

                    System.out.print("Masukkan " + searchType.toString().toLowerCase() + ": ");
                    String searchValue = sc.nextLine();

                    List<Jadwal> searchResults = service.searchByCondition(searchType, searchValue);
                    if (searchResults.isEmpty()) {
                        System.out.println("Data tidak ditemukan!\n");
                    } else {
                        showAllJadwal(searchResults);
                    }
                    break;

                case 5:
                    showMenuSort();
                    int choiceSort = sc.nextInt();
                    sc.nextLine();

                    SortType sortType = SortType.fromChoice(choiceSort);
                    if (sortType == null) {
                        System.out.println("Pilihan tidak valid!");
                        continue;
                    }

                    showSortType();
                    int choiceSortType = sc.nextInt();
                    sc.nextLine();

                    SortOrder sortOrder = SortOrder.fromChoice(choiceSortType);
                    if (sortOrder == null) {
                        System.out.println("Pilihan tidak valid!");
                        continue;
                    }

                    List<Jadwal> sortResults = service.sortByCondition(sortType, sortOrder);
                    if (sortResults.isEmpty()) {
                        System.out.println("Tidak ada data!\n");
                    } else {
                        showAllJadwal(sortResults);
                    }
                    break;

                case 6:
                    showMenuRemove();
                    int choiceDelete = sc.nextInt();
                    sc.nextLine();

                    switch (choiceDelete) {
                        case 1:
                            System.out.print("Masukkan ID: ");
                            String deleteId = sc.nextLine();

                            if (!service.deleteDataById(deleteId)) {
                                System.out.println("Data berdasarkan ID tidak ada!\n");
                            } else {
                                System.out.println("Data berhasil dihapus.\n");
                            }
                            break;

                        case 2:
                            System.out.print("Apakah anda yakin? (y/n): ");
                            String DelAll = sc.nextLine();

                            switch (DelAll) {
                                case "y":
                                    service.reset();
                                    System.out.println("Semua data berhasil dihapus!\n");
                                    break;

                                case "n":
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

                case 7:
                    System.out.println("Keluar dari program.");
                    break;
                default:
                    break;
            }

            if (choice == 7) {
                break;
            }
        } while (true);

        sc.close();
    }
}