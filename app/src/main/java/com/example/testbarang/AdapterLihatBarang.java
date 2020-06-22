package com.example.testbarang;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterLihatBarang extends RecyclerView.Adapter<AdapterLihatBarang.ViewHolder> {

    private ArrayList<Barang> daftarBarang;
    private DatabaseReference database;
    private Context context;
    FirebaseDataListener listener;

    public AdapterLihatBarang(ArrayList<Barang> barangs, Context ctx){
        daftarBarang = barangs;
        context = ctx;
        listener = (LihatBarang)ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvCode;

        ViewHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tv_namabarang);
            tvCode = (TextView) v.findViewById(R.id.tv_kode);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String name = daftarBarang.get(position).getNama();
        final String code = daftarBarang.get(position).getKode();
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //untuk lihat detail data
            }
        });
        holder.tvTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.itemView);
                popupMenu.inflate(R.menu.option_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.opUpdate:
                                context.startActivity(TambahData.getActIntent((Activity)context).putExtra("data", daftarBarang.get(position)));
                                break;
                            case R.id.opHapus:
                                //deleteDialog
                                listener.onDeleteData(daftarBarang.get(position),position);
                                break;

                        }
                        return true;
                    }
                });
                popupMenu.show();
                return false;
            }
        });
        holder.tvTitle.setText(name);
        holder.tvCode.setText(code);

    }

    @Override
    public int getItemCount() {
        return daftarBarang.size();
    }

    public interface FirebaseDataListener{
        void onDeleteData(Barang barang, int position);
    }

}
