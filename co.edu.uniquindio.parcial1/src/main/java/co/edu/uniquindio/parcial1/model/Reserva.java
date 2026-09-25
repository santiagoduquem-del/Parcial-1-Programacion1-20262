package model;

import java.util.ArrayList;
import java.util.List;

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
    private List<Habitacion> listaHabitaciones;

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
        this.listaHabitaciones = new ArrayList<>();
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

    public List<Habitacion> getListaHabitaciones() {
        return listaHabitaciones;
    }

    public void setListaHabitaciones(List<Habitacion> listaHabitaciones) {
        this.listaHabitaciones = listaHabitaciones;
    }

    public int getCantidadHabitacionesAsignadas() {
        return listaHabitaciones.size();
    }

    public int buscarHabitacion(String numeroHabitacion) {
        for (int i = 0; i < listaHabitaciones.size(); i++) {
            if (listaHabitaciones.get(i).getNumero().equals(numeroHabitacion)) {
                return i;
            }
        }
        return -1;
    }

    public boolean agregarHabitacion(Habitacion nuevaHabitacion) {

        if (!nuevaHabitacion.estaDisponible()) {
            return false;
        }

        if (buscarHabitacion(nuevaHabitacion.getNumero()) != -1) {
            return false;
        }

        listaHabitaciones.add(nuevaHabitacion);
        calcularValorTotal();
        return true;
    }

    public boolean eliminarHabitacion(String numeroHabitacion) {
        int posicion = buscarHabitacion(numeroHabitacion);

        if (posicion != -1) {
            listaHabitaciones.get(posicion).liberar();
            listaHabitaciones.remove(posicion);
            calcularValorTotal();
            return true;
        }

        return false;
    }

    public boolean confirmarReserva() {
        if (!estado.equals("Pendiente")) {
            return false;
        }

        for (Habitacion habitacion : listaHabitaciones) {
            habitacion.reservar();
        }

        estado = "Confirmada";
        calcularValorTotal();
        return true;
    }

    public void cambiarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;

        if (nuevoEstado.equals("Finalizada") || nuevoEstado.equals("Cancelada")) {
            for (Habitacion habitacion : listaHabitaciones) {
                habitacion.liberar();
            }
        }
    }

    public void agregarServicioAdicional(double precioServicio) {
        costoServiciosAdicionales += precioServicio;
        calcularValorTotal();
    }

    public double calcularValorTotal() {
        double totalPrecioHabitaciones = 0;

        for (Habitacion habitacion : listaHabitaciones) {
            totalPrecioHabitaciones += habitacion.getPrecioPorNoche();
        }

        double subtotal = (totalPrecioHabitaciones * cantidadNoches) + costoServiciosAdicionales;
        valorTotal = subtotal - (subtotal * descuento / 100);

        return valorTotal;
    }
}
