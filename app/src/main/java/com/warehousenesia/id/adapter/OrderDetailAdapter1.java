package com.warehousenesia.id.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.warehousenesia.id.Model.DataOrder;
import com.warehousenesia.id.OrderDetailCustomer;
import com.warehousenesia.id.R;
import com.warehousenesia.id.SharedPrefManager;

import java.util.ArrayList;

public class OrderDetailAdapter1 extends RecyclerView.Adapter<OrderDetailAdapter1.ViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<DataOrder> listOrder;
    SharedPrefManager sharedPrefManager;

    public OrderDetailAdapter1(Context context, ArrayList<DataOrder> listOrder){
        inflater = LayoutInflater.from(context);
        this.listOrder = listOrder;
    }

    @NonNull
    @Override
    public OrderDetailAdapter1.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_order_detail1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter1.ViewHolder holder, int position) {
        sharedPrefManager = new SharedPrefManager(holder.itemView.getContext());
        String id_cus = listOrder.get(position).getId_customer();
        String id_tran = listOrder.get(position).getId_transaksi();
        holder.nama_customer.setText(listOrder.get(position).getNama_lengkap());
        holder.jumlah_barang.setText(listOrder.get(position).getJumlah_beli());
        holder.detail.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), OrderDetailCustomer.class);
            intent.putExtra("id_transaksi", id_tran);
            intent.putExtra("id_customer", id_cus);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listOrder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nama_customer, jumlah_barang;
        Button detail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nama_customer = itemView.findViewById(R.id.nama_customer);
            jumlah_barang = itemView.findViewById(R.id.jumlah_barang);
            detail = itemView.findViewById(R.id.detail);

        }
    }

}
