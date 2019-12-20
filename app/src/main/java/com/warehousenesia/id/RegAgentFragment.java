package com.warehousenesia.id;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.warehousenesia.id.Service.ApiService;
import com.warehousenesia.id.Service.ApiUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegAgentFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    private EditText edit_fullname, edit_companyname, edit_address, edit_provinsi, edit_kota, edit_kodepos, edit_idagent;

    private SharedPrefManager sharedPrefManager;

    public RegAgentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reg_agent, container, false);

        sharedPrefManager = new SharedPrefManager(getActivity());

        Button btn_register = view.findViewById(R.id.agent_regisbtn);

        edit_fullname = view.findViewById(R.id.edt_fullname);
        edit_companyname = view.findViewById(R.id.edt_companyName);
        edit_address = view.findViewById(R.id.edt_address);
        edit_provinsi = view.findViewById(R.id.edt_provinsi);
//        Spinner spinner = view.findViewById(R.id.provinsi);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
//                R.array.provinsi_indonesia, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);
        edit_kota = view.findViewById(R.id.edt_kota);
        edit_kodepos = view.findViewById(R.id.edt_kodepos);
        edit_idagent = view.findViewById(R.id.edt_idagent);

//        edit_fullname.setText(sharedPrefManager.getSPNama());
        edit_idagent.setText(sharedPrefManager.getSpIdmember());

        btn_register.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
//        Spinner spinner = view.findViewById(R.id.provinsi);
//        String spin = spinner.getSelectedItem().toString();
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
//                R.array.provinsi_indonesia, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
        if (edit_fullname.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Tolong isi Fullname", Toast.LENGTH_SHORT).show();
        }
        else if (edit_companyname.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Tolong isi Companyname", Toast.LENGTH_SHORT).show();
        }
        else if (edit_address.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Tolong isi Address", Toast.LENGTH_SHORT).show();
        }
        else if (edit_provinsi.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Tolong isi Provinsi", Toast.LENGTH_SHORT).show();
        }
        else if (edit_kota.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Tolong isi Kota", Toast.LENGTH_SHORT).show();
        }
        else if (edit_kodepos.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Tolong isi Kodepos", Toast.LENGTH_SHORT).show();
        }
        else {
            registerAgent(
                    edit_idagent.getText().toString(),
                    edit_fullname.getText().toString(),
                    edit_companyname.getText().toString(),
                    edit_address.getText().toString(),
                    edit_kota.getText().toString(),
                    edit_provinsi.getText().toString(),
                    Integer.parseInt(edit_kodepos.getText().toString()));
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
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

    private void registerAgent(String id_agent,
                               String fullname,
                               String companyname,
                               String address,
                               String provinsi,
                               String kota,
                               Integer kodepos) {

        final String status = sharedPrefManager.getSpStatus();

        ApiService apiService = ApiUtil.getPaketService();
        Call<ResponseBody> call = apiService.registerAgent(id_agent, fullname, companyname, address, provinsi, kota, kodepos);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        String id_agent = object.getString("id_agent");
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_IDAGENT, id_agent);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_STATUS, status);
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
//                        Toast.makeText(getApplication(), "Registrasi Success", Toast.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Registrasi Success", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Apakah Anda Yakin Ingin Keluar ?");
        builder.setCancelable(true);
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onDestroy();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}
