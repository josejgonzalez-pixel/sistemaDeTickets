/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Camil
 */
public class PasajeroAdultoMayor extends Pasajero{

    public PasajeroAdultoMayor(String cedula, String nombre) {
        super(cedula, nombre);
    }
     
    
    @Override
    public double calcularDescuento() {
        return 0.30;
    }
    
}
