package com.yayan.kripikazzahro.Model;

/**
 * Created by yayan dwi kusuma on 1/28/2018.
 */

public class Food {
    private String Nama, Gambar, Deskripsi, Harga, Berat, MenuId;

    public Food() {
    }

    public Food(String nama, String gambar, String deskripsi, String harga, String berat, String menuId) {
        Nama = nama;
        Gambar = gambar;
        Deskripsi = deskripsi;
        Harga = harga;
        Berat = berat;
        MenuId = menuId;

    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getGambar() {
        return Gambar;
    }

    public void setGambar(String gambar) {
        Gambar = gambar;
    }

    public String getDeskripsi() {
        return Deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        Deskripsi = deskripsi;
    }

    public String getHarga() {
        return Harga;
    }

    public void setHarga(String harga) {
        Harga = harga;
    }

    public String getBerat() {
        return Berat;
    }

    public void setBerat(String berat) {
        Berat = berat;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }



}
