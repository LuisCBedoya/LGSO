package com.example.lab01.ui.databaseAPi;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

import android.app.job.JobScheduler;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lab01.AsociadosActivity;
import com.example.lab01.R;
import com.example.lab01.modelos.BDSesiones.AlmacenSesiones;
import com.example.lab01.modelos.BDSesiones.BDSesion;
import com.example.lab01.modelos.Entidades.ApiServidores;
import com.example.lab01.ui.serversApi.AdaptadorRV;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;

public class DatabaseApiFragment extends Fragment {
    public static AlmacenSesiones almacenSesiones;
    private RecyclerView rv_database_api;
    private AdaptadorDatabaseApiRV adaptadorDatabaseApiRV;
    private LinearLayoutManager linearLayoutManager;
    private FloatingActionButton fab_stop;
    private final static int ID_SERVICIO = 99;
    private LinkedList<ApiServidores> servidores = new LinkedList<>();
    private FloatingActionButton fab_asociados;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_database_api, container, false);
        almacenSesiones = new BDSesion(getActivity());
        almacenSesiones.mostrarInformacionApi(servidores);
        fab_stop = view.findViewById(R.id.fab_stop);
        fab_asociados = view.findViewById(R.id.fab_asociados);
        rv_database_api = view.findViewById(R.id.rv_database_api);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_database_api.setLayoutManager(linearLayoutManager);
        adaptadorDatabaseApiRV = new AdaptadorDatabaseApiRV(servidores, getActivity());
        rv_database_api.setAdapter(adaptadorDatabaseApiRV);
        fab_asociados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AsociadosActivity.class));
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
}