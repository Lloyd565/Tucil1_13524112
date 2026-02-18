# Tugas Kecil 1 Strategi Algoritma (Queens LinkedIn Solver)

## Penjelasan Program
Program ini adalah aplikasi GUI (Graphical User Interface) untuk menyelesaikan persoalan Queens pada papan berukuran N x N. Program ini mendukung dua varian algoritma Brute Force untuk mencari solusi penempatan queen:
1. **Brute Force Optimal**
2. **Brute Force Murni**

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
├── test/           # Berisi file input dan output
└── README.md       # Dokumentasi proyek ini
```

## Requirement dan Instalasi
### Requirement
- **Java Development Kit (JDK)** minimal versi 8.
- Sistem Operasi yang mendukung Java (Windows, Linux, macOS).

### Instalasi
Tidak ada instalasi khusus yang diperlukan menjalankan program ini, cukup pastikan _source code_ lengkap dan Java sudah terinstall.
Dapat verifikasi instalasi Java dengan perintah:
```bash
java -version
javac -version
```

## Cara Menjalankan Program
Program sudah dibuild menjadi file JAR yang siap dijalankan.
1. Buka terminal di folder utama proyek `Tucil1_13524112`.
2. Jalankan perintah:
   ```bash
   java -jar bin/Tucil1.jar
   ```

## Cara Compile Ulang
Jika melakukan perubahan pada *source code* dan ingin compile ulang:
1. Pastikan folder `bin` tersedia.
2. Jalankan perintah compile:
   ```bash
   javac -d bin src/*.java
   ```
3. Buat ulang file JAR:
   ```bash
   cd bin
   jar cfe Tucil1.jar RunGUI *.class
   cd ..
   ```
4. Atau jalankan langsung via class file:
   ```bash
   java -cp bin RunGUI
   ```

### Cara Menggunakan
1. **Persiapan Input File**:
   - Simpan file konfigurasi papan (file `.txt`) di dalam folder `test/`.
   - Format file mengikuti spesifikasi persoalan (matriks karakter N x N).
2. **Load Papan**:
   - Jalankan program
   - Pilih nama file dari _dropdown menu_ di panel sebelah kiri (tekan tombol ↻ jika file baru ditambahkan saat aplikasi berjalan).
   - Klik tombol **Load Board**. Visualisasi papan akan muncul di area utama.
3. **Pilih Algoritma**:
   - Pilih algoritma penyelesaian pada _dropdown menu_ algoritma: "Brute Force Optimal" atau "Brute Force Murni".
4. **Mulai Penyelesaian**:
   - Klik tombol **SOLVE**.
   - Aplikasi akan memvisualisasikan langkah-langkah pencarian solusi.
   - Indikator status akan menunjukkan apakah solusi ditemukan, waktu eksekusi, dan jumlah iterasi.

## Author
- **Nama**: Richard Samuel Simanullang
- **NIM**: 13524112
- **Mata Kuliah**: IF221 Strategi Algoritma
- **Kelas**: K2
