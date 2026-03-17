/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Camil
 */
public abstract class Pasajero extends Persona{

    public Pasajero(String cedula, String nombre) {
        super(cedula, nombre);
    }
    
    public abstract double calcularDescuento();

     @Override
    public String toString() {
        return "Pasajero{" +
                "cedula='" + cedula + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
