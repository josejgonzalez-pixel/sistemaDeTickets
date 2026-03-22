package view;

import DAO.ReservaDAO;
import DAO.RutaDAO;
import Service.PasajeroService;
import Service.ReservaService;
import Service.VehiculoService;
import dao.PersonaDAO;
import dao.TicketDAO;
import dao.VehiculoDAO;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import model.Bus;
import model.Buseta;
import model.Conductor;
import model.MicroBus;
import model.Pasajero;
import model.PasajeroRegular;
import model.Reserva;
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
    private static ReservaDAO reservaDAO = new ReservaDAO(); 
    private static TicketDAO ticketDAO = new TicketDAO();
    private static ReservaService reservaService = new ReservaService(reservaDAO, ticketDAO);

    public static void main(String[] args) {
        int opcion = 0;
        do {
            try {
                opcion = mostrarMenu();
                switch (opcion) {
                    case 1: menuRegistrarVehiculo(); 
                    break;
                    case 2: menuRegistrarConductor(); 
                    break;
                    case 3: menuAsignarConductorAVehiculo(); 
                    break;
                    case 4: menuVentaTicket(); 
                    break;
                    case 5: mostrarReporteDetallado(); 
                    break;
                    case 6: menuModuloReportes(); 
                    break;
                    case 7: menuGestionarReservas();
                    break;
                    case 8: System.out.println("Saliendo del sistema...");
                    break;
                    default: System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: Ingrese un valor numérico válido.");
                leer.nextLine(); 
            }
        } while (opcion != 8);
    }

    public static int mostrarMenu() {
        System.out.println("\n========== SISTEMA TRANSCESAR S.A.S ==========");
        System.out.println("1. Registrar Vehiculo (Placa y Ruta)");
        System.out.println("2. Registrar Conductor (Datos Completos)");
        System.out.println("3. Asignar Conductor a Vehiculo");
        System.out.println("4. Venta de Ticket (Calculo Automatico)");
        System.out.println("5. Reporte General de Flota");
        System.out.println("6. Modulo de Reportes (Filtros y Caja)");
        System.out.println("7. Gestionar reservas");
        System.out.println("8. salir");
        System.out.print("Seleccione una opcion: ");
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
        System.out.print("Cedula: "); String ced = leer.nextLine();
        System.out.print("Nombre: "); String nom = leer.nextLine();
        System.out.print("Licencia: "); String lic = leer.nextLine();
        System.out.print("Categoría: "); String cat = leer.nextLine();
        personaDAO.guardarPersona(new Conductor(lic, cat, ced, nom));
    }

    private static void menuAsignarConductorAVehiculo() {
        leer.nextLine();
        System.out.print("Placa: "); String placa = leer.nextLine();
        System.out.print("Cedula Conductor: "); String cedula = leer.nextLine();
        if (personaDAO.buscarConductorPorCedula(cedula) != null) {
            vehiculoDAO.guardarAsignacion(placa, cedula);
            System.out.println("Asignacion exitosa.");
        } else {
            System.out.println("Conductor no encontrado.");
        }
    }

    private static void menuVentaTicket() {
        leer.nextLine();
        System.out.print("Placa del vehiculo: ");
        String placa = leer.nextLine();
        String cedulaAsignada = vehiculoDAO.buscarCedulaAsignada(placa);
        if (cedulaAsignada == null) {
            System.out.println("Error: Vehículo sin conductor.");
            return;
        }
        Conductor conductor = personaDAO.buscarConductorPorCedula(cedulaAsignada);
        System.out.print("Nombre Pasajero: "); String nomP = leer.nextLine();
        System.out.print("Cedula Pasajero: "); String cedP = leer.nextLine();
        System.out.print("Fecha Nacimiento (yyyy-MM-dd): "); String fN = leer.nextLine();
        
        Pasajero p = new PasajeroRegular(cedP, nomP, LocalDate.parse(fN));
        p = pasajeroService.asignarCategoria(p);

        System.out.println("Tipo Vehiculo: 1. Buseta | 2. MicroBus | 3. Bus");
        int tipoV = leer.nextInt();
        Vehiculo v = (tipoV == 1) ? new Buseta(placa, new Ruta("R1","O","D",1,1), 19, 0, 8000, true, conductor) : new Bus(placa, new Ruta("R1","O","D",1,1), 45, 0, 15000, true, conductor);

        Ticket t = new Ticket(p, v, LocalDate.now(), "Origen", "Destino", 0);
        ticketService.venderTicket(t, conductor);
        System.out.println("VENTA EXITOSA. Total: $" + t.getValorFinal());
    }

    public static void mostrarReporteDetallado() {
        System.out.println("\n--- ESTADO DE LA FLOTA ---");
        for (String linea : vehiculoDAO.listarTodosLosVehiculos()) {
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
            System.out.println("\n--- REPORTES ---");
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
    
    private static void menuGestionarReservas() throws IOException {
    int opReserva = 0;
    do {
        System.out.println("\n--- MODULO DE RESERVAS ---");
        System.out.println("1. Crear Nueva Reserva");
        System.out.println("2. Confirmar/Cancelar Reserva");
        System.out.println("3. Volver");
        System.out.print("Seleccione: ");
        opReserva = leer.nextInt();
        leer.nextLine(); 

        switch (opReserva) {
            case 1:
                System.out.print("Cedula Pasajero: "); String cp = leer.nextLine();
                System.out.print("Placa Vehículo: "); String pv = leer.nextLine();
                System.out.print("Código Reserva: "); String cr = leer.nextLine();

                Pasajero p = personaDAO.buscarPasajeroPorCedula(cp); 
                Vehiculo v = vehiculoDAO.buscarVehiculoPorPlaca(pv);

                if (p != null && v != null) {
                    // Verifica el orden de los datos en el constructor de tu compañero
                    Reserva nr = new Reserva(cr, p, v, v.getRuta(), LocalDate.now(), LocalDate.now(), "PENDIENTE");
                    try {
                        // CAMBIA 'guardarReserva' por el nombre que él usó en ReservaDAO
                        reservaDAO.guardarReserva(nr); 
                        System.out.println("Guardado exitoso.");
                    } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
                } else { System.out.println("Datos no encontrados."); }
                break;
            case 2:
            System.out.println("\n--- RESERVAS REGISTRADAS ---");
    try {
        // Usamos el método que esta en ReservaDAO
        List<String> lineas = reservaDAO.listarReservas();
        
        if (lineas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
        } else {
            for (String linea : lineas) {
                String[] datos = linea.split(";");
                // Mostramos: Código - Cédula - Placa - Estado
                // Según el orden del write de mi compañero: [0];[1];[2];[6]
                System.out.printf("CÓDIGO: %-8s | PASAJERO: %-12s | BUS: %-8s | ESTADO: %s%n", 
                                   datos[0], datos[1], datos[2], datos[6]);
            }

            System.out.print("\nIngrese código para procesar (Confirmar/Cancelar) o '0' para volver: ");
            String codBusqueda = leer.nextLine();
            
            if (!codBusqueda.equals("0")) {
                System.out.print("1. Confirmar (Convertir a Ticket) | 2. Cancelar: ");
                int accion = leer.nextInt(); leer.nextLine();

                if (accion == 1) {
                    // Para confirmar, el Service pide un objeto Reserva. 
                    // Aquí es donde necesitamos "reconstruirlo" rápido
                    Reserva resObj = buscarYReconstruir(codBusqueda, lineas);
                    if (resObj != null) {
                        reservaService.confirmarReserva(resObj);
                        System.out.println("¡Reserva convertida a Ticket!");
                    } else {
                        System.out.println("No se encontró la reserva o faltan datos.");
                    }
                } else if (accion == 2) {
                    reservaService.cancelarReserva(codBusqueda);
                    System.out.println("Reserva cancelada correctamente.");
                    }
                }
            }
        } catch (IOException e) {
        System.out.println("Error al leer el archivo de reservas.");
        }
        break;
            }
        } while (opReserva != 3);
    }
    
    private static Reserva buscarYReconstruir(String codigo, List<String> lineas) {
    for (String l : lineas) {
        String[] d = l.split(";");
        if (d[0].equals(codigo)) {
            // Buscamos los objetos reales con tus DAOs
            Pasajero p = personaDAO.buscarPasajeroPorCedula(d[1]);
            Vehiculo v = vehiculoDAO.buscarVehiculoPorPlaca(d[2]);
            if (p != null && v != null) {
                return new Reserva(d[0], p, v, v.getRuta(), 
                                   LocalDate.parse(d[4]), LocalDate.parse(d[5]), d[6]);
                }
            }
        }
        return null;
    }
}
