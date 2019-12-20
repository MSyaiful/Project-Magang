package com.warehousenesia.id;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.warehousenesia.id.Model.DataCustomer;
import com.warehousenesia.id.Service.ApiService;
import com.warehousenesia.id.Service.ApiUtil;
import com.warehousenesia.id.Service.GetClient;
import com.warehousenesia.id.adapter.CustomerAdapter;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Customer extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rvlist;
    SharedPrefManager sharedPrefManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        sharedPrefManager = new SharedPrefManager(getApplication());

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        rvlist = findViewById(R.id.customer);
        getCustomer(rvlist);

        Button button = findViewById(R.id.ExcelWrite);
        button.setOnClickListener(this);

        FloatingActionButton fab = findViewById(R.id.add_customer);
        fab.setOnClickListener(view -> {
            finish();
            Intent intent = new Intent(getApplication(), AddCustomer.class);
            startActivity(intent);
        });

        swipeRefreshLayout.setOnRefreshListener(() -> new Handler().postDelayed(() -> {

            getCustomer(rvlist);
            swipeRefreshLayout.setRefreshing(false);

        }, 2500));

    }

    private void getCustomer(final RecyclerView rvlist) {
        Retrofit retrofit = GetClient.getClient(ApiUtil.Base_url);
        ApiService apiService = retrofit.create(ApiService.class);

        String username = sharedPrefManager.getSPNama();

        Call<ArrayList<DataCustomer>> data = apiService.getCustomer(username);
        data.enqueue(new Callback<ArrayList<DataCustomer>>() {
            @Override
            public void onResponse(Call<ArrayList<DataCustomer>> call, Response<ArrayList<DataCustomer>> response) {
                CustomerAdapter customerAdapter = new CustomerAdapter(getApplication(), response.body());
                rvlist.setLayoutManager(new LinearLayoutManager(getApplication()));
                rvlist.setAdapter(customerAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<DataCustomer>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {

        Retrofit retrofit = GetClient.getClient(ApiUtil.Base_url);
        ApiService apiService = retrofit.create(ApiService.class);

        String username = sharedPrefManager.getSPNama();

        Call<ArrayList<DataCustomer>> datas = apiService.getCustomer(username);
        datas.enqueue(new Callback<ArrayList<DataCustomer>>() {
            @Override
            public void onResponse(Call<ArrayList<DataCustomer>> call, Response<ArrayList<DataCustomer>> response) {
                if (response.body().size() > 0) {

                    Workbook wb = new HSSFWorkbook();
                    Sheet sheet = wb.createSheet("Customer");
                    sheet.setColumnWidth(0, (10 * 500));
                    for (int i = 0; i < response.body().size(); i++) {

                        Row row = sheet.createRow(i);
                        row.createCell(0).setCellValue(String.valueOf(response.body().get(i)));

                    }

                    File file = new File(getExternalFilesDir(null), "Customer.xls");
                    FileOutputStream outputStream = null;

                    try {

                        outputStream = new FileOutputStream(file);
                        wb.write(outputStream);
                        Toast.makeText(getApplication(), "Export Successfull", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {

                        e.printStackTrace();
                        Toast.makeText(getApplication(), "Export Failed", Toast.LENGTH_SHORT).show();

                        try {

                            outputStream.close();

                        } catch (Exception ex) {

                            ex.printStackTrace();

                        }
                    }

                }

            }

            @Override
            public void onFailure(Call<ArrayList<DataCustomer>> call, Throwable t) {

            }
        });

    }
}
