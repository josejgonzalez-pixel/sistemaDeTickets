/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import model.Pasajero;
import model.PasajeroAdultoMayor;
import model.PasajeroRegular;

/**
 *
 * @author hp
 */
public class PasajeroService {
    public int calcularEdad(LocalDate nacimiento) {
        return (int) ChronoUnit.YEARS.between(nacimiento, LocalDate.now());
    }

    public Pasajero asignarCategoria(Pasajero p) {
        int edad = calcularEdad(p.getFechaNacimiento());
        if (edad >= 60) {
            // convertir automáticamente a Adulto Mayor
            return new PasajeroAdultoMayor(p.getCedula(), p.getNombre(), p.getFechaNacimiento());
        } else {
            // si no es estudiante, queda como regular
            return new PasajeroRegular(p.getCedula(), p.getNombre(), p.getFechaNacimiento());
        }
    }
}
