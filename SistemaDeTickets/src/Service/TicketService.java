/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.TicketDAO;
import model.Conductor;
import model.Ticket;
import model.Vehiculo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

/**
 *
 * @author Camil
 */
public class TicketService {
    private TicketDAO ticketDAO = new TicketDAO();

    public void venderTicket(Ticket ticket, Conductor conductor) {

        Vehiculo vehiculo = ticket.getVehiculo();
        
        if (!conductor.tieneLicencia()) {
            System.out.println("El conductor no tiene licencia valida");
            return;
        }

        if (!vehiculo.isDisponible()) {
            System.out.println("El vehículo no esta disponible");
            return;
        }

        if (!vehiculo.hayCupos()) {
            System.out.println("No hay cupos disponibles");
            return;
        }
        
        vehiculo.subirPasajero();
        
        ticket.calcularTotal();

        ticketDAO.guardarTicket(ticket);

        System.out.println("Ticket vendido correctamente");
    }
    
    public boolean validarLimiteTickets(String cedula, LocalDate fecha) {

        int contador = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("tickets.txt"))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                String[] datos = linea.split(";");

                String cedulaArchivo = datos[0];
                String fechaArchivo = datos[5];

                if (cedulaArchivo.equals(cedula) && fechaArchivo.equals(fecha.toString())) {
                    contador++;
                }
            }

        } catch (IOException e) {
            System.out.println("Error leyendo archivo");
        }

        if (contador >= 3) {
            System.out.println("Ya tiene " + contador + " tickets hoy");
            return false;
        }

        return true;
    }
}
