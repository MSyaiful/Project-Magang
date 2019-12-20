package com.warehousenesia.id;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.warehousenesia.id.Model.DataAgent;
import com.warehousenesia.id.Model.DataCountry;
import com.warehousenesia.id.Model.DataProduct;
import com.warehousenesia.id.Model.DataProductBest;
import com.warehousenesia.id.Model.DataProductOffer;
import com.warehousenesia.id.Service.ApiService;
import com.warehousenesia.id.Service.GetClient;
import com.warehousenesia.id.Service.ProductService;
import com.warehousenesia.id.Service.ApiUtil;
import com.warehousenesia.id.adapter.ProductAdapter;
import com.warehousenesia.id.adapter.ProductBestAdapter;
import com.warehousenesia.id.adapter.ProductOfferAdapter;
import com.warehousenesia.id.adapter.SearchByAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment{
    private RecyclerView rvlist;
    private RecyclerView rvlist2;
    private RecyclerView rvlist3;
    private RecyclerView rvlist4;
    private SwipeRefreshLayout swipeRefreshLayout;
    ScrollView scrollView;
    private ShimmerFrameLayout shimmerFrameLayout1, shimmerFrameLayout2, shimmerFrameLayout3, shimmerFrameLayout4;

    SharedPrefManager sharedPrefManager;

    private int[] sampleImages = {R.drawable.carou1, R.drawable.carou2};

    private CardView search_product;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        CarouselView img = view.findViewById(R.id.carouselView);
        img.setPageCount(sampleImages.length);
        img.setImageListener(imageListener);

        sharedPrefManager = new SharedPrefManager(getActivity());
        String id = sharedPrefManager.getSpIdagent();

//        Toast.makeText(getActivity(), sharedPrefManager.getSpIdmember(), Toast.LENGTH_SHORT).show();

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);

        cekagent(id);

        rvlist = view.findViewById(R.id.new_arrival);
        shimmerFrameLayout1 = view.findViewById(R.id.shimmer_layout_new);
        rvlist.setHasFixedSize(true);
        getProduct(rvlist);

        rvlist2 = view.findViewById(R.id.best_product);
        shimmerFrameLayout2 = view.findViewById(R.id.shimmer_layout_best);
        rvlist2.setHasFixedSize(true);
        getProductBest(rvlist2);

        rvlist3 = view.findViewById(R.id.best_offers);
        shimmerFrameLayout3 = view.findViewById(R.id.shimmer_layout_offer);
        rvlist3.setHasFixedSize(true);
        getProductOffer(rvlist3);

        rvlist4 = view.findViewById(R.id.country);
        shimmerFrameLayout4 = view.findViewById(R.id.shimmer_layout_country);
        rvlist4.setHasFixedSize(true);
        getCountry(rvlist4);

        search_product = view.findViewById(R.id.search);
        search_product.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), SearchProduct.class);
            startActivity(intent);
        });

        swipeRefreshLayout.setOnRefreshListener(() -> new Handler().postDelayed(() -> {

            getProduct(rvlist);
            getProductBest(rvlist2);
            getProductOffer(rvlist3);
            getCountry(rvlist4);
            swipeRefreshLayout.setRefreshing(false);

        }, 2500));

        return view;
    }

    public void onResume() {

        super.onResume();
        shimmerFrameLayout1.startShimmer();
        shimmerFrameLayout2.startShimmer();
        shimmerFrameLayout3.startShimmer();
        shimmerFrameLayout4.startShimmer();

    }

    public void onPause() {

        super.onPause();
        shimmerFrameLayout1.stopShimmer();
        shimmerFrameLayout2.stopShimmer();
        shimmerFrameLayout3.stopShimmer();
        shimmerFrameLayout4.stopShimmer();

    }

    private void cekagent(String id) {

    }

    private void getCountry(final RecyclerView rvlist4) {
        Retrofit retrofit = GetClient.getClient(ApiUtil.Base_url);
        ProductService api = retrofit.create(ProductService.class);

        Call<ArrayList<DataCountry>> db = api.getCountry();
        db.enqueue(new Callback<ArrayList<DataCountry>>() {
            @Override
            public void onResponse(Call<ArrayList<DataCountry>> call, Response<ArrayList<DataCountry>> response) {
                SearchByAdapter searchByAdapter = new SearchByAdapter(getActivity(), response.body());
                shimmerFrameLayout4.stopShimmer();
                shimmerFrameLayout4.setVisibility(View.GONE);
                rvlist4.setVisibility(View.VISIBLE);
                rvlist4.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                rvlist4.setAdapter(searchByAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<DataCountry>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    private void getProductBest(final RecyclerView rv){
        Retrofit ret = GetClient.getClient(ApiUtil.Base_url);
        ProductService api = ret.create(ProductService.class);

        Call<ArrayList<DataProductBest>> db = api.getProductBest();
        db.enqueue(new Callback<ArrayList<DataProductBest>>() {
            @Override
            public void onResponse(Call<ArrayList<DataProductBest>> call, Response<ArrayList<DataProductBest>> response) {
                ProductBestAdapter productBestAdapter = new ProductBestAdapter(getActivity(), response.body());
                shimmerFrameLayout2.stopShimmer();
                shimmerFrameLayout2.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);
                rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                rv.setAdapter(productBestAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<DataProductBest>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    private void getProductOffer(final RecyclerView reVi){
        Retrofit rt = GetClient.getClient(ApiUtil.Base_url);
        ProductService as = rt.create(ProductService.class);

        Call<ArrayList<DataProductOffer>> dof = as.getProductOffer();
        dof.enqueue(new Callback<ArrayList<DataProductOffer>>() {
            @Override
            public void onResponse(Call<ArrayList<DataProductOffer>> call, Response<ArrayList<DataProductOffer>> response) {
                ProductOfferAdapter productOfferAdapter = new ProductOfferAdapter(getActivity(), response.body());
                shimmerFrameLayout3.stopShimmer();
                shimmerFrameLayout3.setVisibility(View.GONE);
                reVi.setVisibility(View.VISIBLE);
                reVi.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                reVi.setAdapter(productOfferAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<DataProductOffer>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    private void getProduct(final RecyclerView recyclerView){
        Retrofit retrofit = GetClient.getClient(ApiUtil.Base_url);
        ProductService apiService = retrofit.create(ProductService.class);

        Call<ArrayList<DataProduct>> data = apiService.getProduct();
        data.enqueue(new Callback<ArrayList<DataProduct>>() {
            @Override
            public void onResponse(Call<ArrayList<DataProduct>> call, Response<ArrayList<DataProduct>> response) {
                ProductAdapter productAdapter = new ProductAdapter(getActivity(), response.body());
                shimmerFrameLayout1.stopShimmer();
                shimmerFrameLayout1.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                recyclerView.setAdapter(productAdapter);
            }

            @Override
            public void onFailure(Call<ArrayList<DataProduct>> call, Throwable t) {
                Log.e("ERROR", t.getMessage() );
            }
        });
    }

    private ImageListener imageListener = (position, imageView) -> imageView.setImageResource(sampleImages[position]);

}