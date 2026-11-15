public class Makanan extends MenuItem {

    private String jenisMakanan;

    public Makanan(String nama, double harga, String jenisMakanan) {
        super(nama, harga, "Makanan");
        this.jenisMakanan = jenisMakanan;
    }

    @Override
    public void tampilMenu() {
        System.out.printf(" %-20s %-10s %s%n", getNama(), jenisMakanan, Main.formatRupiah(getHarga()));
    }

    @Override
    public String keBarisFile() {
        return "Makanan;" + getNama() + ";" + getHarga() + ";" + jenisMakanan;
    }
}
