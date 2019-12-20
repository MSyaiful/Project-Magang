package com.warehousenesia.id;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.warehousenesia.id.Service.ApiService;
import com.warehousenesia.id.Service.ApiUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCustomer extends AppCompatActivity {
    TextView det_nama, det_ktp, det_npwp, det_telpon, det_alamat, det_kecamatan, det_kota, det_provinsi, det_kodePos, id_customer, id_agent;
    ImageView edit_customer, delete_customer;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_customer);
        sharedPrefManager = new SharedPrefManager(getApplication());

        id_customer = findViewById(R.id.id_customer);
        id_agent = findViewById(R.id.id_agent);
        det_nama = findViewById(R.id.nama_lengkap);
        det_ktp = findViewById(R.id.id_ktp);
        det_npwp = findViewById(R.id.npwp);
        det_telpon = findViewById(R.id.no_telpon);
        det_alamat = findViewById(R.id.alamat);
        det_kecamatan = findViewById(R.id.kecamatan);
        det_kota = findViewById(R.id.kota);
        det_provinsi = findViewById(R.id.provinsi);
        det_kodePos = findViewById(R.id.kode_pos);

        id_customer.setText(getIntent().getStringExtra("d_idcustomer"));
        id_agent.setText(getIntent().getStringExtra("d_idagent"));
        det_nama.setText(getIntent().getStringExtra("d_name"));
        det_ktp.setText(getIntent().getStringExtra("d_ktp"));
        det_npwp.setText(getIntent().getStringExtra("d_npwp"));
        det_telpon.setText(getIntent().getStringExtra("d_telpon"));
        det_alamat.setText(getIntent().getStringExtra("d_alamat"));
        det_kecamatan.setText(getIntent().getStringExtra("d_kecamatan"));
        det_kota.setText(getIntent().getStringExtra("d_kota"));
        det_provinsi.setText(getIntent().getStringExtra("d_provinsi"));
        det_kodePos.setText(getIntent().getStringExtra("d_kodePos"));

        final String id_customer = (getIntent().getStringExtra("d_idcustomer"));
        String id_agent = (getIntent().getStringExtra("d_idagent"));
        String det_nama = (getIntent().getStringExtra("d_name"));
        String det_ktp = (getIntent().getStringExtra("d_ktp"));
        String det_npwp = (getIntent().getStringExtra("d_npwp"));
        String det_telpon = (getIntent().getStringExtra("d_telpon"));
        String det_alamat = (getIntent().getStringExtra("d_alamat"));
        String det_kecamatan = (getIntent().getStringExtra("d_kecamatan"));
        String det_kota = (getIntent().getStringExtra("d_kota"));
        String det_provinsi = (getIntent().getStringExtra("d_provinsi"));
        String det_kodePos = (getIntent().getStringExtra("d_kodePos"));

        sharedPrefManager.saveSPString(SharedPrefManager.SP_IdCustomer, id_customer);
        sharedPrefManager.saveSPString(SharedPrefManager.SP_IdAgentCustomer, id_agent);
        sharedPrefManager.saveSPString(SharedPrefManager.SP_NamaCustomer, det_nama);
        sharedPrefManager.saveSPString(SharedPrefManager.SP_KtpCustomer, det_ktp);
        sharedPrefManager.saveSPString(SharedPrefManager.SP_NpwpCustomer, det_npwp);
        sharedPrefManager.saveSPString(SharedPrefManager.SP_TelponCustomer, det_telpon);
        sharedPrefManager.saveSPString(SharedPrefManager.SP_AlamatCustomer, det_alamat);
        sharedPrefManager.saveSPString(SharedPrefManager.SP_KecamatanCustomer, det_kecamatan);
        sharedPrefManager.saveSPString(SharedPrefManager.SP_KotaCustomer, det_kota);
        sharedPrefManager.saveSPString(SharedPrefManager.SP_ProvinsiCustomer, det_provinsi);
        sharedPrefManager.saveSPString(SharedPrefManager.SP_KodeposCustomer, det_kodePos);



        edit_customer = findViewById(R.id.edit);
        edit_customer.setOnClickListener(view -> {
            Intent intent = new Intent(getApplication(), EditCustomer.class);
            startActivity(intent);
        });

        delete_customer = findViewById(R.id.delete);
        delete_customer.setOnClickListener(view -> {
            ApiService apiService = ApiUtil.getPaketService();
            Call<ResponseBody> deleteCustomer = apiService.deleteCustomer(id_customer);
            deleteCustomer.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    finish();
                    Intent intent = new Intent(getApplication(), Customer.class);
                    startActivity(intent);
                    Toast.makeText(getApplication(), "Sukses", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        });

    }
}
