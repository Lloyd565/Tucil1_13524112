# Tucil 1 Stima (Queens Solver)

## Penjelasan Singkat
Program ini adalah aplikasi GUI (Graphical User Interface) untuk menyelesaikan persoalan Queens pada papan berukuran N x N. Program ini mendukung dua varian algoritma Brute Force untuk mencari solusi penempatan ratu agar tidak saling menyerang:
1. **Brute Force Optimal**: Mencari solusi dengan pendekatan yang lebih optimal dalam menelusuri ruang pencarian.
2. **Brute Force Murni**: Mencari solusi dengan mencoba seluruh kemungkinan posisi ratu secara naif.

Aplikasi ini menampilkan visualisasi papan dengan pewarnaan region (jika relevan dengan format input) dan proses penempatan ratu secara real-time. Program dibangun menggunakan bahasa pemrograman Java dan pustaka Swing.

## Struktur Proyek

Susunan folder dan file dalam proyek ini adalah sebagai berikut:

```
.
├── bin/            # file *.class
├── src/            # Source code program
│   ├── GUI.java        # Implementasi antarmuka grafis (Swing)
│   ├── RunGUI.java     # Entry point utama untuk menjalankan GUI
│   ├── main.java       # Entry point untuk CLI
│   ├── solve.java      # Algoritma Brute Force Murni
│   └── opt_solve.java  # Algoritma Brute Force Optimal       
├── test/           # Berisi file input uji (*.txt)
└── README.md       # Dokumentasi proyek ini
```

## Requirement dan Instalasi
### Requirement
- **Java Development Kit (JDK)** minimal versi 8.
- Sistem Operasi yang mendukung Java (Windows, Linux, macOS).

### Instalasi
Tidak ada instalasi khusus yang diperlukan menjalankan program ini, cukup pastikan _source code_ lengkap dan Java sudah terinstall.
Anda dapat memverifikasi instalasi Java dengan perintah:
```bash
java -version
javac -version
```

## Cara Kompilasi
Untuk mengompilasi program dari _source code_:

1. Buka terminal atau command prompt.
2. Navigasikan ke direktori root proyek ini (di mana folder `src`, `bin`, dan `test` berada).
3. Jalankan perintah berikut untuk mengompilasi seluruh file Java ke dalam folder `bin`:
   ```bash
   javac -d bin src/*.java
   ```
   *Catatan: Pastikan folder `bin` sudah tersedia. Jika belum, buat dengan perintah `mkdir bin` (Linux/Mac) atau `md bin` (Windows).*

## Cara Menjalankan dan Menggunakan
### Menjalankan Program
Setelah proses kompilasi selesai, jalankan program dengan perintah:
```bash
java -cp bin RunGUI
```

### Cara Menggunakan
1. **Persiapan Input File**:
   - Simpan file konfigurasi papan (file `.txt`) di dalam folder `test/`.
   - Format file mengikuti spesifikasi persoalan (matriks karakter N x N).
2. **Load Papan**:
   - Buka aplikasi.
   - Pilih nama file dari _dropdown menu_ di panel sebelah kiri (tekan tombol ↻ jika file baru ditambahkan saat aplikasi berjalan).
   - Klik tombol **Load Board**. Visualisasi papan akan muncul di area utama.
3. **Pilih Algoritma**:
   - Pilih algoritma penyelesaian pada _dropdown menu_ algoritma: "Brute Force Optimal" atau "Brute Force Murni".
4. **Mulai Penyelesaian**:
   - Klik tombol **SOLVE**.
   - Aplikasi akan memvisualisasikan langkah-langkah pencarian solusi.
   - Indikator status akan menunjukkan apakah solusi ditemukan, waktu eksekusi, dan jumlah iterasi.

## Author
- **Nama**: Richard
- **NIM**: 13524112
- **Mata Kuliah**: Strategi Algoritma (IF2211)
- **Kelas**: K2
