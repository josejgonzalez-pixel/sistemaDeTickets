package view;

import java.util.Scanner;
// Importar las clases que tus compañeros crearán en model y service
// import model.*; 
// import service.*;

public class Main {
    private static Scanner leer = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = 0;
        do {
            opcion = mostrarMenu();
            switch (opcion) {
                case 1:
                    System.out.println("--- GESTIÓN DE VEHÍCULOS ---");
                    // Aquí se llamará a los métodos del Desarrollador 1
                    break;
                case 2:
                    System.out.println("--- GESTIÓN DE PERSONAS ---");
                    // Aquí se llamará a los métodos del Desarrollador 2
                    break;
                case 3:
                    System.out.println("--- VENTA DE TICKETS ---");
                    // Aquí se integrará la lógica de venta
                    break;
                case 4:
                    System.out.println("--- REPORTES Y ESTADÍSTICAS ---");
                    // Tu Actividad 4: Implementar consultas
                    break;
                case 5:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
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
        System.out.print("Seleccione una opción: ");
        return leer.nextInt();
    }
}