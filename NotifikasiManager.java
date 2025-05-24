// Nama: Wahyu Nur Hidayat NRP 3124640005
import java.util.ArrayList;
import java.util.List;

public class NotifikasiManager {
    private static NotifikasiManager instance;
    private List<NotifikasiObserver> observers = new ArrayList<>(); // Pastikan tipe List adalah NotifikasiObserver

    private NotifikasiManager() {
        // Constructor private agar tidak bisa diinstansiasi di luar class ini
    }

    public static synchronized NotifikasiManager getInstance() {
        if (instance == null) {
            instance = new NotifikasiManager();
        }
        return instance;
    }

    // Metode untuk menambahkan observer
    public void addObserver(NotifikasiObserver observer) { // Pastikan parameter adalah NotifikasiObserver
        observers.add(observer);
    }

    // Metode untuk menghapus observer
    public void removeObserver(NotifikasiObserver observer) {
        observers.remove(observer);
    }

    // Metode untuk memberitahu semua observer
    private void notifyObservers(Obat obat) {
        for (NotifikasiObserver observer : observers) { // Pastikan iterasi menggunakan NotifikasiObserver
            observer.update(obat);
        }
    }

    // Metode pengiriman notifikasi yang dimodifikasi
    public void kirimNotifikasi(Notifikasi notifikasi, Obat obat) {
        notifikasi.kirim(); // Kirim notifikasi standar
        notifyObservers(obat); // Beri tahu observer tentang notifikasi ini
    }
}