package com.warehousenesia.id;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.warehousenesia.id.Model.DataOrder;
import com.warehousenesia.id.Service.ApiService;
import com.warehousenesia.id.Service.ApiUtil;
import com.warehousenesia.id.Service.GetClient;
import com.warehousenesia.id.adapter.OrderAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Order extends AppCompatActivity {
    private RecyclerView recyclerView;
    SharedPrefManager sharedPrefManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        sharedPrefManager = new SharedPrefManager(getApplication());

        recyclerView = findViewById(R.id.order);
        getOrder(recyclerView);
        
    }

    private void getOrder(RecyclerView recyclerView) {
        Retrofit retrofit = GetClient.getClient(ApiUtil.Base_url);
        ApiService apiService = retrofit.create(ApiService.class);

        String username = sharedPrefManager.getSPNama();

        Call<ArrayList<DataOrder>> data = apiService.getOrder(username);
        data.enqueue(new Callback<ArrayList<DataOrder>>() {
            @Override
            public void onResponse(Call<ArrayList<DataOrder>> call, Response<ArrayList<DataOrder>> response) {
                OrderAdapter orderAdapter = new OrderAdapter(getApplication(), response.body());
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplication()));
                recyclerView.setAdapter(orderAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<DataOrder>> call, Throwable t) {

            }
        });
    }
}
