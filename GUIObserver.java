// Nama: Wahyu Nur Hidayat NRP: 3124640005
import javax.swing.JTextArea;

public class GUIObserver implements NotifikasiObserver {
    private JTextArea displayArea;

    public GUIObserver(JTextArea area) {
        this.displayArea = area;
    }

    @Override
    public void update(Obat obat) {
        // Memperbarui JTextArea di GUI saat notifikasi terjadi
        displayArea.append("=================================\n");
        displayArea.append("🔔 NOTIFIKASI PENTING! 🔔\n");
        displayArea.append("  Saatnya Minum Obat: " + obat.getNama() + "\n");
        displayArea.append("  Jenis Obat        : " + obat.getJenis().toUpperCase() + "\n");
        displayArea.append("  Dosis             : " + obat.getDosis() + "\n");
        displayArea.append("  Waktu             : " + obat.getJadwal() + "\n");
        displayArea.append("=================================\n\n");
        // Pastikan scrollbar bergerak ke bawah setiap kali ada update
        displayArea.setCaretPosition(displayArea.getDocument().getLength());
    }
}