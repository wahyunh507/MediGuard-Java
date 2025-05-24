// Nama: Wahyu Nur Hidayat NRP: 3124640005
import javax.swing.SwingUtilities;
import javax.swing.UIManager; // Tambahan untuk Look and Feel
import javax.swing.UnsupportedLookAndFeelException; // Tambahan untuk Look and Feel

public class Main {
    public static void main(String[] args) {
        // --- Atur Look and Feel ke Nimbus ---
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // Jika Nimbus tidak ditemukan atau gagal diatur, gunakan default
            System.err.println("Gagal mengatur Nimbus Look and Feel. Menggunakan default.");
        }

        // Jalankan GUI di Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MedicationReminderGUI gui = new MedicationReminderGUI();
                gui.setVisible(true);
            }
        });
    }
}