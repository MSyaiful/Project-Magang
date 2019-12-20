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
import android.widget.Toast;

import com.warehousenesia.id.Service.ApiService;
import com.warehousenesia.id.Service.ApiUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCustomer extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    EditText id_agent, fullname, no_ktp, no_npwp, phone_number, address, kecamatan, kota, provinsi, kodepos;
    Button add_customer;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        sharedPrefManager = new SharedPrefManager(this);
        add_customer = findViewById(R.id.submit);

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

        id_agent.setText(sharedPrefManager.getSpIdmember());

        add_customer.setOnClickListener(this);

        sharedPrefManager.getSPSudahLogin();
    }

    public void onClick(View view) {
        Spinner spinner = findViewById(R.id.provinsi);
        String spin = spinner.getSelectedItem().toString();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.provinsi_indonesia, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (fullname.getText().toString().isEmpty()) {
            Toast.makeText(getApplication(), "Tolong isi Nama Anda!", Toast.LENGTH_SHORT).show();
        } else if (no_ktp.getText().toString().isEmpty()) {
            Toast.makeText(getApplication(), "Tolong isi No.KTP Anda!", Toast.LENGTH_SHORT).show();
        } else if (no_npwp.getText().toString().isEmpty()) {
            Toast.makeText(getApplication(), "Tolong isi No.NPWP Anda!", Toast.LENGTH_SHORT).show();
        } else if (phone_number.getText().toString().isEmpty()) {
            Toast.makeText(getApplication(), "Tolong isi No.Telpon Anda!", Toast.LENGTH_SHORT).show();
        } else if (address.getText().toString().isEmpty()) {
            Toast.makeText(getApplication(), "Tolong isi Alamat Anda!", Toast.LENGTH_SHORT).show();
        } else if (kecamatan.getText().toString().isEmpty()) {
            Toast.makeText(getApplication(), "Tolong isi Kecamatan Anda!", Toast.LENGTH_SHORT).show();
        } else if (kota.getText().toString().isEmpty()) {
            Toast.makeText(getApplication(), "Tolong isi Kota Anda!", Toast.LENGTH_SHORT).show();
        } else if (kodepos.getText().toString().isEmpty()) {
            Toast.makeText(getApplication(), "Tolong isi Kodepos Anda!", Toast.LENGTH_SHORT).show();
        } else {
            registerCustomer(
                    id_agent.getText().toString(),
                    fullname.getText().toString(),
                    no_ktp.getText().toString(),
                    no_npwp.getText().toString(),
                    phone_number.getText().toString(),
                    address.getText().toString(),
                    kecamatan.getText().toString(),
                    kota.getText().toString(),
                    spin,
                    kodepos.getText().toString()
            );
            Intent intent = new Intent(getApplication(), Customer.class);
            startActivity(intent);
            finish();
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private void registerCustomer(String id_agent,
                                  String fullname,
                                  String no_ktp,
                                  String no_npwp,
                                  String phone_number,
                                  String address,
                                  String kecamatan,
                                  String kota,
                                  String provinsi,
                                  String kodepos) {
        ApiService apiService = ApiUtil.getPaketService();
        Call<ResponseBody> call = apiService.registerCustomer(id_agent, fullname, no_ktp, no_npwp, phone_number, address, kecamatan, kota, provinsi, kodepos);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getApplication(), "Add Customer Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
