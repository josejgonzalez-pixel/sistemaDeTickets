/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author hp
 */
public class Bus extends Vehiculo{
    
    public Bus(String placa, String ruta, int capacidadMaxima, int pasajerosActuales, double tarifaBase, boolean disponible) {
        super(placa, ruta, capacidadMaxima, pasajerosActuales, tarifaBase, disponible);
    }
    
}
