package com.warehousenesia.id;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.warehousenesia.id.Service.ApiService;
import com.warehousenesia.id.Service.ApiUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    TextView status;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!isConnected(MainActivity.this)) buildDialog(MainActivity.this).show();
        else {
            setContentView(R.layout.activity_main);
        }

        sharedPrefManager = new SharedPrefManager(getApplication());
        status = findViewById(R.id.status);
        status.setText(sharedPrefManager.getSpStatus());

        String id = sharedPrefManager.getSpIdmember();

        ApiService apiService = ApiUtil.getPaketService();

        Call<ResponseBody> cek = apiService.CheckData(id);
        cek.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try {
                    JSONArray array = new JSONArray(response.body().string());

                    String panjang = String.valueOf(array.length());
                    sharedPrefManager.saveSPString(SharedPrefManager.SP_PANJANG, panjang);

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

        getFragmentPage(new HomeFragment());

        this.mHandler = new Handler();
        m_Runnable.run();

        //Menampilkan halaman Fragment yang pertama kali muncul

        /*Inisialisasi BottomNavigationView beserta listenernya untuk
         *menangkap setiap kejadian saat salah satu menu item diklik
         */
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigationView);
        bottomNavigation.setOnNavigationItemSelectedListener(item -> {

            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.home:
                    fragment = new HomeFragment();
                    break;

                case R.id.dashboard:
                    String role = sharedPrefManager.getSpRole();
                    boolean check = !sharedPrefManager.getSpStatus().equals("Verified");
                    String cek1 = sharedPrefManager.getPanjang();
                    if (role.equals("Shopper")) {
                        fragment =  new DashboardShopper();
                        break;
                    }else if(cek1.equals("0")) {
                        fragment = new RegAgentFragment();
                        break;
                    } else if(check) {
                        fragment = new PaketFragment();
                        break;
                    }  else {
                        fragment = new DashboardFragment();
                        break;
                    }
                case R.id.account:
                    boolean roles = sharedPrefManager.getSpRole().equals("Shopper");
                    if (roles){
                        fragment = new ShopperFragment();
                        break;
                    }else {
                        fragment = new AccountFragment();
                        break;
                    }
            }

            return getFragmentPage(fragment);
        });
    }

    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
            else return false;
        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", (dialog, which) -> finish());

        return builder;
    }

    private final Runnable m_Runnable = new Runnable()
    {
        public void run()

        {

            MainActivity.this.mHandler.postDelayed(m_Runnable,1000);
        }

    };


    //Menampilkan halaman Fragment
    private boolean getFragmentPage(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.page_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakah Anda Yakin Ingin Keluar ?");
        builder.setCancelable(true);
        builder.setNegativeButton("Tidak", (dialogInterface, i) -> dialogInterface.cancel());

        builder.setPositiveButton("Ya", (dialogInterface, i) -> finish());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}
