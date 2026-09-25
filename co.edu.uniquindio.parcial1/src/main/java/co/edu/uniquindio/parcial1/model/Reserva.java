package co.edu.uniquindio.parcial1.model;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
public class Reserva {

    private String codigoReserva;
    private LocalDate fechaRealizacion;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private String estado;
    private String metodoPago;
    private int cantidadNoches;
    private double descuento;
    private double costoServiciosAdicionales;
    private double valorTotal;

    private Huesped huesped;
    private List<Habitacion> habitaciones;
    private List<ServicioAdicional> serviciosUtilizados;

    public Reserva(String codigoReserva, LocalDate fechaRealizacion, LocalDate fechaEntrada,
                   LocalDate fechaSalida, String metodoPago, int cantidadNoches,
                   Huesped huesped) {


        this.codigoReserva = codigoReserva;
        this.fechaRealizacion = fechaRealizacion;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.metodoPago = metodoPago;
        this.cantidadNoches = cantidadNoches;
        this.huesped = huesped;
        this.huesped.agregarReserva(this); // registra la reserva en la lista del huésped

        this.estado = "Pendiente";
        this.descuento = 0;
        this.costoServiciosAdicionales = 0;
        this.valorTotal = 0;
        this.habitaciones = new ArrayList<>();
        this.serviciosUtilizados = new ArrayList<>();
    }

    public String getCodigoReserva() {
        return codigoReserva;
    }
    public void setCodigoReserva(String codigoReserva) {
        this.codigoReserva = codigoReserva;
    }
    public LocalDate getFechaRealizacion() {
        return fechaRealizacion;
    }
    public void setFechaRealizacion(LocalDate fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }
    public LocalDate getFechaEntrada() {
        return fechaEntrada;
    }
    public void setFechaEntrada(LocalDate fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }
    public LocalDate getFechaSalida() {
        return fechaSalida;
    }
    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getMetodoPago() {
        return metodoPago;
    }
    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
    public int getCantidadNoches() {
        return cantidadNoches;
    }
    public void setCantidadNoches(int cantidadNoches) {
        this.cantidadNoches = cantidadNoches;
    }
    public double getDescuento() {
        return descuento;
    }
    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
    public double getCostoServiciosAdicionales() {
        return costoServiciosAdicionales;
    }
    public void setCostoServiciosAdicionales(double costoServiciosAdicionales) {
        this.costoServiciosAdicionales = costoServiciosAdicionales;
    }
    public double getValorTotal() {
        return valorTotal;
    }
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
    public Huesped getHuesped() {
        return huesped;
    }
    public void setHuesped(Huesped huesped) {
        this.huesped = huesped;
    }
    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }
    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public Habitacion buscarHabitacion(int numeroHabitacion) {
        for (Habitacion habitacion : habitaciones) {
            if (habitacion.getNumero() == numeroHabitacion) {
                return habitacion;
            }
        }
        return null;
    }

    public boolean agregarHabitacion(Habitacion nuevaHabitacion) {
        if (!nuevaHabitacion.validarDisponibilidad(fechaEntrada, fechaSalida)) {
            return false;
        }

        if (buscarHabitacion(nuevaHabitacion.getNumero()) != null) {
            return false;
        }

        habitaciones.add(nuevaHabitacion);
        return true;
    }

    public boolean eliminarHabitacion(int numeroHabitacion) {
        Habitacion habitacion = buscarHabitacion(numeroHabitacion);

        if (habitacion != null) {
            if (estado.equals("Confirmada") || estado.equals("En curso")) {
                habitacion.liberar(fechaEntrada, fechaSalida);
            }
            habitaciones.remove(habitacion);
            return true;
        }

        return false;
    }

    public boolean confirmarReserva() {
        if (!estado.equals("Pendiente")) {
            return false;
        }

        for (Habitacion habitacion : habitaciones) {
            if (!habitacion.reservar(fechaEntrada, fechaSalida)) {
                return false; // alguna habitacion ya no esta disponible para esas fechas
            }
        }

        estado = "Confirmada";
        calcularValorTotal();
        return true;
    }

    public void cambiarEstado(String nuevoEstado) {
        String estadoAnterior = this.estado;
        this.estado = nuevoEstado;

        boolean sePierdeLaOcupacion = (estadoAnterior.equals("Confirmada") || estadoAnterior.equals("En curso"))
                && (nuevoEstado.equals("Finalizada") || nuevoEstado.equals("Cancelada"));

        if (sePierdeLaOcupacion) {
            for (Habitacion habitacion : habitaciones) {
                habitacion.liberar(fechaEntrada, fechaSalida);
            }
        }
    }

    public void agregarServicioAdicional(ServicioAdicional servicio) {
        serviciosUtilizados.add(servicio);
        costoServiciosAdicionales += servicio.getPrecio();
    }

    public List<ServicioAdicional> getServiciosUtilizados() {
        return serviciosUtilizados;
    }

    public double calcularValorTotal() {
        double totalPrecioHabitaciones = 0;

        for (Habitacion habitacion : habitaciones) {
            totalPrecioHabitaciones += habitacion.getPrecioPorNoche();
        }

        // Descuento automatico para huespedes frecuentes: 3 reservas o mas
        if (huesped.getListReservasHuesped().size() >= 3) {
            descuento = 10;
        }

        double subtotal = (totalPrecioHabitaciones * cantidadNoches) + costoServiciosAdicionales;
        valorTotal = subtotal - (subtotal * descuento / 100);

        return valorTotal;
    }

    // Imprime el resumen del pedido/estadía a partir de los datos de la propia reserva
    public void imprimirDetalleReserva() {
        System.out.println(" Detalle de la reserva " + codigoReserva );
        System.out.println("Huesped: " + huesped.getNombre());
        System.out.println("Fecha realizacion: " + fechaRealizacion);
        System.out.println("Fecha entrada: " + fechaEntrada);
        System.out.println("Fecha salida: " + fechaSalida);
        System.out.println("Estado: " + estado);
        System.out.println("Metodo de pago: " + metodoPago);
        System.out.println("Cantidad de noches: " + cantidadNoches);
        System.out.println("Habitaciones incluidas:");
        for (Habitacion habitacion : habitaciones) {
            System.out.println("  - Numero " + habitacion.getNumero() + " (" + habitacion.getTipo()
                    + ") - $" + habitacion.getPrecioPorNoche() + " por noche");
        }
        System.out.println("Servicios adicionales utilizados:");
        if (serviciosUtilizados.isEmpty()) {
            System.out.println("  (ninguno)");
        } else {
            for (ServicioAdicional servicio : serviciosUtilizados) {
                System.out.println("  - " + servicio.getNombre() + " - $" + servicio.getPrecio());
            }
        }
        System.out.println("Costo servicios adicionales: $" + costoServiciosAdicionales);
        System.out.println("Descuento aplicado: " + descuento + "%");
        System.out.println("VALOR TOTAL: $" + valorTotal);

    }
}