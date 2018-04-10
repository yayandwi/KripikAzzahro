package com.yayan.kripikazzahro.Model;

/**
 * Created by yayan dwi kusuma on 3/25/2018.
 */

public class Ongkir {
    private String Tarif;
    private String Tujuan;
    private String KotaId;


    public Ongkir() {

    }

    public Ongkir(String tarif, String tujuan, String kotaId) {
        Tarif = tarif;
        Tujuan = tujuan;
        KotaId = kotaId;
    }

    public String getTarif() {
        return Tarif;
    }

    public void setTarif(String tarif) {
        Tarif = tarif;
    }

    public String getTujuan() {
        return Tujuan;
    }

    public void setTujuan(String tujuan) {
        Tujuan = tujuan;
    }


    public String getKotaId() {
        return KotaId;
    }

    public void getKotaId(String kotaId) {
        KotaId = kotaId;
    }
}
