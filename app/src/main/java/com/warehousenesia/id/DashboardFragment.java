package com.warehousenesia.id;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.warehousenesia.id.Model.DataRequestProduct;
import com.warehousenesia.id.Service.ApiService;
import com.warehousenesia.id.Service.ApiUtil;
import com.warehousenesia.id.Service.GetClient;
import com.warehousenesia.id.adapter.RequestProductAdapter;

import org.json.JSONArray;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class DashboardFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    TextView news;
    TextView dollar;
    TextView level;
    SharedPrefManager sharedPrefManager;
    private ShimmerFrameLayout shimmerFrameLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        shimmerFrameLayout = view.findViewById(R.id.shimmer_layout);
        RecyclerView recyclerView = view.findViewById(R.id.request);
        recyclerView.setHasFixedSize(true);

        sharedPrefManager = new SharedPrefManager(getActivity());
        level = view.findViewById(R.id.level);
        level.setText(sharedPrefManager.getSpLevel());

        sharedPrefManager.getSPSudahLogin();

        String id = sharedPrefManager.getSpIdmember();

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);

        getProduct(recyclerView, id);

        news = view.findViewById(R.id.isi_news);
        getNews(news);
        
        dollar = view.findViewById(R.id.isi_currency);
        getDollar(dollar);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.add_request);
        fab.setOnClickListener(view15 -> {
            Intent intent = new Intent(getActivity(), RequestProduct.class);
            startActivity(intent);
        });

        CardView cart = (CardView) view.findViewById(R.id.cart);
        cart.setOnClickListener(view14 -> {
            Intent intent = new Intent(getActivity(), Keranjang.class);
            startActivity(intent);
        });

        CardView order = (CardView) view.findViewById(R.id.pesanan);
        order.setOnClickListener(view13 -> {
            Intent intent = new Intent(getActivity(), Order.class);
            startActivity(intent);
        });

        CardView customer = (CardView) view.findViewById(R.id.customer);
        customer.setOnClickListener(view12 -> {
            Intent intent = new Intent(getActivity(), Customer.class);
            startActivity(intent);
        });

        CardView confirmPayment = (CardView) view.findViewById(R.id.payment);
        confirmPayment.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ConfirmPayment.class);
            startActivity(intent);
        });

        swipeRefreshLayout.setOnRefreshListener(() -> new Handler().postDelayed(() -> {

            getProduct(recyclerView, id);
            getNews(news);
            getDollar(dollar);
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

    private void getProduct(final RecyclerView recyclerView, String id) {
        Retrofit retrofit = GetClient.getClient(ApiUtil.Base_url);
        ApiService apiService = retrofit.create(ApiService.class);

        Call<ArrayList<DataRequestProduct>> data = apiService.getRequestProduct(id);
        data.enqueue(new Callback<ArrayList<DataRequestProduct>>() {
            @Override
            public void onResponse(Call<ArrayList<DataRequestProduct>> call, Response<ArrayList<DataRequestProduct>> response) {
                if(getActivity()!= null){
                    RequestProductAdapter requestProductAdapter = new RequestProductAdapter(getActivity(), response.body());
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    recyclerView.setAdapter(requestProductAdapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DataRequestProduct>> call, Throwable t) {

            }
        });
    }

    private void getDollar(final TextView dollar) {
        Retrofit retrofit = GetClient.getClient(ApiUtil.Base_url);
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> call = apiService.getCurency();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                boolean checkplatinum = sharedPrefManager.getSpLevel().equals("PLATINUM");
                boolean checkgold = sharedPrefManager.getSpLevel().equals("GOLD");
                boolean checksilver = sharedPrefManager.getSpLevel().equals("SILVER");
                boolean checkbronze = sharedPrefManager.getSpLevel().equals("BRONZE");
                if (checkplatinum){
                    try {
                        JSONArray array = new JSONArray(response.body().string());
                        String harga = array.getJSONObject(0).getString("dollar");
                        String curencyplatinum = array.getJSONObject(0).getString("platinum");
                        int hrg = Integer.parseInt(harga);
                        int cp = Integer.parseInt(curencyplatinum);
                        double persen = hrg * cp;
                        double jumlah = persen/100;
                        double akhir = hrg + jumlah;
                        int total = (int) akhir;
                        dollar.setText("$1 : " + total);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if (checkgold){
                    try {
                        JSONArray array = new JSONArray(response.body().string());
                        String harga = array.getJSONObject(0).getString("dollar");
                        String curencygold = array.getJSONObject(0).getString("gold");
                        int hrg = Integer.parseInt(harga);
                        double jumlah = hrg * 0.075;
                        double akhir = hrg + jumlah;
                        int total = (int) akhir;
                        dollar.setText("$1 : " + total);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if (checksilver){
                    try {
                        JSONArray array = new JSONArray(response.body().string());
                        String harga = array.getJSONObject(0).getString("dollar");
                        String curencysilver = array.getJSONObject(0).getString("silver");
                        int hrg = Integer.parseInt(harga);
                        int cs = Integer.parseInt(curencysilver);
                        double persen = hrg * cs;
                        double jumlah = persen/100;
                        double akhir = hrg + jumlah;
                        int total = (int) akhir;
                        dollar.setText("$1 : " + total);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if (checkbronze){
                    try {
                        JSONArray array = new JSONArray(response.body().string());
                        String harga = array.getJSONObject(0).getString("dollar");
                        String curencybronze = array.getJSONObject(0).getString("bronze");
                        int hrg = Integer.parseInt(harga);
                        int cs = Integer.parseInt(curencybronze);
                        double persen = hrg * 0.15;
                        double jumlah = persen/100;
                        double akhir = hrg + jumlah;
                        int total = (int) akhir;
                        dollar.setText("$1 : " + total);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void getNews(final TextView news) {
        Retrofit retrofit = GetClient.getClient(ApiUtil.Base_url);
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> call = apiService.getNews();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONArray object = new JSONArray(response.body().string());
                    String berita = object.getJSONObject(0).getString("news");
                    news.setText(berita);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}