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
import com.warehousenesia.id.Model.DataProductOffer;
import com.warehousenesia.id.R;

import java.util.ArrayList;


public class ProductOfferAdapter extends RecyclerView.Adapter<ProductOfferAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<DataProductOffer> ProductOfferList;

    public ProductOfferAdapter(Context context, ArrayList<DataProductOffer> ProductOfferList){
//        inflater = LayoutInflater.from(context);
        this.ProductOfferList = ProductOfferList;
    }

    @NonNull
    @Override
    public  ProductOfferAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_best_offers, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductOfferAdapter.ViewHolder viewHolder, final int i) {

        viewHolder.nama_pro3.setText(ProductOfferList.get(i).getNama_produk());
        viewHolder.harga_pro3.setText("$ " + ProductOfferList.get(i).getHarga());
        viewHolder.nagara_pro3.setText(ProductOfferList.get(i).getNama_negara());
        Glide.with(viewHolder.itemView.getContext())
                .load("http://192.168.100.17:1997/"+ProductOfferList.get(i).getGambar())
                .apply(new RequestOptions().override(360, 360))
                .into(viewHolder.gambar_pro3);

        viewHolder.detail_pro3.setOnClickListener(view -> {
            Intent intent = new Intent(viewHolder.itemView.getContext(), DetailProduct.class);
            intent.putExtra("p_id", ProductOfferList.get(i).getId());
            intent.putExtra("p_name", ProductOfferList.get(i).getNama_produk());
            intent.putExtra("p_deskripsi", ProductOfferList.get(i).getDeskripsi());
            intent.putExtra("p_harga", ProductOfferList.get(i).getHarga());
            intent.putExtra("p_negara", ProductOfferList.get(i).getNama_negara());
            intent.putExtra("p_berat", ProductOfferList.get(i).getBerat());
            intent.putExtra("p_kategori", ProductOfferList.get(i).getNama_kategori());
            intent.putExtra("p_gambar", "http://192.168.100.17:1997/"+ProductOfferList.get(i).getGambar());
            viewHolder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount(){return ProductOfferList.size();}
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nama_pro3, harga_pro3, nagara_pro3;
        ImageView gambar_pro3;
        CardView detail_pro3;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            nama_pro3 = itemView.findViewById(R.id.txt_item_product);
            harga_pro3 = itemView.findViewById(R.id.txt_item_price);
            nagara_pro3 = itemView.findViewById(R.id.txt_item_country);
            gambar_pro3 = itemView.findViewById(R.id.img_item_product);
            detail_pro3 = itemView.findViewById(R.id.card_item);

        }

    }
}

