package com.warehousenesia.id.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.warehousenesia.id.EditKeranjang;
import com.warehousenesia.id.MainActivity;
import com.warehousenesia.id.Model.DataAll;
import com.warehousenesia.id.R;
import com.warehousenesia.id.Service.ApiService;
import com.warehousenesia.id.Service.ApiUtil;
import com.warehousenesia.id.Service.GetClient;
import com.warehousenesia.id.SharedPrefManager;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MYcartAdapter extends RecyclerView.Adapter<MYcartAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<DataAll> Mycart;
    SharedPrefManager sharedPrefManager;
//    Context context;

    public MYcartAdapter(Context context, ArrayList<DataAll> Mycart) {
        inflater = LayoutInflater.from(context);
        this.Mycart = Mycart;
    }

    @NonNull
    @Override
    public MYcartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.item_detailkeranjang, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MYcartAdapter.ViewHolder viewHolder, int pb) {

        sharedPrefManager = new SharedPrefManager(viewHolder.itemView.getContext());
        String level = sharedPrefManager.getSpLevel();
        String h_barang = Mycart.get(pb).getHarga();
        String jb_quantity = String.valueOf(Mycart.get(pb).getJumlah_beli());
        String th_barang = String.valueOf(Mycart.get(pb).getTotal_harga());

        int total = 0;

        if (level.equals("PLATINUM")) {
            double jumlah = 15000 * 0.05;
            double akhir = 15000 + jumlah;
            total = (int) akhir;
        } else if (level.equals("GOLD")) {
            double jumlah = 15000 * 0.075;
            double akhir = 15000 + jumlah;
            total = (int) akhir;
        } else if (level.equals("SILVER")) {
            double jumlah = 15000 * 0.1;
            double akhir = 15000 + jumlah;
            total = (int) akhir;
        } else if (level.equals("BRONZE")) {
            double jumlah = 15000 * 0.15;
            double akhir = 15000 + jumlah;
            total = (int) akhir;
        }

        double harga_b = Double.parseDouble(h_barang);
        double quantity_jb = Double.parseDouble(jb_quantity);

        double real_harga = harga_b * total;

        real_harga = Math.round(real_harga);

        real_harga = (int) real_harga;

        String harga = String.valueOf(real_harga);

        String totals = String.valueOf(total);

        double tax_jumlah1 = harga_b * quantity_jb;

        double bea_masuk = 0.00;
        double ppn = 0.00;
        double ppnbh = 0.00;
        double pph = 0.00;

        if (tax_jumlah1 >= 5) {

            bea_masuk = tax_jumlah1 * 0.1;
            //        bea_masuk = Math.round(bea_masuk);
            //        bea_masuk = (int) bea_masuk;

            ppn = tax_jumlah1 * 0.1;
            //        ppn = Math.round(ppn);
            //        ppn = (int) ppn;

            pph = tax_jumlah1 * 0.075;
            //        pph = Math.round(pph);
            //        pph = (int) pph;

            ppnbh = tax_jumlah1 * 0.1;
            //        ppnbh = Math.round(ppnbh);
            //        ppnbh = (int) ppnbh;

        }

        double jumlah_persen = bea_masuk + ppn + pph + ppnbh;
        double tax_akhir = total * jumlah_persen;
        tax_akhir = Math.round(tax_akhir);
        tax_akhir = (int) tax_akhir;

        double th_asli = Double.parseDouble(th_barang);
        double tp_barang = total * th_asli;
        double tpa_barang = tp_barang + tax_akhir;
        tpa_barang = Math.round(tpa_barang);
        tpa_barang = (int) tpa_barang;

        double tp_akhir = Double.parseDouble(String.valueOf(tpa_barang));
        String tax_s = "0";
        if (tax_jumlah1 >= 5) {
            tax_s = String.valueOf(tax_akhir);
        }

        int jumlah_item = 0;
        for (int I = 0; I < getItemCount(); I++) {
            jumlah_item += Mycart.get(I).getJumlah_beli();
        }
        String total_ji = String.valueOf(jumlah_item);
        sharedPrefManager.saveSPString(SharedPrefManager.SP_TotalItemKeranjang2, total_ji);

        double jumlah_weight = 0;
        for (double a = 0; a < getItemCount(); a++) {
            jumlah_weight += Mycart.get((int) a).getTotal_berat();
        }
        String total_jw = String.valueOf(jumlah_weight);
        sharedPrefManager.saveSPString(SharedPrefManager.SP_TotalWeightKeranjang2, total_jw);

        double jumlah_harga1 = 0.000;
        for (double b = 0; b < getItemCount(); b++) {
            jumlah_harga1 += tp_akhir;
        }
        String total_jh = String.format("%.0f",jumlah_harga1);
        sharedPrefManager.saveSPString(SharedPrefManager.SP_TotalHargaKeranjang2, total_jh);


        viewHolder.name_customer.setText(Mycart.get(pb).getNama_lengkap());
        viewHolder.name_product.setText(Mycart.get(pb).getNama_produk());
        viewHolder.weight.setText(Mycart.get(pb).getBerat());
        viewHolder.currency.setText(totals);
        viewHolder.dollar.setText(h_barang);
        viewHolder.tax.setText(tax_s);
        viewHolder.rupiah.setText(harga);
        viewHolder.quantity.setText(String.valueOf(Mycart.get(pb).getJumlah_beli()));
        viewHolder.total_price.setText(String.format("%.0f",tp_akhir));
        viewHolder.total_weight.setText(String.valueOf(Mycart.get(pb).getTotal_berat()));
        viewHolder.origin.setText(Mycart.get(pb).getNegara());
        viewHolder.idagent.setText(Mycart.get(pb).getId_agent());
        viewHolder.idcustomer.setText(Mycart.get(pb).getId_customer());
        viewHolder.idproduk.setText(Mycart.get(pb).getId_produk());
        viewHolder.catatan.setText(Mycart.get(pb).getCatatan());
        viewHolder.delete_keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = GetClient.getClient(ApiUtil.Base_url);
                ApiService apiService = retrofit.create(ApiService.class);
                String id_agent = Mycart.get(pb).getId_agent();
                String id_cus = Mycart.get(pb).getId_customer();
                String id_produk = Mycart.get(pb).getId_produk();

                Call<ResponseBody> delete = apiService.deleteKeranjang(id_agent, id_cus, id_produk);
                delete.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Intent intent = new Intent(viewHolder.itemView.getContext(), MainActivity.class);
                        viewHolder.itemView.getContext().startActivity(intent);
                        Toast.makeText(viewHolder.itemView.getContext(), "Sukses", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
        viewHolder.edit_keranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefManager = new SharedPrefManager(viewHolder.itemView.getContext());
                String level = sharedPrefManager.getSpLevel();
                String h_barang = Mycart.get(pb).getHarga();
                String n_customer = Mycart.get(pb).getNama_lengkap();
                String n_produk = Mycart.get(pb).getNama_produk();
                String p_berat = Mycart.get(pb).getBerat();
                String j_beli = String.valueOf(Mycart.get(pb).getJumlah_beli());
                String t_harga = String.valueOf(Mycart.get(pb).getTotal_harga());
                String t_berat = String.valueOf(Mycart.get(pb).getTotal_berat());
                String negara = Mycart.get(pb).getNegara();
                String p_idagent = Mycart.get(pb).getId_agent();
                String p_idcustomer = Mycart.get(pb).getId_customer();
                String p_idproduk = Mycart.get(pb).getId_produk();
                String p_idkeranjang = Mycart.get(pb).getId();
                String p_catatan = Mycart.get(pb).getCatatan();


                int total = 0;

                if (level.equals("PLATINUM")) {
                    double jumlah = 15000 * 0.05;
                    double akhir = 15000 + jumlah;
                    total = (int) akhir;
                } else if (level.equals("GOLD")) {
                    double jumlah = 15000 * 0.075;
                    double akhir = 15000 + jumlah;
                    total = (int) akhir;
                } else if (level.equals("SILVER")) {
                    double jumlah = 15000 * 0.1;
                    double akhir = 15000 + jumlah;
                    total = (int) akhir;
                } else if (level.equals("BRONZE")) {
                    double jumlah = 15000 * 0.15;
                    double akhir = 15000 + jumlah;
                    total = (int) akhir;
                }

                double harga_b = Double.parseDouble(h_barang);

                double real_harga = harga_b * total;

                real_harga = Math.round(real_harga);

                real_harga = (int) real_harga;

                String harga = String.valueOf(real_harga);

                String totals = String.valueOf(total);

                sharedPrefManager.saveSPString(SharedPrefManager.SP_CurrencyProduk, totals);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_HargaProduk, h_barang);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_Rupiah, harga);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_NCProduk, n_customer);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_NamaProduk, n_produk);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_BeratProduk, p_berat);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_JumlahBeli, j_beli);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_TotalHarga, t_harga);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_TotalBerat, t_berat);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_Origin, negara);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_IDAProduk, p_idagent);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_IDCProduk, p_idcustomer);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_IdProduk, p_idproduk);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_IdKeranjang, p_idkeranjang);
                sharedPrefManager.saveSPString(SharedPrefManager.SP_Catatan, p_catatan);

                Intent intent = new Intent(viewHolder.itemView.getContext(), EditKeranjang.class);
                viewHolder.itemView.getContext().startActivity(intent);
            }
        });

//        viewHolder.idagent.setText(Mycart.get(pb).getId_agent());
    }

    @Override
    public int getItemCount() {
        return Mycart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name_customer, name_product, weight, currency, dollar, rupiah, tax, quantity, total_price, total_weight, origin, catatan, idagent, idcustomer, idproduk, id_keranjang;
        ImageView edit_keranjang, delete_keranjang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name_customer = itemView.findViewById(R.id.customer_name);
            name_product = itemView.findViewById(R.id.product_name);
            weight = itemView.findViewById(R.id.product_weight);
            currency = itemView.findViewById(R.id.product_currency);
            dollar = itemView.findViewById(R.id.product_dollar);
            rupiah = itemView.findViewById(R.id.product_rupiah);
            tax = itemView.findViewById(R.id.product_tax);
            quantity = itemView.findViewById(R.id.product_quantity);
            total_price = itemView.findViewById(R.id.product_totalprice);
            total_weight = itemView.findViewById(R.id.product_totalweight);
            origin = itemView.findViewById(R.id.product_origin);
            catatan = itemView.findViewById(R.id.product_catatan);
            edit_keranjang = itemView.findViewById(R.id.edit_keranjang);
            delete_keranjang = itemView.findViewById(R.id.delete_keranjang);
            idagent = itemView.findViewById(R.id.det_idagent);
            idcustomer = itemView.findViewById(R.id.det_idcustomer);
            idproduk = itemView.findViewById(R.id.det_idproduk);
            id_keranjang = itemView.findViewById(R.id.det_idkeranjang);

        }

    }
}
