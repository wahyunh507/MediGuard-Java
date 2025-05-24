// Nama : Wahyu Nur Hidayat - NRP : 3124640005

public class NotifikasiFactory {
  public static Notifikasi buatNotifikasi(String jenis) {
      if (jenis.equalsIgnoreCase("pil")) {
          return new NotifikasiPil();
      } else if (jenis.equalsIgnoreCase("sirup")) {
          return new NotifikasiSirup();
      } else {
          throw new IllegalArgumentException("Jenis notifikasi tidak dikenal: " + jenis);
      }
  }
}
