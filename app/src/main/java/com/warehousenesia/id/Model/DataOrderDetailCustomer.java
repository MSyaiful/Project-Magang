package com.warehousenesia.id.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataOrderDetailCustomer {

    @SerializedName("id")
    @Expose
    String id;

    @SerializedName("id_produk")
    @Expose
    String id_produk;

    @SerializedName("id_customer")
    @Expose
    String id_customer;

    @SerializedName("nama_lengkap")
    @Expose
    String nama_lengkap;

    @SerializedName("nama_produk")
    @Expose
    String nama_produk;

    @SerializedName("harga")
    @Expose
    String harga;

    @SerializedName("berat")
    @Expose
    String berat;

    @SerializedName("jumlah_beli")
    @Expose
    String jumlah_beli;

    @SerializedName("total_berat")
    @Expose
    String total_berat;

    @SerializedName("total_harga")
    @Expose
    String total_harga;

    @SerializedName("negara")
    @Expose
    String negara;

    @SerializedName("catatan")
    @Expose
    String catatan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_produk() {
        return id_produk;
    }

    public void setId_produk(String id_produk) {
        this.id_produk = id_produk;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getBerat() {
        return berat;
    }

    public void setBerat(String berat) {
        this.berat = berat;
    }

    public String getJumlah_beli() {
        return jumlah_beli;
    }

    public void setJumlah_beli(String jumlah_beli) {
        this.jumlah_beli = jumlah_beli;
    }

    public String getTotal_berat() {
        return total_berat;
    }

    public void setTotal_berat(String total_berat) {
        this.total_berat = total_berat;
    }

    public String getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(String total_harga) {
        this.total_harga = total_harga;
    }

    public String getNegara() {
        return negara;
    }

    public void setNegara(String negara) {
        this.negara = negara;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }
}
