package com.warehousenesia.id.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.warehousenesia.id.DetailProduct;
import com.warehousenesia.id.Model.DataCountry;
import com.warehousenesia.id.ProductCountry;
import com.warehousenesia.id.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchByAdapter extends RecyclerView.Adapter<SearchByAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<DataCountry> CountryList;

    public SearchByAdapter(Context context, ArrayList<DataCountry> countryList){
//        inflater = LayoutInflater.from(context);
        this.CountryList = countryList;
    }

    @NonNull
    @Override
    public SearchByAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_search_by, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchByAdapter.ViewHolder holder, final int position) {
        Glide.with(holder.itemView.getContext())
                .load("http://192.168.100.17:1997/"+CountryList.get(position).getGambar())
                .apply(new RequestOptions().override(360, 360))
                .into(holder.img_country);
        holder.img_country.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), ProductCountry.class);
            intent.putExtra("id", CountryList.get(position).getId());
            intent.putExtra("negara", CountryList.get(position).getNegara());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return CountryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img_country;

        public ViewHolder(View view) {
            super(view);
            img_country = view.findViewById(R.id.img);

        }
    }
}
