package com.warehousenesia.id;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.warehousenesia.id.Model.DataAll;
import com.warehousenesia.id.Service.ApiService;
import com.warehousenesia.id.Service.ApiUtil;
import com.warehousenesia.id.Service.GetClient;
import com.warehousenesia.id.Service.ProductService;
import com.warehousenesia.id.adapter.AllAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Keranjang extends AppCompatActivity implements View.OnClickListener {
    SharedPrefManager sharedPrefManager;
    TextView idcustomer, total_item, total_weight, total_harga1, transfee, shipfee, devfee, total_harga2, memberlimit;
    Button checkOUT;
    ApiService apiServices;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);

        sharedPrefManager = new SharedPrefManager(getApplication());
        String level = sharedPrefManager.getSpLevel();
        String negara_ship = sharedPrefManager.getSP_ShipNegara();
        RecyclerView rvlist = findViewById(R.id.cart);
        String id = sharedPrefManager.getSPNama();
        getCart(rvlist, id);
        devfee = findViewById(R.id.delivery_fee);
        total_item = findViewById(R.id.total_item);
        total_item.setText(sharedPrefManager.getSP_TotalItemKeranjang());

        total_weight = findViewById(R.id.total_weight);
//        double tw = Double.parseDouble(String.valueOf(sharedPrefManager.getSP_TotalWeightKeranjang()));
//        double akhir_tw = tw / 1000;
//        total_weight.setText(String.valueOf(akhir_tw));

        total_harga1 = findViewById(R.id.total_harga1);
        int total = 0;

        if(level.equals("PLATINUM")){
            double jumlah = 15000 * 0.05;
            double akhir = 15000 + jumlah;
            total = (int) akhir;
        }else if(level.equals("GOLD")){
            double jumlah = 15000 * 0.075;
            double akhir = 15000 + jumlah;
            total = (int) akhir;
        }else if(level.equals("SILVER")){
            double jumlah = 15000 * 0.1;
            double akhir = 15000 + jumlah;
            total = (int) akhir;
        }else if (level.equals("BRONZE")){
            double jumlah = 15000 * 0.15;
            double akhir = 15000 + jumlah;
            total = (int) akhir;
        }
//        double th1 = Double.parseDouble(sharedPrefManager.getSP_TotalHargaKeranjang());
//        double akhir_th1 = total * th1;
//        double fee10 = akhir_th1 * 0.1;
//        double akhir_totprice = akhir_th1 + fee10;


        transfee = findViewById(R.id.transfee_cart);
//        transfee.setText(String.valueOf(fee10));

        String username = sharedPrefManager.getSPNama();

        memberlimit = findViewById(R.id.limit_member);


        apiServices = ApiUtil.getPaketService();
        final double[] bayarall = {0};
        final double[] tranall = {0};
        final double[] beratall = {0};
        Call<ResponseBody> call = apiServices.getlistChart(username);
        int finalTotal2 = total;
        final int[] deli = {0};
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
//                    Log.e("TEST ARRAY", response.body().string());
                    JSONArray array = new JSONArray(response.body().string());

                    double tot_akhir = 0;
                    double tot_akhir2 = 0.0;


                    for (int i = 0; i < array.length(); i++){


                        double price_sum = Double.parseDouble(array.getJSONObject(i).getString("total_harga"));
                        double weight_sum = Double.parseDouble(array.getJSONObject(i).getString("total_berat"));
                        String provinsi = array.getJSONObject(i).getString("provinsi");


                        if(provinsi.equals("West Java")){
                            deli[0] += 20000;
                        }else if (provinsi.equals("East Java")){
                            deli[0] += 15000;
                        }else if (provinsi.equals("Central Java")){
                            deli[0] += 22000;
                        }else if (provinsi.equals("DKI Jakarta")){
                            deli[0] += 13000;
                        }else {
                            deli[0] += 35000;
                        }

                        tot_akhir += price_sum;
                        tot_akhir2 += weight_sum;



                        double total_all = tot_akhir;
                        double total_allweight = tot_akhir2;

                        double bea_masuk = 0.0;
                        double ppn = 0.0;
                        double pph = 0.0;
                        double ppnbh = 0.0;

                        if(total_all > 5){

                            bea_masuk = total_all * 0.1;

                            ppn = total_all * 0.1;

                            pph = total_all * 0.075;

                            ppnbh = total_all * 0.1;

                        }

                        double all = total_all + bea_masuk + ppn + pph + ppnbh;
                        double bayar = all * finalTotal2;
                        Log.d("BAYARS", String.valueOf(bayar));
                        double feetran10 = bayar * 0.1;
                        double allweight = total_allweight / 1000;

                        bayarall[0] = bayar;
                        tranall[0] = feetran10;
                        beratall[0] = allweight;


                    }
                    Log.d("BAYAR_ALL", String.valueOf(bayarall[0]));
                    total_harga1.setText(String.format("%.0f",bayarall[0]));
                    transfee.setText(String.format("%.0f",tranall[0]));
                    total_weight.setText(String.valueOf(beratall[0]));
                    devfee.setText(String.valueOf(deli[0]));

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


        shipfee = findViewById(R.id.shipping_fee);
        total_harga2 = findViewById(R.id.total_harga2);
//        total_harga2.setText(String.valueOf(akhir_totprice));

        idcustomer = findViewById(R.id.keranjang_idcustomer);




        Retrofit ret = GetClient.getClient(ApiUtil.Base_url);
        ApiService apiService = ret.create(ApiService.class);
        Call<ResponseBody> ship = apiService.getShipping();
        int finalTotal = total;
        int finalTotal1 = total;
        ship.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (level.equals("PLATINUM")){
                    try {
                        JSONArray array = new JSONArray(response.body().string());

                        if (negara_ship.equals("Japan")){
                            String shippla1 = array.getJSONObject(0).getString("platinum");
                            double plaship = Double.parseDouble(shippla1);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));
                        }
                        else if (negara_ship.equals("Hongkong")){
                            String shippla2 = array.getJSONObject(1).getString("platinum");
                            double plaship = Double.parseDouble(shippla2);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));
                        }
                        else if (negara_ship.equals("Australia")){
                            String shippla3 = array.getJSONObject(2).getString("platinum");
                            double plaship = Double.parseDouble(shippla3);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));
                        }
                        else if (negara_ship.equals("Malaysia")){
                            String shippla4 = array.getJSONObject(3).getString("platinum");
                            double plaship = Double.parseDouble(shippla4);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));
                        }
                        else if (negara_ship.equals("Singapore")){
                            String shippla5 = array.getJSONObject(4).getString("platinum");
                            double plaship = Double.parseDouble(shippla5);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));
                        }
                        else if (negara_ship.equals("Korea Selatan")){
                            String shippla6 = array.getJSONObject(5).getString("platinum");
                            double plaship = Double.parseDouble(shippla6);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));
                        }
                        else if (negara_ship.equals("Thailand")){
                            String shippla7 = array.getJSONObject(6).getString("platinum");
                            double plaship = Double.parseDouble(shippla7);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if(level.equals("GOLD")){
                    try {
                        JSONArray array = new JSONArray(response.body().string());

                        if (negara_ship.equals("Japan")){
                            String shipgold1 = array.getJSONObject(0).getString("gold");
                            double plaship = Double.parseDouble(shipgold1);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));
                        }
                        else if (negara_ship.equals("Hongkong")){
                            String shipgold2 = array.getJSONObject(1).getString("gold");
                            double plaship = Double.parseDouble(shipgold2);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));
                        }
                        else if (negara_ship.equals("Australia")){
                            String shipgold3 = array.getJSONObject(2).getString("gold");
                            double plaship = Double.parseDouble(shipgold3);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));
                        }
                        else if (negara_ship.equals("Malaysia")){
                            String shipgold4 = array.getJSONObject(3).getString("gold");
                            double plaship = Double.parseDouble(shipgold4);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));
                        }
                        else if (negara_ship.equals("Singapore")){
                            String shipgold5 = array.getJSONObject(4).getString("gold");
                            double plaship = Double.parseDouble(shipgold5);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));
                        }
                        else if (negara_ship.equals("Korea Selatan")){
                            String shipgold6 = array.getJSONObject(5).getString("gold");
                            double plaship = Double.parseDouble(shipgold6);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));
                        }
                        else if (negara_ship.equals("Thailand")){
                            String shipgold7 = array.getJSONObject(6).getString("gold");
                            double plaship = Double.parseDouble(shipgold7);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if (level.equals("SILVER")){
                    try {
                        JSONArray array = new JSONArray(response.body().string());
                        if (negara_ship.equals("Japan")){
                            String shipsil1 = array.getJSONObject(0).getString("silver");
                            double plaship = Double.parseDouble(shipsil1);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));

                            String limit = sharedPrefManager.getSpLimit();

                            int limits = Integer.parseInt(limit);
                            double akhir_limit = limits - akhirfinal;
                            Log.d("Limit Member", String.valueOf(akhir_limit));
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_LIMIT, String.valueOf(Integer.parseInt(String.valueOf(akhir_limit))));
//                            memberlimit.setText(Integer.parseInt(String.valueOf(akhir_limit)));

                            if(akhirfinal > limits){
                                checkOUT.setEnabled(false);
                            }

                            String level = sharedPrefManager.getSpLevel();
                            String new_limit = String.valueOf(akhir_limit);
                            String id_agent = sharedPrefManager.getSpIdmember();

                            updateLimit(id_agent, new_limit, level);


                        }
                        else if (negara_ship.equals("Hongkong")){
                            String shipsil2 = array.getJSONObject(1).getString("silver");
                            double plaship = Double.parseDouble(shipsil2);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));

                            String limit = sharedPrefManager.getSpLimit();

                            int limits = Integer.parseInt(limit);
                            double akhir_limit = limits - akhirfinal;
                            Log.d("Limit Member", String.valueOf(akhir_limit));
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_LIMIT, String.valueOf(Integer.parseInt(String.valueOf(akhir_limit))));
//                            memberlimit.setText(Integer.parseInt(String.valueOf(akhir_limit)));

                            if(akhirfinal > limits){
                                checkOUT.setEnabled(false);
                            }

                            String level = sharedPrefManager.getSpLevel();
                            String new_limit = String.valueOf(akhir_limit);
                            String id_agent = sharedPrefManager.getSpIdmember();

                            updateLimit(id_agent, new_limit, level);
                        }
                        else if (negara_ship.equals("Australia")){
                            String shipsil3 = array.getJSONObject(2).getString("silver");
                            double plaship = Double.parseDouble(shipsil3);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));

                            String limit = sharedPrefManager.getSpLimit();

                            int limits = Integer.parseInt(limit);
                            double akhir_limit = limits - akhirfinal;
                            Log.d("Limit Member", String.valueOf(akhir_limit));
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_LIMIT, String.valueOf(Integer.parseInt(String.valueOf(akhir_limit))));
//                            memberlimit.setText(Integer.parseInt(String.valueOf(akhir_limit)));

                            if(akhirfinal > limits){
                                checkOUT.setEnabled(false);
                            }

                            String level = sharedPrefManager.getSpLevel();
                            String new_limit = String.valueOf(akhir_limit);
                            String id_agent = sharedPrefManager.getSpIdmember();

                            updateLimit(id_agent, new_limit, level);
                        }
                        else if (negara_ship.equals("Malaysia")){
                            String shipsil4 = array.getJSONObject(3).getString("silver");
                            double plaship = Double.parseDouble(shipsil4);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));

                            String limit = sharedPrefManager.getSpLimit();

                            int limits = Integer.parseInt(limit);
                            double akhir_limit = limits - akhirfinal;
                            Log.d("Limit Member", String.valueOf(akhir_limit));
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_LIMIT, String.valueOf(Integer.parseInt(String.valueOf(akhir_limit))));
//                            memberlimit.setText(Integer.parseInt(String.valueOf(akhir_limit)));

                            if(akhirfinal > limits){
                                checkOUT.setEnabled(false);
                            }

                            String level = sharedPrefManager.getSpLevel();
                            String new_limit = String.valueOf(akhir_limit);
                            String id_agent = sharedPrefManager.getSpIdmember();

                            updateLimit(id_agent, new_limit, level);
                        }
                        else if (negara_ship.equals("Singapore")){
                            String shipsil5 = array.getJSONObject(4).getString("silver");
                            double plaship = Double.parseDouble(shipsil5);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));

                            String limit = sharedPrefManager.getSpLimit();

                            int limits = Integer.parseInt(limit);
                            double akhir_limit = limits - akhirfinal;
                            Log.d("Limit Member", String.valueOf(akhir_limit));
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_LIMIT, String.valueOf(Integer.parseInt(String.valueOf(akhir_limit))));
//                            memberlimit.setText(Integer.parseInt(String.valueOf(akhir_limit)));

                            if(akhirfinal > limits){
                                checkOUT.setEnabled(false);
                            }

                            String level = sharedPrefManager.getSpLevel();
                            String new_limit = String.valueOf(akhir_limit);
                            String id_agent = sharedPrefManager.getSpIdmember();

                            updateLimit(id_agent, new_limit, level);
                        }
                        else if (negara_ship.equals("Korea Selatan")){
                            String shipsil6 = array.getJSONObject(5).getString("silver");
                            double plaship = Double.parseDouble(shipsil6);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));

                            String limit = sharedPrefManager.getSpLimit();

                            int limits = Integer.parseInt(limit);
                            double akhir_limit = limits - akhirfinal;
                            Log.d("Limit Member", String.valueOf(akhir_limit));
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_LIMIT, String.valueOf(Integer.parseInt(String.valueOf(akhir_limit))));
//                            memberlimit.setText(Integer.parseInt(String.valueOf(akhir_limit)));

                            if(akhirfinal > limits){
                                checkOUT.setEnabled(false);
                            }

                            String level = sharedPrefManager.getSpLevel();
                            String new_limit = String.valueOf(akhir_limit);
                            String id_agent = sharedPrefManager.getSpIdmember();

                            updateLimit(id_agent, new_limit, level);
                        }
                        else if (negara_ship.equals("Thailand")){
                            String shipsil7 = array.getJSONObject(6).getString("silver");
                            double plaship = Double.parseDouble(shipsil7);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));

                            String limit = sharedPrefManager.getSpLimit();

                            int limits = Integer.parseInt(limit);
                            double akhir_limit = limits - akhirfinal;
                            Log.d("Limit Member", String.valueOf(akhir_limit));
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_LIMIT, String.valueOf(Integer.parseInt(String.valueOf(akhir_limit))));
//                            memberlimit.setText(Integer.parseInt(String.valueOf(akhir_limit)));

                            if(akhirfinal > limits){
                                checkOUT.setEnabled(false);
                            }

                            String level = sharedPrefManager.getSpLevel();
                            String new_limit = String.valueOf(akhir_limit);
                            String id_agent = sharedPrefManager.getSpIdmember();

                            updateLimit(id_agent, new_limit, level);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if (level.equals("BRONZE")){
                    try {
                        JSONArray array = new JSONArray(response.body().string());
                        if (negara_ship.equals("Japan")){
                            String shipsil1 = array.getJSONObject(0).getString("bronze");
                            double plaship = Double.parseDouble(shipsil1);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));

                            String limit = sharedPrefManager.getSpLimit();

                            int limits = Integer.parseInt(limit);
                            double akhir_limit = limits - akhirfinal;
                            Log.d("Limit Member", String.valueOf(akhir_limit));
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_LIMIT, String.valueOf(Integer.parseInt(String.valueOf(akhir_limit))));
//                            memberlimit.setText(Integer.parseInt(String.valueOf(akhir_limit)));

                            if(akhirfinal > limits){
                                checkOUT.setEnabled(false);
                            }

                            String level = sharedPrefManager.getSpLevel();
                            String new_limit = String.valueOf(akhir_limit);
                            String id_agent = sharedPrefManager.getSpIdmember();

                            updateLimit(id_agent, new_limit, level);


                        }
                        else if (negara_ship.equals("Hongkong")){
                            String shipsil2 = array.getJSONObject(1).getString("bronze");
                            double plaship = Double.parseDouble(shipsil2);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));

                            String limit = sharedPrefManager.getSpLimit();

                            int limits = Integer.parseInt(limit);
                            double akhir_limit = limits - akhirfinal;
                            Log.d("Limit Member", String.valueOf(akhir_limit));
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_LIMIT, String.valueOf(Integer.parseInt(String.valueOf(akhir_limit))));
//                            memberlimit.setText(Integer.parseInt(String.valueOf(akhir_limit)));

                            if(akhirfinal > limits){
                                checkOUT.setEnabled(false);
                            }

                            String level = sharedPrefManager.getSpLevel();
                            String new_limit = String.valueOf(akhir_limit);
                            String id_agent = sharedPrefManager.getSpIdmember();

                            updateLimit(id_agent, new_limit, level);
                        }
                        else if (negara_ship.equals("Australia")){
                            String shipsil3 = array.getJSONObject(2).getString("bronze");
                            double plaship = Double.parseDouble(shipsil3);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));

                            String limit = sharedPrefManager.getSpLimit();

                            int limits = Integer.parseInt(limit);
                            double akhir_limit = limits - akhirfinal;
                            Log.d("Limit Member", String.valueOf(akhir_limit));
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_LIMIT, String.valueOf(Integer.parseInt(String.valueOf(akhir_limit))));
//                            memberlimit.setText(Integer.parseInt(String.valueOf(akhir_limit)));

                            if(akhirfinal > limits){
                                checkOUT.setEnabled(false);
                            }

                            String level = sharedPrefManager.getSpLevel();
                            String new_limit = String.valueOf(akhir_limit);
                            String id_agent = sharedPrefManager.getSpIdmember();

                            updateLimit(id_agent, new_limit, level);
                        }
                        else if (negara_ship.equals("Malaysia")){
                            String shipsil4 = array.getJSONObject(3).getString("bronze");
                            double plaship = Double.parseDouble(shipsil4);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));

                            String limit = sharedPrefManager.getSpLimit();

                            int limits = Integer.parseInt(limit);
                            double akhir_limit = limits - akhirfinal;
                            Log.d("Limit Member", String.valueOf(akhir_limit));
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_LIMIT, String.valueOf(Integer.parseInt(String.valueOf(akhir_limit))));
//                            memberlimit.setText(Integer.parseInt(String.valueOf(akhir_limit)));

                            if(akhirfinal > limits){
                                checkOUT.setEnabled(false);
                            }

                            String level = sharedPrefManager.getSpLevel();
                            String new_limit = String.valueOf(akhir_limit);
                            String id_agent = sharedPrefManager.getSpIdmember();

                            updateLimit(id_agent, new_limit, level);
                        }
                        else if (negara_ship.equals("Singapore")){
                            String shipsil5 = array.getJSONObject(4).getString("bronze");
                            double plaship = Double.parseDouble(shipsil5);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));

                            String limit = sharedPrefManager.getSpLimit();

                            int limits = Integer.parseInt(limit);
                            double akhir_limit = limits - akhirfinal;
                            Log.d("Limit Member", String.valueOf(akhir_limit));
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_LIMIT, String.valueOf(Integer.parseInt(String.valueOf(akhir_limit))));
//                            memberlimit.setText(Integer.parseInt(String.valueOf(akhir_limit)));

                            if(akhirfinal > limits){
                                checkOUT.setEnabled(false);
                            }

                            String level = sharedPrefManager.getSpLevel();
                            String new_limit = String.valueOf(akhir_limit);
                            String id_agent = sharedPrefManager.getSpIdmember();

                            updateLimit(id_agent, new_limit, level);
                        }
                        else if (negara_ship.equals("Korea Selatan")){
                            String shipsil6 = array.getJSONObject(5).getString("bronze");
                            double plaship = Double.parseDouble(shipsil6);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));

                            String limit = sharedPrefManager.getSpLimit();

                            int limits = Integer.parseInt(limit);
                            double akhir_limit = limits - akhirfinal;
                            Log.d("Limit Member", String.valueOf(akhir_limit));
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_LIMIT, String.valueOf(Integer.parseInt(String.valueOf(akhir_limit))));
//                            memberlimit.setText(Integer.parseInt(String.valueOf(akhir_limit)));

                            if(akhirfinal > limits){
                                checkOUT.setEnabled(false);
                            }

                            String level = sharedPrefManager.getSpLevel();
                            String new_limit = String.valueOf(akhir_limit);
                            String id_agent = sharedPrefManager.getSpIdmember();

                            updateLimit(id_agent, new_limit, level);
                        }
                        else if (negara_ship.equals("Thailand")){
                            String shipsil7 = array.getJSONObject(6).getString("bronze");
                            double plaship = Double.parseDouble(shipsil7);
                            double akhir_plaship1 = beratall[0] * plaship;
                            double akhir_plaship2 = finalTotal * akhir_plaship1;
                            double akhir_th1 = finalTotal1 * bayarall[0];
                            double fee10 = akhir_th1 * 0.1;
                            double akhir_totprice = bayarall[0] + tranall[0];
                            double akhir_sumprice = akhir_totprice + akhir_plaship2;
                            double akhirfinal = akhir_sumprice + deli[0];
                            shipfee.setText(String.valueOf(akhir_plaship2));
                            total_harga2.setText(String.format("%.0f",akhirfinal));

                            String limit = sharedPrefManager.getSpLimit();

                            int limits = Integer.parseInt(limit);
                            double akhir_limit = limits - akhirfinal;
                            Log.d("Limit Member", String.valueOf(akhir_limit));
                            sharedPrefManager.saveSPString(SharedPrefManager.SP_LIMIT, String.valueOf(Integer.parseInt(String.valueOf(akhir_limit))));
//                            memberlimit.setText(Integer.parseInt(String.valueOf(akhir_limit)));

                            if(akhirfinal > limits){
                                checkOUT.setEnabled(false);
                            }

                            String level = sharedPrefManager.getSpLevel();
                            String new_limit = String.valueOf(akhir_limit);
                            String id_agent = sharedPrefManager.getSpIdmember();

                            updateLimit(id_agent, new_limit, level);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        checkOUT = findViewById(R.id.checkout);
        checkOUT.setOnClickListener(this);
    }

    private void getCart(final RecyclerView rvlist, String id) {
        Retrofit retrofit = GetClient.getClient(ApiUtil.Base_url);
        ProductService apiService = retrofit.create(ProductService.class);

        Call<ArrayList<DataAll>> call = apiService.getKeranjang(id);
        call.enqueue(new Callback<ArrayList<DataAll>>() {
            @Override
            public void onResponse(Call<ArrayList<DataAll>> call, Response<ArrayList<DataAll>> response) {
                AllAdapter allAdapter= new AllAdapter(getApplication(), response.body());
                rvlist.setLayoutManager(new LinearLayoutManager(getApplication()));
                rvlist.setAdapter(allAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<DataAll>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }

//    private String getDateTime() {
//        SimpleDateFormat dateFormat = new SimpleDateFormat(
//                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//        Date date = new Date();
//        return dateFormat.format(date);
//    }

    @Override
    public void onClick(View view) {
        Date date = new Date();
        String waktu = String.valueOf(date);
        String is_finish = "no";
        String idagent = sharedPrefManager.getSpIdmember();
        addTransaction(
                waktu,
                total_harga2.getText().toString(),
                total_weight.getText().toString(),
                is_finish,
                idagent,
                transfee.getText().toString(),
                shipfee.getText().toString(),
                total_item.getText().toString());


    }

    private void updateLimit(String idagent,
                             String limit_member,
                             String level) {
        Retrofit retrofitlimit = GetClient.getClient(ApiUtil.Base_url);
        ApiService apiServicelimit = retrofitlimit.create(ApiService.class);
        Call<ResponseBody> memberLimit = apiServicelimit.updateLimit(idagent, limit_member, level);
        memberLimit.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    private void addTransaction(String waktu,
                                    String total_bayar,
                                    String total_berat,
                                    String is_finish,
                                    String agentid,
                                    String transactionfee,
                                    String shippingfee,
                                    String total_barang) {
        Retrofit rfit = GetClient.getClient(ApiUtil.Base_url);
        ApiService as = rfit.create(ApiService.class);
        Call<ResponseBody> addtran = as.addTransaction(waktu, total_bayar, total_berat, is_finish, agentid, transactionfee, shippingfee, total_barang);
        addtran.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String username = sharedPrefManager.getSPNama();

                try {
//                    Log.d("ARRAY LOL",response.body().string());
                    JSONObject obj = new JSONObject(response.body().string());

                    String id = obj.getString("newId");
//                    String id_agent = obj.getString("id_agent");
//                    Toast.makeText(getApplication(), id, Toast.LENGTH_SHORT).show();

                    Retrofit retrofitss = GetClient.getClient(ApiUtil.Base_url);
                    ApiService apiServicess = retrofitss.create(ApiService.class);
                    Call<ResponseBody> chart = apiServicess.getlistChart(username);
                    chart.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            try {
                                JSONArray array = new JSONArray(response.body().string());

                                for (int i = 0; i < array.length(); i++){

                                    String id_cus = array.getJSONObject(i).getString("id_customer");
                                    String id_agent = sharedPrefManager.getSpIdmember();
                                    String id_produk = array.getJSONObject(i).getString("id_produk");
                                    String nama_produk = array.getJSONObject(i).getString("nama_produk");
                                    String harga = array.getJSONObject(i).getString("harga");
                                    String jumlah_beli = array.getJSONObject(i).getString("jumlah_beli");
                                    String total_harga = array.getJSONObject(i).getString("total_harga");
                                    String catatan = array.getJSONObject(i).getString("catatan");
                                    String bpom = array.getJSONObject(i).getString("bpom");
                                    String id_negara = array.getJSONObject(i).getString("id_negara");
                                    String id_kategori = array.getJSONObject(i).getString("kategori");
                                    String berat = array.getJSONObject(i).getString("berat");
                                    String tot_berat = array.getJSONObject(i).getString("total_berat");
                                    Date date = new Date();
                                    String waktu2 = String.valueOf(date);

                                    ApiService apiService = ApiUtil.getPaketService();

                                    Call<ResponseBody> detail = apiService.addDetailTransaction(
                                            id,
                                            id_agent,
                                            id_cus,
                                            id_produk,
                                            nama_produk,
                                            harga,
                                            jumlah_beli,
                                            total_harga,
                                            catatan,
                                            bpom,
                                            id_negara,
                                            id_kategori,
                                            berat,
                                            tot_berat,
                                            waktu2);

                                    detail.enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                            if(response.isSuccessful()){

                                                clearCart(id_agent);
                                                Intent intent = new Intent(getApplication(), MainActivity.class);
                                                startActivity(intent);
                                                Toast.makeText(getApplication(), "Checkout Success", Toast.LENGTH_SHORT).show();
                                                finish();

                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                Log.d("ERROR_NJER", t.getMessage());
                                        }
                                    });

                                }




                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
//                    Call<ResponseBody> detail = apiServices.addDetailTransaction()



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

//    private void addDetailTransaction(String id_customer,
//                                      String id_transaksi,
//                                      String id_produk,
//                                      String nama_produk,
//                                      String harga_produk,
//                                      String jumlah_beli,
//                                      String total_harga,
//                                      String catatan,
//                                      String bpom) {
//        Retrofit retrofit2 = GetClient.getClient(ApiUtil.Base_url);
//        ApiService apiService2 = retrofit2.create(ApiService.class);
//        Call<ResponseBody> adddetailtran = apiService2.addDetailTransaction(id_customer, id_transaksi, id_produk, nama_produk, harga_produk, jumlah_beli, total_harga, catatan, bpom);
//        adddetailtran.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
//    }

    private void clearCart(String idagent) {
        Retrofit retrofit3 = GetClient.getClient(ApiUtil.Base_url);
        ApiService apiService3 = retrofit3.create(ApiService.class);
        Call<ResponseBody> clearcart = apiService3.clearCart(idagent);
        clearcart.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
