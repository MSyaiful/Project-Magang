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
import com.warehousenesia.id.Model.DataProductBest;
import com.warehousenesia.id.R;

import java.util.ArrayList;


public class ProductBestAdapter extends RecyclerView.Adapter<ProductBestAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<DataProductBest> ProductBestList;

    public ProductBestAdapter(Context context, ArrayList<DataProductBest> ProductBestList){
//        inflater = LayoutInflater.from(context);
        this.ProductBestList = ProductBestList;
    }

    @NonNull
    @Override
    public  ProductBestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_best_product, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductBestAdapter.ViewHolder viewHolder, final int pb) {

        viewHolder.nama_pro2.setText(ProductBestList.get(pb).getNama_produk());
        viewHolder.harga_pro2.setText("$ " + ProductBestList.get(pb).getHarga());
        viewHolder.nagara_pro2.setText(ProductBestList.get(pb).getNama_negara());
        Glide.with(viewHolder.itemView.getContext())
                .load("http://192.168.100.17:1997/"+ProductBestList.get(pb).getGambar())
                .apply(new RequestOptions().override(360, 360))
                .into(viewHolder.gambar_pro2);

        viewHolder.detail_pro2.setOnClickListener(view -> {
            Intent intent = new Intent(viewHolder.itemView.getContext(), DetailProduct.class);
            intent.putExtra("p_id", ProductBestList.get(pb).getId());
            intent.putExtra("p_name", ProductBestList.get(pb).getNama_produk());
            intent.putExtra("p_deskripsi", ProductBestList.get(pb).getDeskripsi());
            intent.putExtra("p_harga", ProductBestList.get(pb).getHarga());
            intent.putExtra("p_negara", ProductBestList.get(pb).getNama_negara());
            intent.putExtra("p_berat", ProductBestList.get(pb).getBerat());
            intent.putExtra("p_kategori", ProductBestList.get(pb).getNama_kategori());
            intent.putExtra("p_gambar", "http://192.168.100.17:1997/"+ProductBestList.get(pb).getGambar());
            viewHolder.itemView.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount(){return ProductBestList.size();}
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nama_pro2, harga_pro2, nagara_pro2;
        ImageView gambar_pro2;
        CardView detail_pro2;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            nama_pro2 = itemView.findViewById(R.id.txt_item_product);
            harga_pro2 = itemView.findViewById(R.id.txt_item_price);
            nagara_pro2 = itemView.findViewById(R.id.txt_item_country);
            gambar_pro2 = itemView.findViewById(R.id.img_item_product);
            detail_pro2 = itemView.findViewById(R.id.card_item);

        }

    }
}
