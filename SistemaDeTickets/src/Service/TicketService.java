/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.TicketDAO;
import model.Conductor;
import model.Ticket;
import model.Vehiculo;

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
}
