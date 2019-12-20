package com.warehousenesia.id.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.warehousenesia.id.Model.DataOrderDetailCustomer;
import com.warehousenesia.id.R;
import com.warehousenesia.id.SharedPrefManager;

import java.util.ArrayList;

public class OrderDetailCustomerAdapter extends RecyclerView.Adapter<OrderDetailCustomerAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<DataOrderDetailCustomer> list;
    SharedPrefManager sharedPrefManager;

    public OrderDetailCustomerAdapter(Context context, ArrayList<DataOrderDetailCustomer>list){
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @NonNull
    @Override
    public OrderDetailCustomerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_order_detail2, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderDetailCustomerAdapter.ViewHolder holder, int position) {
        sharedPrefManager = new SharedPrefManager(holder.itemView.getContext());
        String level = sharedPrefManager.getSpLevel();

        int total = 0;

        if(level.equals("PLATINUM")){
            double jumlah = 15000 * 0.05;
            double akhir = 15000 + jumlah;
            total = (int) akhir;
        }else if(level.equals("GOLD")){
            double jumlah = 15000 * 0.075;
            double akhir = 15000 + jumlah;
            total = (int) akhir;
        }else if(level.equals("SILVER")){
            double jumlah = 15000 * 0.1;
            double akhir = 15000 + jumlah;
            total = (int) akhir;
        }else if (level.equals("BRONZE")){
            double jumlah = 15000 * 0.15;
            double akhir = 15000 + jumlah;
            total = (int) akhir;
        }

        int totals = Integer.parseInt(String.valueOf(total));

        int harga = Integer.parseInt(list.get(position).getHarga());
        double hrgrupiah = harga * totals;
        int rupiah = (int) hrgrupiah;
        String hargaBarang = list.get(position).getHarga();
        int harga_barang = Integer.parseInt(hargaBarang);
        int jumlah_beli = Integer.parseInt(list.get(position).getJumlah_beli());
        double pajak = harga_barang * jumlah_beli;
        double bea_masuk = pajak * 0.1;
        double ppn = pajak * 0.1;
        double pph = pajak * 0.075;
        double ppnbh = pajak * 0.1;
        int bea_masuk1 = (int) bea_masuk;
        int ppn1 = (int) ppn;
        int pph1 = (int) pph;
        int ppnbh1 = (int) ppnbh;

        double jumlah_persen = bea_masuk1 + ppn1 + pph1 + ppnbh1;
        int persen = (int) jumlah_persen;
        double tax_akhir = total * persen;
        tax_akhir = Math.round(tax_akhir);
        tax_akhir = (int) tax_akhir;

        int tax_s = 0;
        if (pajak >= 5){
            tax_s = (int) tax_akhir;
        }

        holder.namaCustomer.setText(list.get(position).getNama_lengkap());
        holder.namaProduk.setText(list.get(position).getNama_produk());
        holder.hargaRupiah.setText("Rp. " + String.valueOf(rupiah));
        holder.hargaDollar.setText("$ " + list.get(position).getHarga());
        holder.nilaiTukar.setText("Rp. " + String.valueOf(totals));
        holder.pajak.setText("Rp. " + tax_s);
        holder.berat.setText(list.get(position).getBerat() + " gram");
        holder.jumlah.setText(list.get(position).getJumlah_beli());
        holder.totalBerat.setText(list.get(position).getTotal_berat() + " gram");
        holder.totalHarga.setText("$ " + list.get(position).getTotal_harga());
        holder.negara.setText(list.get(position).getNegara());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaCustomer, namaProduk, hargaRupiah, hargaDollar, nilaiTukar, pajak, berat, jumlah, totalBerat, totalHarga, negara;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            namaCustomer = itemView.findViewById(R.id.nama_customer);
            namaProduk = itemView.findViewById(R.id.nama_produk);
            hargaRupiah = itemView.findViewById(R.id.product_rupiah);
            hargaDollar = itemView.findViewById(R.id.product_dollar);
            nilaiTukar = itemView.findViewById(R.id.nilai_tukar);
            pajak = itemView.findViewById(R.id.product_tax);
            berat = itemView.findViewById(R.id.berat);
            jumlah = itemView.findViewById(R.id.jumlah);
            totalBerat = itemView.findViewById(R.id.total_berat);
            totalHarga = itemView.findViewById(R.id.total_harga);
            negara = itemView.findViewById(R.id.negara);

        }
    }
}
