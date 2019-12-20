package com.warehousenesia.id;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.warehousenesia.id.Model.DataPaket;
import com.warehousenesia.id.Model.DataProductBest;
import com.warehousenesia.id.Service.ApiService;
import com.warehousenesia.id.Service.ApiUtil;
import com.warehousenesia.id.Service.GetClient;
import com.warehousenesia.id.Service.ProductService;
import com.warehousenesia.id.adapter.PaketAdapter;
import com.warehousenesia.id.adapter.ProductBestAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaketFragment extends Fragment {
    private RecyclerView rvPaket;
//    Button subscribe;


    public PaketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        subscribe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), BeliPaket.class);
//                startActivity(intent);
//            }
//        });
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_paket, container, false);

        rvPaket = view.findViewById(R.id.paket);
        getPaket(rvPaket);


        return view;

    }

    private void getPaket(final RecyclerView rvPaket) {
        Retrofit ret = GetClient.getClient(ApiUtil.Base_url);
        ApiService api = ret.create(ApiService.class);

        Call<ArrayList<DataPaket>> db = api.getPaket();
        db.enqueue(new Callback<ArrayList<DataPaket>>() {
            @Override
            public void onResponse(Call<ArrayList<DataPaket>> call, Response<ArrayList<DataPaket>> response) {
                PaketAdapter paketAdapter = new PaketAdapter(getActivity(), response.body());
                rvPaket.setLayoutManager(new LinearLayoutManager(getActivity()));
                rvPaket.setAdapter(paketAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<DataPaket>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }

}
