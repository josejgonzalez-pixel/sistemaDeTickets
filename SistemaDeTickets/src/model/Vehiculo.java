/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author hp
 */
public abstract class Vehiculo implements imprimible{

    public String placa;
    public String ruta;
    public int capacidadMaxima;
    public int pasajerosActuales;
    public double tarifaBase;
    public boolean disponible;
    private Conductor conductorAsignado;

    public Vehiculo(String placa, String ruta, int capacidadMaxima, int pasajerosActuales, double tarifaBase, boolean disponible) {
        this.placa = placa;
        this.ruta = ruta;
        this.capacidadMaxima = capacidadMaxima;
        this.pasajerosActuales = pasajerosActuales;
        this.tarifaBase = tarifaBase;
        this.disponible = disponible;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public int getPasajerosActuales() {
        return pasajerosActuales;
    }

    public void setPasajerosActuales(int pasajerosActuales) {
        this.pasajerosActuales = pasajerosActuales;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public void setTarifaBase(double tarifaBase) {
        this.tarifaBase = tarifaBase;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void subirPasajero() {
        if (hayCupos()) {
            pasajerosActuales++;
            if (pasajerosActuales >= capacidadMaxima) {
                disponible = false;
            }
        }
    }

    public boolean hayCupos() {
        return pasajerosActuales < capacidadMaxima;
    }

    public int getCuposDisponibles() {
        return capacidadMaxima - pasajerosActuales;
    }

    public Conductor getConductorAsignado() {
        return conductorAsignado;
    }

    public void setConductorAsignado(Conductor conductorAsignado) {
        this.conductorAsignado = conductorAsignado;
    }
  
     @Override
    public void imprimirDetalles() {
         System.out.println("================================");
        System.out.println("Placa: " + placa);
        System.out.println("Ruta: " + ruta);
        System.out.println("Capacidad maxima: " + capacidadMaxima);
        System.out.println("Pasajeros actuales: " + pasajerosActuales);
        System.out.println("Cupos disponibles: " + getCuposDisponibles());
        System.out.println("Tarifa base: $" + tarifaBase);
        System.out.println("Disponible: " + (disponible ? "SI" : "No"));
        if (conductorAsignado != null) {
        System.out.println("Conductor Asignado: " + conductorAsignado.getNombre());
        System.out.println("Licencia: " + conductorAsignado.getNumeroLicencia());
    } else {
        System.out.println("Conductor Asignado: [PENDIENTE POR ASIGNAR]");
    }
        System.out.println("================================");
    }
}
