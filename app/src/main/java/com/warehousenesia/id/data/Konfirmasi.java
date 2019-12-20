package com.warehousenesia.id.data;

public class Konfirmasi {

    private String id_transaksi;
    private String id_agent;
    private String status;
    private String waktu;

    public Konfirmasi(String id_transaksi, String id_agent, String status, String waktu) {
        this.id_transaksi = id_transaksi;
        this.id_agent = id_agent;
        this.status = status;
        this.waktu = waktu;
    }
}
