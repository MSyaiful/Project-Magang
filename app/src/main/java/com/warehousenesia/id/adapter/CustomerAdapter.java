package com.warehousenesia.id.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.warehousenesia.id.DetailCustomer;
import com.warehousenesia.id.DetailProduct;
import com.warehousenesia.id.EditCustomer;
import com.warehousenesia.id.Model.DataCustomer;
import com.warehousenesia.id.R;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<DataCustomer> CustomerList;

    public CustomerAdapter(Context context, ArrayList<DataCustomer> CustomerList){
        inflater = LayoutInflater.from(context);
        this.CustomerList = CustomerList;
    }

    @NonNull
    @Override
    public CustomerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_customer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomerAdapter.ViewHolder holder, final int position) {
        holder.nama_lengkap.setText(CustomerList.get(position).getNama_lengkap());
        holder.phone.setText(CustomerList.get(position).getNumber());
        holder.id_customer.setText(CustomerList.get(position).getId_customer());
        holder.alamat.setText(CustomerList.get(position).getAlamat());
        holder.kecamatan.setText(CustomerList.get(position).getKecamatan());
        holder.kota.setText(CustomerList.get(position).getKota());
        holder.provinsi.setText(CustomerList.get(position).getProvinsi());
        holder.ktp.setText(CustomerList.get(position).getId_ktp());
        holder.npwp.setText(CustomerList.get(position).getNpwp());
        holder.kodePos.setText(CustomerList.get(position).getKodepos());

        holder.detail.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailCustomer.class);
            intent.putExtra("d_idcustomer", CustomerList.get(position).getId_customer());
            intent.putExtra("d_idagent", CustomerList.get(position).getId_agent());
            intent.putExtra("d_name", CustomerList.get(position).getNama_lengkap());
            intent.putExtra("d_telpon", CustomerList.get(position).getNumber());
            intent.putExtra("d_alamat", CustomerList.get(position).getAlamat());
            intent.putExtra("d_kecamatan", CustomerList.get(position).getKecamatan());
            intent.putExtra("d_kota", CustomerList.get(position).getKota());
            intent.putExtra("d_provinsi", CustomerList.get(position).getProvinsi());
            intent.putExtra("d_ktp", CustomerList.get(position).getId_ktp());
            intent.putExtra("d_npwp", CustomerList.get(position).getNpwp());
            intent.putExtra("d_kodePos", CustomerList.get(position).getKodepos());
            holder.itemView.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return CustomerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nama_lengkap, phone, id_customer, alamat, kecamatan, kota, provinsi, ktp, npwp, kodePos, id_agent;
        TableRow detail;
        ImageView edit;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            edit = itemView.findViewById(R.id.edit);
            nama_lengkap = itemView.findViewById(R.id.nama_lengkap);
            phone = itemView.findViewById(R.id.no_telpon);
            id_customer = itemView.findViewById(R.id.id_customer);
            id_agent = itemView.findViewById(R.id.id_agent);
            alamat = itemView.findViewById(R.id.alamat);
            kecamatan = itemView.findViewById(R.id.kecamatan);
            kota = itemView.findViewById(R.id.kota);
            provinsi = itemView.findViewById(R.id.provinsi);
            ktp = itemView.findViewById(R.id.id_ktp);
            npwp = itemView.findViewById(R.id.npwp);
            kodePos = itemView.findViewById(R.id.kode_pos);
            detail = itemView.findViewById(R.id.head_table);

        }

    }

}
