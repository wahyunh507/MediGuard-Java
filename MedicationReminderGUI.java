// Nama: Wahyu Nur Hidayat NRP: 3124640005
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.JTextComponent; // Untuk placeholder

public class MedicationReminderGUI extends JFrame {
    private JTextField namaObatField;
    private JComboBox<String> jenisObatComboBox;
    private JTextField jadwalField;
    private JTextField dosisField;
    private JTextArea notifikasiArea;

    private NotifikasiManager notifikasiManager;
    private JButton tambahObatButton; // Referensi tombol agar bisa dinonaktifkan/diaktifkan

    public MedicationReminderGUI() {
        super("Sistem Pengingat Obat Cerdas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 600); // Perbesar lagi ukuran jendela
        setLocationRelativeTo(null);

        // Atur ikon jendela (opsional, butuh file gambar)
        try {
            setIconImage(new ImageIcon(getClass().getResource("/icons/pill_icon.png")).getImage()); // Pastikan path benar
        } catch (Exception e) {
            System.err.println("Ikon jendela tidak ditemukan: " + e.getMessage());
        }

        notifikasiManager = NotifikasiManager.getInstance();

        notifikasiArea = new JTextArea(10, 40);
        notifikasiArea.setEditable(false);
        notifikasiArea.setWrapStyleWord(true);
        notifikasiArea.setLineWrap(true);
        notifikasiArea.setFont(new Font("Segoe UI", Font.PLAIN, 13)); // Ganti font ke Segoe UI atau sesuai selera
        notifikasiArea.setForeground(new Color(40, 40, 40)); // Warna teks gelap
        notifikasiArea.setBackground(new Color(245, 245, 245)); // Latar belakang lebih terang

        GUIObserver guiObserver = new GUIObserver(notifikasiArea);
        notifikasiManager.addObserver(guiObserver);

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(20, 20)); // Padding antar komponen utama
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(20, 20, 20, 20)); // Padding untuk konten keseluruhan
        getContentPane().setBackground(new Color(230, 240, 250)); // Warna latar belakang JFrame (biru sangat muda)

        // --- Panel Input Obat ---
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(60, 179, 113), 2),
                "📝 Tambah Obat Baru",
                TitledBorder.LEADING, // Ubah ke LEADING untuk rata kiri
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 15), // Font lebih modern untuk judul
                new Color(46, 139, 87)
        ));
        inputPanel.setBackground(new Color(240, 255, 240));
        inputPanel.setBorder(new EmptyBorder(15, 15, 15, 15)); // Padding internal panel

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); // Jarak antar komponen dalam grid
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Helper untuk menambahkan label dan field
        addField(inputPanel, gbc, "Nama Obat:", namaObatField = new JTextField(20), 0);
        addField(inputPanel, gbc, "Jenis Obat:", jenisObatComboBox = new JComboBox<>(new String[]{"pil", "sirup"}), 1);
        addField(inputPanel, gbc, "Jadwal Minum (HH:mm):", jadwalField = new JTextField(20), 2);
        addField(inputPanel, gbc, "Dosis Obat:", dosisField = new JTextField(20), 3);

        // Atur placeholder text (membutuhkan implementasi kustom, contoh sederhana)
        setPlaceholder(namaObatField, "Misal: Paracetamol");
        setPlaceholder(jadwalField, "Contoh: 08:30");
        setPlaceholder(dosisField, "Misal: 500mg");

        // Tombol Tambah Obat
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 8, 8, 8); // Tambah jarak di atas tombol
        tambahObatButton = new JButton("➕ Tambah Obat");
        tambahObatButton.setBackground(new Color(50, 205, 50));
        tambahObatButton.setForeground(Color.WHITE);
        tambahObatButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tambahObatButton.setFocusPainted(false);
        tambahObatButton.setBorder(BorderFactory.createCompoundBorder( // Border agak bulat
            BorderFactory.createLineBorder(new Color(40, 150, 40), 1),
            new EmptyBorder(8, 20, 8, 20)
        ));
        // Tambahkan ikon pada tombol (pastikan path gambar benar, e.g., icons/add_icon.png)
        try {
             tambahObatButton.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/icons/add_icon.png")).getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
             System.err.println("Ikon tombol tambah tidak ditemukan: " + e.getMessage());
        }

        tambahObatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahObat();
            }
        });
        inputPanel.add(tambahObatButton, gbc);

        add(inputPanel, BorderLayout.NORTH);

        // --- Area Notifikasi ---
        JScrollPane scrollPane = new JScrollPane(notifikasiArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(135, 206, 235), 2),
                "🔔 Log Notifikasi Sistem",
                TitledBorder.LEADING,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 15),
                new Color(65, 105, 225)
        ));
        scrollPane.setBackground(new Color(240, 248, 255));
        notifikasiArea.setBackground(new Color(240, 248, 255));

        add(scrollPane, BorderLayout.CENTER);
    }

    // Helper method untuk menambahkan label dan field/combo box
    private void addField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent component, int gridY) {
        gbc.gridx = 0; gbc.gridy = gridY; gbc.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1; gbc.gridy = gridY; gbc.anchor = GridBagConstraints.WEST;
        panel.add(component, gbc);
        component.setBackground(new Color(250, 250, 250)); // Warna field lebih terang
    }

    // Helper method untuk placeholder text
    private void setPlaceholder(JTextField field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(Color.GRAY);
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });
    }


    private void tambahObat() {
        // Menonaktifkan tombol dan field saat proses
        tambahObatButton.setEnabled(false);
        setFieldsEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); // Ubah cursor

        String nama = namaObatField.getText().trim();
        String jenis = (String) jenisObatComboBox.getSelectedItem();
        String jadwal = jadwalField.getText().trim();
        String dosis = dosisField.getText().trim();

        // Check for placeholder text before validation
        if (nama.equals("Misal: Paracetamol") || nama.isEmpty() ||
            jadwal.equals("Contoh: 08:30") || jadwal.isEmpty() ||
            dosis.equals("Misal: 500mg") || dosis.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "⚠️ Semua kolom harus diisi dengan data yang valid!", "Input Error", JOptionPane.WARNING_MESSAGE);
            resetAfterProcess();
            return;
        }

        if (!jadwal.matches("^([01]\\d|2[0-3]):([0-5]\\d)$")) { // Regex lebih ketat untuk HH:mm
            JOptionPane.showMessageDialog(this, "⚠️ Format jadwal tidak valid. Gunakan HH:mm (contoh: 14:30).", "Input Error", JOptionPane.WARNING_MESSAGE);
            resetAfterProcess();
            return;
        }

        notifikasiArea.append(">> Memproses Penambahan Obat: " + nama + "...\n");
        notifikasiArea.setCaretPosition(notifikasiArea.getDocument().getLength());

        // Lakukan proses di background thread agar GUI tidak freeze
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Simulasi delay proses
                Thread.sleep(1500); // Delay 1.5 detik
                return null;
            }

            @Override
            protected void done() {
                try {
                    Notifikasi notifikasi = NotifikasiFactory.buatNotifikasi(jenis);
                    Obat obatBaru = new Obat(nama, jenis, jadwal, dosis); // Buat objek obat di sini
                    notifikasiManager.kirimNotifikasi(notifikasi, obatBaru);

                    // Reset input fields dan placeholder
                    resetInputFields();

                    notifikasiArea.append(">> Obat '" + nama + "' berhasil ditambahkan!\n\n");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(MedicationReminderGUI.this, "❌ Error: " + ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
                    notifikasiArea.append(">> Gagal menambahkan obat: " + ex.getMessage() + "\n\n");
                } finally {
                    resetAfterProcess();
                }
            }
        };
        worker.execute(); // Jalankan SwingWorker
    }

    private void resetAfterProcess() {
        tambahObatButton.setEnabled(true);
        setFieldsEnabled(true);
        setCursor(Cursor.getDefaultCursor()); // Kembalikan cursor
    }

    private void setFieldsEnabled(boolean enabled) {
        namaObatField.setEnabled(enabled);
        jenisObatComboBox.setEnabled(enabled);
        jadwalField.setEnabled(enabled);
        dosisField.setEnabled(enabled);
    }

    private void resetInputFields() {
        namaObatField.setText("");
        setPlaceholder(namaObatField, "Misal: Paracetamol");
        jadwalField.setText("");
        setPlaceholder(jadwalField, "Contoh: 08:30");
        dosisField.setText("");
        setPlaceholder(dosisField, "Misal: 500mg");
        jenisObatComboBox.setSelectedIndex(0);
    }
}