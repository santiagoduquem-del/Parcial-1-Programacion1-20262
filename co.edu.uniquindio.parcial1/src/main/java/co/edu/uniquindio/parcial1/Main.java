package co.edu.uniquindio.parcial1;

import co.edu.uniquindio.parcial1.model.Habitacion;
import co.edu.uniquindio.parcial1.model.Hotel;
import co.edu.uniquindio.parcial1.model.Huesped;
import co.edu.uniquindio.parcial1.model.Reserva;
import co.edu.uniquindio.parcial1.model.ServicioAdicional;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static Hotel hotel;

    public static void main(String[] args) {

        //aqui se inicializan datos del hotel
        hotel = new Hotel("StayPlus", "900123456-7", "Calle 10 # 5-20", "6067391234", "www.stayplus.com");

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {
                case 1 -> registrarHuesped();
                case 2 -> agregarHabitacion();
                case 3 -> agregarServicioAdicional();
                case 4 -> crearReserva();
                case 5 -> agregarHabitacionAReserva();
                case 6 -> agregarServicioAReserva();
                case 7 -> confirmarReserva();
                case 8 -> cambiarEstadoReserva();
                case 9 -> buscarHuespedPorTelefono();
                case 10 -> verificarNumeroPerfecto();
                case 11 -> calcularIngresosPorFecha();
                case 12 -> listarHabitacionesDisponibles();
                case 13 -> imprimirDetalleReserva();
                case 0 -> System.out.println("adios...");
                default -> System.out.println("Opcion invalida intente de nuevo.");
            }
            System.out.println();
        } while (opcion != 0);

        sc.close();
    }



    private static void mostrarMenu() {

        System.out.println(" Hotel " + hotel.getNombreComercial());

        System.out.println("1.  Registrar huesped");
        System.out.println("2.  Agregar habitacion");
        System.out.println("3.  Agregar servicio adicional");
        System.out.println("4.  Crear reserva");
        System.out.println("5.  Agregar habitacion a una reserva");
        System.out.println("6.  Agregar servicio adicional a una reserva");
        System.out.println("7.  Confirmar reserva");
        System.out.println("8.  Cambiar estado de una reserva");
        System.out.println("9.  Buscar huesped por telefono");
        System.out.println("10. Verificar si un telefono es numero perfecto");
        System.out.println("11. Calcular ingresos por fecha");
        System.out.println("12. Listar habitaciones disponibles");
        System.out.println("13. Imprimir detalle de una reserva");
        System.out.println("0.  Salir");
    }



    private static void registrarHuesped() {
        System.out.println("--- Registrar huesped ---");
        String nombre = leerTexto("Nombre completo: ");
        String documento = leerTexto("Documento de identidad: ");
        String correo = leerTexto("Correo electronico: ");
        int telefono = leerEntero("Telefono: ");
        String pais = leerTexto("Pais de procedencia: ");

        Huesped huesped = new Huesped(nombre, documento, correo, telefono, pais);
        hotel.registrarHuesped(huesped);
        System.out.println("Huesped registrado con exito.");
    }

    private static void agregarHabitacion() {
        System.out.println("--- Agregar habitacion ---");
        int numero = leerEntero("Numero de habitacion: ");
        int piso = leerEntero("Piso: ");
        String tipo = seleccionarTipoHabitacion();
        int capacidad = leerEntero("Capacidad maxima: ");
        double precio = leerDouble("Precio por noche: ");

        Habitacion habitacion = new Habitacion(numero, piso, tipo, capacidad, precio);
        hotel.agregarHabitacion(habitacion);
        System.out.println("Habitacion agregada con exito.");
    }

    private static void agregarServicioAdicional() {
        System.out.println(" Agregar servicio adicional ");
        int codigo = leerEntero("Codigo del servicio: ");
        String nombre = leerTexto("Nombre del servicio: ");
        String descripcion = leerTexto("Descripcion: ");
        double precio = leerDouble("Precio: ");

        ServicioAdicional servicio = new ServicioAdicional(codigo, nombre, descripcion, precio);
        hotel.agregarServicioAdicional(servicio);
        System.out.println("Servicio adicional agregado con exito.");
    }

    private static void crearReserva() {
        System.out.println("Crear reserva ");
        int telefono = leerEntero("Telefono del huesped que reserva: ");
        Huesped huesped = hotel.buscarHuespedPorTelefono(telefono);
        if (huesped == null) {
            System.out.println("No existe un huesped registrado con ese telefono.");
            return;
        }

        String codigoReserva = leerTexto("Codigo de la reserva: ");
        LocalDate fechaRealizacion = leerFecha("Fecha de realizacion (AAAA-MM-DD): ");
        LocalDate fechaEntrada = leerFecha("Fecha de entrada (AAAA-MM-DD): ");
        LocalDate fechaSalida = leerFecha("Fecha de salida (AAAA-MM-DD): ");
        int cantidadNoches = leerEntero("Cantidad de noches: ");
        String metodoPago = seleccionarMetodoPago();

        Reserva reserva = new Reserva(codigoReserva, fechaRealizacion, fechaEntrada,
                fechaSalida, metodoPago, cantidadNoches, huesped);

        System.out.println("Reserva creada con exito en estado Pendiente.");

        boolean agregarMas = true;
        while (agregarMas) {
            int numHabitacion = leerEntero("Numero de habitacion a incluir (0 para terminar): ");
            if (numHabitacion == 0) {
                agregarMas = false;
                continue;
            }
            Habitacion habitacion = buscarHabitacionPorNumero(numHabitacion);
            if (habitacion == null) {
                System.out.println("No existe una habitacion con ese numero.");
            } else if (!reserva.agregarHabitacion(habitacion)) {
                System.out.println("La habitacion no esta disponible o ya fue agregada.");
            } else {
                System.out.println("Habitacion agregada a la reserva.");
            }
        }

        reserva.calcularValorTotal();
        System.out.println("Valor total preliminar: $" + reserva.getValorTotal());
    }

    private static void agregarHabitacionAReserva() {
        System.out.println(" Agregar habitacion a una reserva ");
        String codigo = leerTexto("Codigo de la reserva: ");
        Reserva reserva = buscarReservaPorCodigo(codigo);
        if (reserva == null) {
            System.out.println("No existe una reserva con ese codigo.");
            return;
        }

        int numHabitacion = leerEntero("Numero de habitacion a incluir: ");
        Habitacion habitacion = buscarHabitacionPorNumero(numHabitacion);
        if (habitacion == null) {
            System.out.println("No existe una habitacion con ese numero.");
            return;
        }

        if (reserva.agregarHabitacion(habitacion)) {
            reserva.calcularValorTotal();
            System.out.println("Habitacion agregada. Nuevo valor total: $" + reserva.getValorTotal());
        } else {
            System.out.println("La habitacion no esta disponible o ya fue agregada a esta reserva.");
        }
    }

    private static void agregarServicioAReserva() {
        System.out.println("Agregar servicio adicional a una reserva");
        String codigoReserva = leerTexto("Codigo de la reserva: ");
        Reserva reserva = buscarReservaPorCodigo(codigoReserva);
        if (reserva == null) {
            System.out.println("No existe una reserva con ese codigo.");
            return;
        }

        int codigoServicio = leerEntero("Codigo del servicio adicional: ");
        ServicioAdicional servicio = buscarServicioPorCodigo(codigoServicio);
        if (servicio == null) {
            System.out.println("No existe un servicio con ese codigo.");
            return;
        }

        reserva.agregarServicioAdicional(servicio);
        reserva.calcularValorTotal();
        System.out.println("Servicio agregado Nuevo valor total: $" + reserva.getValorTotal());
    }

    private static void confirmarReserva() {
        System.out.println(" Confirmar reserva ");
        String codigo = leerTexto("Codigo de la reserva: ");
        Reserva reserva = buscarReservaPorCodigo(codigo);
        if (reserva == null) {
            System.out.println("No existe una reserva con ese codigo.");
            return;
        }

        if (reserva.confirmarReserva()) {
            System.out.println("Reserva confirmada. Las habitaciones incluidas quedaron marcadas como Reservada.");
        } else {
            System.out.println("La reserva no se pudo confirmar (verifique que este en estado Pendiente).");
        }
    }

    private static void cambiarEstadoReserva() {
        System.out.println(" Cambiar estado de una reserva ");
        String codigo = leerTexto("Codigo de la reserva: ");
        Reserva reserva = buscarReservaPorCodigo(codigo);
        if (reserva == null) {
            System.out.println("No existe una reserva con ese codigo.");
            return;
        }

        String nuevoEstado = seleccionarEstadoReserva();
        reserva.cambiarEstado(nuevoEstado);
        System.out.println("Estado actualizado a: " + nuevoEstado);
    }

    private static void buscarHuespedPorTelefono() {
        System.out.println(" Buscar huesped por telefono ");
        int telefono = leerEntero("Telefono a buscar: ");
        Huesped huesped = hotel.buscarHuespedPorTelefono(telefono);
        if (huesped == null) {
            System.out.println("No se encontro ningun huesped con ese telefono.");
        } else {
            System.out.println("Huesped encontrado: " + huesped.getNombre()
                    + " Documento: " + huesped.getID()
                    + " Correo: " + huesped.getCorreo()
                    + " Pais: " + huesped.getPaisPorcedencia());
        }
    }

    private static void verificarNumeroPerfecto() {
        System.out.println("Verificar num perfecto");
        int telefono = leerEntero("Telefono a verificar: ");
        if (hotel.telefonoHuespedEsPerfecto(telefono)) {
            System.out.println("El telefono " + telefono + " es un numero perfecto.");
        } else {
            System.out.println("El telefono " + telefono + " NO es un numero perfecto (o el huesped no existe).");
        }
    }

    private static void calcularIngresosPorFecha() {
        System.out.println("ingredsos por fecha ");
        LocalDate fecha = leerFecha("Fecha de realizacion a consultar (AAAA-MM-DD): ");
        double ingresos = hotel.calcularIngresosPorFecha(fecha);
        System.out.println("Ingresos totales el " + fecha + ": $" + ingresos);
    }

    private static void listarHabitacionesDisponibles() {
        System.out.println("--- Habitaciones disponibles ---");
        boolean hayDisponibles = false;
        for (Habitacion habitacion : hotel.getListHotelHabitaciones()) {
            if (habitacion.estaDisponible()) {
                hayDisponibles = true;
                System.out.println("Numero " + habitacion.getNumero() + " - Piso " + habitacion.getPiso()
                        + " - " + habitacion.getTipo() + " - $" + habitacion.getPrecioPorNoche() + " por noche");
            }
        }
        if (!hayDisponibles) {
            System.out.println("No hay habitaciones disponibles en este momento.");
        }
    }

    private static void imprimirDetalleReserva() {
        System.out.println("Imprimir recibo de reserva ");
        String codigo = leerTexto("Codigo de la reserva: ");
        Reserva reserva = buscarReservaPorCodigo(codigo);
        if (reserva == null) {
            System.out.println("No existe una reserva con ese codigo.");
            return;
        }
        reserva.imprimirDetalleReserva();
    }



    private static String seleccionarTipoHabitacion() {
        System.out.println("Tipo de habitacion:");
        System.out.println("1. Individual");
        System.out.println("2. Doble");
        System.out.println("3. Suite");
        int opcion = leerEntero("Seleccione una opcion: ");
        return switch (opcion) {
            case 1 -> "Individual";
            case 2 -> "Doble";
            case 3 -> "Suite";
            default -> "Individual";
        };
    }

    private static String seleccionarMetodoPago() {
        System.out.println("Metodo de pago:");
        System.out.println("1. Tarjeta de credito");
        System.out.println("2. Transferencia bancaria");
        System.out.println("3. Efectivo");
        int opcion = leerEntero("Seleccione una opcion: ");
        return switch (opcion) {
            case 1 -> "Tarjeta de credito";
            case 2 -> "Transferencia bancaria";
            case 3 -> "Efectivo";
            default -> "Efectivo";
        };
    }

    private static String seleccionarEstadoReserva() {
        System.out.println("Nuevo estado:");
        System.out.println("1. Pendiente");
        System.out.println("2. Confirmada");
        System.out.println("3. En curso");
        System.out.println("4. Finalizada");
        System.out.println("5. Cancelada");
        int opcion = leerEntero("Seleccione una opcion: ");
        return switch (opcion) {
            case 1 -> "Pendiente";
            case 2 -> "Confirmada";
            case 3 -> "En curso";
            case 4 -> "Finalizada";
            case 5 -> "Cancelada";
            default -> "Pendiente";
        };
    }



    private static Habitacion buscarHabitacionPorNumero(int numero) {
        for (Habitacion h : hotel.getListHotelHabitaciones()) {
            if (h.getNumero() == numero) {
                return h;
            }
        }
        return null;
    }

    private static ServicioAdicional buscarServicioPorCodigo(int codigo) {
        for (ServicioAdicional s : hotel.getListHotelServiciosAdicionales()) {
            if (s.getCodigo() == codigo) {
                return s;
            }
        }
        return null;
    }

    private static Reserva buscarReservaPorCodigo(String codigo) {
        for (Huesped huesped : hotel.getListHotelHuespedes()) {
            for (Reserva reserva : huesped.getListReservasHuesped()) {
                if (reserva.getCodigoReserva().equals(codigo)) {
                    return reserva;
                }
            }
        }
        return null;
    }



    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = sc.nextLine();
            try {
                return Integer.parseInt(entrada.trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un numero valido.");
            }
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = sc.nextLine();
            try {
                return Double.parseDouble(entrada.trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor ingrese un numero valido.");
            }
        }
    }

    private static LocalDate leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = sc.nextLine();
            try {
                return LocalDate.parse(entrada.trim());
            } catch (DateTimeParseException e) {
                System.out.println("Por favor ingrese una fecha valida en formato AAAA-MM-DD.");
            }
        }
    }
}