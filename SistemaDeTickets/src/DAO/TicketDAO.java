/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import model.Ticket;

/**
 *
 * @author Camil
 */
public class TicketDAO {
    private final String archivo = "tickets.txt";

    public void guardarTicket(Ticket t) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {

            bw.write(
                t.getPasajero().getNombre() + ";" +
                t.getVehiculo().getPlaca() + ";" +
                t.getOrigenCompra() + ";" +
                t.getDestino() + ";" +
                t.getFechaCompra() + ";" +
                t.getValorFinal()
            );

            bw.newLine();

        } catch (IOException e) {
            System.out.println("Error al guardar ticket");
        }
    }
}
