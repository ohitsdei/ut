public class ItemTidakAdaException extends Exception {

    public ItemTidakAdaException(String nama) {
        super("Menu " + nama + " tidak ada di daftar.");
    }
}
