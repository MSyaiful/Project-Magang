package com.warehousenesia.id.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataCountry {

    @SerializedName("gambar")
    @Expose
    private String gambar;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("negara")
    @Expose
    private String negara;

    public String getNegara() {
        return negara;
    }

    public String getId() {
        return id;
    }

    public String getGambar() {
        return gambar;
    }
}
