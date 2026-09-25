package co.edu.uniquindio.parcial1.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Habitacion {

    private int numero;
    private int piso;
    private String tipo; // Individual, Doble, Suite
    private int capacidadMaxima;
    private double precioPorNoche;
    private String estado; // Disponible, Reservada, Ocupada, Mantenimiento

    // Fechas ya ocupadas por reservas confirmadas (para validar disponibilidad real)
    private List<LocalDate[]> rangosOcupados;

    //Constructor
    public Habitacion(int numero, int piso, String tipo,
                      int capacidadMaxima, double precioPorNoche) {
        this.numero = numero;
        this.piso = piso;
        this.tipo = tipo;
        this.capacidadMaxima = capacidadMaxima;
        this.precioPorNoche = precioPorNoche;
        this.estado = "Disponible";
        this.rangosOcupados = new ArrayList<>();
    }

    //Getters and Setters
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public double getPrecioPorNoche() {
        return precioPorNoche;
    }

    public void setPrecioPorNoche(double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    //Metodos propios del negocio

    //Valida el estado general de la habitacion (uso administrativo, ej. Mantenimiento)
    public boolean estaDisponible() {
        return estado.equals("Disponible") || estado.equals("Reservada");
    }

    /**
     * Valida si la habitacion esta disponible durante un rango de fechas especifico,
     * comparando contra las fechas ya ocupadas por otras reservas confirmadas.
     * Tal como pide el enunciado: "se debe validar que se encuentre disponible
     * durante las fechas solicitadas".
     */
    public boolean validarDisponibilidad(LocalDate fechaEntrada, LocalDate fechaSalida) {
        if (estado.equals("Mantenimiento")) {
            return false;
        }
        for (LocalDate[] rango : rangosOcupados) {
            boolean seCruzan = fechaEntrada.isBefore(rango[1]) && rango[0].isBefore(fechaSalida);
            if (seCruzan) {
                return false;
            }
        }
        return true;
    }

    //Se ejecuta cuando se confirma una reserva en la que la habitacion quedo incluida
    public boolean reservar(LocalDate fechaEntrada, LocalDate fechaSalida) {
        if (!validarDisponibilidad(fechaEntrada, fechaSalida)) {
            return false;
        }
        rangosOcupados.add(new LocalDate[]{fechaEntrada, fechaSalida});
        estado = "Reservada";
        return true;
    }

    //Se ejecuta cuando la reserva en la que participaba finaliza o se cancela
    public void liberar(LocalDate fechaEntrada, LocalDate fechaSalida) {
        rangosOcupados.removeIf(r -> r[0].equals(fechaEntrada) && r[1].equals(fechaSalida));
        if (rangosOcupados.isEmpty()) {
            estado = "Disponible";
        }
    }
}