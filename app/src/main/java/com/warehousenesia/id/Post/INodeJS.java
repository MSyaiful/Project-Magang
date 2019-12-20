package com.warehousenesia.id.Post;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface INodeJS {
    @POST("auth/register")
    @FormUrlEncoded
    Observable<String> registerMember(@Field("username") String username,
                                      @Field("password") String password,
                                      @Field("email") String email,
                                      @Field("phone") String phone);

    @POST("auth/signin")
    @FormUrlEncoded
    Observable<String> loginMember(@Field("username") String username,
                                   @Field("password") String password);

    @POST("auth/addagentdata")
    @FormUrlEncoded
    Observable<String> registerAgent(@Field("fullname") String fullname,
                                     @Field("companyName") String companyName,
                                     @Field("address") String address,
                                     @Field("kota") String kota,
                                     @Field("Provinsi") String provinsi,
                                     @Field("kodepos") Integer kodepos);
}
