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
    System.out.println("1. Registrar Vehículo");
    System.out.println("2. Registrar Conductor");
    System.out.println("3. Asignar Conductor a Vehículo");
    System.out.println("4. Venta de Ticket");
    System.out.println("5. Reporte de Flota y Asignaciones");
    System.out.println("6. Módulo de Consultas y Reportes (Ventas)"); // <-- NUEVO
    System.out.println("7. Salir");
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
    leer.nextLine(); // Limpiar buffer
    System.out.println("\n--- MÓDULO DE VENTAS TRANSCESAR ---");
    
    System.out.print("Ingrese Placa del vehículo: ");
    String placa = leer.nextLine();

    // 1. Recuperar Conductor
    String cedulaAsignada = vehiculoDAO.buscarCedulaAsignada(placa);
    if (cedulaAsignada == null) {
        System.out.println("❌ Error: Este vehículo no tiene conductor asignado.");
        return;
    }
    Conductor conductor = personaDAO.buscarConductorPorCedula(cedulaAsignada);

    // 2. Datos del Pasajero
    System.out.print("Nombre Pasajero: "); String nomP = leer.nextLine();
    System.out.print("Cédula Pasajero: "); String cedP = leer.nextLine();
    System.out.println("Categoría: 1. Estudiante | 2. Adulto Mayor | 3. Regular");
    System.out.print("Seleccione: ");
    int tipoP = leer.nextInt();
    leer.nextLine(); // Limpiar buffer
    
    Pasajero pasajero;
    if (tipoP == 1) pasajero = new PasajeroEstudiante(cedP, nomP);
    else if (tipoP == 2) pasajero = new PasajeroAdultoMayor(cedP, nomP);
    else pasajero = new PasajeroRegular(cedP, nomP);

    // 3. Identificar tipo de vehículo (Polimorfismo)
    System.out.println("Tipo de Vehículo: 1. Buseta | 2. MicroBus | 3. Bus");
    System.out.print("Seleccione: ");
    int tipoV = leer.nextInt();
    leer.nextLine(); // Limpiar buffer
    
    Vehiculo v;
    // Aquí podrías luego buscar el vehículo real en el DAO en lugar de crear uno nuevo
    if (tipoV == 1) v = new Buseta(placa, "Ruta Valledupar", 19, 0, 8000, true);
    else if (tipoV == 2) v = new MicroBus(placa, "Ruta Tamalameque", 25, 0, 10000, true);
    else v = new Bus(placa, "Ruta Intermunicipal", 45, 0, 15000, true);

    // 4. Procesar Venta
    // Creamos el ticket (el valor final inicia en 0 porque el Service lo calculará)
    Ticket t = new Ticket(pasajero, v, LocalDate.now(), "Valledupar", "Destino", 0.0);
    
    // El TicketService de tu compañera hace todas las validaciones (festivos, 3 tickets, etc.)
    ticketService.venderTicket(t, conductor);

    // 5. Mostrar resultado solo si se asignó un valor (Venta exitosa)
    if (t.getValorFinal() > 0) {
        System.out.println("\n====================================");
        System.out.println("   RESUMEN DE VENTA - TRANSCESAR");
        System.out.println("====================================");
        System.out.println("Pasajero: " + pasajero.getNombre());
        System.out.println("Vehículo: " + v.getClass().getSimpleName() + " (" + placa + ")");
        System.out.println("Total a Pagar: $" + t.getValorFinal());
        System.out.println("====================================\n");
    }
}

   public static void mostrarReporteDetallado() {
    System.out.println("\n" + "=".repeat(60));
    System.out.println("       ESTADO GLOBAL DE LA FLOTA - TRANSCESAR S.A.S");
    System.out.println("=".repeat(60));

    // Arreglo con los nombres de tus archivos de persistencia
    String[] archivos = {"busetas.txt", "microbuses.txt", "buses.txt"};
    
    for (String archivo : archivos) {
        // Extraemos el tipo de vehículo del nombre del archivo para el encabezado
        String tipo = archivo.replace(".txt", "").toUpperCase();
        System.out.println("\n>>> CATEGORÍA: " + tipo);
        System.out.println("-".repeat(60));

        for (String linea : vehiculoDAO.listarVehiculos(archivo)) {
            String[] datos = linea.split(";");
            if (datos.length < 2) continue; // Salta líneas vacías o corruptas

            String placa = datos[0];
            String ruta = datos[1];
            
            // Lógica de recuperación de conductor
            String cedulaC = vehiculoDAO.buscarCedulaAsignada(placa);
            String nombreC = "[POR ASIGNAR]";
            
            if (cedulaC != null) {
                Conductor c = personaDAO.buscarConductorPorCedula(cedulaC);
                if (c != null) nombreC = c.getNombre();
            }

            // Formateo limpio para la consola
            System.out.printf("PLACA: %-10s | CONDUCTOR: %-20s | RUTA: %s%n", 
                              placa, nombreC, ruta);
        }
    }
        System.out.println("=".repeat(60));
    }
}