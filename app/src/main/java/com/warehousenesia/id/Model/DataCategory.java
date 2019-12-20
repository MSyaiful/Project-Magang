package com.warehousenesia.id.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataCategory {
    @SerializedName("id")
    @Expose
    String id;

    @SerializedName("kode")
    @Expose
    String kode;

    @SerializedName("nama")
    @Expose
    String nama;

    public DataCategory(String id, String kode, String nama) {
        this.id = id;
        this.kode = kode;
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String toString(){
        return nama;
    }

}
