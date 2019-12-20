package com.warehousenesia.id;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.warehousenesia.id.Model.DataCustomer;
import com.warehousenesia.id.Service.ApiService;
import com.warehousenesia.id.Service.ApiUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProduct extends AppCompatActivity implements View.OnClickListener {
    TextView det_tit, det_des, det_har, det_neg, det_ber, det_kat;
    EditText jumlah_barang, total_berat, total_harga, id_agent, id_customer, id_produk, pilih_customer, jumlah_lama;
    ImageView det_img;
    List<DataCustomer> list = new ArrayList<>();

    private Spinner spinner;
    private Button button;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        sharedPrefManager = new SharedPrefManager(getApplication());
        String level = sharedPrefManager.getSpLevel();
        String role = sharedPrefManager.getSpRole();

        spinner = findViewById(R.id.spinner);

        String username = sharedPrefManager.getSPNama();

        ApiService apiService = ApiUtil.getPaketService();

        DataCustomer dataCustomer1 = new DataCustomer("", "", "Pilih Customer", "", "", "", "", "", "", "", "");

        list.add(dataCustomer1);
        Call<ArrayList<DataCustomer>> call = apiService.getCustomer(username);
        call.enqueue(new Callback<ArrayList<DataCustomer>>() {
            @Override
            public void onResponse(Call<ArrayList<DataCustomer>> call, Response<ArrayList<DataCustomer>> response) {
                if(response.isSuccessful()){

                    list.addAll(response.body());

                }
            }

            @Override
            public void onFailure(Call<ArrayList<DataCustomer>> call, Throwable t) {

            }
        });

        ArrayAdapter<DataCustomer> adapter = new ArrayAdapter<DataCustomer>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        jumlah_barang = findViewById(R.id.jumlah_barang);
        total_berat = findViewById(R.id.total_berat);
        total_harga = findViewById(R.id.total_harga);
        id_agent = findViewById(R.id.cart_idagent);
        id_agent.setText(sharedPrefManager.getSpIdmember());
        id_customer = findViewById(R.id.cart_idcustomer);
        id_produk = findViewById(R.id.id_produk);

        det_img = findViewById(R.id.img_item_product);
        det_tit = findViewById(R.id.nama_product);
        det_des = findViewById(R.id.description);
        det_har = findViewById(R.id.harga);
        det_neg = findViewById(R.id.country);
        det_ber = findViewById(R.id.weight);
        det_kat = findViewById(R.id.category);

        Glide.with(this).load(getIntent().getStringExtra("p_gambar")).into(det_img);
        id_produk.setText(getIntent().getStringExtra("p_id"));
        det_tit.setText(getIntent().getStringExtra("p_name"));
        det_des.setText(getIntent().getStringExtra("p_deskripsi"));
        det_har.setText(getIntent().getStringExtra("p_harga"));
        det_neg.setText(getIntent().getStringExtra("p_negara"));
        det_ber.setText(getIntent().getStringExtra("p_berat"));
        det_kat.setText(getIntent().getStringExtra("p_kategori"));

        button = findViewById(R.id.add);
//        button.setOnClickListener(this);
//        button.setEnabled(false);

        if (role.equals("Shopper")){
            button.setOnClickListener(this);
            button.setEnabled(false);
        } else if (level.equals("PLATINUM")){
            button.setOnClickListener(this);
        } else if (level.equals("GOLD")){
            button.setOnClickListener(this);
        } else if (level.equals("SILVER")){
            button.setOnClickListener(this);
        } else if (level.equals("BRONZE")){
            button.setOnClickListener(this);
        } else if (level.equals("null")){
            button.setEnabled(false);
        }

    }

    @Override
    public void onClick(View view) {
        if(jumlah_barang.getText().toString().isEmpty()) {
            Toast.makeText(getApplication(), "Tolong Masukan Jumlah Barang", Toast.LENGTH_SHORT).show();
        }
        else {
            DataCustomer dataCustomer = (DataCustomer) spinner.getSelectedItem();
            String idCustomer = dataCustomer.getId_customer();
            String pro_price = getIntent().getStringExtra("p_harga");
            String pro_weight = getIntent().getStringExtra("p_berat");
            Integer jb = Integer.parseInt(jumlah_barang.getText().toString());

            double product_price = Double.parseDouble(pro_price);
            double weight_pro = Integer.parseInt(pro_weight);
            double total_price = jb * product_price;
            double total_weight = weight_pro * jb;

            Log.d("TOTAL_BERAT", String.valueOf(total_weight));
            Log.d("TOTAL_HARGA", String.valueOf(total_price));
            Log.d("JUMLAH", String.valueOf(jb));

            addCart(det_tit.getText().toString(),
                    jb,
                    String.valueOf(total_weight),
                    String.valueOf(total_price),
                    idCustomer,
                    id_agent.getText().toString(),
                    id_produk.getText().toString(),
                    det_har.getText().toString(),
                    det_ber.getText().toString());
            Toast.makeText(getApplication(), "Add to Cart Success", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void addCart(String nama_pro,
                         Integer jum_barang,
                         String total_berat,
                         String total_harga,
                         String id_customer,
                         String id_agent,
                         String id_produk,
                         String harga_pro,
                         String berat_pro) {
        ApiService apiService = ApiUtil.getPaketService();
        Call<ResponseBody> call = apiService.addtoCart(nama_pro, jum_barang, total_berat, total_harga, id_customer, id_agent, id_produk, harga_pro, berat_pro);
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
