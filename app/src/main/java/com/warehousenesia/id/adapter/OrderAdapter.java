package com.warehousenesia.id.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.warehousenesia.id.DetailOrder;
import com.warehousenesia.id.Model.DataOrder;
import com.warehousenesia.id.Order;
import com.warehousenesia.id.R;
import com.warehousenesia.id.SharedPrefManager;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<DataOrder> listOrder;
    SharedPrefManager sharedPrefManager;

    public OrderAdapter(Context context, ArrayList<DataOrder> listOrder){
        inflater = LayoutInflater.from(context);
        this.listOrder = listOrder;
    }
    
    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        sharedPrefManager = new SharedPrefManager(holder.itemView.getContext());
        String id_trans = listOrder.get(position).getId_transaksi();
        sharedPrefManager.saveSPString(SharedPrefManager.SP_IdTransaksi, id_trans);
        holder.id_transaksi.setText(listOrder.get(position).getId_transaksi());
        holder.total_bayar.setText(listOrder.get(position).getTotal_bayar());
        holder.status.setText(listOrder.get(position).getIs_finished());
        holder.detail.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailOrder.class);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listOrder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView id_transaksi, total_bayar, status;
        CardView detail;

        public ViewHolder(View view) {
            super(view);

            id_transaksi = view.findViewById(R.id.kode_transaksi);
            total_bayar = view.findViewById(R.id.total_bayar);
            status = view.findViewById(R.id.status);
            detail = view.findViewById(R.id.detail);

        }
    }
}
