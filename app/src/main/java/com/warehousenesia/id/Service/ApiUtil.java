package com.warehousenesia.id.Service;

public class ApiUtil {

    public ApiUtil(){

    }

    public static final String Base_url = "http://192.168.100.17:1997/";

    public static ApiService getPaketService(){
        return GetClient.getClient(Base_url).create(ApiService.class);
    }
}
