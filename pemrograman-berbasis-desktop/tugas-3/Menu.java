import java.io.*;
import java.util.ArrayList;

public class Menu {

    private ArrayList<MenuItem> daftarItem = new ArrayList<>();

    public void tambahItem(MenuItem item) {
        daftarItem.add(item);
    }

    public void tampilkanMenu(){
        System.out.println("===============================================");
        System.out.println("              MENU MASAKAN SIMPANG TIGA        ");
        System.out.println("===============================================");
        String[] kategori = {"Makanan", "Minuman", "Diskon"};
        for (String k : kategori) {
            tampilkanKategori(k);
        }
    }

    public void tampilkanKategori(String kategori){
        System.out.println(kategori + ":");
        for (MenuItem item : daftarItem) {
            if (item.getKategori().equals(kategori)) {
                item.tampilMenu();
            }
        }
    }

    public MenuItem cariItem(String nama) throws ItemTidakAdaException {
        for (MenuItem item : daftarItem) {
            if (item.getNama().equalsIgnoreCase(nama)) {
                return item;
            }
        }
        throw new ItemTidakAdaException(nama);
    }

    public void simpanKeFile(String namaFile) {
        try {
            PrintWriter tulis = new PrintWriter(new FileWriter(namaFile));
            for (MenuItem item : daftarItem) {
                tulis.println(item.keBarisFile());
            }
            tulis.close();
        } catch (IOException e) {
            System.out.println("  -> Gagal menyimpan menu.");
        }
    }

    public boolean muatDariFile(String namaFile) {
        try {
            BufferedReader baca = new BufferedReader(new FileReader(namaFile));
            String baris;
            while ((baris = baca.readLine()) != null) {
                String[] bagian = baris.split(";");
                double angka = Double.parseDouble(bagian[2]);
                if (bagian[0].equals("Makanan")) {
                    daftarItem.add(new Makanan(bagian[1], angka, bagian[3]));
                } else if (bagian[0].equals("Minuman")) {
                    daftarItem.add(new Minuman(bagian[1], angka, bagian[3]));
                } else {
                    daftarItem.add(new Diskon(bagian[1], angka));
                }
            }
            baca.close();
            System.out.println("Menu dimuat dari " + namaFile);
            return true;
        } catch (IOException e) {
            System.out.println("File " + namaFile + " belum ada, memakai menu bawaan.");
            return false;
        }
    }
}
