package com.warehousenesia.id.Service;

import com.warehousenesia.id.Model.DataAll;
import com.warehousenesia.id.Model.DataBank;
import com.warehousenesia.id.Model.DataCountry;
import com.warehousenesia.id.Model.DataCukaiFee;
import com.warehousenesia.id.Model.DataProduct;
import com.warehousenesia.id.Model.DataProductBest;
import com.warehousenesia.id.Model.DataProductOffer;
import com.warehousenesia.id.Model.DataProductSearch;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductService {

    @GET("product/getproduct")
    Call<ArrayList<DataProduct>> getProduct();

    @GET("product/getproductbest")
    Call<ArrayList<DataProductBest>> getProductBest();

    @GET("product/getproductoffer")
    Call<ArrayList<DataProductOffer>> getProductOffer();

    @GET("product/getdatabank")
    Call<ArrayList<DataBank>> getBank();

    @GET("transaction/cukai")
    Call<ArrayList<DataCukaiFee>> getCukaiFee();

    @GET("product/getallcountry")
    Call<ArrayList<DataCountry>> getCountry();

    @GET("transaction/getlistcartall/{username}")
    Call<ArrayList<DataAll>> getKeranjang(@Path("username") String username);

    @GET("transaction/getlistcart/{id_customer}")
    Call<ArrayList<DataAll>> getListKeranjang(@Path("id_customer") String id_customer);

}
