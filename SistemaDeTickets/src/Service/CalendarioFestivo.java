/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Camil
 */
public class CalendarioFestivo {
    
    private static List<LocalDate> festivos = new ArrayList<>();
     static {
        festivos.add(LocalDate.of(2026, 1, 1));   // Año nuevo
        festivos.add(LocalDate.of(2026, 1, 12));  // Reyes
        festivos.add(LocalDate.of(2026, 3, 23));  // San Jose
        festivos.add(LocalDate.of(2026, 4, 2));   // Jueves santo
        festivos.add(LocalDate.of(2026, 4, 3));   // Viernes santo
        festivos.add(LocalDate.of(2026, 5, 1));   // Día del trabajo
        festivos.add(LocalDate.of(2026, 6, 29));  // San Pedro y San Pablo
        festivos.add(LocalDate.of(2026, 7, 20));  // Independencia
        festivos.add(LocalDate.of(2026, 8, 7));   // Batalla de Boyacá
        festivos.add(LocalDate.of(2026, 12, 25)); // Navidad
    }
     
     public static boolean esFestivo(LocalDate fecha) {
        return festivos.contains(fecha);
    }
}
