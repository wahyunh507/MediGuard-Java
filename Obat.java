// Nama: Wahyu Nur Hidayat NRP: 3124640005
public class Obat {
    private String nama;
    private String jenis; // pil atau sirup
    private String jadwal;
    private String dosis;

    public Obat(String nama, String jenis, String jadwal, String dosis) {
        this.nama = nama;
        this.jenis = jenis;
        this.jadwal = jadwal;
        this.dosis = dosis;
    }

    public String getNama() {
        return nama;
    }

    public String getJenis() {
        return jenis;
    }

    public String getJadwal() {
        return jadwal;
    }

    public String getDosis() {
        return dosis;
    }

    @Override
    public String toString() {
        return "Nama: " + nama + ", Jenis: " + jenis + ", Jadwal: " + jadwal + ", Dosis: " + dosis;
    }
}