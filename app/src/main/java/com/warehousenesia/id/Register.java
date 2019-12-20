package com.warehousenesia.id;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class Register extends AppCompatActivity implements View.OnClickListener {
    TextView login;

    INodeJS myApi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    EditText edit_username, edit_password, edit_phone, edit_email;
    CardView btn_register;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        login = findViewById(R.id.masuk);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), Login.class);
                startActivity(intent);
            }
        });

//        init API
        Retrofit retrofit = RetrofitClient.getInstance();
        myApi = retrofit.create(INodeJS.class);

        sharedPrefManager = new SharedPrefManager(this);

        //View
        btn_register = findViewById(R.id.go_regis);

        edit_username = findViewById(R.id.username);
        edit_password = findViewById(R.id.password);
        edit_email = findViewById(R.id.email);
        edit_phone = findViewById(R.id.phone);

        btn_register.setOnClickListener(this);

//        if (sharedPrefManager.getSPSudahLogin()) {
//            startActivity(new Intent(getApplication(), RegisterAgent.class)
//                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
//            finish();
//        }
    }

    @Override
    public void onClick(View view) {
        if (edit_username.getText().toString().isEmpty()) {
            Toast.makeText(this, "Tolong isi Username", Toast.LENGTH_SHORT).show();
        } else if (edit_password.getText().toString().isEmpty()) {
            Toast.makeText(this, "Tolong isi Password", Toast.LENGTH_SHORT).show();
        } else if (edit_email.getText().toString().isEmpty()) {
            Toast.makeText(this, "Tolong isi Email", Toast.LENGTH_SHORT).show();
        } else if (edit_phone.getText().toString().isEmpty()) {
            Toast.makeText(this, "Tolong isi Nomor Telphone", Toast.LENGTH_SHORT).show();
        } else {
//            String username = edit_username.getText().toString();
//            String password = edit_password.getText().toString();
            String email = edit_email.getText().toString();
//            String phone = edit_phone.getText().toString();
            registerMember(edit_username.getText().toString(),
                    edit_password.getText().toString(),
                    edit_email.getText().toString(),
                    edit_phone.getText().toString());
//            Intent intent = new Intent(getApplication(), RegisterAgent.class);
            sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, email);
//            intent.putExtra(Main2Activity.EXTRA_USERNAME, username);
//            intent.putExtra(Main2Activity.EXTRA_PASSWORD, password);
//            intent.putExtra(Main2Activity.EXTRA_EMAIL, email);
//            intent.putExtra(Main2Activity.EXTRA_PHONE, phone);
//            startActivity(intent);
        }
    }

    private void registerMember(final String username,
                                final String password,
                                final String email,
                                final String phone) {
        ApiService apiService = ApiUtil.getPaketService();
        Call<ResponseBody> call = apiService.registerMember(email, password, username, phone);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        String id = object.getString("id");
//                        String username = object.getString("username");
                        String status = object.getString("status");
//                        String level = object.getString("level");
                        Intent intent = new Intent(getApplication(), RegisterAgent.class);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_IDAGENT, id);
//                        sharedPrefManager.saveSPString(SharedPrefManager.SP_NAMA, username);
                        sharedPrefManager.saveSPString(SharedPrefManager.SP_STATUS, status);
//                        sharedPrefManager.saveSPString(SharedPrefManager.SP_LEVEL, level);
//                        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                        startActivity(intent);
                        finish();
                        Toast.makeText(getApplication(), "Registrasi Success", Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

}
