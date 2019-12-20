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
import com.warehousenesia.id.data.DataCart;
import com.warehousenesia.id.R;

import java.util.ArrayList;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.CartHolder> {
    private ArrayList<DataCart> listCart;

    public AdapterCart(ArrayList<DataCart> list){
        this.listCart = list;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        final DataCart dataCart = listCart.get(position);
        Glide.with(holder.itemView.getContext()).load(dataCart.getImg())
                .apply(new RequestOptions()).override(100, 70)
                .into(holder.imgProduct);

        int awal = dataCart.getPrice();

        int tambah = awal;

        holder.tvName.setText(dataCart.getName());
        holder.tvCountry.setText(dataCart.getCountry());
        holder.tvPrice.setText(String.valueOf(tambah));
    }

    @Override
    public int getItemCount() {
        return listCart.size();
    }

    class CartHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvName, tvCountry, tvPrice;
        CartHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.img_item_product);
            tvName = itemView.findViewById(R.id.txt_item_product);
            tvCountry = itemView.findViewById(R.id.txt_item_country);
            tvPrice = itemView.findViewById(R.id.txt_item_price);
        }
    }
}
