package com.yayan.kripikazzahro.Model;

/**
 * Created by yayan dwi kusuma on 1/25/2018.
 */

public class Category {
    private String Nama;
    private String Gambar;

    public Category() {

    }

    public Category(String nama, String gambar) {
        Nama = nama;
        Gambar = gambar;
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

    public void setGambar(String gambar)
    {
        Gambar = gambar;
    }
}
