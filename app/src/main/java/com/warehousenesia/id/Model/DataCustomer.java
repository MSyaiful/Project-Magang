package com.warehousenesia.id.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataCustomer {

    @SerializedName("id_customer")
    @Expose
    String id_customer;

    @SerializedName("id_agent")
    @Expose
    String id_agent;

    @SerializedName("nama_lengkap")
    @Expose
    private String nama_lengkap;

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

    @SerializedName("kodepos")
    @Expose
    private String kodepos;

    public DataCustomer(String id_customer, String id_agent, String nama_lengkap, String alamat, String kecamatan, String kota, String provinsi, String id_ktp, String npwp, String number, String kodepos) {
        this.id_customer = id_customer;
        this.id_agent = id_agent;
        this.nama_lengkap = nama_lengkap;
        this.alamat = alamat;
        this.kecamatan = kecamatan;
        this.kota = kota;
        this.provinsi = provinsi;
        this.id_ktp = id_ktp;
        this.npwp = npwp;
        this.number = number;
        this.kodepos = kodepos;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public void setId_agent(String id_agent) {
        this.id_agent = id_agent;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public void setId_ktp(String id_ktp) {
        this.id_ktp = id_ktp;
    }

    public void setNpwp(String npwp) {
        this.npwp = npwp;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setKodepos(String kodepos) {
        this.kodepos = kodepos;
    }

    public String getId_customer() {
        return id_customer;
    }

    public String getId_agent() {
        return id_agent;
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

    public String getKodepos() {
        return kodepos;
    }

    public String toString(){
        return nama_lengkap;
    }
}
