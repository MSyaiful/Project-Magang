package com.warehousenesia.id;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.warehousenesia.id.Model.DataBank;
import com.warehousenesia.id.Model.DataCukaiFee;
import com.warehousenesia.id.Service.ApiService;
import com.warehousenesia.id.Service.ApiUtil;
import com.warehousenesia.id.Service.GetClient;
import com.warehousenesia.id.Service.ProductService;
import com.warehousenesia.id.adapter.BankAdapter;
import com.warehousenesia.id.adapter.CukaiAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class BeliPaket extends AppCompatActivity implements View.OnClickListener {
    TextView jns_paket, hrg_paket, det1, det2, det3, det4, det5, id_paket, total, id_agent, id_bank, namaBank, atasNama, norek;
    private TextView ppns;
    Button checkout;
    List<DataBank> list = new ArrayList<>();
    private Spinner spinner;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beli_paket);

        sharedPrefManager = new SharedPrefManager(getApplication());

        if (sharedPrefManager.getSPSudahLogin()) {
        }

        spinner = findViewById(R.id.spinner);

        ApiService apiService = ApiUtil.getPaketService();

        DataBank dataBank1 = new DataBank("", "Pilih Bank", "","", "");

        list.add(dataBank1);
        Call<ArrayList<DataBank>> call = apiService.getBank();
        call.enqueue(new Callback<ArrayList<DataBank>>() {
            @Override
            public void onResponse(Call<ArrayList<DataBank>> call, Response<ArrayList<DataBank>> response) {
                list.addAll(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<DataBank>> call, Throwable t) {

            }
        });

        ArrayAdapter<DataBank> adapter = new ArrayAdapter<DataBank>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        namaBank = findViewById(R.id.nama_bank);
        norek = findViewById(R.id.norek);
        atasNama = findViewById(R.id.atas_nama);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DataBank dataBank = (DataBank) adapterView.getSelectedItem();
                    displayBank(dataBank);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        id_paket = findViewById(R.id.id_paket);
        jns_paket = findViewById(R.id.nama_paket);
        det1 = findViewById(R.id.detail1);
        det2 = findViewById(R.id.detail2);
        det3 = findViewById(R.id.detail3);
        det4 = findViewById(R.id.detail4);
        det5 = findViewById(R.id.detail5);
        hrg_paket = findViewById(R.id.harga_paket);
        ppns = findViewById(R.id.ppn);
        getPpn(ppns);

        total = findViewById(R.id.total);
        checkout = findViewById(R.id.checkout);

        id_agent = findViewById(R.id.id_agent);
        id_agent.setText(sharedPrefManager.getSpIdmember());

        id_bank = findViewById(R.id.id_bank);
//        getIdbank(id_bank);

        String as = getIntent().getStringExtra("harga_paket");
        int getPPN = Integer.parseInt(as);
        ppns.getText().toString();
        double jumlah = getPPN * 0.1;
        int ngen = (int) jumlah;
        int tot = getPPN + ngen;
        total.setText("" + tot);

        checkout.setOnClickListener(this);

        id_paket.setText(getIntent().getStringExtra("id_paket"));
        jns_paket.setText(getIntent().getStringExtra("jenis_paket"));
        det1.setText(getIntent().getStringExtra("detail1"));
        det2.setText(getIntent().getStringExtra("detail2"));
        det3.setText(getIntent().getStringExtra("detail3"));
        det4.setText(getIntent().getStringExtra("detail4"));
        det5.setText(getIntent().getStringExtra("detail5"));
        hrg_paket.setText("" + getIntent().getStringExtra("harga_paket"));

    }

    private void displayBank(DataBank dataBank){
        String namaBanks = dataBank.getNama_bank();
        String noreks = dataBank.getNorek();
        String atasNamae = dataBank.getAtasnama();

        namaBank.setText(namaBanks);
        norek.setText(noreks);
        atasNama.setText(atasNamae);
    }

//    private void getIdbank(final TextView id_bank) {
//        Retrofit retrofit = GetClient.getClient(ApiUtil.Base_url);
//        final ApiService apiService = retrofit.create(ApiService.class);
//
//        final Call<ResponseBody> IDAgent = apiService.getIDbank();
//        IDAgent.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    JSONArray array = new JSONArray(response.body().string());
//                    String ID_Agent = array.getJSONObject(0).getString("id");
//                    id_bank.setText(ID_Agent);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
//    }


    private void getPpn(final TextView ppns) {
        Retrofit retro = GetClient.getClient(ApiUtil.Base_url);
        ApiService apiService = retro.create(ApiService.class);

        final Call<ResponseBody> cukai = apiService.getIDCukaiFee();
        cukai.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    JSONArray array = new JSONArray(response.body().string());

                    String ppn = array.getJSONObject(0).getString("PPN");

                    ppns.setText(ppn + "%");


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View view) {

        DataBank dataBank = (DataBank)spinner.getSelectedItem();
        String id_bank = dataBank.getId();
//        Toast.makeText(this, id_bank, Toast.LENGTH_SHORT).show();

            beliPaket(total.getText().toString(),
                    id_agent.getText().toString(),
                    id_bank);
            Intent intent = new Intent(getApplication(), ConfirmPaket.class);
            startActivity(intent);
            finish();
    }

    private void beliPaket(final String total, final String id_agent, final String bank) {
        final ApiService apiService = ApiUtil.getPaketService();
        Call<ResponseBody> confirm = apiService.beliPaket(total, id_agent, bank);
        confirm.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        JSONObject object = new JSONObject(response.body().string());
//                        String anjing = object.getJSONArray().pa
//                        String total = object.getString("total");
//                        String id_agent = object.getJSONObject(0).getString("id_agent");
                        String id_transaksi = object.getString("insertId");
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_IDBANK, id_transaksi);
//                        sharedPrefManager.saveSPString(SharedPrefManager.SP_IDMEMBER, id_agent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }
}