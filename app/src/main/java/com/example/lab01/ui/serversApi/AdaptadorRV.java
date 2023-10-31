package com.example.lab01.ui.serversApi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab01.R;
import com.example.lab01.modelos.BDSesiones.BDSesion;
import com.example.lab01.modelos.Entidades.ApiServidores;

import java.util.LinkedList;

public class AdaptadorRV extends RecyclerView.Adapter<AdaptadorRV.AdapterRVViewHolder> {

    private LinkedList<ApiServidores> servidores;
    private Activity activity;

    public AdaptadorRV(LinkedList<ApiServidores> servidores, Activity activity) {
        this.servidores = servidores;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdapterRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_alerts, null,
                false);
        AdapterRVViewHolder adapterRVViewHolder = new AdapterRVViewHolder(view);
        return adapterRVViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRVViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if((servidores.get(position).getMarca()) == null ||
                (servidores.get(position).getModelo() == null)){
            holder.tv_marca.setText(servidores.get(position).getLic().toString());
            holder.tv_modelo.setText(servidores.get(position).getDependencia().toString());
            holder.tv_so.setText(servidores.get(position).getSo().toString());
        }else{
            holder.tv_marca.setText(servidores.get(position).getMarca().toString());
            holder.tv_modelo.setText(servidores.get(position).getModelo().toString());
            holder.tv_so.setText(servidores.get(position).getSo().toString());
        }

        holder.iv_card.setImageResource(R.drawable.ic_server);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.getMenuInflater().inflate(R.menu.popupsesion, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.pop_agregar:
                                BDSesion almacenSesiones = new BDSesion(activity);
                                if(servidores.get(position).getN_serie() == null || servidores.get(position).getModelo() == null){
                                    almacenSesiones.guardarInformacionApi(
                                            servidores.get(position).getDependencia().toString(),
                                            "No Contiene",
                                            "No Contiene",
                                            "No Contiene",
                                            servidores.get(position).getSo().toString(),
                                            servidores.get(position).getLic().toString()
                                    );
                                }else{
                                    almacenSesiones.guardarInformacionApi(
                                            servidores.get(position).getDependencia().toString(),
                                            servidores.get(position).getMarca().toString(),
                                            servidores.get(position).getModelo().toString(),
                                            servidores.get(position).getN_serie().toString(),
                                            servidores.get(position).getSo().toString(),
                                            servidores.get(position).getLic().toString());
                                }
                                Toast.makeText(activity, "Registro realizado con exito",
                                        Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return servidores.size();
    }

    public class AdapterRVViewHolder extends RecyclerView.ViewHolder {
        TextView tv_modelo, tv_marca, tv_so;
        ImageView iv_card;
        public AdapterRVViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_card = itemView.findViewById(R.id.iv_card);
            tv_marca = itemView.findViewById(R.id.tv_id);
            tv_modelo = itemView.findViewById(R.id.tv_fecha);
            tv_so = itemView.findViewById(R.id.tv_ip);
        }
    }
}
