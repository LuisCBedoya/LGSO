package com.example.lab01.modelos.Entidades;

public class Eventos {
    private String n_serie;
    private String id;

    public Eventos() {
    }

    public Eventos(String n_serie, String id) {
        this.n_serie = n_serie;
        this.id = id;
    }

    public String getN_serie() {
        return n_serie;
    }

    public void setN_serie(String n_serie) {
        this.n_serie = n_serie;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
