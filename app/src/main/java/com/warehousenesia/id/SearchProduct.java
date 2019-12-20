package com.warehousenesia.id;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.warehousenesia.id.Model.DataProductSearch;
import com.warehousenesia.id.Service.ApiService;
import com.warehousenesia.id.Service.ApiUtil;
import com.warehousenesia.id.Service.GetClient;
import com.warehousenesia.id.Service.ProductService;
import com.warehousenesia.id.adapter.SearchProductAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchProduct extends AppCompatActivity {
    private RecyclerView rvlist;
    SharedPrefManager sharedPrefManager;
    EditText editText;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        sharedPrefManager = new SharedPrefManager(getApplication());
        rvlist = findViewById(R.id.recyclerView);

        editText = findViewById(R.id.editText);
        imageView = findViewById(R.id.img);
        imageView.setOnClickListener(view -> {
            String nama = editText.getText().toString();
            getSearch(rvlist, nama);
        });
    }

    private void getSearch(final RecyclerView rvlist, String nama) {

        Retrofit retrofit = GetClient.getClient(ApiUtil.Base_url);
        ApiService apiService = retrofit.create(ApiService.class);

        String nama_product = nama;

        Call<ArrayList<DataProductSearch>> search = apiService.SearchProduct(nama_product);
        search.enqueue(new Callback<ArrayList<DataProductSearch>>() {
            @Override
            public void onResponse(Call<ArrayList<DataProductSearch>> call, Response<ArrayList<DataProductSearch>> response) {
                SearchProductAdapter searchProductAdapter = new SearchProductAdapter(getApplication(), response.body());
                rvlist.setLayoutManager(new GridLayoutManager(getApplication(), 2));
                rvlist.setAdapter(searchProductAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<DataProductSearch>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });

    }

}
