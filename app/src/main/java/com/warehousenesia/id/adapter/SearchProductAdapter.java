package com.warehousenesia.id.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.warehousenesia.id.DetailProduct;
import com.warehousenesia.id.Model.DataProductSearch;
import com.warehousenesia.id.R;

import java.util.ArrayList;

public class SearchProductAdapter extends RecyclerView.Adapter<SearchProductAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<DataProductSearch> searhProduct;

    public SearchProductAdapter(Context context, ArrayList<DataProductSearch> searhProduct){
//        inflater = LayoutInflater.from(context);
        this.searhProduct = searhProduct;
    }

    @NonNull
    @Override
    public SearchProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_search, parent, false);
        return new SearchProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchProductAdapter.ViewHolder holder, int position) {

        holder.nama_pro.setText(searhProduct.get(position).getNama_produk());
        holder.harga_pro.setText("$ " + searhProduct.get(position).getHarga());
        holder.negara_pro.setText(searhProduct.get(position).getNama_negara());
        Glide.with(holder.itemView.getContext())
                .load("http://192.168.100.17:1997/"+searhProduct.get(position).getGambar())
                .apply(new RequestOptions().override(360, 360))
                .into(holder.gambar_pro);

        holder.detail_pro.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailProduct.class);
            intent.putExtra("p_name", searhProduct.get(position).getNama_produk());
            intent.putExtra("p_deskripsi", searhProduct.get(position).getDeskripsi());
            intent.putExtra("p_harga", searhProduct.get(position).getHarga());
            intent.putExtra("p_negara", searhProduct.get(position).getNama_negara());
            intent.putExtra("p_berat", searhProduct.get(position).getBerat());
            intent.putExtra("p_kategori", searhProduct.get(position).getNama_kategori());
            intent.putExtra("p_gambar", "http://192.168.100.17:1997/"+searhProduct.get(position).getGambar());
            holder.itemView.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return searhProduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama_pro, harga_pro, negara_pro;
        ImageView gambar_pro;
        CardView detail_pro;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            nama_pro = itemView.findViewById(R.id.txt_item_product);
            harga_pro = itemView.findViewById(R.id.txt_item_price);
            negara_pro = itemView.findViewById(R.id.txt_item_country);
            gambar_pro = itemView.findViewById(R.id.img_item_product);
            detail_pro = itemView.findViewById(R.id.card_item);

        }
    }
}
