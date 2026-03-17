/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Service;

import dao.VehiculoDAO;
import model.Vehiculo;

/**
 *
 * @author hp
 */
public class VehiculoService {

    private VehiculoDAO vehiculoDAO;

    public VehiculoService(VehiculoDAO vehiculoDAO) {
        this.vehiculoDAO = vehiculoDAO;
    }

    public boolean registrarVehiculo(Vehiculo v) {
        if (validarPlacaUnica(v.getPlaca())) {
            vehiculoDAO.guardarVehiculo(v);
            System.out.println("Vehiculo registrado correctamente en archivo.");
            return true;
        } else {
            System.out.println("Error: Ya existe un vehiculo con la placa " + v.getPlaca());
            return false;
        }
    }

    public boolean validarPlacaUnica(String placa) {
        return !vehiculoDAO.buscarPorPlaca(placa);
    }

    public boolean vehiculoDisponible(Vehiculo v) {
        return v.isDisponible() && v.hayCupos();
    }
}
