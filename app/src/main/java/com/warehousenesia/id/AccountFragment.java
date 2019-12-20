package com.warehousenesia.id;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.warehousenesia.id.adapter.AdapterFavorite;
import com.warehousenesia.id.data.DataFavorite;
import com.warehousenesia.id.isi.IsiFavorite;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {
    //    private ArrayList<DataFavorite> list = new ArrayList<>();
    private TextView logout;
    SharedPrefManager sharedPrefManager;
    private TextView nama, level, status, countdown, limit;
    private ImageView edit_profile;
    private CircleImageView circleImageView;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
//        RecyclerView recyclerView = view.findViewById(R.id.favorite);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setAdapter(new AdapterFavorite(list));
//        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//        list.addAll(IsiFavorite.getData());

        sharedPrefManager = new SharedPrefManager(getActivity());
        String levels = sharedPrefManager.getSpLevel();

        nama = view.findViewById(R.id.name_user);
        nama.setText(sharedPrefManager.getSPNama());

        circleImageView = view.findViewById(R.id.user_image);
        Glide.with(this)
                .load(sharedPrefManager.getSP_GambarUser())
                .placeholder(R.drawable.nav_account)
                .into(circleImageView);

        edit_profile = view.findViewById(R.id.edit_profile);
        edit_profile.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), EditProfile.class);
            startActivity(intent);
        });

        level = view.findViewById(R.id.level);
        if (levels.equals("PLATINUM")){
            level.setText(sharedPrefManager.getSpLevel());
        } else if (levels.equals("GOLD")){
            level.setText(sharedPrefManager.getSpLevel());
        } else if (levels.equals("SILVER")){
            level.setText(sharedPrefManager.getSpLevel());
        } else if (levels.equals("BRONZE")){
            level.setText(sharedPrefManager.getSpLevel());
        }

        status = view.findViewById(R.id.status);
        status.setText(sharedPrefManager.getSpStatus());

        limit = view.findViewById(R.id.limit_akunmember);
//        limit.setText(sharedPrefManager.getSpLimit());


        if(levels.equals("PLATINUM")){
            limit.setText("UNLIMITED");
        } else if (levels.equals("GOLD")){
            limit.setText("UNLIMITED");
        } else if (levels.equals("SILVER")){
            limit.setText(sharedPrefManager.getSpLimit());
        } else if (levels.equals("BRONZE")){
            limit.setText(sharedPrefManager.getSpLimit());
        }

        countdown = view.findViewById(R.id.countdown);

        if (levels.equals("PLATINUM")) {
            Date date1 = new Date();

            String date = sharedPrefManager.getSpLastmember().substring(8, 10);
            String month = sharedPrefManager.getSpLastmember().substring(5, 7);
            String year = sharedPrefManager.getSpLastmember().substring(0, 4);
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();

            SimpleDateFormat t = new SimpleDateFormat("dd");
            SimpleDateFormat b = new SimpleDateFormat("MM");
            SimpleDateFormat y = new SimpleDateFormat("yyyy");

            int tanggal = Integer.parseInt(date);
            int bulan = Integer.parseInt(month);
            int tahun = Integer.parseInt(year);

            int tanggal1 = Integer.parseInt(t.format(date1));
            int bulan1 = Integer.parseInt(b.format(date1));
            int tahun1 = Integer.parseInt(y.format(date1));

            start.set(tahun1, bulan1, tanggal1);
            end.set(tahun, bulan, tanggal);

            Date startDate = start.getTime();
            Date endDate = end.getTime();
            long starttime = startDate.getTime();
            long endtime = endDate.getTime();

            long diff = endtime - starttime;
            long diffdays = diff / (1000 * 60 * 60 * 24);

            String waktu1 = String.valueOf(diffdays);

            countdown.setText(waktu1 + " Day");
        } else if (levels.equals("GOLD")) {
            Date date1 = new Date();

            String date = sharedPrefManager.getSpLastmember().substring(8, 10);
            String month = sharedPrefManager.getSpLastmember().substring(5, 7);
            String year = sharedPrefManager.getSpLastmember().substring(0, 4);
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();

            SimpleDateFormat t = new SimpleDateFormat("dd");
            SimpleDateFormat b = new SimpleDateFormat("MM");
            SimpleDateFormat y = new SimpleDateFormat("yyyy");

            int tanggal = Integer.parseInt(date);
            int bulan = Integer.parseInt(month);
            int tahun = Integer.parseInt(year);

            int tanggal1 = Integer.parseInt(t.format(date1));
            int bulan1 = Integer.parseInt(b.format(date1));
            int tahun1 = Integer.parseInt(y.format(date1));

            start.set(tahun1, bulan1, tanggal1);
            end.set(tahun, bulan, tanggal);

            Date startDate = start.getTime();
            Date endDate = end.getTime();
            long starttime = startDate.getTime();
            long endtime = endDate.getTime();

            long diff = endtime - starttime;
            long diffdays = diff / (1000 * 60 * 60 * 24);

            String waktu1 = String.valueOf(diffdays);

            countdown.setText(waktu1 + " Day");
        } else if (levels.equals("SILVER")) {
            Date date1 = new Date();

            String date = sharedPrefManager.getSpLastmember().substring(8, 10);
            String month = sharedPrefManager.getSpLastmember().substring(5, 7);
            String year = sharedPrefManager.getSpLastmember().substring(0, 4);
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();

            SimpleDateFormat t = new SimpleDateFormat("dd");
            SimpleDateFormat b = new SimpleDateFormat("MM");
            SimpleDateFormat y = new SimpleDateFormat("yyyy");

            int tanggal = Integer.parseInt(date);
            int bulan = Integer.parseInt(month);
            int tahun = Integer.parseInt(year);

            int tanggal1 = Integer.parseInt(t.format(date1));
            int bulan1 = Integer.parseInt(b.format(date1));
            int tahun1 = Integer.parseInt(y.format(date1));

            start.set(tahun1, bulan1, tanggal1);
            end.set(tahun, bulan, tanggal);

            Date startDate = start.getTime();
            Date endDate = end.getTime();
            long starttime = startDate.getTime();
            long endtime = endDate.getTime();

            long diff = endtime - starttime;
            long diffdays = diff / (1000 * 60 * 60 * 24);

            String waktu1 = String.valueOf(diffdays);

            countdown.setText(waktu1 + " Day");
        } else if (levels.equals("BRONZE")) {
            Date date1 = new Date();

            String date = sharedPrefManager.getSpLastmember().substring(8, 10);
            String month = sharedPrefManager.getSpLastmember().substring(5, 7);
            String year = sharedPrefManager.getSpLastmember().substring(0, 4);
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();

            SimpleDateFormat t = new SimpleDateFormat("dd");
            SimpleDateFormat b = new SimpleDateFormat("MM");
            SimpleDateFormat y = new SimpleDateFormat("yyyy");

            int tanggal = Integer.parseInt(date);
            int bulan = Integer.parseInt(month);
            int tahun = Integer.parseInt(year);

            int tanggal1 = Integer.parseInt(t.format(date1));
            int bulan1 = Integer.parseInt(b.format(date1));
            int tahun1 = Integer.parseInt(y.format(date1));

            start.set(tahun1, bulan1, tanggal1);
            end.set(tahun, bulan, tanggal);

            Date startDate = start.getTime();
            Date endDate = end.getTime();
            long starttime = startDate.getTime();
            long endtime = endDate.getTime();

            long diff = endtime - starttime;
            long diffdays = diff / (1000 * 60 * 60 * 24);

            String waktu1 = String.valueOf(diffdays);

            countdown.setText(waktu1 + " Day");
        }

        logout = view.findViewById(R.id.Logout);

        logout.setOnClickListener(v -> {
            sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
            startActivity(new Intent(getActivity(), Login.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            getActivity().finish();
        });

        return view;
    }

}