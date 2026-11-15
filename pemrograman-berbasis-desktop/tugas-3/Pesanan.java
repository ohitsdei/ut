import java.io.*;
import java.util.ArrayList;

public class Pesanan {
    static final double PAJAK = 0.10;
    static final double BIAYA_PELAYANAN = 20000;

    private ArrayList<MenuItem> daftarItem = new ArrayList<>();
    private ArrayList<Integer> daftarJumlah = new ArrayList<>();
    private Diskon diskon;

    public void tambahItem(MenuItem item, int jumlah) {
        if (item instanceof Diskon) {
            diskon = (Diskon) item;
        } else {
            daftarItem.add(item);
            daftarJumlah.add(jumlah);
        }
    }

    double hitungTotalBayar() {
        double setelahPotongan = hitungSubtotal() - hitungDiskon(hitungSubtotal());
        return setelahPotongan + setelahPotongan * PAJAK + BIAYA_PELAYANAN;
    }

    double hitungTotalItem(int index) {
        return daftarItem.get(index).getHarga() * daftarJumlah.get(index);
    }

    double hitungSubtotal() {
        double subtotal = 0;
        for (int i = 0; i < daftarItem.size(); i++) {
            subtotal += hitungTotalItem(i);
        }
        return subtotal;
    }

    double hitungDiskon(double subtotal) {
        if (diskon != null) {
            return diskon.hitungPotongan(subtotal);
        } else {
            return 0;
        }
    }

    void cetakStruk() {
        double subtotal = hitungSubtotal();

        System.out.println();
        if (subtotal == 0) {
            System.out.println("Tidak ada pesanan. Terima kasih sudah berkunjung!");
            return;
        }

        double diskon = hitungDiskon(subtotal);
        double setelahPotongan = subtotal - diskon;
        double pajak = setelahPotongan * PAJAK;
        double totalBayar = setelahPotongan + pajak + BIAYA_PELAYANAN;

        System.out.println("====================================================");
        System.out.println("       STRUK PESANAN WARUNG SIMPANG TIGA            ");
        System.out.println("====================================================");
        System.out.printf("%-18s %4s %12s %13s%n", "Item", "Qty", "Harga", "Total");
        System.out.println("----------------------------------------------------");
        for (int i = 0; i < daftarItem.size(); i++) {
            cetakBarisItem(i);
        }
        System.out.println("----------------------------------------------------");
        cetakBaris("Total biaya pemesanan", Main.formatRupiah(subtotal));

        if (diskon > 0) {
            cetakBaris("Diskon", "-" + Main.formatRupiah(diskon));
        } else {
            System.out.println("Diskon: tidak ada");
        }

        if (diskon > 0) {
            cetakBaris("Harga setelah diskon", Main.formatRupiah(setelahPotongan));
        }

        cetakBaris("Pajak 10%", Main.formatRupiah(pajak));
        cetakBaris("Biaya pelayanan", Main.formatRupiah(BIAYA_PELAYANAN));
        System.out.println("==========================================================");
        cetakBaris("TOTAL BAYAR", Main.formatRupiah(totalBayar));
        System.out.println("==========================================================");
        System.out.println("     TERIMA KASIH, SELAMAT MENIKMATI           ");
    }

    void cetakBarisItem(int index) {
        if (daftarJumlah.get(index) > 0) {
            MenuItem menu = daftarItem.get(index);
            System.out.printf("%-18s %4d %12s %13s%n",
                menu.getNama(), daftarJumlah.get(index),
                Main.formatRupiah(menu.getHarga()), Main.formatRupiah(hitungTotalItem(index)));
        }
    }

    void cetakBaris(String label, String nilai) {
        System.out.printf("%-35s %14s%n", label, nilai);
    }

    void simpanStruk(String namaFile) {
        PrintStream layar = System.out;
        try {
            System.setOut(new PrintStream(namaFile));
            cetakStruk();
            System.out.close();
            System.setOut(layar);
            System.out.println("  -> Struk disimpan ke " + namaFile);
        } catch (FileNotFoundException e) {
            System.out.println("  -> Gagal menyimpan struk.");
        }
    }

    static void tampilkanStrukDariFile(String namaFile) {
        try {
            BufferedReader baca = new BufferedReader(new FileReader(namaFile));
            String baris;
            while ((baris = baca.readLine()) != null) {
                System.out.println(baris);
            }
            baca.close();
        } catch (IOException e) {
            System.out.println("  -> Belum ada struk tersimpan.");
        }
    }
}
