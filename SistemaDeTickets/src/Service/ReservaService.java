/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import DAO.ReservaDAO;
import dao.TicketDAO;
import java.io.IOException;
import java.time.LocalDate;
import model.Reserva;
import model.Ticket;
/**
 *
 * @author Camil
 */
public class ReservaService {
    private ReservaDAO reservaDAO;
    private TicketDAO ticketDAO;

    public ReservaService(ReservaDAO reservaDAO, TicketDAO ticketDAO) {
        this.reservaDAO = reservaDAO;
        this.ticketDAO = ticketDAO;
    }

    public void confirmarReserva(Reserva r) throws IOException {
        // Cambiar estado a CONVERTIDA
        reservaDAO.actualizarEstado(r.getCodigo(), "CONVERTIDA");

        // Crear ticket y guardarlo en tickets.txt
        Ticket t = new Ticket(r.getPasajero(), r.getVehiculo(), LocalDate.now(),
                              r.getRuta().getOrigen(), r.getRuta().getDestino(), 0);
        ticketDAO.guardarTicket(t);
    }

    public void cancelarReserva(String codigoReserva) throws IOException {
        reservaDAO.actualizarEstado(codigoReserva, "CANCELADA");
    }
}
