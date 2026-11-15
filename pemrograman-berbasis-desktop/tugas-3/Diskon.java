public class Diskon extends MenuItem {

    private double diskon;

    public Diskon(String nama, double diskon) {
        super(nama, 0, "Diskon");
        this.diskon = diskon;
    }

    public double hitungPotongan(double subtotal) {
        return subtotal * diskon / 100;
    }

    @Override
    public void tampilMenu() {
        System.out.printf(" %-20s potongan %.0f%%%n", getNama(), diskon);
    }

    @Override
    public String keBarisFile() {
        return "Diskon;" + getNama() + ";" + diskon;
    }
}
