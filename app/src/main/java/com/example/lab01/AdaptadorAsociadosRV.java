package com.example.lab01;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab01.modelos.BDSesiones.AlmacenSesiones;
import com.example.lab01.modelos.BDSesiones.BDSesion;
import com.example.lab01.modelos.Entidades.ApiServidores;
import com.example.lab01.modelos.Entidades.Eventos;
import com.example.lab01.modelos.Entidades.Sesiones;
import com.example.lab01.ui.databaseAPi.AdaptadorDatabaseApiRV;

import java.util.LinkedList;

public class AdaptadorAsociadosRV extends RecyclerView.Adapter<AdaptadorAsociadosRV.AdaptadorRVViewHolder> {

    private LinkedList<Eventos> eventos = new LinkedList<>();
    private LinkedList<Sesiones> sesiones = new LinkedList<>();
    private LinkedList<ApiServidores> apiServidores = new LinkedList<>();
    public static AlmacenSesiones almacenSesiones;
    private Activity activity;
    private int contador = 0;

    public AdaptadorAsociadosRV(LinkedList<Eventos> eventos, Activity activity) {
        this.eventos = eventos;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdaptadorRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_alerts, null,
                false);
        AdaptadorRVViewHolder adaptadorRVViewHolder= new AdaptadorRVViewHolder(view);
        return adaptadorRVViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorRVViewHolder holder, int position) {
        holder.tv_n_serie.setText(eventos.get(position).getN_serie().toString());
        almacenSesiones = new BDSesion(activity);
        if(contador<1){
            almacenSesiones.mostrarInformacionApi(apiServidores);
            almacenSesiones.mostrarSesion(sesiones);
            contador++;
        }

        int indiceSesiones = -1;
        int indiceServidor = -1;

        for (int i=0; i<sesiones.size(); i++){
            if(Integer.toString(sesiones.get(i).getId()).toString()
                    .equals(eventos.get(position).getId().toString())){
                indiceSesiones = i;
            }
        }

        for (int i=0; i<apiServidores.size(); i++){
            if(apiServidores.get(i).getN_serie().toString()
                    .equals(eventos.get(position).getN_serie().toString())){
                indiceServidor = i;
            }
        }

        String cadena = sesiones.get(indiceSesiones).getFecha().toString()
                + "\n" + apiServidores.get(indiceServidor).getDependencia().toString();

        holder.tv_ip.setText(sesiones.get(indiceSesiones).getIp().toString().trim());
        holder.tv_fecha.setText(cadena);
        holder.iv_card.setImageResource(R.drawable.ic_join);
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    public class AdaptadorRVViewHolder extends RecyclerView.ViewHolder {
        TextView tv_n_serie, tv_ip, tv_fecha;
        ImageView iv_card;
        public AdaptadorRVViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_card = itemView.findViewById(R.id.iv_card);
            tv_n_serie = itemView.findViewById(R.id.tv_id);
            tv_ip = itemView.findViewById(R.id.tv_fecha);
            tv_fecha = itemView.findViewById(R.id.tv_ip);
        }
    }
}
