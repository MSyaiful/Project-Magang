package com.warehousenesia.id;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.warehousenesia.id.Model.DataOrder;
import com.warehousenesia.id.Service.ApiService;
import com.warehousenesia.id.Service.ApiUtil;
import com.warehousenesia.id.Service.GetClient;
import com.warehousenesia.id.adapter.OrderDetailAdapter1;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailOrder extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);

        sharedPrefManager = new SharedPrefManager(getApplication());

        recyclerView = findViewById(R.id.order_customer);
        getOrderCustomer(recyclerView);

    }

    private void getOrderCustomer(RecyclerView recyclerView) {
        Retrofit retrofit = GetClient.getClient(ApiUtil.Base_url);
        ApiService apiService = retrofit.create(ApiService.class);

        String id_transaksi = sharedPrefManager.getSP_IdTransaksi();

        Call<ArrayList<DataOrder>> data = apiService.getOrderDetail(id_transaksi);
        data.enqueue(new Callback<ArrayList<DataOrder>>() {
            @Override
            public void onResponse(Call<ArrayList<DataOrder>> call, Response<ArrayList<DataOrder>> response) {
                OrderDetailAdapter1 orderDetailAdapter1 = new OrderDetailAdapter1(getApplication(), response.body());
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
                recyclerView.setAdapter(orderDetailAdapter1);
            }

            @Override
            public void onFailure(Call<ArrayList<DataOrder>> call, Throwable t) {

            }
        });

    }
}
