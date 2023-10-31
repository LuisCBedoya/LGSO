package com.example.lab01.ui.home;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

import android.app.job.JobScheduler;
import android.app.job.JobService;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.lab01.JobAndroidApi;
import com.example.lab01.R;
import com.example.lab01.modelos.BDSesiones.AlmacenSesiones;
import com.example.lab01.modelos.BDSesiones.BDSesion;
import com.example.lab01.modelos.Entidades.Sesiones;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;

public class HomeFragment extends Fragment {
    public static AlmacenSesiones almacenSesiones;
    private SwipeRefreshLayout srl_actualizar;
    private FloatingActionButton fab_stop;
    private final static int ID_SERVICIO = 99;
    private ConstraintLayout cl_actualizar;
    private ListView lv_sesiones;
    private LinkedList<Sesiones> listado_sesiones = new LinkedList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        almacenSesiones = new BDSesion(getActivity());
        almacenSesiones.mostrarSesion(listado_sesiones);
        fab_stop = view.findViewById(R.id.fab_stop);
        lv_sesiones = view.findViewById(R.id.lv_sesiones);
        srl_actualizar = view.findViewById(R.id.srl_actualizar);
        srl_actualizar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adaptadorSesiones adapter = new adaptadorSesiones();
                lv_sesiones.setEnabled(true);
                lv_sesiones.setAdapter(adapter);
                srl_actualizar.setRefreshing(false);
            }
        });

        adaptadorSesiones adapter = new adaptadorSesiones();
        lv_sesiones.setAdapter(adapter);
        lv_sesiones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View views, int position, long id) {

                PopupMenu popupMenu = new PopupMenu(getActivity(), views);
                popupMenu.getMenuInflater().inflate(R.menu.popuphome, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.pop_eliminar:
                                almacenSesiones.eliminarSesion(listado_sesiones.get(position).getId());
                                listado_sesiones.remove(position);
                                lv_sesiones.setEnabled(false);
                                Toast.makeText(getActivity(), "Se ha eliminado correctamente! " +
                                                "Deslize hacia abajo para actualizar la pagina",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.pop_modificar:
                                if (listado_sesiones.get(position).getEstado().toString().equals("Pendiente")){
                                    almacenSesiones.modificarSesion(listado_sesiones.get(position).getId(),
                                            listado_sesiones.get(position).getFecha().toString(),
                                            listado_sesiones.get(position).getIp().toString(),
                                            "Revisado");
                                    Sesiones sesiones = new Sesiones(listado_sesiones.get(position).getId(),
                                            listado_sesiones.get(position).getFecha().toString(),
                                            listado_sesiones.get(position).getIp().toString(),
                                            "Revisado");
                                    listado_sesiones.set(position, sesiones);
                                    Toast.makeText(getActivity(), "Se ha realizado el cambio " +
                                                    "con exito!, deslize hacia abajo para actualizar la pagina",
                                            Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getActivity(), "Ya ha sido revisado!",
                                            Toast.LENGTH_SHORT).show();
                                }
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

    public class adaptadorSesiones extends BaseAdapter {

        @Override
        public int getCount() {
            return listado_sesiones.size();
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
            tv_id.setText(Integer.toString(listado_sesiones.get(position).getId()).toString());
            tv_fecha.setText(listado_sesiones.get(position).getFecha().toString());
            tv_ip.setText(listado_sesiones.get(position).getIp().toString() +
                    "\n" + listado_sesiones.get(position).getEstado().toString() );

            return convertView;
        }
    }

}