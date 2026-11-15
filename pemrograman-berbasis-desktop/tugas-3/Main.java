import java.util.Locale;
import java.util.Scanner;

public class Main {
    static final Locale INDONESIA = Locale.forLanguageTag("id-ID");

    static Menu menu = new Menu();
    static Pesanan pesanan = new Pesanan();
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        if (!menu.muatDariFile("menu.txt")) {
            isiMenu();
            menu.simpanKeFile("menu.txt");
        }
        int pilihan;
        do {
            System.out.println();
            System.out.println("==================================================");
            System.out.println("              WARUNG SIMPANG TIGA");
            System.out.println("==================================================");
            System.out.println("1. Tambah item menu");
            System.out.println("2. Tampilkan menu");
            System.out.println("3. Terima pesanan");
            System.out.println("4. Hitung total pesanan");
            System.out.println("5. Tampilkan struk");
            System.out.println("6. Lihat struk tersimpan");
            System.out.println("7. Keluar");
            pilihan = bacaAngka("Pilih menu [1-7]: ", 1, 7);
            switch (pilihan) {
                case 1:
                    tambahMenu();
                    break;
                case 2:
                    menu.tampilkanMenu();
                    break;
                case 3:
                    terimaPesanan();
                    break;
                case 4:
                    System.out.println("Total bayar: " + formatRupiah(pesanan.hitungTotalBayar()));
                    break;
                case 5:
                    pesanan.cetakStruk();
                    pesanan.simpanStruk("struk.txt");
                    break;
                case 6:
                    Pesanan.tampilkanStrukDariFile("struk.txt");
                    break;
                default:
                    System.out.println("Terima kasih, sampai jumpa!");
            }
        } while (pilihan != 7);
    }

    static void isiMenu(){
        menu.tambahItem(new Makanan("Nasi Padang", 25000, "Berat"));
        menu.tambahItem(new Makanan("Sate Ayam", 30000, "Berat"));
        menu.tambahItem(new Makanan("Soto Betawi", 28000, "Berat"));
        menu.tambahItem(new Makanan("Gado-Gado", 20000, "Berat"));
        menu.tambahItem(new Minuman("Es Teh Manis", 5000, "Dingin"));
        menu.tambahItem(new Minuman("Jus Alpukat", 15000, "Dingin"));
        menu.tambahItem(new Minuman("Es Jeruk", 8000, "Dingin"));
        menu.tambahItem(new Minuman("Kopi Susu", 18000, "Dingin"));
        menu.tambahItem(new Diskon("Diskon Member", 10));
    }

    static String formatRupiah(double nilai) {
        return "Rp" + String.format(INDONESIA, "%,.0f", nilai);
    }

    static void terimaPesanan() {
        pesanan = new Pesanan();
        menu.tampilkanMenu();

        System.out.println();
        System.out.println("Masukkan pesanan dengan format:");
        System.out.println("  Nama Menu = Jumlah    contoh: Nasi Padang = 2");
        System.out.println("Ketik 'selesai' jika pesanan sudah selesai.");
        System.out.println();

        boolean lanjut = true;
        while (lanjut) {
            lanjut = bacaPesanan();
        }
    }

    static boolean bacaPesanan() {
        System.out.print("Pesanan: ");
        String baris = scan.nextLine().trim();

        if (baris.equalsIgnoreCase("selesai")) {
            return false;
        }

        String[] bagian = baris.split("=");
        if (bagian.length != 2) {
            System.out.println("  -> Format salah, gunakan: Nama Menu = Jumlah. Silakan ulangi.");
            return true;
        }

        String namaMenu = bagian[0].trim();
        String textJumlah = bagian[1].trim();
        try {
            MenuItem item = menu.cariItem(namaMenu);
            if (!textJumlah.matches("[0-9]{1,3}")) {
                System.out.println("  -> Jumlah harus berupa angka 1-999. Silakan ulangi");
            } else if (Integer.parseInt(textJumlah) == 0) {
                System.out.println("  -> Jumlah tidak boleh 0. Silakan ulangi");
            } else {
                pesanan.tambahItem(item, Integer.parseInt(textJumlah));
                System.out.println("  -> " + item.getNama() + " x" + textJumlah + " ditambahkan.");
            }
        } catch (ItemTidakAdaException e) {
            System.out.println("  -> " + e.getMessage() + " Silakan ulangi.");
        }
        return true;
    }

    static int bacaAngka(String pesan, int min, int maks) {
        int angka;
        boolean valid;
        do {
            System.out.print(pesan);
            String input = scan.nextLine().trim();
            if (input.matches("[0-9]{1,9}")) {
                angka = Integer.parseInt(input);
            } else {
                angka = -1;
            }
            valid = angka >= min && angka <= maks;
            if (!valid) {
                System.out.println("  -> Input tidak valid, masukkan angka " + min + " sampai " + maks + ".");
            }
        } while (!valid);
        return angka;
    }

    static void tambahMenu() {
        String nama = bacaNamaBaru();
        int harga = bacaAngka("Harga (Rp) atau persen diskon: ", 1, 10000000);
        System.out.println("Kategori: 1. Makanan  2. Minuman  3. Diskon");
        int pilihKategori = bacaAngka("Pilih kategori [1-3]: ", 1, 3);
        if (pilihKategori == 3) {
            menu.tambahItem(new Diskon(nama, harga));
        } else {
            System.out.print("Jenis: ");
            String jenis = scan.nextLine().trim();
            if (pilihKategori == 1) {
                menu.tambahItem(new Makanan(nama, harga, jenis));
            } else {
                menu.tambahItem(new Minuman(nama, harga, jenis));
            }
        }
        menu.simpanKeFile("menu.txt");
        System.out.println("  -> Menu " + nama + " berhasil ditambahkan.");
    }

    static String bacaNamaBaru() {
        String nama;
        boolean valid;
        do {
            System.out.print("Nama menu baru: ");
            nama = scan.nextLine().trim();
            if (nama.isEmpty() || nama.contains("=") || nama.equalsIgnoreCase("selesai")) {
                System.out.println("  -> Nama tidak boleh kosong, berisi tanda =, atau 'selesai'.");
                valid = false;
            } else {
                valid = true;
            }
        } while (!valid);
        return nama;
    }

}
