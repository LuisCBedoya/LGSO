package com.example.lab01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.lab01.modelos.BDSesiones.AlmacenSesiones;
import com.example.lab01.modelos.BDSesiones.BDSesion;
import com.example.lab01.modelos.Entidades.Eventos;
import com.example.lab01.ui.databaseAPi.AdaptadorDatabaseApiRV;

import java.util.LinkedList;

public class AsociadosActivity extends AppCompatActivity {
    private RecyclerView rv_asociados;
    private AdaptadorAsociadosRV adaptadorAsociadosRV;
    private LinearLayoutManager linearLayoutManager;
    private LinkedList<Eventos> eventos = new LinkedList<>();
    public static AlmacenSesiones almacenSesiones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        almacenSesiones = new BDSesion(this);
        almacenSesiones.mostrarEvento(eventos);
        setContentView(R.layout.activity_asociados);
        rv_asociados = findViewById(R.id.rv_asociados);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_asociados.setLayoutManager(linearLayoutManager);
        adaptadorAsociadosRV = new AdaptadorAsociadosRV(eventos, this);
        rv_asociados.setAdapter(adaptadorAsociadosRV);

    }
}