package com.warehousenesia.id.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataAgent {

    @SerializedName("id_agent")
    @Expose
    private String id_agent;

    @SerializedName("fullname")
    @Expose
    private String fullname;

    public String getId_agent() {
        return id_agent;
    }

    public void setId_agent(String id_agent) {
        this.id_agent = id_agent;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
