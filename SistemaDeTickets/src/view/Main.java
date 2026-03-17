package view;

import java.util.Scanner;
import java.util.ArrayList;
import model.ticket; // Importante importar la clase que creamos arriba

public class Main {
    private static Scanner leer = new Scanner(System.in);
    
    // Lista donde se guardarán los tickets (Accesible para reportes)
    private static ArrayList<ticket> listaTickets = new ArrayList<>();

    public static void main(String[] args) {
        int opcion = 0;
        do {
            try {
                opcion = mostrarMenu();
                switch (opcion) {
                    case 1: System.out.println("Módulo de Vehículos (D1)"); break;
                    case 2: System.out.println("Módulo de Personas (D2)"); break;
                    case 3: System.out.println("Módulo de Ventas (D2)"); break;
                    case 4: 
                        mostrarReporte(); // Tu Actividad 4
                        break;
                    case 5: System.out.println("Saliendo..."); break;
                    default: System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Error: Ingrese un número.");
                leer.nextLine(); // Limpia el error
            }
        } while (opcion != 5);
    }

    public static int mostrarMenu() {
        System.out.println("\n--- SISTEMA TRANSCESAR S.A.S ---");
        System.out.println("1. Gestión de Vehículos");
        System.out.println("2. Gestión de Personas");
        System.out.println("3. Venta de Tickets");
        System.out.println("4. Reportes y Estadísticas");
        System.out.println("5. Salir");
        System.out.print("Seleccione: ");
        return leer.nextInt();
    }

    // Código final de tu Actividad 4
    public static void mostrarReporte() {
        System.out.println("\n======= REPORTE DE VENTAS TOTALES =======");
        if (listaTickets.isEmpty()) {
            System.out.println("No hay ventas registradas.");
        } else {
            double granTotal = 0;
            for (ticket t : listaTickets) {
                t.imprimirDetalle(); // Usa interfaz Imprimible
                granTotal += t.calcularTotal(); // Usa interfaz Calculable
            }
            System.out.println("---------------------------------------");
            System.out.println("TOTAL RECAUDADO EN EL DÍA: $" + granTotal);
        }
    }
}