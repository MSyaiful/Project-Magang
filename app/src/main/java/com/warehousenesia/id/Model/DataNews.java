package com.warehousenesia.id.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataNews {
    @SerializedName("news")
    @Expose
    String news;

    public String getNews() {
        return news;
    }
}
