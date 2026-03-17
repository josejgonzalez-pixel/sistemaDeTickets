/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Camil
 */
public class Conductor extends Persona{
    private String numeroLicencia;
    private String categoriaLicencia;

    public Conductor(String numeroLicencia, String categoriaLicencia, String cedula, String nombre) {
        super(cedula, nombre);
        this.numeroLicencia = numeroLicencia;
        this.categoriaLicencia = categoriaLicencia;
    } 

    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }

    public String getCategoriaLicencia() {
        return categoriaLicencia;
    }

    public void setCategoriaLicencia(String categoriaLicencia) {
        this.categoriaLicencia = categoriaLicencia;
    }

     @Override
    public String toString() {
        return "Conductor{" +
                "cedula='" + cedula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", numeroLicencia='" + numeroLicencia + '\'' +
                ", categoriaLicencia='" + categoriaLicencia + '\'' +
                '}';
    }
    
     public boolean tieneLicencia() {
        return numeroLicencia != null && !numeroLicencia.isEmpty();
    }
}
