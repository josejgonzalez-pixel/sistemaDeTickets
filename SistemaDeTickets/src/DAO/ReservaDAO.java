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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Reserva;

/**
 *
 * @author Camil
 */
public class ReservaDAO {
    private static final String FILE_NAME = "reservas.txt";

    public void guardarReserva(Reserva r) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(r.getCodigo() + ";" +
                     (r.getPasajero() != null ? r.getPasajero().getCedula() : "") + ";" +
                     (r.getVehiculo() != null ? r.getVehiculo().getPlaca() : "") + ";" +
                     (r.getRuta() != null ? r.getRuta().getCodigo() : "") + ";" +
                     r.getFechaCreacion() + ";" +
                     r.getFechaViaje() + ";" +
                     r.getEstado());
            bw.newLine();
        }

    public List<Reserva> cargarReservas() throws IOException {
        List<Reserva> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                String codigo = datos[0];
                LocalDate fechaCreacion = LocalDate.parse(datos[4]);
                LocalDate fechaViaje = LocalDate.parse(datos[5]);
                String estado = datos[6];
                // Dejamos null pasajero, vehiculo y ruta porque se cargan desde otros DAO
                Reserva r = new Reserva(codigo, null, null, null, fechaCreacion, fechaViaje, estado);
                lista.add(r);
            }
        }
        return lista;
    }

    public Reserva buscarPorCodigo(String codigo) throws IOException {
        for (Reserva r : cargarReservas()) {
            if (r.getCodigo().equals(codigo)) return r;
        }
        return null;
    }

    public List<Reserva> listarActivas() throws IOException {
        List<Reserva> lista = new ArrayList<>();
        for (Reserva r : cargarReservas()) {
            if ("ACTIVA".equals(r.getEstado())) {
                lista.add(r);
            }
        }
        return lista;
    }

    public List<Reserva> listarHistorialPasajero(String cedula) throws IOException {
        List<Reserva> lista = new ArrayList<>();
        for (Reserva r : cargarReservas()) {
            if (r.getPasajero() != null && cedula.equals(r.getPasajero().getCedula())) {
                lista.add(r);
            }
        }
        return lista;
    }

    public void actualizarEstado(String codigoReserva, String nuevoEstado) throws IOException {
        List<String> reservas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                if (datos[0].equals(codigoReserva)) datos[6] = nuevoEstado;
                reservas.add(String.join(";", datos));
            }
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String linea : reservas) {
                bw.write(linea);
                bw.newLine();
            }
        }
    }
}
