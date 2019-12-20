package com.warehousenesia.id.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataBank {

    @SerializedName("id")
    @Expose
    String id;

    @SerializedName("nama_bank")
    @Expose
    String nama_bank;

    @SerializedName("noRek")
    @Expose
    String norek;

    @SerializedName("atasNama")
    @Expose
    String atasnama;

    @SerializedName("gambar")
    @Expose
    String gambar;

    public DataBank(String id, String nama_bank, String norek, String atasnama, String gambar) {
        this.id = id;
        this.nama_bank = nama_bank;
        this.norek = norek;
        this.atasnama = atasnama;
        this.gambar = gambar;
    }

    public String getId() {
        return id;
    }

    public String getNama_bank() {
        return nama_bank;
    }

    public String getNorek() {
        return norek;
    }

    public String getAtasnama() {
        return atasnama;
    }

    public String getGambar() {
        return gambar;
    }

    public String toString(){
        return nama_bank;
    }
}