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
public class Ticket implements imprimible, calculable{
     private Pasajero pasajero;
    private Vehiculo vehiculo;
    private LocalDate fechaCompra;
    private String origenCompra;
    private String destino;
    private double valorFinal;

    public Ticket(Pasajero pasajero, Vehiculo vehiculo, LocalDate fechaCompra, String origenCompra, String destino, double valorFinal) {
        this.pasajero = pasajero;
        this.vehiculo = vehiculo;
        this.fechaCompra = fechaCompra;
        this.origenCompra = origenCompra;
        this.destino = destino;
        this.valorFinal = valorFinal;
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

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getOrigenCompra() {
        return origenCompra;
    }

    public void setOrigenCompra(String origenCompra) {
        this.origenCompra = origenCompra;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(double valorFinal) {
        this.valorFinal = valorFinal;
    }
    
     @Override
    public String toString() {
        return "Ticket{" + "pasajero=" + pasajero + ", vehiculo=" + vehiculo + ", fechaCompra=" + fechaCompra + ", origenCompra=" + origenCompra + ", destino=" + destino + ", valorFinal=" + valorFinal + '}';
    }

    @Override
    public void imprimirDetalles() {
        System.out.println("----- DETALLE DEL TICKET -----");
        System.out.println("Pasajero: " + pasajero.getNombre());
        System.out.println("Vehiculo: " + vehiculo.getPlaca());
        System.out.println("Origen: " + origenCompra);
        System.out.println("Destino: " + destino);
        System.out.println("Fecha: " + fechaCompra);
        System.out.println("Valor Final: " + valorFinal);
    }

    @Override
    public double calcularTotal() {
        double precioBase = vehiculo.getTarifaBase();
        double descuento = pasajero.calcularDescuento();

        valorFinal = precioBase - (precioBase * descuento);

        return valorFinal;
    }
}
