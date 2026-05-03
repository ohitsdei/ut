//Nama Bayu Andrian Rukmana
//NIM 055826616
//SISTEM INFORMASI

import java.util.LinkedList;

public class Tugas {

    public static void main(String[] args) {
        System.out.println("Nama : Bayu Andrian Rukmana");
        System.out.println("NIM : 055826616");
        System.out.println("Jurusan : Sistem Informasi");

        //soal 1
        float StrukturBaris;

        //soal 2
        String SusunanKataBaru = "Pemrograman Struktur Data Java";
        System.out.println("String SusunanKataBaru: " + SusunanKataBaru);

        //soal 3
        int [] DelapanAngka = {10,12,18,23,33,58,67,82};
        System.out.println("Array DelapanAngka: " + DelapanAngka[4]); // menampilkan angka ke 5 yaitu 33
        System.out.println("Array DelapanAngka: " + DelapanAngka[0]); // menampilkan angka ke 1 yaitu 10

        //soal 4
        String[][] DuaAngka = {
            {"2", "4", "6"}, // baris 1 | 0
            {"8", "10", "12"}, // baris 2 | 1 
            {"14", "16", "18"} // baris 3 | 2
        };
       System.out.println("DuaAngka Baris 2 kolom 2: " + DuaAngka[1][1]); // menampilkan angka 10

       //soal 5
       LinkedList<Integer> UrutanListAngka = new LinkedList<Integer>();
         UrutanListAngka.add(15); //0
            UrutanListAngka.add(28); // 1
            UrutanListAngka.add(33); // 2
            UrutanListAngka.add(47); // 3
            UrutanListAngka.add(59); // 4

        System.out.println("LinkedList UrutanListAngka: " + UrutanListAngka.get(1)); // menampilkan angka ke 2 yaitu 28
        
    }
    
}
