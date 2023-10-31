package com.example.lab01.ui.notifications;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

import android.app.job.JobScheduler;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.lab01.R;
import com.example.lab01.modelos.BDSesiones.AlmacenSesiones;
import com.example.lab01.modelos.BDSesiones.BDSesion;
import com.example.lab01.modelos.Entidades.Sesiones;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.LinkedList;

public class NotificationsFragment extends Fragment {

    public static AlmacenSesiones almacenSesiones;
    private ListView lv_alerts;
    private FloatingActionButton fab_stop;
    private final static int ID_SERVICIO = 99;
    private LinkedList<Sesiones> listado_alertas = new LinkedList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        fab_stop = view.findViewById(R.id.fab_stop);
        InputStream inputStream = getResources().openRawResource(R.raw.salidalog);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,
                Charset.forName("UTF-8")));
        String line = "";
        try {
            while ((line = reader.readLine()) != null){
                if (line.contains("Access denied")){
                    String[] tokens = line.split("]");
                    String fechaTime = tokens[0].replace("[", "").replace("]", "").trim();
                    String ip = tokens[3].replace("[", "").replace("]", "");
                    int id = (int)(Math.random()*10000);
                    String estado = "Pendiente";
                    Sesiones sesiones = new Sesiones(id, fechaTime, ip, estado);
                    listado_alertas.add(sesiones);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        lv_alerts = view.findViewById(R.id.lv_alert);
        adaptadorAlertas adapter = new adaptadorAlertas();
        lv_alerts.setAdapter(adapter);

        lv_alerts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View views, int position, long id) {

                PopupMenu popupMenu = new PopupMenu(getActivity(), views);
                popupMenu.getMenuInflater().inflate(R.menu.popupsesion, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.pop_agregar:
                                almacenSesiones = new BDSesion(getActivity());
                                almacenSesiones.guardarSesion(listado_alertas.get(position).getId(),
                                        listado_alertas.get(position).getFecha().toString(), listado_alertas.get(position).getIp().toString(),
                                        listado_alertas.get(position).getEstado().toString());
                                Toast.makeText(getActivity(), "Registro realizado con exito", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();

            }
        });

        fab_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobScheduler jobScheduler = (JobScheduler) getActivity().getSystemService(JOB_SCHEDULER_SERVICE);
                jobScheduler.cancel(ID_SERVICIO);
                jobScheduler.cancelAll();
                Toast.makeText(getActivity(), "Servicio en Stop", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


    public class adaptadorAlertas extends BaseAdapter {

        @Override
        public int getCount() {
            return listado_alertas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.card_alerts, parent, false);
            TextView tv_fecha, tv_id, tv_ip;
            ImageView iv_card;
            tv_id = convertView.findViewById(R.id.tv_id);
            tv_fecha = convertView.findViewById(R.id.tv_fecha);
            tv_ip = convertView.findViewById(R.id.tv_ip);
            tv_id.setText(Integer.toString(listado_alertas.get(position).getId()).toString());
            tv_fecha.setText(listado_alertas.get(position).getFecha().toString());
            tv_ip.setText(listado_alertas.get(position).getIp().toString());

            return convertView;
        }
    }
}