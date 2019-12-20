package com.warehousenesia.id.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.warehousenesia.id.Model.DataRequestProduct;
import com.warehousenesia.id.R;

import java.util.ArrayList;

public class RequestProductAdapter extends RecyclerView.Adapter<RequestProductAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<DataRequestProduct> ProductList;

    public RequestProductAdapter(Context context, ArrayList<DataRequestProduct> ProductList){
        inflater = LayoutInflater.from(context);
        this.ProductList = ProductList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.nama_pro.setText(ProductList.get(position).getNama_barang());
            holder.deskripsi_pro.setText(ProductList.get(position).getDeskripsi());
            holder.kategori_pro.setText(ProductList.get(position).getKategori());
            holder.status_pro.setText(ProductList.get(position).getStatus());

            Glide.with(holder.itemView.getContext())
                    .load("http://192.168.100.17:1997/"+ProductList.get(position).getGambar())
                    .apply(new RequestOptions().override(360, 360))
                    .into(holder.gambar_pro);

        }

    @Override
    public int getItemCount() {
        return ProductList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView nama_pro, deskripsi_pro, kategori_pro, status_pro, idmember;
        ImageView gambar_pro;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            nama_pro = itemView.findViewById(R.id.txt_item_product);
            deskripsi_pro = itemView.findViewById(R.id.txt_item_description);
            kategori_pro = itemView.findViewById(R.id.txt_item_category);
            status_pro = itemView.findViewById(R.id.txt_item_status);
            idmember = itemView.findViewById(R.id.idmember);
            gambar_pro = itemView.findViewById(R.id.img_item_product);

        }
    }

}
