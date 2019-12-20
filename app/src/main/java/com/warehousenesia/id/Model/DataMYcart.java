package com.warehousenesia.id.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataMYcart {

    @SerializedName("nama_produk")
    @Expose
    private String nama_produk;

    @SerializedName("jumlah_beli")
    @Expose
    private String jumlah_beli;

    @SerializedName("total_berat")
    @Expose
    private String total_berat;

    @SerializedName("total_harga")
    @Expose
    private String total_harga;

    @SerializedName("id_customer")
    @Expose
    private String id_customer;

    @SerializedName("id_agent")
    @Expose
    private String id_agent;

    @SerializedName("id_produk")
    @Expose
    private String id_produk;

    @SerializedName("harga")
    @Expose
    private String harga;

    @SerializedName("berat")
    @Expose
    private String berat;

    public String getNama_produk() {
        return nama_produk;
    }

    public String getJumlah_beli() {
        return jumlah_beli;
    }

    public String getTotal_berat() {
        return total_berat;
    }

    public String getTotal_harga() {
        return total_harga;
    }

    public String getId_customer() {
        return id_customer;
    }

    public String getId_agent() {
        return id_agent;
    }

    public String getId_produk() {
        return id_produk;
    }

    public String getHarga() {return harga;}

    public String getBerat() {return  berat;}
}
