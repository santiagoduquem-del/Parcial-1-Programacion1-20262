

import javax.swing.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import model.Habitacion;
import model.Hotel;
import model.Huesped;
import model.Reserva;
import model.ServicioAdicional;

public class Main {

    static Hotel hotel;

    private static String pedirFecha(String mensaje) {
        JSpinner spinnerFecha = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerFecha, "yyyy-MM-dd");
        spinnerFecha.setEditor(editor);
        spinnerFecha.setValue(new Date());

        int opcion = JOptionPane.showConfirmDialog(null, spinnerFecha, mensaje,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (opcion != JOptionPane.OK_OPTION) {
            return null;
        }

        Date fechaSeleccionada = (Date) spinnerFecha.getValue();
        LocalDate fechaLocal = fechaSeleccionada.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return fechaLocal.toString();
    }

    public static void main(String[] args) {
        hotel = new Hotel("StayPlus", "900123456-7", "Armenia, Quindio", "6067441000", "www.stayplus.com");

        int opcion = 0;
        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog("Sistema StayPlus:" +
                    "\nSeleccione una opcion:" +
                    "\n1. Huespedes" +
                    "\n2. Habitaciones" +
                    "\n3. Servicios adicionales" +
                    "\n4. Reservas" +
                    "\n5. Consultar numero perfecto por telefono de huesped" +
                    "\n6. Consultar ingresos por fecha" +
                    "\n0. Salir del sistema"));

            switch (opcion) {

                case 1:
                    menuHuespedes();
                    break;

                case 2:
                    menuHabitaciones();
                    break;

                case 3:
                    menuServicios();
                    break;

                case 4:
                    menuReservas();
                    break;

                case 5:
                    consultarNumeroPerfecto();
                    break;

                case 6:
                    consultarIngresosPorFecha();
                    break;

                case 0:
                    JOptionPane.showMessageDialog(null, "El programa finalizo");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "La opcion no es valida");
                    break;
            }

        } while (opcion != 0);
    }

    private static void menuHuespedes() {
        int opcion = 0;
        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog("Huespedes:" +
                    "\n1. Registrar huesped" +
                    "\n2. Mostrar huespedes" +
                    "\n3. Actualizar huesped" +
                    "\n4. Eliminar huesped" +
                    "\n0. Volver"));

            switch (opcion) {

                case 1:
                    registrarHuesped();
                    break;

                case 2:
                    mostrarHuespedes();
                    break;

                case 3:
                    actualizarHuesped();
                    break;

                case 4:
                    eliminarHuesped();
                    break;

                case 0:
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "La opcion no es valida");
                    break;
            }

        } while (opcion != 0);
    }

    private static void registrarHuesped() {
        String documento = JOptionPane.showInputDialog("Ingrese el documento de identidad del huesped:");
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre completo:");
        String telefono = JOptionPane.showInputDialog("Ingrese el telefono del huesped:");
        String correo = JOptionPane.showInputDialog("Ingrese el correo electronico:");
        String pais = JOptionPane.showInputDialog("Ingrese el pais de procedencia:");

        Huesped nuevoHuesped = new Huesped(documento, nombre, telefono, correo, pais);

        boolean resultado = hotel.registrarHuesped(nuevoHuesped);

        if (resultado) {
            JOptionPane.showMessageDialog(null, "Registro exitoso");
        } else {
            JOptionPane.showMessageDialog(null, "No se hizo el registro");
        }
    }

    private static void mostrarHuespedes() {
        JOptionPane.showMessageDialog(null, hotel.mostrarHuespedes());
    }

    private static void actualizarHuesped() {
        String documento = JOptionPane.showInputDialog("Ingrese el documento del huesped a actualizar:");
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre completo:");
        String telefono = JOptionPane.showInputDialog("Ingrese el telefono del huesped:");
        String correo = JOptionPane.showInputDialog("Ingrese el correo electronico:");
        String pais = JOptionPane.showInputDialog("Ingrese el pais de procedencia:");

        boolean resultado = hotel.actualizarHuesped(documento, nombre, telefono, correo, pais);

        if (resultado) {
            JOptionPane.showMessageDialog(null, "Se actualizo el huesped");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontro el huesped");
        }
    }

    private static void eliminarHuesped() {
        String documento = JOptionPane.showInputDialog("Ingrese el documento del huesped a eliminar:");

        boolean resultado = hotel.eliminarHuesped(documento);

        if (resultado) {
            JOptionPane.showMessageDialog(null, "Se elimino el huesped");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontro el huesped");
        }
    }

    private static void menuHabitaciones() {
        int opcion = 0;
        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog("Habitaciones:" +
                    "\n1. Registrar habitacion" +
                    "\n2. Mostrar habitaciones" +
                    "\n3. Actualizar habitacion" +
                    "\n4. Eliminar habitacion" +
                    "\n0. Volver"));

            switch (opcion) {

                case 1:
                    registrarHabitacion();
                    break;

                case 2:
                    mostrarHabitaciones();
                    break;

                case 3:
                    actualizarHabitacion();
                    break;

                case 4:
                    eliminarHabitacion();
                    break;

                case 0:
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "La opcion no es valida");
                    break;
            }

        } while (opcion != 0);
    }

    private static void registrarHabitacion() {
        String numero = JOptionPane.showInputDialog("Ingrese el numero de la habitacion:");
        String piso = JOptionPane.showInputDialog("Ingrese el piso:");
        String tipo = JOptionPane.showInputDialog("Ingrese el tipo (Individual, Doble, Suite):");
        int capacidadMaxima = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la capacidad maxima:"));
        double precioPorNoche = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio por noche:"));

        Habitacion nuevaHabitacion = new Habitacion(numero, piso, tipo, capacidadMaxima, precioPorNoche);

        boolean resultado = hotel.registrarHabitacion(nuevaHabitacion);

        if (resultado) {
            JOptionPane.showMessageDialog(null, "Registro exitoso");
        } else {
            JOptionPane.showMessageDialog(null, "No se hizo el registro");
        }
    }

    private static void mostrarHabitaciones() {
        JOptionPane.showMessageDialog(null, hotel.mostrarHabitaciones());
    }

    private static void actualizarHabitacion() {
        String numero = JOptionPane.showInputDialog("Ingrese el numero de la habitacion a actualizar:");
        String piso = JOptionPane.showInputDialog("Ingrese el piso:");
        String tipo = JOptionPane.showInputDialog("Ingrese el tipo (Individual, Doble, Suite):");
        int capacidadMaxima = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la capacidad maxima:"));
        double precioPorNoche = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio por noche:"));

        boolean resultado = hotel.actualizarHabitacion(numero, piso, tipo, capacidadMaxima, precioPorNoche);

        if (resultado) {
            JOptionPane.showMessageDialog(null, "Se actualizo la habitacion");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontro la habitacion");
        }
    }

    private static void eliminarHabitacion() {
        String numero = JOptionPane.showInputDialog("Ingrese el numero de la habitacion a eliminar:");

        boolean resultado = hotel.eliminarHabitacion(numero);

        if (resultado) {
            JOptionPane.showMessageDialog(null, "Se elimino la habitacion");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontro la habitacion");
        }
    }

    private static void menuServicios() {
        int opcion = 0;
        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog("Servicios adicionales:" +
                    "\n1. Registrar servicio" +
                    "\n2. Mostrar servicios" +
                    "\n3. Actualizar servicio" +
                    "\n4. Eliminar servicio" +
                    "\n0. Volver"));

            switch (opcion) {

                case 1:
                    registrarServicio();
                    break;

                case 2:
                    mostrarServicios();
                    break;

                case 3:
                    actualizarServicio();
                    break;

                case 4:
                    eliminarServicio();
                    break;

                case 0:
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "La opcion no es valida");
                    break;
            }

        } while (opcion != 0);
    }

    private static void registrarServicio() {
        String codigo = JOptionPane.showInputDialog("Ingrese el codigo del servicio:");
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del servicio:");
        String descripcion = JOptionPane.showInputDialog("Ingrese la descripcion del servicio:");
        double precio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio del servicio:"));
        int opcionDisponible = Integer.parseInt(JOptionPane.showInputDialog("El servicio esta disponible:" +
                "\n1. Si" +
                "\n2. No"));
        boolean disponible = opcionDisponible == 1;

        ServicioAdicional nuevoServicio = new ServicioAdicional(codigo, nombre, descripcion, precio, disponible);

        boolean resultado = hotel.registrarServicio(nuevoServicio);

        if (resultado) {
            JOptionPane.showMessageDialog(null, "Registro exitoso");
        } else {
            JOptionPane.showMessageDialog(null, "No se hizo el registro");
        }
    }

    private static void mostrarServicios() {
        JOptionPane.showMessageDialog(null, hotel.mostrarServicios());
    }

    private static void actualizarServicio() {
        String codigo = JOptionPane.showInputDialog("Ingrese el codigo del servicio a actualizar:");
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del servicio:");
        String descripcion = JOptionPane.showInputDialog("Ingrese la descripcion del servicio:");
        double precio = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio del servicio:"));
        int opcionDisponible = Integer.parseInt(JOptionPane.showInputDialog("El servicio esta disponible:" +
                "\n1. Si" +
                "\n2. No"));
        boolean disponible = opcionDisponible == 1;

        boolean resultado = hotel.actualizarServicio(codigo, nombre, descripcion, precio, disponible);

        if (resultado) {
            JOptionPane.showMessageDialog(null, "Se actualizo el servicio");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontro el servicio");
        }
    }

    private static void eliminarServicio() {
        String codigo = JOptionPane.showInputDialog("Ingrese el codigo del servicio a eliminar:");

        boolean resultado = hotel.eliminarServicio(codigo);

        if (resultado) {
            JOptionPane.showMessageDialog(null, "Se elimino el servicio");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontro el servicio");
        }
    }

    private static void menuReservas() {
        int opcion = 0;
        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog("Reservas:" +
                    "\n1. Registrar reserva" +
                    "\n2. Mostrar reservas" +
                    "\n3. Agregar habitacion a reserva" +
                    "\n4. Agregar servicio a reserva" +
                    "\n5. Confirmar reserva" +
                    "\n6. Cambiar estado de reserva" +
                    "\n7. Eliminar reserva" +
                    "\n0. Volver"));

            switch (opcion) {

                case 1:
                    registrarReserva();
                    break;

                case 2:
                    mostrarReservas();
                    break;

                case 3:
                    agregarHabitacionAReserva();
                    break;

                case 4:
                    agregarServicioAReserva();
                    break;

                case 5:
                    confirmarReserva();
                    break;

                case 6:
                    cambiarEstadoReserva();
                    break;

                case 7:
                    eliminarReserva();
                    break;

                case 0:
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "La opcion no es valida");
                    break;
            }

        } while (opcion != 0);
    }

    private static void registrarReserva() {
        String documentoHuesped = JOptionPane.showInputDialog("Ingrese el documento del huesped que hace la reserva:");
        int posicionHuesped = hotel.buscarHuesped(documentoHuesped);

        if (posicionHuesped == -1) {
            JOptionPane.showMessageDialog(null, "El huesped no existe");
            return;
        }

        Huesped huesped = hotel.getListaHuespedes().get(posicionHuesped);

        String codigoReserva = JOptionPane.showInputDialog("Ingrese el codigo de la reserva:");
        String fechaRealizacion = pedirFecha("Seleccione la fecha de realizacion:");
        if (fechaRealizacion == null) {
            JOptionPane.showMessageDialog(null, "Registro cancelado");
            return;
        }

        String fechaEntrada = pedirFecha("Seleccione la fecha de entrada:");
        if (fechaEntrada == null) {
            JOptionPane.showMessageDialog(null, "Registro cancelado");
            return;
        }

        String fechaSalida = pedirFecha("Seleccione la fecha de salida:");
        if (fechaSalida == null) {
            JOptionPane.showMessageDialog(null, "Registro cancelado");
            return;
        }

        String metodoPago = JOptionPane.showInputDialog("Ingrese el metodo de pago (Tarjeta de credito, Transferencia bancaria, Efectivo):");
        int cantidadNoches = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad de noches:"));

        Reserva nuevaReserva = new Reserva(codigoReserva, fechaRealizacion, fechaEntrada, fechaSalida,
                metodoPago, cantidadNoches, huesped);

        boolean resultado = hotel.registrarReserva(nuevaReserva);

        if (resultado) {
            JOptionPane.showMessageDialog(null, "Registro exitoso");
        } else {
            JOptionPane.showMessageDialog(null, "No se hizo el registro");
        }
    }

    private static void mostrarReservas() {
        JOptionPane.showMessageDialog(null, hotel.mostrarReservas());
    }

    private static void agregarHabitacionAReserva() {
        String codigoReserva = JOptionPane.showInputDialog("Ingrese el codigo de la reserva:");
        int posicionReserva = hotel.buscarReserva(codigoReserva);

        String numeroHabitacion = JOptionPane.showInputDialog("Ingrese el numero de la habitacion:");
        int posicionHabitacion = hotel.buscarHabitacion(numeroHabitacion);

        if (posicionReserva == -1 || posicionHabitacion == -1) {
            JOptionPane.showMessageDialog(null, "La reserva o la habitacion no existen");
            return;
        }

        Reserva reserva = hotel.getListaReservas().get(posicionReserva);
        Habitacion habitacion = hotel.getListaHabitaciones().get(posicionHabitacion);

        boolean resultado = reserva.agregarHabitacion(habitacion);

        if (resultado) {
            JOptionPane.showMessageDialog(null, "Habitacion agregada a la reserva");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo agregar la habitacion");
        }
    }

    private static void agregarServicioAReserva() {
        String codigoReserva = JOptionPane.showInputDialog("Ingrese el codigo de la reserva:");
        String codigoServicio = JOptionPane.showInputDialog("Ingrese el codigo del servicio:");

        boolean resultado = hotel.agregarServicioAReserva(codigoReserva, codigoServicio);

        if (resultado) {
            JOptionPane.showMessageDialog(null, "Servicio agregado a la reserva");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo agregar el servicio");
        }
    }

    private static void confirmarReserva() {
        String codigoReserva = JOptionPane.showInputDialog("Ingrese el codigo de la reserva a confirmar:");
        int posicion = hotel.buscarReserva(codigoReserva);

        if (posicion == -1) {
            JOptionPane.showMessageDialog(null, "La reserva no existe");
            return;
        }

        Reserva reserva = hotel.getListaReservas().get(posicion);
        boolean resultado = reserva.confirmarReserva();

        if (resultado) {
            JOptionPane.showMessageDialog(null, "Reserva confirmada. Valor total: " + reserva.getValorTotal());
        } else {
            JOptionPane.showMessageDialog(null, "La reserva no se pudo confirmar");
        }
    }

    private static void cambiarEstadoReserva() {
        String codigoReserva = JOptionPane.showInputDialog("Ingrese el codigo de la reserva:");
        int posicion = hotel.buscarReserva(codigoReserva);

        if (posicion == -1) {
            JOptionPane.showMessageDialog(null, "La reserva no existe");
            return;
        }

        String nuevoEstado = JOptionPane.showInputDialog("Ingrese el nuevo estado" +
                "\n(Pendiente, Confirmada, En curso, Finalizada, Cancelada):");

        hotel.getListaReservas().get(posicion).cambiarEstado(nuevoEstado);

        JOptionPane.showMessageDialog(null, "Estado actualizado");
    }

    private static void eliminarReserva() {
        String codigoReserva = JOptionPane.showInputDialog("Ingrese el codigo de la reserva a eliminar:");

        boolean resultado = hotel.eliminarReserva(codigoReserva);

        if (resultado) {
            JOptionPane.showMessageDialog(null, "Se elimino la reserva");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontro la reserva");
        }
    }

    private static void consultarNumeroPerfecto() {
        String telefono = JOptionPane.showInputDialog("Ingrese el telefono del huesped a consultar:");

        Huesped huesped = hotel.buscarHuespedPorTelefono(telefono);

        if (huesped == null) {
            JOptionPane.showMessageDialog(null, "No se encontro un huesped con ese telefono");
            return;
        }

        boolean esPerfecto = hotel.esNumeroPerfecto(telefono);

        String mensaje = "Huesped encontrado: " + huesped.getNombre() +
                "\nEl telefono " + telefono + (esPerfecto ? " SI es un numero perfecto" : " NO es un numero perfecto");

        JOptionPane.showMessageDialog(null, mensaje);
    }

    private static void consultarIngresosPorFecha() {
        String fecha = pedirFecha("Seleccione la fecha a consultar:");
        if (fecha == null) {
            JOptionPane.showMessageDialog(null, "Consulta cancelada");
            return;
        }

        double ingresos = hotel.calcularIngresosPorFecha(fecha);

        JOptionPane.showMessageDialog(null, "Los ingresos por reservas realizadas en " + fecha + " son: " + ingresos);
    }
}
