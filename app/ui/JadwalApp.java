package app.ui;

import java.util.List;
import java.util.Scanner;
import java.time.LocalTime;

import app.model.Jadwal;
import app.model.SearchType;
import app.service.JadwalService;
import app.util.InputValidation;

public class JadwalApp {
    static JadwalService service = new JadwalService();

    public static void showMenu() {
        System.out.println("===== MANAJEMEN JADWAL MHS =====");
        System.out.println("1. Input data jadwal kuliah");
        System.out.println("2. Tampilkan jadwal kuliah");
        System.out.println("3. Cari jadwal");
        System.out.println("4. Hapus jadwal");
        System.out.println("5. Keluar");
        System.out.println("===============================");
        System.out.print("Masukkan pilihan (1 - 5): ");
    }

    public static void showJadwal(List<Jadwal> listData) {
        if (listData.isEmpty()) {
            System.out.println("Tidak ada data!\n");
        } else {
            System.out.println("\n=== LIST JADWAL ===");
            System.out.println(
                    "------------------------------------------------------------------------------------------------");
            System.out.printf("| %-3s | %-30s | %-10s | %-10s | %-12s | %-12s |%n", "No", "Nama Mata Kuliah", "Ruang",
                    "Hari", "Jam Mulai", "Jam Selesai");
            for (int i = 0; i < listData.size(); i++) {
                System.out.println(
                        "------------------------------------------------------------------------------------------------");
                System.out.printf("| %-3d | %-30s | %-10s | %-10s | %-12s | %-12s |%n",
                        i + 1,
                        listData.get(i).getNamaMatkul(),
                        listData.get(i).getNamaRuang(),
                        listData.get(i).getHari(),
                        listData.get(i).getJamMulai(),
                        listData.get(i).getJamSelesai());
            }
            System.out.println(
                    "------------------------------------------------------------------------------------------------\n");
        }
    }

    public static void inputData(Scanner sc) {
        String namaMatkul, namaRuang, hari, jamMulai, jamSelesai;
        LocalTime parsedTimeStart, parsedTimeEnd;
        String error;

        System.out.println("\n=== INPUT DATA ===");
        while (true) {
            System.out.print("- Masukkan nama mata kuliah: ");
            namaMatkul = sc.nextLine();
            error = InputValidation.validateRequiredText("nama matkul", namaMatkul, 3);
            if (error != null) {
                System.out.println(error);
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("- Masukkan nama ruangan: ");
            namaRuang = sc.nextLine();
            error = InputValidation.validateRequiredText("nama ruangan", namaRuang, 3);
            if (error != null) {
                System.out.println(error);
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("- Masukkan hari: ");
            hari = sc.nextLine();
            error = InputValidation.validateDay(hari);
            if (error != null) {
                System.out.println(error);
                continue;
            }
            break;
        }

        while (true) {
            while (true) {
                System.out.print("- Masukkan jam mulai: ");
                jamMulai = sc.nextLine();
                error = InputValidation.validateTimeFormat("jam mulai", jamMulai);
                if (error != null) {
                    System.out.println(error);
                    continue;
                }
                break;
            }
            
            while (true) {
                System.out.print("- Masukkan jam selesai: ");
                jamSelesai = sc.nextLine();
                error = InputValidation.validateTimeFormat("jam selesai", jamSelesai);
                if (error != null) {
                    System.out.println(error);
                    continue;
                }
                break;
            }

            parsedTimeStart = InputValidation.parseTime(jamMulai);
            parsedTimeEnd = InputValidation.parseTime(jamSelesai);

            error = InputValidation.validateTimeLogic(parsedTimeStart, parsedTimeEnd);
            if (error != null) {
                System.out.println(error);
                continue;
            }

            error = InputValidation.validateTimeConflict(service.showJadwal(), hari, parsedTimeStart, parsedTimeEnd);
            if (error != null) {
                System.out.println(error);
                continue;
            }
            
            break;
        }
        service.addData(namaMatkul, namaRuang, hari, parsedTimeStart, parsedTimeEnd);
        System.out.println("Data berhasil ditambahkan!\n");
    }

    public static void showMenuSearch() {
        System.out.println("\n=== SEARCH DATA ===");
        System.out.println("1. Cari berdasarkan nama mata kuliah");
        System.out.println("2. Cari berdasarkan nama ruangan");
        System.out.println("3. Cari berdasarkan hari");
        System.out.println("4. Cari berdasarkan jam mulai");
        System.out.println("5. Cari berdasarkan jam selesai");
        System.out.print("Masukkan pilihan (1 - 4): ");
    }

    public static void showMenuRemove() {
        System.out.println("\n=== REMOVE JADWAL ===");
        System.out.println("1. Remove from name");
        System.out.println("2. Remove from nomor");
        System.out.println("3. Delete all");
        System.out.print("Masukkan pilihan (1-3): ");
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
                    showJadwal(service.showJadwal());
                    break;

                case 3:
                    showMenuSearch();
                    int choiceSearch = sc.nextInt();
                    sc.nextLine();

                    SearchType type = SearchType.fromChoice(choiceSearch);
                    if (type == null)
                        continue;

                    System.out.print("Masukkan " + type.toString().toLowerCase() + ": ");
                    String searchValue = sc.nextLine();

                    List<Jadwal> searchResults = service.searchByCondition(type, searchValue);
                    if (searchResults.isEmpty()) {
                        System.out.println("Data tidak ditemukan!\n");
                    } else {
                        showJadwal(searchResults);
                    }
                    break;

                case 4:
                    showMenuRemove();
                    int choiceRemove = sc.nextInt();
                    sc.nextLine();

                    switch (choiceRemove) {
                        case 1:
                            System.out.print("Masukkan nama matkul: ");
                            String removeFromName = sc.nextLine();

                            List<Jadwal> isSearchName = service.searchByCondition(SearchType.MATKUL, removeFromName);
                            if (isSearchName.isEmpty()) {
                                System.out.println("Data tidak ada!\n");
                            } else {
                                service.deleteDataByNameMatkul(removeFromName);
                                System.out.println("Data berhasil dihapus!\n");
                            }
                            break;

                        case 2:
                            System.out.print("Masukkan nomor: ");
                            int removeFromNomor = sc.nextInt();
                            sc.nextLine();

                            if (removeFromNomor > 0 && removeFromNomor <= service.showJadwal().size()) {
                                boolean deleted = service.deleteDataByNomor(removeFromNomor);
                                if (deleted) {
                                    System.out.println("Data berhasil dihapus!\n");
                                } else {
                                    System.out.println("Data gagal dihapus!\n");
                                }
                            } else {
                                System.out.println("Nomor tidak ada!\n");
                            }
                            break;

                        case 3:
                            System.out.print("Apakah anda yakin ingin menghapus semua data? (y/n): ");
                            String askRemove = sc.nextLine();

                            if (askRemove.equalsIgnoreCase("y")) {
                                service.reset();
                                System.out.println("Data berhasil dihapus!\n");
                            } else if (askRemove.equalsIgnoreCase("n")) {
                                System.out.println("Semua data tidak jadi dihapus!\n");
                            } else {
                                System.out.println("Pilihan tidak valid!\n");
                            }
                            break;

                        default:
                            System.out.println("Pilihan tidak valid!");
                            break;
                    }
                    break;

                case 5:
                    System.out.println("Keluar dari program.");
                    break;
                default:
                    break;
            }

            if (choice == 5) {
                break;
            }
        } while (true);

        sc.close();
    }
}