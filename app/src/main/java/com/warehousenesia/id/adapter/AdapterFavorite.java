package com.warehousenesia.id.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.warehousenesia.id.data.DataFavorite;
import com.warehousenesia.id.R;

import java.util.ArrayList;

public class AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.FavoriteHolder> {
    private ArrayList<DataFavorite> listFavorite;

    public AdapterFavorite(ArrayList<DataFavorite> list){
        this.listFavorite = list;
    }

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new FavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder holder, int position) {
        final DataFavorite dataFavorite = listFavorite.get(position);
        Glide.with(holder.itemView.getContext()).load(dataFavorite.getImg())
                .apply(new RequestOptions()).override(150, 150)
                .into(holder.imgProduct);

        int awal = dataFavorite.getPrice();

        int tambah = awal;

        holder.tvName.setText(dataFavorite.getName());
        holder.tvCountry.setText(dataFavorite.getCountry());
        holder.tvDesc.setText(dataFavorite.getDesc());
        holder.tvPrice.setText(String.valueOf(tambah));
    }

    @Override
    public int getItemCount() {
        return listFavorite.size();
    }

    class FavoriteHolder extends RecyclerView.ViewHolder{
        ImageView imgProduct;
        TextView tvName, tvCountry, tvDesc, tvPrice;
        FavoriteHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.img_item_product);
            tvName = itemView.findViewById(R.id.txt_item_product);
            tvCountry = itemView.findViewById(R.id.txt_item_country);
            tvDesc = itemView.findViewById(R.id.txt_item_description);
            tvPrice = itemView.findViewById(R.id.txt_item_price);
        }
    }
}
