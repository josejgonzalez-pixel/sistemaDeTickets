/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.Bus;
import model.Buseta;
import model.MicroBus;
import model.Vehiculo;

/**
 *
 * @author hp
 */
public class VehiculoDAO {

    private String obtenerArchivo(Vehiculo v) {
        if (v instanceof Buseta) {
            return "busetas.txt";
        } else if (v instanceof MicroBus) {
            return "microbus.txt";
        } else if (v instanceof Bus) {
            return "bus.txt";
        }
        return "vehiculos.txt"; 
    }

    public void guardarVehiculo(Vehiculo v) {
        String archivo = obtenerArchivo(v);
        try (var bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(v.getPlaca() + ";" + v.getRuta() + ";" + v.getCapacidadMaxima() + ";"
                    + v.getPasajerosActuales() + ";" + v.getTarifaBase() + ";" + v.isDisponible());
            bw.newLine();
        } catch (IOException e) {
        }
    }

    public List<String> listarVehiculos(String archivo) {
        List<String> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lista.add(linea);
            }
        } catch (IOException e) {
        }
        return lista;
    }

    public boolean buscarPorPlaca(String placa) {
        String[] archivos = {"busetas.txt", "microbus.txt", "bus.txt"};
        for (String archivo : archivos) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    if (linea.startsWith(placa + ";")) {
                        return true;
                    }
                }
            } catch (IOException e) {

            }
        }
        return false;
    }
    
    public void guardarAsignacion(String placa, String cedula) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter("asignaciones.txt", true))) {
        bw.write(placa + ";" + cedula);
        bw.newLine();
        System.out.println("Asignación guardada en el historial.");
    } catch (IOException e) {
        System.out.println("Error al guardar la asignación.");
    }
}

public String buscarCedulaAsignada(String placa) {
    try (BufferedReader br = new BufferedReader(new FileReader("asignaciones.txt"))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] datos = linea.split(";");
            if (datos[0].equals(placa)) return datos[1]; 
        }
    } catch (IOException e) {
        
    }
    return null;
}

    public List<String> listarTodosLosVehiculos() {
    List<String> todos = new ArrayList<>();
    todos.addAll(listarVehiculos("busetas.txt"));
    todos.addAll(listarVehiculos("microbus.txt"));
    todos.addAll(listarVehiculos("bus.txt"));
    return todos;
}
    
    public Vehiculo buscarVehiculoPorPlaca(String placa) {
    String[] archivos = {"busetas.txt", "microbus.txt", "bus.txt"};
    for (String archivo : archivos) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(";");
                if (d[0].equals(placa)) {
                    // d[0]:placa, d[1]:ruta, d[2]:cap, d[4]:tarifa
                    // Creamos una ruta genérica para el objeto (puedes mejorar esto luego)
                    model.Ruta rutaDummy = new model.Ruta(d[1], "Origen", "Destino", 0, 0);
                    
                    // Retornamos el tipo hijo correcto según el archivo
                    if (archivo.equals("busetas.txt")) 
                        return new model.Buseta(d[0], rutaDummy, Integer.parseInt(d[2]), 0, Double.parseDouble(d[4]), true, null);
                    if (archivo.equals("microbus.txt")) 
                        return new model.MicroBus(d[0], rutaDummy, Integer.parseInt(d[2]), 0, Double.parseDouble(d[4]), true, null);
                    if (archivo.equals("bus.txt")) 
                        return new model.Bus(d[0], rutaDummy, Integer.parseInt(d[2]), 0, Double.parseDouble(d[4]), true, null);
                    }
                }
            } catch (IOException | NumberFormatException e) {
            // Error al leer o convertir números
            }
        }
        return null; // Si no lo encuentra en ningún archivo
    }
}
