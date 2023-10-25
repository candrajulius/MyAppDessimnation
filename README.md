# Aplikasi Disseminasi

## Overview Aplikasi Disseminasi
Disseminasi adalah sebuah aplikasi yang digunakan untuk mahasiswa untuk mentracking disseminasi mereka.

## Design Aplikasi Disseminasi
### User Interface Untuk Mahasiswa
|Splash Screen| Pilihan Login | Login Mahasiswa | Index Disseminasi | Tambah Anggota | Koreksi Anggota | Ubah Status Disseminasi |
|--|--|--|--|--|--|--|
| <p align="center"><img src="https://raw.githubusercontent.com/candrajulius/MyAppDessimnation/master/splash_screen.jpeg" width="200"></p> |<p align="center"><img src="https://raw.githubusercontent.com/candrajulius/MyAppDessimnation/master/pilihan_login.jpeg" width="200"></p> | <p><img src="https://raw.githubusercontent.com/candrajulius/MyAppDessimnation/master/login_mahasiswa.jpeg" width="200"></p>| <p align="center"><img src="https://raw.githubusercontent.com/candrajulius/MyAppDessimnation/master/index_disseminasi_kepala_peneliti.jpeg" width="200"></p> | <p align="center"><img src="https://raw.githubusercontent.com/candrajulius/MyAppDessimnation/master/ajukan_anggota.jpeg" width="200"></p> | <p align="center"><img src="https://raw.githubusercontent.com/candrajulius/MyAppDessimnation/master/koreksi_anggota.jpeg" width="200"></p> | <p align="center"><img src="https://raw.githubusercontent.com/candrajulius/MyAppDessimnation/master/ubah_catatan_disseminasi.jpeg" width="200"></p>|

## Fitur Aplikasi
- **Tambah Anggota Disseminasi**
- **Ubah Status Disseminasi**
- **Koreksi Anggota Disseminasi**
- **Catatn Disseminasi**
- **Tambah Disseminasi Oleh Dosen**
- **Cek Status Disseminasi Oleh Dosen**
- **Menerima Anggota Disseminasi Oleh Mahasiswa**
- **Jadwal Disseminasi**
- **Notifikasi Jadwal Dari Disseminasi**
- **Show Disseminasi**

## Library Yang Digunakan
-  [Coil](https://coil-kt.github.io/coil/) -> **For fect image from internet**
-  [Retrofit](https://square.github.io/retrofit/) -> **For request API**
-  [DataStore](https://developer.android.com/topic/libraries/architecture/datastore?hl=id) -> **For Session Login**
-  [Karumi Dexter](https://github.com/Karumi/Dexter) -> **For permission in android**
-  [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) -> **For observer live data**
-  [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android?hl=id) -> **For Dependecy Injection**
-  [Shimmer](https://facebook.github.io/shimmer-android/) -> **For loading**

## Arsitektur Yang Digunakan
|Architecture MVVM | Overview Architecture |
|--|--|
|<p align="center"><img src="https://raw.githubusercontent.com/MEY-Mental-Education-Yes/The-Logo-MEY/main/mvvm.png" width="1000"></p> | Model–view–viewmodel (MVVM) is a software architectural pattern that facilitates the separation of the development of the graphical user interface (the view) – be it via a markup language or GUI code – from the development of the business logic or back-end logic (the model) so that the view is not dependent on any specific model platform.
