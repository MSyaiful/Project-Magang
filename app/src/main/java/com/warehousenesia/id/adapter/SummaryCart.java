package com.warehousenesia.id.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.warehousenesia.id.Model.DataAll;
import com.warehousenesia.id.R;

import java.util.ArrayList;

public class SummaryCart extends RecyclerView.Adapter<SummaryCart.ViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<DataAll> SummaryBelanja;

    public SummaryCart(Context context, ArrayList<DataAll> SummaryBelanja){
        inflater = LayoutInflater.from(context);
        this.SummaryBelanja = SummaryBelanja;
    }

    @NonNull
    @Override
    public  SummaryCart.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        View view = inflater.inflate(R.layout.item_summary_cart, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SummaryCart.ViewHolder holder, int position) {
        holder.total_item.setText(SummaryBelanja.get(position).getJumlah_beli());
        holder.total_weight.setText(SummaryBelanja.get(position).getJumlah_beli());
        holder.total_harga1.setText(SummaryBelanja.get(position).getJumlah_beli());
        holder.transfee.setText(SummaryBelanja.get(position).getJumlah_beli());
        holder.shipfee.setText(SummaryBelanja.get(position).getJumlah_beli());
        holder.devfee.setText(SummaryBelanja.get(position).getJumlah_beli());
        holder.total_harga2.setText(SummaryBelanja.get(position).getJumlah_beli());
    }

    @Override
    public int getItemCount(){
        return SummaryBelanja.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView total_item, total_weight, total_harga1, transfee, shipfee, devfee, total_harga2;
        CardView detail_summary;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            total_item = itemView.findViewById(R.id.total_item);
            total_weight = itemView.findViewById(R.id.total_weight);
            total_harga1 = itemView.findViewById(R.id.total_harga1);
            transfee = itemView.findViewById(R.id.transfee_cart);
            shipfee = itemView.findViewById(R.id.shipping_fee);
            devfee = itemView.findViewById(R.id.delivery_fee);
            total_harga2 = itemView.findViewById(R.id.total_harga2);

        }

    }
}
