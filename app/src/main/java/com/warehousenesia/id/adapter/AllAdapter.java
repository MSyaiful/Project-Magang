package com.warehousenesia.id.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.warehousenesia.id.DetailKeranjangActivity;
import com.warehousenesia.id.R;
import com.warehousenesia.id.SharedPrefManager;

import java.util.ArrayList;

public class AllAdapter extends RecyclerView.Adapter<AllAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<com.warehousenesia.id.Model.DataAll> DataAll;
    SharedPrefManager sharedPrefManager;

    public AllAdapter(Context context, ArrayList<com.warehousenesia.id.Model.DataAll> DataAll) {
        inflater = LayoutInflater.from(context);
        this.DataAll = DataAll;
    }

    @NonNull
    @Override
    public AllAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.item_cart, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        sharedPrefManager = new SharedPrefManager(holder.itemView.getContext());
        int jumlah_item = 0;
        for (int I= 0; I<getItemCount(); I++) {
            jumlah_item+=DataAll.get(I).getJumlah_beli();
        }
        String total_ji = String.valueOf(jumlah_item);
        sharedPrefManager.saveSPString(SharedPrefManager.SP_TotalItemKeranjang, total_ji);

        double jumlah_weight = 0.000;
        for (double a=0; a<getItemCount(); a++){
            jumlah_weight+=DataAll.get((int) a).getTotal_berat();
        }
        String total_jw = String.valueOf(jumlah_weight);
        sharedPrefManager.saveSPString(SharedPrefManager.SP_TotalWeightKeranjang, total_jw);

        double jumlah_harga1 = 0.000;
        for (double b=0; b<getItemCount(); b++){
            jumlah_harga1+=DataAll.get((int) b).getTotal_harga();
        }
        String total_jh = String.valueOf(jumlah_harga1);
        sharedPrefManager.saveSPString(SharedPrefManager.SP_TotalHargaKeranjang, total_jh);

        String negara_ship = DataAll.get(position).getNegara();
        sharedPrefManager.saveSPString(SharedPrefManager.SP_ShipNegara, negara_ship);

        holder.nama_customer.setText(DataAll.get(position).getNama_lengkap());
        holder.jumlah_beli.setText(String.valueOf(DataAll.get(position).getJumlah_beli()));
        holder.harga_product.setText(DataAll.get(position).getNegara());
        holder.idagent.setText(DataAll.get(position).getId_agent());
        holder.idcustomer.setText(DataAll.get(position).getId_customer());
        holder.idproduk.setText(DataAll.get(position).getId_produk());
        holder.detail_cart.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailKeranjangActivity.class);
            intent.putExtra("k_namacustomer", DataAll.get(position).getNama_lengkap());
            intent.putExtra("k_namaproduct", DataAll.get(position).getNama_produk());
            intent.putExtra("k_weight", DataAll.get(position).getBerat());
            intent.putExtra("k_dollar", DataAll.get(position).getHarga());
            intent.putExtra("detk_quantity", DataAll.get(position).getJumlah_beli());
            intent.putExtra("k_totalprice", DataAll.get(position).getTotal_harga());
            intent.putExtra("detk_totalweight", DataAll.get(position).getTotal_berat());
            intent.putExtra("k_origin", DataAll.get(position).getNegara());
            intent.putExtra("k_idagent", DataAll.get(position).getId_agent());
            intent.putExtra("k_idcustomer", DataAll.get(position).getId_customer());
            intent.putExtra("k_idproduk", DataAll.get(position).getId_produk());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return DataAll.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nama_customer, jumlah_beli, harga_product, idagent, idcustomer, idproduk, item_cart;
        CardView detail_cart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            idagent = itemView.findViewById(R.id.keranjang_idagent);
            idcustomer = itemView.findViewById(R.id.keranjang_idcustomer);
            idproduk = itemView.findViewById(R.id.keranjang_idproduk);
            nama_customer = itemView.findViewById(R.id.nama_customer);
            jumlah_beli = itemView.findViewById(R.id.jumlah_beli);
            harga_product = itemView.findViewById(R.id.harga_barang);
            detail_cart = itemView.findViewById(R.id.card_keranjang);
            item_cart = itemView.findViewById(R.id.total_itemcart);

        }

    }
}
