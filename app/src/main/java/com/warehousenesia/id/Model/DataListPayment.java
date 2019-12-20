package com.warehousenesia.id.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataListPayment {

    @SerializedName("id_transaksi")
    @Expose
    String id_transaksi;

    @SerializedName("total_bayar")
    @Expose
    String total_bayar;

    @SerializedName("total_berat")
    @Expose
    String total_berat;

    public String getId_transaksi() {
        return id_transaksi;
    }

    public String getTotal_bayar() {
        return total_bayar;
    }

    public String getTotal_berat() {
        return total_berat;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public void setTotal_bayar(String total_bayar) {
        this.total_bayar = total_bayar;
    }

    public void setTotal_berat(String total_berat) {
        this.total_berat = total_berat;
    }
}
