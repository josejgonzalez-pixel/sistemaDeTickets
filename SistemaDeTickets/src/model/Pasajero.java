/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author Camil
 */
public abstract class Pasajero extends Persona {
    protected LocalDate fechaNacimiento;
    protected String tipoPasajero; // ESTUDIANTE, REGULAR, ADULTO_MAYOR

    public Pasajero(String cedula, String nombre, LocalDate fechaNacimiento) {
        super(cedula, nombre);
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getTipoPasajero() { return tipoPasajero; }
    public void setTipoPasajero(String tipoPasajero) { this.tipoPasajero = tipoPasajero; }

    public abstract double calcularDescuento();

    @Override
    public String toString() {
        return fechaNacimiento + ", fechaNacimiento=" +
                "Pasajero{" +
                "cedula='" + cedula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", tipoPasajero=" + tipoPasajero +
                '}';
    }
}

