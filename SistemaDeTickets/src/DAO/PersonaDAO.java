/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.io.BufferedWriter;
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

            bw.write(persona.getCedula() + ";" + persona.getNombre());
            bw.newLine();

        } catch (IOException e) {
            System.out.println("Error al guardar persona");
        }
    }
}
