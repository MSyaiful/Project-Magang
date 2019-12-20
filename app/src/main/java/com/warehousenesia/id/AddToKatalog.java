package com.warehousenesia.id;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.warehousenesia.id.Model.DataCategory;
import com.warehousenesia.id.Service.ApiService;
import com.warehousenesia.id.Service.ApiUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddToKatalog extends AppCompatActivity {
    EditText idProduk, namaProduk, harga, berat, deskripsi;
    Spinner spinner;
    List<DataCategory> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_katalog);

        spinner = findViewById(R.id.kategori);

        ApiService apiService = ApiUtil.getPaketService();

        DataCategory dataCategory1 = new DataCategory("", "", "Pilih Kategori");

        list.add(dataCategory1);
        Call<ArrayList<DataCategory>> call = apiService.getCategory();
        call.enqueue(new Callback<ArrayList<DataCategory>>() {
            @Override
            public void onResponse(Call<ArrayList<DataCategory>> call, Response<ArrayList<DataCategory>> response) {
                list.addAll(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<DataCategory>> call, Throwable t) {

            }
        });

        ArrayAdapter<DataCategory> adapter = new ArrayAdapter<DataCategory>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        idProduk = findViewById(R.id.id_produk);
        namaProduk = findViewById(R.id.nama_produk);
        harga = findViewById(R.id.harga);
        berat = findViewById(R.id.berat);
        deskripsi = findViewById(R.id.deskripsi);

        idProduk.setText(getIntent().getStringExtra("id_produk"));
        idProduk.setFocusable(false);
        idProduk.setBackgroundColor(Color.LTGRAY);
        namaProduk.setText(getIntent().getStringExtra("nama_barang"));



    }
}
