package com.warehousenesia.id.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.warehousenesia.id.BeliPaket;
import com.warehousenesia.id.Model.DataPaket;
import com.warehousenesia.id.R;
import com.warehousenesia.id.SharedPrefManager;

import java.util.ArrayList;

public class PaketAdapter extends RecyclerView.Adapter<PaketAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<DataPaket> PaketList;

    SharedPrefManager sharedPrefManager;

    public PaketAdapter(Context ctx, ArrayList<DataPaket> PaketList){
        inflater = LayoutInflater.from(ctx);
        this.PaketList = PaketList;
    }

    @NonNull
    @Override
    public PaketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int holder) {
        View view = inflater.inflate(R.layout.item_paket, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        sharedPrefManager = new SharedPrefManager(holder.itemView.getContext());
        holder.id_paket.setText(PaketList.get(position).getId());
        holder.nama_paket.setText(PaketList.get(position).getNama());
        holder.harga_paket.setText("IDR " + PaketList.get(position).getPrice());
        holder.detail1.setText(PaketList.get(position).getDetail1());
        holder.detail2.setText(PaketList.get(position).getDetail2());
        holder.detail3.setText(PaketList.get(position).getDetail3());
        holder.detail4.setText(PaketList.get(position).getDetail4());
        holder.detail5.setText(PaketList.get(position).getDetail5());


        holder.subscribe.setOnClickListener(view -> {
            String Id_bank = PaketList.get(position).getId();
            sharedPrefManager.saveSPString(SharedPrefManager.SP_BANKID,Id_bank);
            Intent intent = new Intent(holder.itemView.getContext(), BeliPaket.class);
            intent.putExtra("id_paket", PaketList.get(position).getId());
            intent.putExtra("jenis_paket", PaketList.get(position).getNama());
            intent.putExtra("harga_paket", PaketList.get(position).getPrice());
            intent.putExtra("detail1", PaketList.get(position).getDetail1());
            intent.putExtra("detail2", PaketList.get(position).getDetail2());
            intent.putExtra("detail3", PaketList.get(position).getDetail3());
            intent.putExtra("detail4", PaketList.get(position).getDetail4());
            intent.putExtra("detail5", PaketList.get(position).getDetail5());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount(){return PaketList.size();}
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nama_paket, harga_paket, detail1, detail2, detail3, detail4, detail5, id_paket;
        CardView detail_paket;
        Button subscribe;


        public ViewHolder(@NonNull View itemView){
            super(itemView);

            id_paket = itemView.findViewById(R.id.id_paket);
            nama_paket = itemView.findViewById(R.id.nama_paket);
            harga_paket = itemView.findViewById(R.id.harga_paket);
            detail1 = itemView.findViewById(R.id.detail1);
            detail2 = itemView.findViewById(R.id.detail2);
            detail3 = itemView.findViewById(R.id.detail3);
            detail4 = itemView.findViewById(R.id.detail4);
            detail5 = itemView.findViewById(R.id.detail5);
            detail_paket = itemView.findViewById(R.id.paket);
            subscribe = itemView.findViewById(R.id.buy);

        }
    }

}
