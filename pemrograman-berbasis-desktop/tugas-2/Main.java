import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    static final double PAJAK = 0.10;
    static final double BIAYA_PELAYANAN = 20000;
    static final double BATAS_DISKON = 100000;
    static final double PERSEN_DISKON = 0.10;
    static final double BATAS_PROMO = 50000;
    static final int MAKS_MENU = 20;
    static final Locale INDONESIA = Locale.forLanguageTag("id-ID");

    static Menu[] daftarMenu = new Menu[MAKS_MENU];
    static int jumlahMenu = 0;
    static int[] jmlPesanan = new int[MAKS_MENU];
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        isiMenu();
        int pilihan;
        do {
            System.out.println();
            System.out.println("==================================================");
            System.out.println("              WARUNG SIMPANG TIGA");
            System.out.println("==================================================");
            System.out.println("1. Pesan makanan (pelanggan)");
            System.out.println("2. Kelola menu (pemilik)");
            System.out.println("3. Keluar");
            pilihan = bacaAngka("Pilih menu [1-3]: ", 1, 3);
            switch (pilihan) {
                case 1:
                    menuPelanggan();
                    break;
                case 2:
                    menuPemilik();
                    break;
                default:
                    System.out.println("Terima kasih, sampai jumpa!");
            }
        } while (pilihan != 3);
    }

    static void isiMenu(){
        daftarMenu[0] = new Menu("Nasi Padang", 25000, "Makanan");
        daftarMenu[1] = new Menu("Sate Ayam", 30000, "Makanan");
        daftarMenu[2] = new Menu("Soto Betawi", 28000, "Makanan");
        daftarMenu[3] = new Menu("Gado-Gado", 20000, "Makanan");
        daftarMenu[4] = new Menu("Es Teh Manis", 5000, "Minuman");
        daftarMenu[5] = new Menu("Jus Alpukat", 15000, "Minuman");
        daftarMenu[6] = new Menu("Es Jeruk", 8000, "Minuman");
        daftarMenu[7] = new Menu("Kopi Susu", 18000, "Minuman");
        jumlahMenu = 8;
    }

    static void tampilkanMenu(){
        System.out.println("===============================================");
        System.out.println("              MENU MASAKAN SIMPANG TIGA        ");
        System.out.println("===============================================");
        String[] kategori = {"Makanan", "Minuman"};
        for (String k : kategori) {
            tampilkanKategori(k);
        }
    }

    static void tampilkanKategori(String kategori){
        System.out.println(kategori + ":");
        for (int i = 0; i < jumlahMenu; i++) {
            if (daftarMenu[i].getKategori().equals(kategori)) {
                tampilkanItem(i);
            }
        }
    }

    static void tampilkanItem(int index) {
        Menu menu = daftarMenu[index];
        System.out.printf(" %-20s %s%n", menu.getNama(), formatRupiah(menu.getHarga()));
    }

    static String formatRupiah(double nilai) {
        return "Rp" + String.format(INDONESIA, "%,.0f", nilai);
    }

    static void terimaPesanan() {
        Arrays.fill(jmlPesanan, 0);

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
        int index = cariIndexMenu(namaMenu);

        if (index == -1) {
            System.out.println("  -> Menu \"" + namaMenu + "\" tidak ada di daftar. Silakan ulangi.");
        } else if (!textJumlah.matches("[0-9]{1,3}")) {
            System.out.println("  -> Jumlah harus berupa angka 1-999. Silakan ulangi");
        } else if (Integer.parseInt(textJumlah) == 0) {
            System.out.println("  -> Jumlah tidak boleh 0. Silakan ulangi");
        } else {
            jmlPesanan[index] += Integer.parseInt(textJumlah);
            System.out.println("  -> " + daftarMenu[index].getNama() + " x" + textJumlah + " ditambahkan.");
        }
        return true;
    }

    static int cariIndexMenu(String nama) {
        for (int i = 0; i < jumlahMenu; i++) {
            if (daftarMenu[i].getNama().equalsIgnoreCase(nama)) {
                return i;
            }
        }
        return -1;
    }

    static double hitungTotalItem(int index) {
        return daftarMenu[index].getHarga() * jmlPesanan[index];
    }

    static double hitungSubtotal() {
        double subtotal = 0;
        for (int i = 0; i < jumlahMenu; i++) {
            subtotal += hitungTotalItem(i);
        }
        return subtotal;
    }

    static double hitungDiskon(double subtotal) {
        if (subtotal > BATAS_DISKON) {
            return subtotal * PERSEN_DISKON;
        } else {
            return 0;
        }
    }

    static boolean bisaDapatPromo (int index) {
        return daftarMenu[index].isMinuman() && jmlPesanan[index] >= 2;
    }

    static int cariIndexPromo() {
        for (int i = 0; i < jumlahMenu; i++) {
            if (bisaDapatPromo(i)) {
                return i;
            }
        }
        return -1;
    }

    static int hitungPorsiGratis(int indexPromo) {
        if (indexPromo == -1) {
            return 0;
        } else {
            return jmlPesanan[indexPromo] / 2;
        }
    }

    static double hitungPotonganPromo(double subtotal, int indexPromo) {
        if (subtotal > BATAS_PROMO) {
            if (indexPromo != -1) {
                return hitungPorsiGratis(indexPromo) * daftarMenu[indexPromo].getHarga();
            }
        }
        return 0;
    }

    static void cetakStruk() {
        double subtotal = hitungSubtotal();

        System.out.println();
        if (subtotal == 0) {
            System.out.println("Tidak ada pesanan. Terima kasih sudah berkunjung!");
            return;
        }

        int indexPromo = cariIndexPromo();
        double diskon = hitungDiskon(subtotal);
        double potonganPromo = hitungPotonganPromo(subtotal, indexPromo);
        double setelahPotongan = subtotal - diskon - potonganPromo;
        double pajak = setelahPotongan * PAJAK;
        double totalBayar = setelahPotongan + pajak + BIAYA_PELAYANAN;

        System.out.println("====================================================");
        System.out.println("       STRUK PESANAN WARUNG SIMPANG TIGA            ");
        System.out.println("====================================================");
        System.out.printf("%-18s %4s %12s %13s%n", "Item", "Qty", "Harga", "Total");
        System.out.println("----------------------------------------------------");
        for (int i = 0; i < jumlahMenu; i++) {
            cetakBarisItem(i);
        }
        System.out.println("----------------------------------------------------");
        cetakBaris("Total biaya pemesanan", formatRupiah(subtotal));

        if (diskon > 0) {
            cetakBaris("Diskon 10% (subtotal > Rp.100.000)", "-" + formatRupiah(diskon));
        } else {
            System.out.println("Diskon 10%: tidak berlaku (subtotal <= Rp.100.000)");
        }

        if (potonganPromo > 0) {
            Menu minumanPromo = daftarMenu[indexPromo];
            int porsiGratis = hitungPorsiGratis(indexPromo);
            cetakBaris("Promo beli 1 gratis 1", "-" + formatRupiah(potonganPromo));
            System.out.println(" (" + minumanPromo.getNama() + ": " + porsiGratis + " porsi gratis)");
        } else if (subtotal > BATAS_PROMO) {
            System.out.println("Promo beli 1 gratis 1: berlaku, tetapi tidak ada");
            System.out.println(" minuman yang dipesan minimal 2 porsi");
        } else {
            System.out.println("Promo B1G1: tidak berlaku (subtotal <= Rp.50.000)");
        }

        if (diskon > 0 || potonganPromo > 0) {
            cetakBaris("Harga setelah diskon/promo", formatRupiah(setelahPotongan));
        }

        cetakBaris("Pajak 10%", formatRupiah(pajak));
        cetakBaris("Biaya pelayanan", formatRupiah(BIAYA_PELAYANAN));
        System.out.println("==========================================================");
        cetakBaris("TOTAL BAYAR", formatRupiah(totalBayar));
        System.out.println("==========================================================");
        System.out.println("     TERIMA KASIH, SELAMAT MENIKMATI           ");
    }

    static void cetakBarisItem(int index) {
        if (jmlPesanan[index] > 0) {
            Menu menu = daftarMenu[index];
            System.out.printf("%-18s %4d %12s %13s%n",
                menu.getNama(), jmlPesanan[index],
                formatRupiah(menu.getHarga()), formatRupiah(hitungTotalItem(index)));
        }
    }

    static void cetakBaris(String label, String nilai) {
        System.out.printf("%-35s %14s%n", label, nilai);
    }

    static void menuPelanggan() {
        if (jumlahMenu == 0) {
            System.out.println("  -> Belum ada menu yang tersedia.");
            return;
        }
        tampilkanMenu();
        terimaPesanan();
        cetakStruk();
        System.out.print("Tekan Enter untuk kembali ke menu utama...");
        scan.nextLine();
    }

    static void tampilkanDaftarBernomor() {
        System.out.println("--------------------------------------------------");
        for (int i = 0; i < jumlahMenu; i++) {
            Menu menu = daftarMenu[i];
            System.out.printf("%2d. %-20s %-8s %s%n", i + 1, menu.getNama(), menu.getKategori(), formatRupiah(menu.getHarga()));
        }
        System.out.println("--------------------------------------------------");
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

    static boolean bacaKonfirmasi(String pesan) {
        String jawaban;
        do {
            System.out.print(pesan + " (Ya/Tidak): ");
            jawaban = scan.nextLine().trim();
            if (!jawaban.equalsIgnoreCase("ya") && !jawaban.equalsIgnoreCase("tidak")) {
                System.out.println("  -> Ketik Ya atau Tidak.");
            }
        } while (!jawaban.equalsIgnoreCase("ya") && !jawaban.equalsIgnoreCase("tidak"));
        return jawaban.equalsIgnoreCase("ya");
    }

    static void tambahMenu() {
        if (jumlahMenu == MAKS_MENU) {
            System.out.println("  -> Daftar menu sudah penuh (maksimal " + MAKS_MENU + " menu).");
            return;
        }
        String nama = bacaNamaBaru();
        int harga = bacaAngka("Harga (Rp): ", 1, 10000000);
        System.out.println("Kategori: 1. Makanan  2. Minuman");
        int pilihKategori = bacaAngka("Pilih kategori [1-2]: ", 1, 2);
        String kategori;
        if (pilihKategori == 1) {
            kategori = "Makanan";
        } else {
            kategori = "Minuman";
        }
        daftarMenu[jumlahMenu] = new Menu(nama, harga, kategori);
        jumlahMenu++;
        System.out.println("  -> Menu " + nama + " berhasil ditambahkan.");
        tampilkanDaftarBernomor();
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
            } else if (cariIndexMenu(nama) != -1) {
                System.out.println("  -> Menu " + nama + " sudah ada.");
                valid = false;
            } else {
                valid = true;
            }
        } while (!valid);
        return nama;
    }

    static void ubahHarga() {
        if (jumlahMenu == 0) {
            System.out.println("  -> Daftar menu kosong.");
            return;
        }
        tampilkanDaftarBernomor();
        int nomor = bacaAngka("Nomor menu yang diubah [0 = batal]: ", 0, jumlahMenu);
        if (nomor == 0) {
            System.out.println("  -> Dibatalkan.");
            return;
        }
        Menu menu = daftarMenu[nomor - 1];
        int hargaBaru = bacaAngka("Harga baru (Rp): ", 1, 10000000);
        String pesan = "Ubah harga " + menu.getNama() + " dari " + formatRupiah(menu.getHarga()) + " menjadi " + formatRupiah(hargaBaru) + "?";
        if (bacaKonfirmasi(pesan)) {
            menu.setHarga(hargaBaru);
            System.out.println("  -> Harga berhasil diubah.");
            tampilkanDaftarBernomor();
        } else {
            System.out.println("  -> Tidak ada perubahan.");
        }
    }

    static void hapusMenu() {
        if (jumlahMenu == 0) {
            System.out.println("  -> Daftar menu kosong.");
            return;
        }
        tampilkanDaftarBernomor();
        int nomor = bacaAngka("Nomor menu yang dihapus [0 = batal]: ", 0, jumlahMenu);
        if (nomor == 0) {
            System.out.println("  -> Dibatalkan.");
            return;
        }
        String nama = daftarMenu[nomor - 1].getNama();
        if (bacaKonfirmasi("Yakin hapus " + nama + "?")) {
            for (int i = nomor - 1; i < jumlahMenu - 1; i++) {
                daftarMenu[i] = daftarMenu[i + 1];
            }
            jumlahMenu--;
            daftarMenu[jumlahMenu] = null;
            System.out.println("  -> Menu " + nama + " berhasil dihapus.");
            tampilkanDaftarBernomor();
        } else {
            System.out.println("  -> Tidak ada perubahan.");
        }
    }

    static void menuPemilik() {
        int pilihan;
        do {
            System.out.println();
            System.out.println("==================================================");
            System.out.println("              PENGELOLAAN MENU");
            System.out.println("==================================================");
            System.out.println("1. Tambah menu baru");
            System.out.println("2. Ubah harga menu");
            System.out.println("3. Hapus menu");
            System.out.println("4. Kembali ke menu utama");
            pilihan = bacaAngka("Pilih menu [1-4]: ", 1, 4);
            switch (pilihan) {
                case 1:
                    tambahMenu();
                    break;
                case 2:
                    ubahHarga();
                    break;
                case 3:
                    hapusMenu();
                    break;
                default:
                    System.out.println("  -> Kembali ke menu utama.");
            }
        } while (pilihan != 4);
    }

}
