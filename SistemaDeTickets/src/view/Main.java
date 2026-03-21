package view;

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
    System.out.println("\n============================================================");
    System.out.println("           ESTADO GENERAL DE LA FLOTA - TRANSCESAR          ");
    System.out.println("============================================================");

    // Definimos los archivos que manejamos en el sistema
    String[] archivos = {"busetas.txt", "buses.txt", "microbuses.txt"};
    String[] etiquetas = {"BUSETAS", "BUSES GRANDES", "MICROBUSES"};

    for (int i = 0; i < archivos.length; i++) {
        System.out.println("\n>>> CATEGORÍA: " + etiquetas[i]);
        System.out.println("------------------------------------------------------------");
        
        // Obtenemos la lista de cada archivo a través del DAO del Desarrollador 1
        for (String linea : vehiculoDAO.listarVehiculos(archivos[i])) {
            String[] datos = linea.split(";");
            String placa = datos[0];
            String ruta = datos[1];
            String capacidad = datos[2];
            
            // Buscamos la asignación del conductor
            String cedulaC = vehiculoDAO.buscarCedulaAsignada(placa);
            String nombreC;
            
            if (cedulaC != null && !cedulaC.isEmpty()) {
                // Usamos el DAO de personas del Desarrollador 2
                var conductor = personaDAO.buscarConductorPorCedula(cedulaC);
                nombreC = (conductor != null) ? conductor.getNombre() : "[ERROR EN DATOS]";
            } else {
                nombreC = "[PENDIENTE POR ASIGNAR]";
            }

            // Formateamos la salida para que se vea organizada
            System.out.printf("PLACA: %-10s | RUTA: %-15s | CAP: %-3s | CONDUCTOR: %s%n", 
                             placa, ruta, capacidad, nombreC);
        }
    }
    System.out.println("============================================================");
}
    
    private static void menuModuloReportes() {
    int opReporte = 0;
    do {
        System.out.println("\n========== MÓDULO DE REPORTES (LÍDER) ==========");
        System.out.println("1. Consultar por Fecha específica");
        System.out.println("2. Consultar por Tipo de Vehículo");
        System.out.println("3. Consultar por Tipo de Pasajero");
        System.out.println("4. Resumen del día (Tickets y Recaudación)");
        System.out.println("5. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
        
        try {
            opReporte = leer.nextInt();
            leer.nextLine(); // Limpiar buffer

            switch (opReporte) {
                case 1:
                    System.out.print("Ingrese fecha (AAAA-MM-DD): ");
                    String fecha = leer.nextLine();
                    generarReporteFiltrado(5, fecha, "Fecha: " + fecha);
                    break;
                case 2:
                    System.out.print("Ingrese tipo de vehículo (Bus/Buseta/MicroBus): ");
                    String tipoV = leer.nextLine();
                    // Buscamos en la columna donde se guarda el tipo/placa
                    generarReporteFiltrado(2, tipoV, "Tipo de Vehículo: " + tipoV);
                    break;
                case 3:
                    System.out.print("Ingrese tipo de pasajero: ");
                    String tipoP = leer.nextLine();
                    generarReporteFiltrado(1, tipoP, "Tipo de Pasajero: " + tipoP);
                    break;
                case 4:
                    mostrarResumenCaja(LocalDate.now().toString());
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error en la entrada de datos.");
            leer.nextLine(); 
        }
    } while (opReporte != 5);
}

// Método auxiliar de la View para no tocar el Service de tus compañeros
private static void generarReporteFiltrado(int columna, String valorBusqueda, String titulo) {
    System.out.println("\n--- Reporte: " + titulo + " ---");
    try (BufferedReader br = new BufferedReader(new FileReader("tickets.txt"))) {
        String linea;
        boolean encontro = false;
        while ((linea = br.readLine()) != null) {
            String[] datos = linea.split(";");
            if (datos[columna].equalsIgnoreCase(valorBusqueda)) {
                // Aquí usamos el estándar de mostrar los datos básicos que ya están en el archivo
                System.out.println("Ticket -> Pasajero: " + datos[0] + " | Vehículo: " + datos[2] + " | Fecha: " + datos[5]);
                encontro = true;
            }
        }
        if (!encontro) System.out.println("No se encontraron registros.");
    } catch (IOException e) {
        System.out.println("Error al leer el archivo de tickets.");
        }
    }

private static void mostrarResumenCaja(String fechaHoy) {
    double recaudoTotal = 0;
    int contador = 0;
    try (BufferedReader br = new BufferedReader(new FileReader("tickets.txt"))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            String[] datos = linea.split(";");
            if (datos[5].equals(fechaHoy)) {
                contador++;
                // Asumiendo que el valor final es el último dato guardado por TicketDAO
                recaudoTotal += Double.parseDouble(datos[datos.length - 1]); 
            }
        }
        System.out.println("\n--- BALANCE DIARIO (" + fechaHoy + ") ---");
        System.out.println("Total Tickets: " + contador);
        System.out.println("Total Recaudado: $" + recaudoTotal);
    } catch (Exception e) {
        System.out.println("Error al calcular el resumen de caja.");
        }
    }
}