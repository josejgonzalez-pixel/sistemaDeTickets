package view;

import Service.VehiculoService;
import dao.PersonaDAO;
import dao.VehiculoDAO;
import java.time.LocalDate;
import java.util.Scanner;
import model.Bus;
import model.Buseta;
import model.Conductor;
import model.MicroBus;
import model.Pasajero;
import model.PasajeroAdultoMayor;
import model.PasajeroEstudiante;
import model.PasajeroRegular;
import model.Ticket; 
import model.Vehiculo;
import service.TicketService;

public class Main {
    
    private static Scanner leer = new Scanner(System.in);
   private static PersonaDAO personaDAO = new PersonaDAO();
    private static VehiculoDAO vehiculoDAO = new VehiculoDAO();
    private static VehiculoService vehiculoService = new VehiculoService(vehiculoDAO);
    private static TicketService ticketService = new TicketService();

    public static void main(String[] args) {
        int opcion = 0;
        do {
            try {
                opcion = mostrarMenu();
                switch (opcion) {
                    case 1: menuRegistrarVehiculo(); break;
                    case 2: menuRegistrarConductor(); break;
                    case 3: menuAsignarConductorAVehiculo(); break;
                    case 4: menuVentaTicket(); break;
                    case 5: mostrarReporteDetallado(); break;
                    case 6: System.out.println("Saliendo del sistema..."); break;
                    default: System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: Ingrese un valor numérico válido.");
                leer.nextLine(); 
            }
        } while (opcion != 6);
    }

    public static int mostrarMenu() {
        System.out.println("\n========== SISTEMA TRANSCESAR S.A.S ==========");
        System.out.println("1. Registrar Vehículo (Placa y Ruta)");
        System.out.println("2. Registrar Conductor (Datos Completos)");
        System.out.println("3. Asignar Conductor a Vehículo");
        System.out.println("4. Venta de Ticket (Cálculo Automático)");
        System.out.println("5. Reporte de Flota y Asignaciones");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
        return leer.nextInt();
    }

    private static void menuRegistrarVehiculo() {
        leer.nextLine();
        System.out.print("Ingrese la Placa: ");
        String placa = leer.nextLine();
        System.out.print("Ingrese la Ruta: ");
        String ruta = leer.nextLine();

        System.out.println("\nSeleccione Tipo de Vehículo:");
        System.out.println("1. Buseta (19 pas. - $8.000)");
        System.out.println("2. MicroBus (25 pas. - $10.000)");
        System.out.println("3. Bus (45 pas. - $15.000)");
        int tipo = leer.nextInt();

        Vehiculo v = null;
        switch (tipo) {
            case 1: v = new Buseta(placa, ruta, 19, 0, 8000, true); break;
            case 2: v = new MicroBus(placa, ruta, 25, 0, 10000, true); break;
            case 3: v = new Bus(placa, ruta, 45, 0, 15000, true); break;
            default: System.out.println("Tipo inválido."); return;
        }
        vehiculoService.registrarVehiculo(v);
    }

    private static void menuRegistrarConductor() {
        leer.nextLine();
        System.out.println("\n--- REGISTRO DE CONDUCTOR ---");
        System.out.print("Cédula: "); String ced = leer.nextLine();
        System.out.print("Nombre: "); String nom = leer.nextLine();
        System.out.print("Número de Licencia: "); String lic = leer.nextLine();
        System.out.print("Categoría (C1, C2, C3): "); String cat = leer.nextLine();

        Conductor nuevoC = new Conductor(lic, cat, ced, nom);
        personaDAO.guardarPersona(nuevoC); 
    }

    private static void menuAsignarConductorAVehiculo() {
        leer.nextLine();
        System.out.println("\n--- VINCULAR CONDUCTOR Y VEHÍCULO ---");
        System.out.print("Ingrese Placa del Vehículo: ");
        String placa = leer.nextLine();
        System.out.print("Ingrese Cédula del Conductor: ");
        String cedula = leer.nextLine();

        Conductor c = personaDAO.buscarConductorPorCedula(cedula);
        if (c == null) {
            System.out.println("Error: El conductor no está registrado.");
            return;
        }

        vehiculoDAO.guardarAsignacion(placa, cedula);
        System.out.println("¡Éxito! " + c.getNombre() + " asignado a placa: " + placa);
    }

    private static void menuVentaTicket() {
        leer.nextLine();
        System.out.println("\n--- MÓDULO DE VENTAS ---");
        System.out.print("Ingrese Placa del vehículo: ");
        String placa = leer.nextLine();

        // Recupera automáticamente al conductor asignado desde el archivo
        String cedulaAsignada = vehiculoDAO.buscarCedulaAsignada(placa);
        if (cedulaAsignada == null) {
            System.out.println("Error: Este vehículo no tiene conductor asignado.");
            return;
        }
        Conductor conductor = personaDAO.buscarConductorPorCedula(cedulaAsignada);

        // Datos del Pasajero
        System.out.print("Nombre Pasajero: "); String nomP = leer.nextLine();
        System.out.print("Cédula Pasajero: "); String cedP = leer.nextLine();
        System.out.println("Categoría: 1. Estudiante | 2. Adulto Mayor | 3. Regular");
        int tipoP = leer.nextInt();
        
        Pasajero pasajero;
        if (tipoP == 1) pasajero = new PasajeroEstudiante(cedP, nomP);
        else if (tipoP == 2) pasajero = new PasajeroAdultoMayor(cedP, nomP);
        else pasajero = new PasajeroRegular(cedP, nomP);

        // Identificar tipo de vehículo para instanciar (Polimorfismo)
        System.out.println("Tipo de Vehículo: 1. Buseta | 2. MicroBus | 3. Bus");
        int tipoV = leer.nextInt();
        Vehiculo v;
        if (tipoV == 1) v = new Buseta(placa, "Ruta", 19, 0, 8000, true);
        else if (tipoV == 2) v = new MicroBus(placa, "Ruta", 25, 0, 10000, true);
        else v = new Bus(placa, "Ruta", 45, 0, 15000, true);

        Ticket t = new Ticket(pasajero, v, LocalDate.now(), "Origen", "Destino", 0);
        ticketService.venderTicket(t, conductor);

        if (t.getValorFinal() > 0) {
            System.out.println("\nVENTA EXITOSA: " + pasajero.getNombre());
            System.out.println("Total Pagado (con descuentos): $" + t.getValorFinal());
        }
    }

    public static void mostrarReporteDetallado() {
        System.out.println("\n--- ESTADO DE LA FLOTA (Busetas) ---");
        for (String linea : vehiculoDAO.listarVehiculos("busetas.txt")) {
            String[] datos = linea.split(";");
            String placa = datos[0];
            String cedulaC = vehiculoDAO.buscarCedulaAsignada(placa);
            String nombreC = (cedulaC != null) ? personaDAO.buscarConductorPorCedula(cedulaC).getNombre() : "[POR ASIGNAR]";
            System.out.println("PLACA: " + placa + " | CONDUCTOR: " + nombreC + " | RUTA: " + datos[1]);
        }
    }
}