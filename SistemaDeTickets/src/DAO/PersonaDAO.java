/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import model.Conductor;
import model.Persona;

/**
 *
 * @author Camil
 */
public class PersonaDAO {
    private String obtenerArchivo(Persona p) {
        if (p instanceof Conductor) {
            return "conductores.txt";
        }
        return "pasajeros.txt";
    }

    public void guardarPersona(Persona persona) {
        
       String archivo = obtenerArchivo(persona);
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
        if (persona instanceof Conductor) {
            Conductor c = (Conductor) persona;
            // Orden: 0:cedula; 1:nombre; 2:licencia; 3:categoria
            bw.write(c.getCedula() + ";" + c.getNombre() + ";" + 
                     c.getNumeroLicencia() + ";" + c.getCategoriaLicencia());
        } else {
            bw.write(persona.getCedula() + ";" + persona.getNombre());
        }
        bw.newLine();
    } catch (IOException e) {
        System.out.println("Error al guardar persona");
    }
}
    
    public Conductor buscarConductorPorCedula(String cedula) {
    try (BufferedReader br = new BufferedReader(new FileReader("conductores.txt"))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] datos = linea.split(";");
            if (datos[0].equals(cedula)) {
                
                return new Conductor(datos[2], datos[3], datos[0], datos[1]);
            }
        }
    } catch (IOException e) {
        System.out.println("Error al leer conductores.");
    }
    return null;
    }
    
    public model.Pasajero buscarPasajeroPorCedula(String cedula) {
        // Leemos el archivo que definido en obtenerArchivo para pasajeros
        try (BufferedReader br = new BufferedReader(new FileReader("pasajeros.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                // Según tu método guardarPersona, el orden es: 0:cedula; 1:nombre
                if (datos[0].equals(cedula)) {
                    // Retornamos un PasajeroRegular (o la subclase )
                    return new model.PasajeroRegular(datos[0], datos[1], java.time.LocalDate.now());
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer pasajeros.");
        }
        return null;
    }
}
