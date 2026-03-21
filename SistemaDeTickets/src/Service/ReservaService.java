/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import DAO.ReservaDAO;
import java.io.IOException;
import java.time.LocalDate;
import model.Conductor;
import model.Reserva;
import model.Ticket;
/**
 *
 * @author Camil
 */
public class ReservaService {
    private ReservaDAO reservaDAO;

    public ReservaService(ReservaDAO reservaDAO) {
        this.reservaDAO = reservaDAO;
    }

    public boolean validarCupo(Reserva r) throws IOException {
        int ocupados = r.getVehiculo().getPasajerosActuales();
        int reservasActivas = 0;
        for (Reserva res : reservaDAO.listarActivas()) {
            if (res.getVehiculo() != null &&
                res.getVehiculo().getPlaca().equals(r.getVehiculo().getPlaca())) {
                reservasActivas++;
            }
        }
        return (reservasActivas + ocupados) < r.getVehiculo().getCapacidadMaxima();
    }

    public boolean validarReservaUnica(Reserva r) throws IOException {
        for (Reserva res : reservaDAO.listarActivas()) {
            if (res.getPasajero() != null &&
                res.getPasajero().getCedula().equals(r.getPasajero().getCedula()) &&
                res.getVehiculo().getPlaca().equals(r.getVehiculo().getPlaca()) &&
                res.getFechaViaje().equals(r.getFechaViaje())) {
                return false;
            }
        }
        return true;
    }

    public void crearReserva(Reserva r) throws IOException {
        if (!validarCupo(r)) {
            System.out.println("No hay cupos disponibles");
            return;
        }
        if (!validarReservaUnica(r)) {
            System.out.println("Ya tiene una reserva activa para este vehiculo en esa fecha");
            return;
        }
        r.setEstado("ACTIVA");
        reservaDAO.guardarReserva(r);
        System.out.println("Reserva creada correctamente");
    }

    public int verificarReservasVencidas() throws IOException {
        int contador = 0;
        LocalDate hoy = LocalDate.now();
        for (Reserva r : reservaDAO.listarActivas()) {
            if (r.getFechaCreacion().plusDays(1).isBefore(hoy)) {
                reservaDAO.actualizarEstado(r.getCodigo(), "CANCELADA");
                contador++;
            }
        }
        return contador;
    }

    public void convertirReserva(Reserva r, TicketService ticketService, Conductor conductor) throws IOException {
        if (!"ACTIVA".equals(r.getEstado())) {
            System.out.println("La reserva no está activa");
            return;
        }

        Ticket t = new Ticket(
            r.getPasajero(),
            r.getVehiculo(),
            LocalDate.now(),
            r.getRuta().getOrigen(),
            r.getRuta().getDestino(),
            0
        );

        ticketService.venderTicket(t, conductor);
        reservaDAO.actualizarEstado(r.getCodigo(), "CONVERTIDA");
        System.out.println("Reserva convertida en ticket");
    }

    public void cancelarReserva(String codigoReserva) throws IOException {
        reservaDAO.actualizarEstado(codigoReserva, "CANCELADA");
    }
}
