/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author jos13
 */
// Esta clase la completará el Desarrollador 2, pero tú la creas para que el Main compile
public class ticket implements calculable, imprimible {
    
    // El Desarrollador 2 agregará atributos como: placa, pasajero, fecha, etc.

    @Override
    public double calcularTotal() {
        // Lógica temporal para que no de error
        return 0.0;
    }

    public void imprimirDetalle() {
        System.out.println("--- Detalle del Ticket (Pendiente por Desarrollador 2) ---");
    }

    @Override
    public void calcularDetalles() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
