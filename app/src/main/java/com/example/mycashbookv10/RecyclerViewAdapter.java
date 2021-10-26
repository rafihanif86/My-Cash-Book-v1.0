package com.example.mycashbookv10;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    Context context;
    int card_item;
    ArrayList<Model>modelArrayList;
    SQLiteDatabase db;

    public RecyclerViewAdapter(Context context, int card_item, ArrayList<Model> modelArrayList, SQLiteDatabase db) {
        this.context = context;
        this.card_item = card_item;
        this.modelArrayList = modelArrayList;
        this.db = db;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        final Model model = modelArrayList.get(position);
        holder.txtJenis.setText(model.getJenis());
        holder.txtNominal.setText("Rp. " + model.getNominal());
        holder.txtKeterangan.setText(model.getKeterangan());
        holder.txtTanggal.setText(model.getTanggal());
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNominal, txtJenis, txtTanggal, txtKeterangan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNominal = (TextView) itemView.findViewById(R.id.txtNominal);
            txtJenis = (TextView) itemView.findViewById(R.id.txtJenis);
            txtTanggal = (TextView) itemView.findViewById(R.id.txtTanggal);
            txtKeterangan = (TextView)  itemView.findViewById(R.id.txtKeterangan);
        }
    }
}
