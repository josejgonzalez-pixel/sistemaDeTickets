/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Ruta;

/**
 *
 * @author hp
 */
public class RutaDAO {
    private static final String FILE_NAME = "rutas.txt";

    public List<Ruta> leerRutas() throws IOException {
        List<Ruta> rutas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                String codigo = datos[0];
                String origen = datos[1];
                String destino = datos[2];
                double km = Double.parseDouble(datos[3]);
                double tiempo = Double.parseDouble(datos[4]);
                rutas.add(new Ruta(codigo, origen, destino, km, tiempo));
            }
        }
        return rutas;
    }
}
