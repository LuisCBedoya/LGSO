package com.example.lab01.ui.databaseAPi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab01.R;
import com.example.lab01.modelos.BDSesiones.AlmacenSesiones;
import com.example.lab01.modelos.BDSesiones.BDSesion;
import com.example.lab01.modelos.Entidades.ApiServidores;
import com.example.lab01.modelos.Entidades.Eventos;
import com.example.lab01.modelos.Entidades.Sesiones;
import com.example.lab01.ui.home.HomeFragment;

import java.util.LinkedList;

public class AdaptadorDatabaseApiRV extends RecyclerView.Adapter<AdaptadorDatabaseApiRV.AdaptadorDatabaseRVViewHolder> {
    public static AlmacenSesiones almacenSesiones;
    private LinkedList<ApiServidores> apiServidores;
    private LinkedList<Sesiones> sesiones = new LinkedList<>();
    private LinkedList<Eventos> eventos = new LinkedList<>();
    private ArrayAdapter<Sesiones> mAdapter;
    private ListView lv_asociar;
    private Activity activity;
    private String idSesion = "", n_serie = "";
    private int contador;

    public AdaptadorDatabaseApiRV(LinkedList<ApiServidores> apiServidores, Activity activity) {
        this.apiServidores = apiServidores;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdaptadorDatabaseApiRV.AdaptadorDatabaseRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_alerts, null,
                false);
        AdaptadorDatabaseRVViewHolder adaptadorDatabaseRVViewHolder = new AdaptadorDatabaseRVViewHolder(view);
        return adaptadorDatabaseRVViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorDatabaseApiRV.AdaptadorDatabaseRVViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if((apiServidores.get(position).getMarca()).toString().equals("No Contiene") ||
                (apiServidores.get(position).getModelo().toString().equals("No Contiene"))){
            holder.tv_marca.setText(apiServidores.get(position).getLic().toString());
            holder.tv_modelo.setText(apiServidores.get(position).getDependencia().toString());
            holder.tv_so.setText(apiServidores.get(position).getSo().toString());
        }else{
            holder.tv_marca.setText(apiServidores.get(position).getMarca().toString());
            holder.tv_modelo.setText(apiServidores.get(position).getModelo().toString());
            holder.tv_so.setText(apiServidores.get(position).getSo().toString());
        }
        holder.iv_card.setImageResource(R.drawable.ic_server);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.getMenuInflater().inflate(R.menu.popup_database_api, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.pop_asociar_evento:
                                final Dialog dialogEvento = new Dialog(activity);
                                dialogEvento.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialogEvento.setContentView(R.layout.alert_asociar);
                                dialogEvento.setCanceledOnTouchOutside(false);
                                Button b_cerrar = dialogEvento.findViewById(R.id.b_cerrar);
                                almacenSesiones = new BDSesion(activity);
                                if(contador < 1){
                                    almacenSesiones.mostrarSesion(sesiones);
                                    contador++;
                                }
                                lv_asociar = dialogEvento.findViewById(R.id.lv_asociar);
                                mAdapter = new ArrayAdapter<Sesiones>(activity, android.R.layout.simple_list_item_1, sesiones);
                                lv_asociar.setAdapter(mAdapter);
                                lv_asociar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                                        PopupMenu popupMenuAsociar = new PopupMenu(view.getContext(), view);
                                        popupMenuAsociar.getMenuInflater().inflate(R.menu.popup_asociar, popupMenuAsociar.getMenu());
                                        popupMenuAsociar.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                            @Override
                                            public boolean onMenuItemClick(MenuItem item) {
                                                switch (item.getItemId()) {
                                                    case R.id.i_asociar:
                                                        idSesion = Integer.toString(sesiones.get(pos).getId()).toString();
                                                        n_serie = apiServidores.get(position).getN_serie();
                                                        almacenSesiones.guardarEvento(n_serie, idSesion);
                                                        Toast.makeText(activity, "Registro realizado con exito",
                                                                Toast.LENGTH_SHORT).show();
                                                        break;
                                                }
                                                return false;
                                            }
                                        });
                                        popupMenuAsociar.show();

                                    }
                                });
                                b_cerrar.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialogEvento.dismiss();
                                    }
                                });
                                dialogEvento.show();
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
        return apiServidores.size();
    }

    public class AdaptadorDatabaseRVViewHolder extends RecyclerView.ViewHolder {
        TextView tv_modelo, tv_marca, tv_so;
        ImageView iv_card;
        public AdaptadorDatabaseRVViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_card = itemView.findViewById(R.id.iv_card);
            tv_marca = itemView.findViewById(R.id.tv_id);
            tv_modelo = itemView.findViewById(R.id.tv_fecha);
            tv_so = itemView.findViewById(R.id.tv_ip);
        }
    }


}
