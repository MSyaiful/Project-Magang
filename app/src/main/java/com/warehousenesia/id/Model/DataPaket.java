package com.warehousenesia.id.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataPaket {

    @SerializedName("id")
    @Expose
    String id;

    public String getId() {
        return id;
    }

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("price")
    @Expose
    private String  price;

    @SerializedName("detail1")
    @Expose
    private String detail1;

    @SerializedName("detail2")
    @Expose
    private String detail2;

    @SerializedName("detail3")
    @Expose
    private String detail3;

    @SerializedName("detail4")
    @Expose
    private String detail4;

    @SerializedName("detail5")
    @Expose
    private String detail5;

    public String getNama() {return nama;}
    public void setNama(String nama) {this.nama= nama;}

    public String getPrice() {return price;}
    public void setPrice(String  price) {this.price = price;}

    public String getDetail1() {return detail1;}
    public void setDetail1(String detail1) {this.detail1 = detail1;}

    public String getDetail2() {return detail2;}
    public void setDetail2(String detail2) {this.detail2 = detail2;}

    public String getDetail3() {return detail3;}
    public void setDetail3(String detail3) {this.detail3 = detail3;}

    public String getDetail4() {return detail4;}
    public void setDetail4(String detail4) {this.detail4 = detail4;}

    public String getDetail5() {return detail5;}
    public void setDetail5(String detail5) {this.detail5 = detail5;}

}
