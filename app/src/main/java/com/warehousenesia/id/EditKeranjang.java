package com.warehousenesia.id;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.warehousenesia.id.Service.ApiService;
import com.warehousenesia.id.Service.ApiUtil;
import com.warehousenesia.id.Service.GetClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditKeranjang extends AppCompatActivity implements View.OnClickListener {
    TextView name_customer, name_product, weight, currency, dollar, rupiah, tax, total_price, total_weight, origin, idagent, idcustomer, idproduk, id_keranjang;
    EditText quantity, catatan;
    Button s_keranjang;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_keranjang);
        sharedPrefManager = new SharedPrefManager(getApplication());

        name_customer = findViewById(R.id.customer_name);
        name_product = findViewById(R.id.product_name);
        weight = findViewById(R.id.product_weight);
        currency = findViewById(R.id.product_currency);
        dollar = findViewById(R.id.product_dollar);
        rupiah = findViewById(R.id.product_rupiah);
        tax = findViewById(R.id.product_tax);
        quantity = findViewById(R.id.product_quantity);
        total_price = findViewById(R.id.product_totalprice);
        total_weight = findViewById(R.id.product_totalweight);
        origin = findViewById(R.id.product_origin);
        catatan = findViewById(R.id.product_catatan);
        idagent = findViewById(R.id.det_idagent);
        idcustomer = findViewById(R.id.det_idcustomer);
        idproduk = findViewById(R.id.det_idproduk);
        id_keranjang = findViewById(R.id.det_idkeranjang);
        s_keranjang = findViewById(R.id.save_keranjang);

        name_customer.setText(sharedPrefManager.getSP_NCProduk());
        name_product.setText(sharedPrefManager.getSP_NamaProduk());
        weight.setText(sharedPrefManager.getSP_BeratProduk());
        currency.setText(sharedPrefManager.getSP_CurrencyProduk());
        dollar.setText(sharedPrefManager.getSP_HargaProduk());
        rupiah.setText(sharedPrefManager.getSP_Rupiah());
        tax.setText(sharedPrefManager.getSP_Tax());
        quantity.setText(sharedPrefManager.getSP_JumlahBeli());
        total_price.setText(sharedPrefManager.getSP_TotalHarga());
        total_weight.setText(sharedPrefManager.getSP_TotalBerat());
        origin.setText(sharedPrefManager.getSP_Origin());
        catatan.setText(sharedPrefManager.getSP_Catatan());
        idagent.setText(sharedPrefManager.getSP_IDAProduk());
        idcustomer.setText(sharedPrefManager.getSP_IDCProduk());
        idproduk.setText(sharedPrefManager.getSP_IdProduk());
        id_keranjang.setText(sharedPrefManager.getSP_IdKeranjang());

        s_keranjang.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String hrg_pro = sharedPrefManager.getSP_HargaProduk();
        String brt_pro = sharedPrefManager.getSP_BeratProduk();
        Integer qua_product = Integer.parseInt(quantity.getText().toString());
        double product_price = Double.parseDouble(hrg_pro);
        double weight_pro = Integer.parseInt(brt_pro);
        double total_price = qua_product * product_price;
        double total_weight = weight_pro * qua_product;
        editQuantity(quantity.getText().toString(),
                String.valueOf(total_weight),
                String.valueOf(total_price));
        editCatatan(catatan.getText().toString());
        finish();
        Intent intent = new Intent(getApplication(), MainActivity.class);
        startActivity(intent);
        Toast.makeText(getApplication(), "Edit Keranjang Berhasil", Toast.LENGTH_SHORT).show();
    }

    private void editQuantity(String jumlah_beli, String tot_weight, String tot_price) {
        String id_cuspro = sharedPrefManager.getSP_IDCProduk();
        String id_agepro = sharedPrefManager.getSP_IDAProduk();
        String id_pro = sharedPrefManager.getSP_IdProduk();
//        String tot_weight = sharedPrefManager.getSP_TotalBerat();
//        String tot_price = sharedPrefManager.getSP_TotalHarga();
        Retrofit retrofit = GetClient.getClient(ApiUtil.Base_url);
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> quantity = apiService.editQuantity(id_cuspro, id_agepro, id_pro, jumlah_beli, tot_weight, tot_price);
        quantity.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void editCatatan(String catatan) {
        String id_cart = sharedPrefManager.getSP_IdKeranjang();
        Retrofit retrofit = GetClient.getClient(ApiUtil.Base_url);
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> call = apiService.editCatatan(id_cart, catatan);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
