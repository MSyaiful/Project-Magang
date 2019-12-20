package com.warehousenesia.id;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.warehousenesia.id.data.DataFavorite;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomePersonalShopper extends AppCompatActivity {
    private ArrayList<DataFavorite> list = new ArrayList<>();
    private TextView logout;
    SharedPrefManager sharedPrefManager;
    private TextView nama, level, status, countdown;
    private ImageView edit_profile;
    private CircleImageView circleImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_personal_shopper);

        sharedPrefManager = new SharedPrefManager(getApplication());

        nama = findViewById(R.id.name_user);
        nama.setText(sharedPrefManager.getSPNama());

        circleImageView = findViewById(R.id.user_image);
        Glide.with(this)
                .load(sharedPrefManager.getSP_GambarUser())
                .placeholder(R.drawable.nav_account)
                .into(circleImageView);

        edit_profile = findViewById(R.id.edit_profile);
//        edit_profile.setOnClickListener(view1 -> {
//            Intent intent = new Intent(getActivity(), EditProfile.class);
//            startActivity(intent);
//        });

        level = findViewById(R.id.level);
        level.setText(sharedPrefManager.getSpLevel());

        status = findViewById(R.id.status);
        status.setText(sharedPrefManager.getSpStatus());

        logout = findViewById(R.id.Logout);

        logout.setOnClickListener(v -> {
            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
            startActivity(new Intent(getApplication(), Login.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        });
    }
}
