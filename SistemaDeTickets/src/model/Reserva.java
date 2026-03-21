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
public class Reserva {
    private String codigo;
    private Pasajero pasajero;
    private Vehiculo vehiculo;
    private Ruta ruta;
    private LocalDate fechaCreacion;
    private LocalDate fechaViaje;
    private String estado; // ACTIVA, CONVERTIDA, CANCELADA

    public Reserva(String codigo, Pasajero pasajero, Vehiculo vehiculo, Ruta ruta, LocalDate fechaCreacion, LocalDate fechaViaje, String estado) {
        this.codigo = codigo;
        this.pasajero = pasajero;
        this.vehiculo = vehiculo;
        this.ruta = ruta;
        this.fechaCreacion = fechaCreacion;
        this.fechaViaje = fechaViaje;
        this.estado = estado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaViaje() {
        return fechaViaje;
    }

    public void setFechaViaje(LocalDate fechaViaje) {
        this.fechaViaje = fechaViaje;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Reserva{" + "codigo=" + codigo + ", pasajero=" + pasajero + ", vehiculo=" + vehiculo + ", ruta=" + ruta + ", fechaCreacion=" + fechaCreacion + ", fechaViaje=" + fechaViaje + ", estado=" + estado + '}';
    }
}
