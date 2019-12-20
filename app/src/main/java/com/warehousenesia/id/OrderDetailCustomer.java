package com.warehousenesia.id;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.warehousenesia.id.Model.DataOrderDetailCustomer;
import com.warehousenesia.id.Service.ApiService;
import com.warehousenesia.id.Service.ApiUtil;
import com.warehousenesia.id.Service.GetClient;
import com.warehousenesia.id.adapter.OrderDetailCustomerAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrderDetailCustomer extends AppCompatActivity {
    private RecyclerView recyclerView;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_customer);
        sharedPrefManager  = new SharedPrefManager(getApplication());

        recyclerView = findViewById(R.id.order_detail);
        getOrderDetail(recyclerView);

    }

    private void getOrderDetail(RecyclerView recyclerView) {
        Retrofit retrofit = GetClient.getClient(ApiUtil.Base_url);
        ApiService apiService = retrofit.create(ApiService.class);

        String id_customer = getIntent().getStringExtra("id_customer");
        String id_transaksi = getIntent().getStringExtra("id_transaksi");

        Call<ArrayList<DataOrderDetailCustomer>> call = apiService.getDetailOrderCustomer(id_customer, id_transaksi);
        call.enqueue(new Callback<ArrayList<DataOrderDetailCustomer>>() {
            @Override
            public void onResponse(Call<ArrayList<DataOrderDetailCustomer>> call, Response<ArrayList<DataOrderDetailCustomer>> response) {
                OrderDetailCustomerAdapter orderDetailCustomerAdapter = new OrderDetailCustomerAdapter(getApplication(), response.body());
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
                recyclerView.setAdapter(orderDetailCustomerAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<DataOrderDetailCustomer>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }
}
