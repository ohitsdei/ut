public class Minuman extends MenuItem {

    private String jenisMinuman;

    public Minuman(String nama, double harga, String jenisMinuman) {
        super(nama, harga, "Minuman");
        this.jenisMinuman = jenisMinuman;
    }

    @Override
    public void tampilMenu() {
        System.out.printf(" %-20s %-10s %s%n", getNama(), jenisMinuman, Main.formatRupiah(getHarga()));
    }

    @Override
    public String keBarisFile() {
        return "Minuman;" + getNama() + ";" + getHarga() + ";" + jenisMinuman;
    }
}
