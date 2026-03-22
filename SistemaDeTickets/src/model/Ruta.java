/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author hp
 */
public class Ruta {

    private String codigo;
    private String origen;
    private String destino;
    private double km;
    private double tiempo;

    public Ruta(String codigo, String origen, String destino, double km, double tiempo) {
        this.codigo = codigo;
        this.origen = origen;
        this.destino = destino;
        this.km = km;
        this.tiempo = tiempo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getKm() {
        return km;
    }

    public void setKm(double km) {
        this.km = km;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

    @Override
    public String toString() {
        return "Ruta{" + "codigo=" + codigo + ", origen=" + origen + ", destino=" + destino + ", km=" + km + ", tiempo=" + tiempo + '}';
    }
    
     public void imprimirDetalle() {
        System.out.println("Ruta: " + codigo);
        System.out.println("Origen: " + origen);
        System.out.println("Destino: " + destino);
        System.out.println("Km: " + km);
        System.out.println("Tiempo estimado: " + tiempo + " horas");
    }
}


