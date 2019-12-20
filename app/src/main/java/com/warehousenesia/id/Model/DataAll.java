package com.warehousenesia.id.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataAll {

    @SerializedName("nama_produk")
    @Expose
    private String nama_produk;

    @SerializedName("jumlah_beli")
    @Expose
    private int jumlah_beli;

    @SerializedName("total_berat")
    @Expose
    private double total_berat;

    @SerializedName("total_harga")
    @Expose
    private double total_harga;

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

    @SerializedName("gambar")
    @Expose
    private String gambar;

    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;

    @SerializedName("nama_kategori")
    @Expose
    private String nama_kategori;

    @SerializedName("nama_negara")
    @Expose
    private String nama_negara;

    @SerializedName("id_negara")
    @Expose
    private String id_negara;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("nama_lengkap")
    @Expose
    String nama_lengkap;

    @SerializedName("alamat")
    @Expose
    String alamat;

    @SerializedName("kecamatan")
    @Expose
    String kecamatan;

    @SerializedName("kota")
    @Expose
    String kota;

    @SerializedName("provinsi")
    @Expose
    String provinsi;

    @SerializedName("id_ktp")
    @Expose
    String id_ktp;

    @SerializedName("npwp")
    @Expose
    String npwp;

    @SerializedName("number")
    @Expose
    String number;

    @SerializedName("negara")
    @Expose
    private String negara;

    @SerializedName("catatan")
    @Expose
    private String catatan;

    public String getCatatan() {
        return catatan;
    }

    public String getNegara() {
        return negara;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public int getJumlah_beli() {
        return jumlah_beli;
    }

    public double getTotal_berat() {
        return total_berat;
    }

    public double getTotal_harga() {
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

    public String getHarga() {
        return harga;
    }

    public String getBerat() {
        return berat;
    }

    public String getGambar() {
        return gambar;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public String getNama_negara() {
        return nama_negara;
    }

    public String getId_negara() {
        return id_negara;
    }

    public String getId() {
        return id;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public String getKota() {
        return kota;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public String getId_ktp() {
        return id_ktp;
    }

    public String getNpwp() {
        return npwp;
    }

    public String getNumber() {
        return number;
    }
}
