package com.warehousenesia.id.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.warehousenesia.id.Model.DataBank;
import com.warehousenesia.id.Model.DataProduct;
import com.warehousenesia.id.R;

import java.util.ArrayList;

public class BankAdapter extends RecyclerView.Adapter<BankAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<DataBank> BankList;

    public BankAdapter(Context context, ArrayList<DataBank> BankList){
        inflater = LayoutInflater.from(context);
        this.BankList = BankList;
    }

    @NonNull
    @Override
    public BankAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_bank, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BankAdapter.ViewHolder holder, int position) {
        holder.atasnama.setText(BankList.get(position).getAtasnama());
        holder.nama_bank.setText(BankList.get(position).getNama_bank());
        holder.norek.setText(BankList.get(position).getNorek());
        Glide.with(holder.itemView.getContext())
                .load("http://192.168.100.17:1997/" + BankList.get(position).getGambar())
                .apply(new RequestOptions().override(100, 100))
                .into(holder.gambar_bank);

    }

    @Override
    public int getItemCount(){return BankList.size();}
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView norek, atasnama, nama_bank;
        ImageView gambar_bank;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            gambar_bank = itemView.findViewById(R.id.gambar_bank);
            norek = itemView.findViewById(R.id.norek);
            nama_bank = itemView.findViewById(R.id.nama_bank);
            atasnama = itemView.findViewById(R.id.atas_nama);

        }

    }
}