package view;

import DAO.RutaDAO;
import Service.PasajeroService;
import Service.VehiculoService;
import dao.PersonaDAO;
import dao.VehiculoDAO;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;
import model.Bus;
import model.Buseta;
import model.Conductor;
import model.MicroBus;
import model.Pasajero;
import model.PasajeroRegular;
import model.Ruta;
import model.Ticket; 
import model.Vehiculo;
import service.TicketService;

public class Main {
    
    private static Scanner leer = new Scanner(System.in);
    private static PersonaDAO personaDAO = new PersonaDAO();
    private static VehiculoDAO vehiculoDAO = new VehiculoDAO();
    private static RutaDAO rutaDAO = new RutaDAO();
    private static VehiculoService vehiculoService = new VehiculoService(vehiculoDAO);
    private static TicketService ticketService = new TicketService();
    private static PasajeroService pasajeroService = new PasajeroService();

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
        System.out.println("5. Reporte General de Flota");
        System.out.println("6. Módulo de Reportes (Filtros y Caja)");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción: ");
        return leer.nextInt();
    }

    private static void menuRegistrarVehiculo() {
        leer.nextLine();
        System.out.print("Ingrese la Placa: ");
        String placa = leer.nextLine();
        System.out.print("Ingrese código de la Ruta: ");
        String codRuta = leer.nextLine();
        System.out.print("Origen: "); String origen = leer.nextLine();
        System.out.print("Destino: "); String destino = leer.nextLine();
        System.out.print("Kilómetros: "); double km = leer.nextDouble();
        System.out.print("Tiempo estimado (horas): "); double tiempo = leer.nextDouble();

        Ruta rutaObj = new Ruta(codRuta, origen, destino, km, tiempo);

        System.out.println("\nSeleccione Tipo de Vehículo: 1. Buseta | 2. MicroBus | 3. Bus");
        int tipo = leer.nextInt();
        Vehiculo v = null;
        switch (tipo) {
            case 1: v = new Buseta(placa, rutaObj, 19, 0, 8000, true, null); break;
            case 2: v = new MicroBus(placa, rutaObj, 25, 0, 10000, true, null); break;
            case 3: v = new Bus(placa, rutaObj, 45, 0, 15000, true, null); break;
        }
        if (v != null) vehiculoService.registrarVehiculo(v);
    }

    private static void menuRegistrarConductor() {
        leer.nextLine();
        System.out.print("Cédula: "); String ced = leer.nextLine();
        System.out.print("Nombre: "); String nom = leer.nextLine();
        System.out.print("Licencia: "); String lic = leer.nextLine();
        System.out.print("Categoría: "); String cat = leer.nextLine();
        personaDAO.guardarPersona(new Conductor(lic, cat, ced, nom));
    }

    private static void menuAsignarConductorAVehiculo() {
        leer.nextLine();
        System.out.print("Placa: "); String placa = leer.nextLine();
        System.out.print("Cédula Conductor: "); String cedula = leer.nextLine();
        if (personaDAO.buscarConductorPorCedula(cedula) != null) {
            vehiculoDAO.guardarAsignacion(placa, cedula);
            System.out.println("Asignación exitosa.");
        } else {
            System.out.println("Conductor no encontrado.");
        }
    }

    private static void menuVentaTicket() {
        leer.nextLine();
        System.out.print("Placa del vehículo: ");
        String placa = leer.nextLine();
        String cedulaAsignada = vehiculoDAO.buscarCedulaAsignada(placa);
        if (cedulaAsignada == null) {
            System.out.println("Error: Vehículo sin conductor.");
            return;
        }
        Conductor conductor = personaDAO.buscarConductorPorCedula(cedulaAsignada);
        System.out.print("Nombre Pasajero: "); String nomP = leer.nextLine();
        System.out.print("Cédula Pasajero: "); String cedP = leer.nextLine();
        System.out.print("Fecha Nacimiento (yyyy-MM-dd): "); String fN = leer.nextLine();
        
        Pasajero p = new PasajeroRegular(cedP, nomP, LocalDate.parse(fN));
        p = pasajeroService.asignarCategoria(p);

        System.out.println("Tipo Vehículo: 1. Buseta | 2. MicroBus | 3. Bus");
        int tipoV = leer.nextInt();
        Vehiculo v = (tipoV == 1) ? new Buseta(placa, new Ruta("R1","O","D",1,1), 19, 0, 8000, true, conductor) : new Bus(placa, new Ruta("R1","O","D",1,1), 45, 0, 15000, true, conductor);

        Ticket t = new Ticket(p, v, LocalDate.now(), "Origen", "Destino", 0);
        ticketService.venderTicket(t, conductor);
        System.out.println("VENTA EXITOSA. Total: $" + t.getValorFinal());
    }

    public static void mostrarReporteDetallado() {
        System.out.println("\n--- ESTADO DE LA FLOTA ---");
        for (String linea : vehiculoDAO.listarVehiculos()) {
            String[] datos = linea.split(";");
            String cedulaC = vehiculoDAO.buscarCedulaAsignada(datos[0]);
            var conductor = personaDAO.buscarConductorPorCedula(cedulaC);
            String nombreC = (conductor != null) ? conductor.getNombre() : "[PENDIENTE]";
            System.out.printf("PLACA: %-10s | RUTA: %-15s | CONDUCTOR: %s%n", datos[0], datos[1], nombreC);
        }
    }

    private static void menuModuloReportes() {
        int op = 0;
        do {
            System.out.println("\n--- MÓDULO LÍDER: REPORTES ---");
            System.out.println("1. Por Fecha | 2. Por Vehículo | 3. Por Pasajero | 4. Caja | 5. Volver");
            op = leer.nextInt(); leer.nextLine();
            if (op == 1) { System.out.print("Fecha (AAAA-MM-DD): "); generarReporteFiltrado(5, leer.nextLine(), "Reporte Fecha"); }
            if (op == 4) mostrarResumenCaja(LocalDate.now().toString());
        } while (op != 5);
    }

    private static void generarReporteFiltrado(int col, String val, String tit) {
        System.out.println("\n--- " + tit + " ---");
        try (BufferedReader br = new BufferedReader(new FileReader("tickets.txt"))) {
            String l;
            while ((l = br.readLine()) != null) {
                String[] d = l.split(";");
                if (d.length > col && d[col].equalsIgnoreCase(val)) 
                    System.out.println("Ticket: " + d[0] + " | Valor: " + d[d.length-1]);
            }
        } catch (IOException e) { System.out.println("Error de archivo."); }
    }

    private static void mostrarResumenCaja(String fecha) {
        double total = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("tickets.txt"))) {
            String l;
            while ((l = br.readLine()) != null) {
                String[] d = l.split(";");
                if (d.length > 5 && d[5].equals(fecha)) total += Double.parseDouble(d[d.length-1]);
            }
            System.out.println("TOTAL RECAUDADO HOY: $" + total);
        } catch (Exception e) { System.out.println("Error en caja."); }
    }
}
