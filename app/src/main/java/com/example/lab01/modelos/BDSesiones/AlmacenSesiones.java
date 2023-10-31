package com.example.lab01.modelos.BDSesiones;

import com.example.lab01.modelos.Entidades.ApiServidores;
import com.example.lab01.modelos.Entidades.Eventos;
import com.example.lab01.modelos.Entidades.Sesiones;

import java.util.LinkedList;

public interface AlmacenSesiones {

    void guardarSesion(int id, String fecha, String ip, String estado);

    void mostrarSesion(LinkedList<Sesiones> lista_sesiones);

    void modificarSesion(int id, String fecha, String ip, String estado);

    void eliminarSesion(int id);

    void guardarInformacionApi(String dependencia, String marca, String modelo, String n_serie,
                               String so, String lic);

    public void mostrarInformacionApi(LinkedList<ApiServidores> servidores);

    void guardarEvento(String n_serie, String id);

    public void mostrarEvento(LinkedList<Eventos> eventos);

}
