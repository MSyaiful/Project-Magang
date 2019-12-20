package com.warehousenesia.id.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.warehousenesia.id.Model.DataListPayment;
import com.warehousenesia.id.R;

import java.util.ArrayList;

public class ListPaymentAdapter extends RecyclerView.Adapter<ListPaymentAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<DataListPayment> listPayments;
    Context context;

    public ListPaymentAdapter(Context context, ArrayList<DataListPayment>listPayments){
        inflater = LayoutInflater.from(context);
        this.listPayments = listPayments;
        this.context = context;
    }

    @NonNull
    @Override
    public ListPaymentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list_payment, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPaymentAdapter.ViewHolder holder, int position) {

        holder.id_transaksi.setText(listPayments.get(position).getId_transaksi());
        holder.total_bayar.setText("Rp. " + listPayments.get(position).getTotal_bayar());
        holder.total_berat.setText(listPayments.get(position).getTotal_berat() + " gr");
        holder.head.setOnClickListener(view -> {
            view.setBackgroundColor(Color.LTGRAY);
            String text = (String) holder.id_transaksi.getText();
            Intent intent = new Intent("id_transaksi");
            intent.putExtra("id", text);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
//            holder.itemView.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return listPayments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_transaksi, total_bayar, total_berat;
        TableRow head;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id_transaksi = itemView.findViewById(R.id.id_transaksi);
            total_bayar = itemView.findViewById(R.id.total_bayar);
            total_berat = itemView.findViewById(R.id.total_berat);
            head = itemView.findViewById(R.id.head_table);

        }
    }
}
