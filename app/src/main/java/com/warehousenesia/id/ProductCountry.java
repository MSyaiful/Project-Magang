package com.warehousenesia.id;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.warehousenesia.id.Model.DataProduct;
import com.warehousenesia.id.Service.ApiService;
import com.warehousenesia.id.Service.ApiUtil;
import com.warehousenesia.id.Service.GetClient;
import com.warehousenesia.id.Service.ProductService;
import com.warehousenesia.id.adapter.ProductAdapter;
import com.warehousenesia.id.adapter.ProductNegaraAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductCountry extends AppCompatActivity {
    CardView search_product;
    private RecyclerView rvlist;
    SharedPrefManager sharedPrefManager;
    TextView idnegara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_country);

        sharedPrefManager = new SharedPrefManager(getApplication());

        rvlist = findViewById(R.id.productBy_country);
        getProduct(rvlist);

        idnegara = findViewById(R.id.idnegara);
        idnegara.setText(getIntent().getStringExtra("id"));

//        Retrofit retrofit = GetClient.getClient(ApiUtil.Base_url);
//        ApiService apiService = retrofit.create(ApiService.class);
//
//        Call<ArrayList<DataProduct>> call = apiService.getProductNegara("id_negara");
//        call.enqueue(new Callback<ArrayList<DataProduct>>() {
//            @Override
//            public void onResponse(Call<ArrayList<DataProduct>> call, Response<ArrayList<DataProduct>> response) {
//                try {
//                    JSONObject object = new JSONObject(response.body().toString());
//                    String idnegara = object.getString("id_negara");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<DataProduct>> call, Throwable t) {
//
//            }
//        });

//        search_product = findViewById(R.id.search);
//        search_product.setOnClickListener(view -> {
//            Intent intent = new Intent(getApplication(), SearchCategory.class);
//            startActivity(intent);
//        });

    }

    private void getProduct(final RecyclerView recyclerView){
        Retrofit retrofit = GetClient.getClient(ApiUtil.Base_url);
        ApiService apiService = retrofit.create(ApiService.class);
        idnegara = findViewById(R.id.idnegara);
        idnegara.setText(getIntent().getStringExtra("id"));

        String negara = getIntent().getStringExtra("negara");

        Call<ArrayList<DataProduct>> data = apiService.getProductNegara(negara);
        data.enqueue(new Callback<ArrayList<DataProduct>>() {
            @Override
            public void onResponse(Call<ArrayList<DataProduct>> call, @NonNull Response<ArrayList<DataProduct>> response) {
                ProductNegaraAdapter productNegaraAdapter = new ProductNegaraAdapter(getApplication(), response.body());
                recyclerView.setLayoutManager(new GridLayoutManager(getApplication(), 2));
                recyclerView.setAdapter(productNegaraAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<DataProduct>> call, Throwable t) {
                Log.e("ERROR", t.getMessage() );
            }
        });
    }
}
