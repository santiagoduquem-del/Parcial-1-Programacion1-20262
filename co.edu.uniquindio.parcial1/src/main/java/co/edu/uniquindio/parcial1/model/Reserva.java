package main.java.co.edu.uniquindio.parcial1.model;
import java.util.List;
import java.util.ArrayList;
public class Reserva {

    private String codigoReserva;
    private String fechaRealizacion;
    private String fechaEntrada;
    private String fechaSalida;
    private String estado;
    private String metodoPago;
    private int cantidadNoches;
    private double descuento;
    private double costoServiciosAdicionales;
    private double valorTotal;

    private Huesped huesped;
    private List<Habitacion> habitaciones;

    public Reserva(String codigoReserva, String fechaRealizacion, String fechaEntrada,
                   String fechaSalida, String metodoPago, int cantidadNoches,
                   Huesped huesped) {
        this.codigoReserva = codigoReserva;
        this.fechaRealizacion = fechaRealizacion;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.metodoPago = metodoPago;
        this.cantidadNoches = cantidadNoches;
        this.huesped = huesped;

        this.estado = "Pendiente";
        this.descuento = 0;
        this.costoServiciosAdicionales = 0;
        this.valorTotal = 0;
        this.habitaciones = new ArrayList<>();
    }

    public String getCodigoReserva() {
        return codigoReserva;
    }
    public void setCodigoReserva(String codigoReserva) {
        this.codigoReserva = codigoReserva;
    }
    public String getFechaRealizacion() {
        return fechaRealizacion;
    }
    public void setFechaRealizacion(String fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }
    public String getFechaEntrada() {
        return fechaEntrada;
    }
    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }
    public String getFechaSalida() {
        return fechaSalida;
    }
    public void setFechaSalida(String fechaSalida) {
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
        if (!nuevaHabitacion.estaDisponible()) {
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
            habitacion.liberar();
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
            habitacion.reservar();
        }

        estado = "Confirmada";
        calcularValorTotal();
        return true;
    }

    public void cambiarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;

        if (nuevoEstado.equals("Finalizada") || nuevoEstado.equals("Cancelada")) {
            for (Habitacion habitacion : habitaciones) {
                habitacion.liberar();
            }
        }
    }

    public void agregarServicioAdicional(double precioServicio) {
        costoServiciosAdicionales += precioServicio;
    }

    public double calcularValorTotal() {
        double totalPrecioHabitaciones = 0;

        for (Habitacion habitacion : habitaciones) {
            totalPrecioHabitaciones += habitacion.getPrecioPorNoche();
        }

        double subtotal = (totalPrecioHabitaciones * cantidadNoches) + costoServiciosAdicionales;
        valorTotal = subtotal - (subtotal * descuento / 100);

        return valorTotal;
    }
}

