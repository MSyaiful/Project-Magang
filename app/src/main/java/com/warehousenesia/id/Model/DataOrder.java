package com.warehousenesia.id.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataOrder {
    @SerializedName("id_transaksi")
    @Expose
    String id_transaksi;

    @SerializedName("id_customer")
    @Expose
    String id_customer;

    @SerializedName("nama_lengkap")
    @Expose
    String nama_lengkap;

    @SerializedName("jumlah_beli")
    @Expose
    String jumlah_beli;

    @SerializedName("negara")
    @Expose
    String negara;

    @SerializedName("berat")
    @Expose
    String berat;

    @SerializedName("total_bayar")
    @Expose
    String total_bayar;

    @SerializedName("waktu")
    @Expose
    String waktu;

    @SerializedName("is_finished")
    @Expose
    String is_finished;

    @SerializedName("harga")
    @Expose
    String harga;

    public String getId_transaksi() {
        return id_transaksi;
    }

    public String getId_customer() {
        return id_customer;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public String getJumlah_beli() {
        return jumlah_beli;
    }

    public String getNegara() {
        return negara;
    }

    public String getBerat() {
        return berat;
    }

    public String getTotal_bayar() {
        return total_bayar;
    }

    public String getWaktu() {
        return waktu;
    }

    public String getIs_finished() {
        return is_finished;
    }

    public String getHarga() {
        return harga;
    }
}
