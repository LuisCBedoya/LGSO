package com.example.lab01.modelos.Entidades;

public class ApiServidores {
    private String dependencia;
    private String marca;
    private String modelo;
    private String n_serie;
    private String so;
    private String lic;

    public ApiServidores() {
    }

    public ApiServidores(String dependencia, String marca, String modelo,
                         String n_serie, String so, String lic) {

        this.dependencia = dependencia;
        this.marca = marca;
        this.modelo = modelo;
        this.n_serie = n_serie;
        this.so = so;
        this.lic = lic;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getN_serie() {
        return n_serie;
    }

    public void setN_serie(String n_serie) {
        this.n_serie = n_serie;
    }

    public String getSo() {
        return so;
    }

    public void setSo(String so) {
        this.so = so;
    }

    public String getLic() {
        return lic;
    }

    public void setLic(String lic) {
        this.lic = lic;
    }

    @Override
    public String toString() {
        return "ApiServidores{" +
                "dependencia='" + dependencia + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", n_serie='" + n_serie + '\'' +
                ", so='" + so + '\'' +
                ", lic='" + lic + '\'' +
                '}';
    }
}
