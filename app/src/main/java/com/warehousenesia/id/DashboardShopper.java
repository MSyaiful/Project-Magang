package com.warehousenesia.id;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.warehousenesia.id.Model.DataRequestProduct;
import com.warehousenesia.id.Service.ApiService;
import com.warehousenesia.id.Service.ApiUtil;
import com.warehousenesia.id.Service.GetClient;
import com.warehousenesia.id.adapter.RequestShopperAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DashboardShopper extends Fragment {
    private ShimmerFrameLayout shimmerFrameLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_shopper, container, false);

        SharedPrefManager sharedPrefManager = new SharedPrefManager(getActivity());

        shimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
        RecyclerView recyclerView = view.findViewById(R.id.request);
        recyclerView.setHasFixedSize(true);

        sharedPrefManager.getSPSudahLogin();

        String id = sharedPrefManager.getSpIdmember();

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);

        getRequest(recyclerView, id);

        swipeRefreshLayout.setOnRefreshListener(() -> new Handler().postDelayed(() -> {

            getRequest(recyclerView, id);
            swipeRefreshLayout.setRefreshing(false);

        }, 2500));

        return view;
    }

    public void onResume() {

        super.onResume();
        shimmerFrameLayout.startShimmer();

    }

    public void onPause() {

        super.onPause();
        shimmerFrameLayout.stopShimmer();

    }

    private void getRequest(RecyclerView recyclerView, String id) {

        Retrofit retrofit = GetClient.getClient(ApiUtil.Base_url);
        ApiService apiService = retrofit.create(ApiService.class);

        Call<ArrayList<DataRequestProduct>> call = apiService.getRequestShopper(id);
        call.enqueue(new Callback<ArrayList<DataRequestProduct>>() {
            @Override
            public void onResponse(Call<ArrayList<DataRequestProduct>> call, Response<ArrayList<DataRequestProduct>> response) {
                RequestShopperAdapter requestShopperAdapter = new RequestShopperAdapter(getActivity(), response.body());
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                recyclerView.setAdapter(requestShopperAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<DataRequestProduct>> call, Throwable t) {

            }
        });

    }

}
