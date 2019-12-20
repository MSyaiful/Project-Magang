package com.warehousenesia.id.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.warehousenesia.id.Model.DataBank;
import com.warehousenesia.id.Model.DataCukaiFee;
import com.warehousenesia.id.R;

import java.util.ArrayList;

public class CukaiAdapter extends RecyclerView.Adapter<CukaiAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<DataCukaiFee> CukaiList;

    public CukaiAdapter(Context context, ArrayList<DataCukaiFee> CukaiList){
        inflater = LayoutInflater.from(context);
        this.CukaiList = CukaiList;
    }

    @NonNull
    @Override
    public CukaiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_beli_paket, parent, false);
        CukaiAdapter.ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CukaiAdapter.ViewHolder holder, int position) {
        holder.ppn.setText(CukaiList.get(position).getPpn());

    }

    @Override
    public int getItemCount(){return CukaiList.size();}
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView ppn;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            ppn = itemView.findViewById(R.id.ppn);

        }

    }
}