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
        return "vehiculos.txt"; // respaldo genérico
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
}
