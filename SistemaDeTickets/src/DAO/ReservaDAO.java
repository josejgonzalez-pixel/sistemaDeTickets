/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Reserva;

/**
 *
 * @author hp
 */
public class ReservaDAO {
    private static final String FILE_NAME = "reservas.txt";

    public void guardarReserva(Reserva r) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(r.getCodigo() + ";" + r.getPasajero().getCedula() + ";" + r.getVehiculo().getPlaca() + ";" +
                     r.getRuta().getCodigo() + ";" + r.getFechaCreacion() + ";" + r.getFechaViaje() + ";" + r.getEstado());
            bw.newLine();
        }
    }

    public List<String> listarReservas() throws IOException {
        List<String> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lista.add(linea);
            }
        }
        return lista;
    }

    public void actualizarEstado(String codigoReserva, String nuevoEstado) throws IOException {
        List<String> reservas = listarReservas();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String linea : reservas) {
                String[] datos = linea.split(";");
                if (datos[0].equals(codigoReserva)) {
                    datos[6] = nuevoEstado;
                }
                bw.write(String.join(";", datos));
                bw.newLine();
            }
        }
    }
}
