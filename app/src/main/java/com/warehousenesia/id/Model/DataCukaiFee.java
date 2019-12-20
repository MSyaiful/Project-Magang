package com.warehousenesia.id.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataCukaiFee {

    @SerializedName("id")
    @Expose
    String id;

    @SerializedName("bea_masuk")
    @Expose
    String bea_masuk;

    @SerializedName("PPN")
    @Expose
    String ppn;

    @SerializedName("PPh")
    @Expose
    String pph;

    @SerializedName("PPnBM")
    @Expose
    String ppnbm;

    @SerializedName("pungutan")
    @Expose
    String pungutan;

    public String getId() {
        return id;
    }

    public String getBea_masuk() {
        return bea_masuk;
    }

    public String getPpn() {
        return ppn;
    }

    public String getPph() {
        return pph;
    }

    public String getPpnbm() {
        return ppnbm;
    }

    public String getPungutan() {
        return pungutan;
    }
}
