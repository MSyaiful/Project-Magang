package com.warehousenesia.id.Service;


import android.widget.TextView;

import com.google.gson.annotations.Expose;
import com.warehousenesia.id.Model.DataAgent;
import com.warehousenesia.id.Model.DataBank;
import com.warehousenesia.id.Model.DataCategory;
import com.warehousenesia.id.Model.DataCustomer;
import com.warehousenesia.id.Model.DataListPayment;
import com.warehousenesia.id.Model.DataOrder;
import com.warehousenesia.id.Model.DataOrderDetailCustomer;
import com.warehousenesia.id.Model.DataPaket;
import com.warehousenesia.id.Model.DataProduct;
import com.warehousenesia.id.Model.DataProductSearch;
import com.warehousenesia.id.Model.DataRequestProduct;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("transaction/paket")
    Call<ArrayList<DataPaket>> getPaket();

    @POST("auth/register")
    @FormUrlEncoded
    Call<ResponseBody> registerMember(@Field("username") String username,
                                      @Field("password") String password,
                                      @Field("email") String email,
                                      @Field("phone") String phone);

    @POST("auth/signin")
    @FormUrlEncoded
    Call<ResponseBody> loginMember(@Field("username") String username,
                                   @Field("password") String password);

    @POST("auth/addagentdata")
    @FormUrlEncoded
    Call<ResponseBody> registerAgent(@Field("id_agent") String id_agent,
                                     @Field("fullname") String fullname,
                                     @Field("companyName") String companyName,
                                     @Field("address") String address,
                                     @Field("kota") String kota,
                                     @Field("Provinsi") String provinsi,
                                     @Field("kodepos") Integer kodepos);

    @POST("customer/addcustomer")
    @FormUrlEncoded
    Call<ResponseBody> registerCustomer(@Field("id_agent") String id_agent,
                                        @Field("nama_lengkap") String nama_lengkap,
                                        @Field("id_ktp") String id_ktp,
                                        @Field("npwp") String npwp,
                                        @Field("number") String number,
                                        @Field("alamat") String alamat,
                                        @Field("kecamatan") String kecamatan,
                                        @Field("kota") String kota,
                                        @Field("provinsi") String provinsi,
                                        @Field("kodepos") String kodepos);

    @POST("auth/daftaragent")
    @FormUrlEncoded
    Call<ResponseBody> beliPaket(@Field("total") String total,
                                 @Field("id_agent") String id_agent,
                                 @Field("bank") String bank);

    @POST("product/searchproduct")
    @FormUrlEncoded
    Call<ArrayList<DataProductSearch>> SearchProduct(@Field("keyword") String nama_product);

    @POST("transaction/addtocart")
    @FormUrlEncoded
    Call<ResponseBody> addtoCart(@Field("nama_produk") String nama_produk,
                                 @Field("jumlah_beli") Integer jumlah_beli,
                                 @Field("total_berat") String total_berat,
                                 @Field("total_harga") String total_harga,
                                 @Field("id_customer") String id_customer,
                                 @Field("id_agent") String id_agent,
                                 @Field("id_produk") String id_produk,
                                 @Field("harga") String harga,
                                 @Field("berat") String berat);

    @POST("customer/getcustomer")
    @FormUrlEncoded
    Call<ArrayList<DataCustomer>> getCustomer(@Field("username") String username);

    @GET("product/getdatabank")
    Call<ArrayList<DataBank>> getBank();

    @POST("transaction/getlistpayment")
    @FormUrlEncoded
    Call<ArrayList<DataListPayment>> getListPayment(@Field("username") String username);

    @GET("auth/getagentstoredata/{id}")
    Call<ResponseBody> CheckData(@Path("id") String id);

    @GET("product/getdatabank")
    Call<ResponseBody> getIDbank();

    @GET("transaction/shipping")
    Call<ResponseBody> getShipping();

    @GET("transaction/cukai")
    Call<ResponseBody> getIDCukaiFee();

    @GET("product/getproductcountry/{negara}")
    Call<ArrayList<DataProduct>> getProductNegara(@Path("negara") String negara);

    @GET("customer/getnews")
    Call<ResponseBody> getNews();

    @GET("transaction/curency")
    Call<ResponseBody> getCurency();

    @GET("product/getproductrequestbyuser/{id}")
    Call<ArrayList<DataRequestProduct>> getRequestProduct(@Path("id") String id);

    @PUT("customer/editcustomer/{id_customer}")
    @FormUrlEncoded
    Call<ResponseBody> getEditCustomer(@Path("id_customer") String id_customer,
                                       @Field("nama_lengkap") String nama_lengkap,
                                       @Field("id_ktp") String id_ktp,
                                       @Field("npwp") String npwp,
                                       @Field("number") String number,
                                       @Field("alamat") String alamat,
                                       @Field("kecamatan") String kecamatan,
                                       @Field("kota") String kota,
                                       @Field("provinsi") String provinsi,
                                       @Field("kodepos") String kodepos);

    @DELETE("customer/deletecustomer/{id_customer}")
    Call<ResponseBody> deleteCustomer(@Path("id_customer") String id_customer);

    @GET("transaction/getlistorder/{username}")
    Call<ArrayList<DataOrder>> getOrder(@Path("username") String username);

    @GET("transaction/getlistorderdetail/{id_transaksi}")
    Call<ArrayList<DataOrder>> getOrderDetail(@Path("id_transaksi") String id_transaksi);

    @POST("transaction/getlistordercustomer")
    @FormUrlEncoded
    Call<ArrayList<DataOrderDetailCustomer>> getDetailOrderCustomer(@Field("id_customer") String id_customer,
                                                                    @Field("id_transaksi") String id_transaksi);

    @POST("transaction/deleteitemcart")
    @FormUrlEncoded
    Call<ResponseBody> deleteKeranjang(@Field("id_agent") String id_agent,
                                       @Field("id_customer") String id_customer,
                                       @Field("id_produk") String id_produk);

    @POST("transaction/editcatatancustomer")
    @FormUrlEncoded
    Call<ResponseBody> editCatatan(@Field("id") String id,
                                   @Field("catatan") String catatan);

    @POST("transaction/editlistcart")
    @FormUrlEncoded
    Call<ResponseBody> editQuantity(@Field("id_customer") String id_customer,
                                    @Field("id_agent") String id_agent,
                                    @Field("id_produk") String id_produk,
                                    @Field("jumlah_beli") String jumlah_beli,
                                    @Field("total_berat") String total_berat,
                                    @Field("total_harga") String total_harga
    );

    @POST("transaction/addtransaction")
    @FormUrlEncoded
    Call<ResponseBody> addTransaction(@Field("waktu") String waktu,
                                      @Field("total_bayar") String total_bayar,
                                      @Field("total_berat") String total_berat,
                                      @Field("is_finished") String is_finished,
                                      @Field("id_agent") String id_agent,
                                      @Field("transactionfee") String transactionfee,
                                      @Field("hargashipping") String hargashipping,
                                      @Field("total_beli") String total_beli);

    @POST("transaction/adddetailtransaction")
    @FormUrlEncoded
    Call<ResponseBody> addDetailTransaction(
            @Field("id_transaksi") String id,
            @Field("id_agent") String id_agent,
            @Field("id_customer") String id_cus,
            @Field("id_produk") String id_produk,
            @Field("nama_produk") String nama_produk,
            @Field("harga") String harga,
            @Field("jumlah_beli") String jumlah_beli,
            @Field("total_harga") String total_harga,
            @Field("catatan") String catatan,
            @Field("bpom") String bpom,
            @Field("id_negara") String id_negara,
            @Field("id_kategori") String id_kategori,
            @Field("berat") String berat,
            @Field("tot_berat") String tot_harga,
            @Field("waktu") String waktu);

    @POST("transaction/clearcart")
    @FormUrlEncoded
    Call<ResponseBody> clearCart(@Field("id_agent") String id_agent);

    @GET("transaction/getlistchartagent/{username}")
    Call<ResponseBody> getlistChart(@Path("username") String username);

    @POST("auth/updatelimit")
    @FormUrlEncoded
    Call<ResponseBody> updateLimit(@Field("id_agent") String id_agent,
                                   @Field("limit") String limit,
                                   @Field("level") String level);

    @GET("product/getproductrequestShopper/{id_agent}")
    Call<ArrayList<DataRequestProduct>>getRequestShopper(@Path("id_agent") String id_agent);

    @GET("product/getallcategory")
    Call<ArrayList<DataCategory>>getCategory();

}