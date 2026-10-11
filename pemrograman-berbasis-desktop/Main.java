import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    static final double PAJAK = 0.10;
    static final double BIAYA_PELAYANAN = 20000;
    static final double BATAS_DISKON = 100000;
    static final double PERSEN_DISKON = 0.10;
    static final double BATAS_PROMO = 50000;
    static final int MAKS_PESANAN = 4;
    static final Locale INDONESIA = Locale.forLanguageTag("id-ID");

    static Menu[] daftarMenu = new Menu[8];
    static int[] idxPesanan = new int[MAKS_PESANAN];
    static int[] jmlPesanan = new int[MAKS_PESANAN];
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        isiMenu();
        tampilkanMenu();
        terimaPesanan();
        cetakStruk();
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
    }

    static void tampilkanMenu(){
        System.out.println("===============================================");
        System.out.println("              MENU MASAKAN SIMPANG TIGA        ");
        System.out.println("===============================================");
        tampilkanKategori("Makanan", 0);
        tampilkanKategori("Minuman", 4);
    }

    static void tampilkanKategori(String judul, int indexAwal){
        System.out.println(judul + ":");
        tampilkanItem(indexAwal);
        tampilkanItem(indexAwal + 1);
        tampilkanItem(indexAwal + 2);
        tampilkanItem(indexAwal + 3);
    }

    static void tampilkanItem(int index) {
        Menu menu = daftarMenu[index];
        System.out.printf(" %-20s %s%n", menu.getNama(), formatRupiah(menu.getHarga()));
    }

    static String formatRupiah(double nilai) {
        return "Rp" + String.format(INDONESIA, "%,.0f", nilai);
    }

    static void terimaPesanan() {
        Arrays.fill(idxPesanan, -1);

        System.out.println();
        System.out.println("Masukkan pesanan (maksimal " + MAKS_PESANAN + " menu) dengan format:");
        System.out.println("  Nama Menu = Jumlah    contoh: Nasi Padang = 2");
        System.out.println("Tekan Enter tanpa mengetik apapun jika pesanan sudah selesai.");
        System.out.println();

        if (bacaPesanan(0)) {
            if (bacaPesanan(1)) {
                if (bacaPesanan(2)) {
                    bacaPesanan(3);
                }
            }
        }
    }

    static boolean bacaPesanan(int slot) {
        System.out.print("Pesanan ke-" + (slot +1) + ": ");
        String baris = scan.nextLine().trim();

        if (baris.isEmpty()) {
            return false;
        }

        String[] bagian = baris.split("=");
        if (bagian.length != 2) {
            System.out.println("  -> Format salah, gunakan: Nama Menu = Jumlah. Pesanan ini dilewati.");
            return true;
        }

        String namaMenu = bagian[0].trim();
        String textJumlah = bagian[1].trim();
        int index = cariIndexMenu(namaMenu);

        if (index == -1) {
            System.out.println("  -> Menu \"" + namaMenu + "\" tidak ada di daftar. Pesanan ini dilewati.");
        } else if (!textJumlah.matches("[0-9]{1,3}")) {
            System.out.println("  -> Jumlah harus berupa angka 1-999. Pesanan ini dilewati");
        } else if (Integer.parseInt(textJumlah) == 0) {
            System.out.println("  -> Jumlah tidak boleh 0. Pesanan ini dilewati");
        } else {
            idxPesanan[slot] = index;
            jmlPesanan[slot] = Integer.parseInt(textJumlah);
            System.out.println("  -> " + daftarMenu[index].getNama() + " x" + jmlPesanan[slot] + " ditambahkan.");
        }
        return true;
    }

    static int cariIndexMenu(String nama) {
        int index;
        switch (nama.toLowerCase()) {
	case "nasi padang":
		index = 0;
		break;
	case "sate ayam":
		index = 1;
		break;
	case "soto betawi":
		index = 2;
		break;
	case "gado gado":
	case "gado-gado":
		index = 3;
		break;
	case "es teh manis":
		index = 4;
		break;
	case "jus alpukat":
		index = 5;
		break;
	case "es jeruk":
		index = 6;
		break;
	case "kopi susu":
		index = 7;
		break;
	default:
		index = -1;
        }
   	return index;

    }

    static double hitungTotalItem(int slot) {
        if (idxPesanan[slot] == -1) {
            return 0;
        }
        else {
            return daftarMenu[idxPesanan[slot]].getHarga() * jmlPesanan[slot];
        }
    }

    static double hitungSubtotal() {
        return hitungTotalItem(0) + hitungTotalItem(1) + hitungTotalItem(2) + hitungTotalItem(3);
    }

    static double hitungDiskon(double subtotal) {
        if (subtotal > BATAS_DISKON) {
            return subtotal * PERSEN_DISKON;
        } else {
            return 0;
        }
    }

    static boolean bisaDapatPromo (int slot) {
        return idxPesanan[slot] != -1 && daftarMenu[idxPesanan[slot]].isMinuman() && jmlPesanan[slot] >= 2;
    }

    static int cariSlotPromo() {
        if (bisaDapatPromo(0)) {
            return 0;
        }
        else if (bisaDapatPromo(1)) {
            return 1;
        } else if (bisaDapatPromo(2)) {
            return 2;
        } else if (bisaDapatPromo(3)) {
            return 3;
        } else {
            return -1;
        }
    }

    static int hitungPorsiGratis(int slotPromo) {
        if (slotPromo == -1) {
            return 0;
        } else {
            return jmlPesanan[slotPromo] / 2;
        }
    }

    static double hitungPotonganPromo(double subtotal, int slotPromo) {
        if (subtotal > BATAS_PROMO) {
            if (slotPromo != -1) {
                return hitungPorsiGratis(slotPromo) * daftarMenu[idxPesanan[slotPromo]].getHarga();
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

        int slotPromo = cariSlotPromo();
        double diskon = hitungDiskon(subtotal);
        double potonganPromo = hitungPotonganPromo(subtotal, slotPromo);
        double setelahPotongan = subtotal - diskon - potonganPromo;
        double pajak = setelahPotongan * PAJAK;
        double totalBayar = setelahPotongan + pajak + BIAYA_PELAYANAN;

        System.out.println("====================================================");
        System.out.println("       STRUK PESANAN WARUNG SIMPANG TIGA            ");
        System.out.println("====================================================");
        System.out.printf("%-18s %4s %12s %13s%n", "Item", "Qty", "Harga", "Total");
        System.out.println("----------------------------------------------------");
        cetakBarisItem(0);
        cetakBarisItem(1);
        cetakBarisItem(2);
        cetakBarisItem(3);
        System.out.println("----------------------------------------------------");
        cetakBaris("Total biaya pemesanan", formatRupiah(subtotal));

        if (diskon > 0) {
            cetakBaris("Diskon 10% (subtotal > Rp.100.000)", "-" + formatRupiah(diskon));
        } else {
            System.out.println("Diskon 10%: tidak berlaku (subtotal <= Rp.100.000)");
        }

        if (potonganPromo > 0) {
            Menu minumanPromo = daftarMenu[idxPesanan[slotPromo]];
            int porsiGratis = hitungPorsiGratis(slotPromo);
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

    static void cetakBarisItem(int slot) {
        if (idxPesanan[slot] != -1) {
            Menu menu = daftarMenu[idxPesanan[slot]];
            System.out.printf("%-18s %4d %12s %13s%n",
                menu.getNama(), jmlPesanan[slot],
                formatRupiah(menu.getHarga()), formatRupiah(hitungTotalItem(slot)));
        }
    }

    static void cetakBaris(String label, String nilai) {
        System.out.printf("%-35s %14s%n", label, nilai);
    }

}
