package com.warehousenesia.id;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.gson.JsonArray;
import com.warehousenesia.id.Post.INodeJS;
import com.warehousenesia.id.Post.RetrofitClient;
import com.warehousenesia.id.Service.ApiService;
import com.warehousenesia.id.Service.ApiUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity implements View.OnClickListener {
    TextView register;

//    INodeJS myApi;
//    CompositeDisposable compositeDisposable = new CompositeDisposable();

    EditText edit_username, edit_password;
    CardView btn_login;

    SharedPrefManager sharedPrefManager;

//    @Override
//    protected void onStop() {
//        compositeDisposable.clear();
//        super.onStop();
//    }
//
//    @Override
//    protected void onDestroy() {
//        compositeDisposable.clear();
//        super.onDestroy();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.daftar);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getApplication(), Register.class);
                startActivity(intent);
            }
        });

        //init API
//        Retrofit retrofit = RetrofitClient.getInstance();
//        myApi = retrofit.create(INodeJS.class);

        sharedPrefManager = new SharedPrefManager(getApplication());

        //View
        btn_login = findViewById(R.id.go_login);

        edit_username = findViewById(R.id.username);
        edit_password = findViewById(R.id.password);

        btn_login.setOnClickListener(this);

        if (sharedPrefManager.getSPSudahLogin()) {
            startActivity(new Intent(getApplication(), MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        if (edit_username.getText().toString().isEmpty()) {
            Toast.makeText(this, "Tolong isi Username", Toast.LENGTH_SHORT).show();
        } else if (edit_password.getText().toString().isEmpty()) {
            Toast.makeText(this, "Tolong isi Password", Toast.LENGTH_SHORT).show();
        }
        else {
            loginMember(edit_username.getText().toString(),
                    edit_password.getText().toString());
        }
    }

    private void loginMember(final String username, final String password) {
        final ApiService apiService = ApiUtil.getPaketService();
        Call<ResponseBody> call = apiService.loginMember(username, password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONArray array = new JSONArray(response.body().string());
                        final String id = array.getJSONObject(0).getString("id");
                        String status = array.getJSONObject(0).getString("status");
                        String level = array.getJSONObject(0).getString("level");
                        String days = array.getJSONObject(0).getString("lastmember");
                        String phone = array.getJSONObject(0).getString("phone");
                        String email = array.getJSONObject(0).getString("email");
                        String role = array.getJSONObject(0).getString("role");
                        String limit = array.getJSONObject(0).getString("limit");

                        sharedPrefManager.saveSPString(SharedPrefManager.SP_IDMEMBER, id);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_STATUS, status);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, username);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_LEVEL, level);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_LASTMEMBER, days);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_PhoneNumber, phone);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_Email, email);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_ROLE, role);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_LIMIT, limit);

                        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);

                        Call<ResponseBody> cek = apiService.CheckData(id);
                        cek.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                try {
                                    JSONArray array = new JSONArray(response.body().string());

                                    String panjang = String.valueOf(array.length());
                                    String fullname = array.getJSONObject(0).getString("fullname");
                                    String companyname = array.getJSONObject(0).getString("companyName");
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_PANJANG, panjang);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_FullName, fullname);
                                    sharedPrefManager.saveSPString(SharedPrefManager.SP_CompanyName, companyname);
                                    if(panjang.equals("0")){
                                        Intent intent = new Intent(getApplication(), MainActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(getApplication(), id, Toast.LENGTH_SHORT).show();
                                    }else {

                                    }

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

                        if (role.equals("Shopper")){
                            Intent intent = new Intent(getApplication(), MainActivity.class);
                            startActivity(intent);
                        } else if (role.equals("User")){
                            Intent intent = new Intent(getApplication(), MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplication(), "Login Success", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplication(), "Username or Password is Wrong!", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

//    compositeDisposable.add(myApi.loginMember(username, password)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(new Consumer<String>() {
//        @Override
//        public void accept(String s) throws Exception {
//            if (s.contains("password")) {
//                Intent i = new Intent(getApplication(), RegisterAgent.class);
//                startActivity(i);
//                Toast.makeText(getApplication(), "Login Success", Toast.LENGTH_SHORT).show();
//                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
//                sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, username);
//            } else
//                Toast.makeText(Login.this, "Login Gagal", Toast.LENGTH_SHORT).show();
//        }
//    })
//            );
}
