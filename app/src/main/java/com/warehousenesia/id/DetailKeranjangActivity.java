package com.warehousenesia.id;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.warehousenesia.id.Model.DataAll;
import com.warehousenesia.id.Service.ApiUtil;
import com.warehousenesia.id.Service.GetClient;
import com.warehousenesia.id.Service.ProductService;
import com.warehousenesia.id.adapter.MYcartAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailKeranjangActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    TextView tot_item, tot_berat, tot_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_keranjang);
        sharedPrefManager = new SharedPrefManager(getApplication());

        RecyclerView rv_keranjang = findViewById(R.id.rv_keranjang);
        getKeranjangDetail(rv_keranjang);

        tot_item = findViewById(R.id.dettotal_item);
        tot_item.setText(sharedPrefManager.getSP_TotalItemKeranjang2());

        tot_berat = findViewById(R.id.dettotal_weight);
        String weight = sharedPrefManager.getSP_TotalWeightKeranjang2();
        double tw = Double.parseDouble(weight);
        double akhir_tw = tw / 1000;
        tot_berat.setText(String.valueOf(akhir_tw));

        tot_price = findViewById(R.id.dettotal_harga1);
        tot_price.setText(sharedPrefManager.getSP_TotalHargaKeranjang2());
        
    }

    private void getKeranjangDetail(RecyclerView rv_keranjang) {
        Retrofit retrofit = GetClient.getClient(ApiUtil.Base_url);
        String id_customer = getIntent().getStringExtra("k_idcustomer");
        ProductService productService = retrofit.create(ProductService.class);
        Call<ArrayList<DataAll>> call = productService.getListKeranjang(id_customer);
        call.enqueue(new Callback<ArrayList<DataAll>>() {
            @Override
            public void onResponse(Call<ArrayList<DataAll>> call, Response<ArrayList<DataAll>> response) {
                MYcartAdapter mYcartAdapter = new MYcartAdapter(getApplication(), response.body());
                rv_keranjang.setLayoutManager(new LinearLayoutManager(getApplication()));
                rv_keranjang.setAdapter(mYcartAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<DataAll>> call, Throwable t) {
                t.getMessage();
            }
        });
    }
}
