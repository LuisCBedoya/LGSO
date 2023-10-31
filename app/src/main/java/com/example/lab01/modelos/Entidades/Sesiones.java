package com.example.lab01.modelos.Entidades;

public class Sesiones {
    private int id;
    private String fecha;
    private String ip;
    private String estado;

    public Sesiones() {
    }

    public Sesiones(int id, String fecha, String ip, String estado) {
        this.id = id;
        this.fecha = fecha;
        this.ip = ip;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Sesion: " +
                "id: " + id +
                ", fecha: " + fecha +
                ", ip: " + ip  +
                ", estado: " + estado +
                '.';
    }
}
