package com.warehousenesia.id.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataRequestProduct {

    @SerializedName("id_user")
    @Expose
    private String id_user;

    @SerializedName("nama_barang")
    @Expose
    private String nama_barang;

    @SerializedName("gambar")
    @Expose
    private String gambar;

    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;

    @SerializedName("kategori")
    @Expose
    private String kategori;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("id_negara")
    @Expose
    private String id_negara;

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId_negara() {
        return id_negara;
    }

    public void setId_negara(String id_negara) {
        this.id_negara = id_negara;
    }
}
