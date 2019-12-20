package com.warehousenesia.id;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopperFragment extends Fragment {
    private TextView logout, nama;
    SharedPrefManager sharedPrefManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopper, container, false);

        sharedPrefManager = new SharedPrefManager(getActivity());

        nama = view.findViewById(R.id.name_user);
        nama.setText(sharedPrefManager.getSPNama());

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
