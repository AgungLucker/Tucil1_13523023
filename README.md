
# Tugas Kecil 1 IF2211 Strategi Algoritma
> IQ Puzzler Pro Solver

IQ Puzzler Pro adalah suatu permainan papan yang mengharuskan pemain untuk mengisi seluruh bagian pada papan dengan blok atau piece yang tersedia

Program ini dibuat untuk menyelesaikan permainan IQ Puzzler pro dengan pendekatan Algoritma Brute Force. Pencarian solusi dilakukan dengan mencoba semua urutan dari peletakan blok pada papan. Setiap Blok akan dicoba ditempatkan pada posisi yang kosong pada papan dengan variasi rotasi dan pencerminan pada blok. Apabila blok gagal ditempatkan pada semua posisi pada papan meskipun telah dilakukan rotasi dan pencerminan pada blok, program akan mundur satu langkah untuk mengubah arah rotasi atau pencerminan dari blok sebelumnya atau menguji urutan peletakan blok yang berbeda hingga dapat menemukan solusi akhir.


## Made by
Muhammad Aufa Farabi - 13523023

## Features
* fitur dasar *solver* permainan IQ Puzzler Pro
* Bentuk papan *costumizable*
* Pembacaan input dari *file* .txt
    ketentuan format pada *file* txt adalah berikut
  - N M P
  - S (DEFAULT/CUSTOM)
  - KONFIGURASI
  - PAPAN_CUSTOM (opsional untuk konfigurasi CUSTOM)
  - puzzle_1_shape
  - puzzle_2_shape                    

     ...

  - puzzle_P_shape
* Opsi penyimpanan solusi pada *file* .txt
* Program Solver dalam GUI


## How to Run
1. Clone repository pada terminal
   ```sh
   git clone https://github.com/AgungLucker/Tucil1_13523023
   ```
2. Pindah ke direktori src untuk memulai program
    ```sh
    cd src
    ```
2. Compile program 
    ```sh
    javac *.java
    ```
3. Jalankan program dengan command berikut
    ```sh
    java Main.java 
    ```

## Links
- Project Homepage:
(https://github.com/AgungLucker/Tucil1_13523023)

