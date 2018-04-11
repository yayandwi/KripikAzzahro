package com.yayan.kripikazzahro.Model;

public class MessageEvent {

    private int ongkir;
    private String keterangan;

    public MessageEvent(int ongkir, String keterangan) {
        this.ongkir = ongkir;
        this.keterangan = keterangan;
    }

    public int getOngkir() {
        return ongkir;
    }

    public void setOngkir(int ongkir) {
        this.ongkir = ongkir;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    @Override
    public String toString() {
        return "MessageEvent{" +
                "ongkir=" + ongkir +
                ", keterangan='" + keterangan + '\'' +
                '}';
    }
}