package src;

import java.util.List;
import java.util.Scanner;

import src.model.Jadwal;
import src.model.SearchType;
import src.service.JadwalService;

public class JadwalApp {
    public static void showMenu() {
        System.out.println("===== MANAJEMEN JADWAL MHS =====");
        System.out.println("1. Input data jadwal kuliah");
        System.out.println("2. Tampilkan jadwal kuliah");
        System.out.println("3. Cari jadwal");
        System.out.println("4. Hapus jadwal");
        System.out.println("5. Keluar");
        System.out.println("===============================");
    }

    public static void showJadwal(List<Jadwal> listData) {
        System.out.println("\n=== LIST JADWAL ===");
        System.out.printf("%-30s %-10s %-10s %-10s%n", "Nama Mata Kuliah", "Ruang", "Hari", "Jam");
        for (int i = 0; i < listData.size(); i++) {
            System.out.printf("%-30s %-10s %-10s %-10s%n", listData.get(i).getNamaMatkul(),
                    listData.get(i).getNamaRuang(), listData.get(i).getHari(), listData.get(i).getJam());
        }
        System.out.println();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        JadwalService service = new JadwalService();
        List<Jadwal> listData = service.showJadwal();

        do {
            showMenu();
            System.out.print("Masukkan pilihan (1 - 5): ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\n=== INPUT DATA ===");
                    System.out.print("- Masukkan nama mata kuliah: ");
                    String namaMatkul = sc.nextLine();
                    System.out.print("- Masukkan nama ruangan: ");
                    String namaRuang = sc.nextLine();
                    System.out.print("- Masukkan hari: ");
                    String hari = sc.nextLine();
                    System.out.print("- Masukkan jam: ");
                    String jam = sc.nextLine();

                    service.addData(namaMatkul, namaRuang, hari, jam);
                    listData = service.showJadwal();
                    System.out.println("Data berhasil ditambahkan!\n");
                    break;

                case 2:
                    if (listData.isEmpty()) {
                        System.out.println("Data tidak ada!\n");
                    } else {
                        showJadwal(listData);
                    }
                    break;

                case 3:
                    System.out.println("\n=== SEARCH DATA ===");
                    System.out.println("1. Cari berdasarkan nama mata kuliah");
                    System.out.println("2. Cari berdasarkan nama ruangan");
                    System.out.println("3. Cari berdasarkan hari");
                    System.out.println("4. Cari berdasarkan jam");

                    System.out.print("Masukkan pilihan (1 - 4): ");
                    int choiceSearch = sc.nextInt();
                    sc.nextLine();

                    SearchType type = null;
                    switch (choiceSearch) {
                        case 1:
                            type = SearchType.MATKUL;
                            break;

                        case 2:
                            type = SearchType.MATKUL;
                            break;

                        case 3:
                            type = SearchType.HARI;
                            break;

                        case 4:
                            type = SearchType.JAM;
                            break;

                        default:
                            System.out.println("Pilihan tidak valid!\n");
                            break;
                    }
                    if (type == null)
                        continue;

                    System.out.print("Masukkan " + type.toString().toLowerCase() + ": ");
                    String searchValue = sc.nextLine();
                    List<Jadwal> searchResults = service.searchByCondition(type, searchValue);
                    if (searchResults.isEmpty()) {
                        System.out.println("Data tidak ditemmukan!\n");
                    } else {
                        showJadwal(searchResults);
                    }
                    break;

                case 4:
                    System.out.println("\n=== REMOVE JADWAL ===");
                    System.out.println("1. Remove from name");
                    System.out.println("2. Remove from nomor indeks");
                    System.out.println("3. Delete all");

                    System.out.print("Masukkan pilihan (1-3): ");
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
                            System.out.print("Masukkan nomor/index: ");
                            int removeFromIndex = sc.nextInt();
                            sc.nextLine();

                            if (removeFromIndex > 0 && removeFromIndex <= listData.size()) {
                                service.deleteDataByIndex(removeFromIndex - 1);
                                System.out.println("Data berhasil dihapus!\n");
                            } else {
                                System.out.println("Indeks tidak ada!\n");
                            }
                            break;

                        case 3:
                            System.out.print("Apakah anda yakin ingin menghapus semua data? (y/n): ");
                            String askRemove = sc.nextLine();

                            if (askRemove.equalsIgnoreCase("y")) {
                                service.reset();
                                System.out.println("Data berhasil dihapus!\n");
                            } else if (askRemove.equalsIgnoreCase("n")) {
                                System.out.println("Data tidak jadi dihapus!\n");
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
                    System.out.println("Keluar dari program");
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