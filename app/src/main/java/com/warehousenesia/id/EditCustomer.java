package com.warehousenesia.id;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.warehousenesia.id.Service.ApiService;
import com.warehousenesia.id.Service.ApiUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCustomer extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {
    EditText id_agent, fullname, no_ktp, no_npwp, phone_number, address, kecamatan, kota, provinsi, kodepos, id_customer;
    SharedPrefManager sharedPrefManager;
    Button saveEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer);
        sharedPrefManager = new SharedPrefManager(getApplication());

        id_customer = findViewById(R.id.id_customer);
        id_agent = findViewById(R.id.id_agent);
        fullname = findViewById(R.id.nama_lengkap);
        no_ktp = findViewById(R.id.ktp);
        no_npwp = findViewById(R.id.npwp);
        phone_number = findViewById(R.id.no_telpon);
        address = findViewById(R.id.alamat);
        kecamatan = findViewById(R.id.kecamatan);
        kota = findViewById(R.id.kota);
//        provinsi = findViewById(R.id.provinsi);
        Spinner spinner = findViewById(R.id.provinsi);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.provinsi_indonesia, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        kodepos = findViewById(R.id.kode_pos);

        saveEdit = findViewById(R.id.submit);
        saveEdit.setOnClickListener(this);

        id_customer.setText(sharedPrefManager.getSP_IdCustomer());
        id_agent.setText(sharedPrefManager.getSP_IdAgentCustomer());
        fullname.setText(sharedPrefManager.getSP_NamaCustomer());
        no_ktp.setText(sharedPrefManager.getSP_KtpCustomer());
        no_npwp.setText(sharedPrefManager.getSP_NpwpCustomer());
        phone_number.setText(sharedPrefManager.getSP_TelponCustomer());
        address.setText(sharedPrefManager.getSP_AlamatCustomer());
        kecamatan.setText(sharedPrefManager.getSP_KecamatanCustomer());
        kota.setText(sharedPrefManager.getSP_KotaCustomer());
//        provinsi.setText(sharedPrefManager.getSP_ProvinsiCustomer());
        kodepos.setText(sharedPrefManager.getSP_KodeposCustomer());

    }

    @Override
    public void onClick(View view) {
        Spinner spinner = findViewById(R.id.provinsi);
        String spin = spinner.getSelectedItem().toString();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.provinsi_indonesia, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        SaveEditCustomer(
                fullname.getText().toString(),
                no_ktp.getText().toString(),
                no_npwp.getText().toString(),
                phone_number.getText().toString(),
                address.getText().toString(),
                kecamatan.getText().toString(),
                kota.getText().toString(),
                spin,
                kodepos.getText().toString());
        finish();
        Intent intent = new Intent(getApplication(), Customer.class);
        startActivity(intent);
        Toast.makeText(getApplication(), "Edit Customer Berhasil", Toast.LENGTH_SHORT).show();
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private void SaveEditCustomer(
                                  String fullname,
                                  String noktp,
                                  String nonpwp,
                                  String phone,
                                  String address,
                                  String kecamatan,
                                  String kota,
                                  String provinsi,
                                  String kodepos) {
        String id_cus = sharedPrefManager.getSP_IdCustomer();
        ApiService apiService = ApiUtil.getPaketService();
        Call<ResponseBody> call = apiService.getEditCustomer(id_cus, fullname, noktp, nonpwp, phone, address, kecamatan, kota, provinsi, kodepos);
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
