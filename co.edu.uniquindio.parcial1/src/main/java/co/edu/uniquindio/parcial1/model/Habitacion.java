package co.edu.uniquindio.parcial1.model;

public class Habitacion {

    private int numero;
    private int piso;
    private String tipo; // Individual, Doble, Suite
    private int capacidadMaxima;
    private double precioPorNoche;
    private String estado; // Disponible, Reservada, Ocupada, Mantenimiento

    //Constructor
    public Habitacion(int numero, int piso, String tipo,
                      int capacidadMaxima, double precioPorNoche) {
        this.numero = numero;
        this.piso = piso;
        this.tipo = tipo;
        this.capacidadMaxima = capacidadMaxima;
        this.precioPorNoche = precioPorNoche;
        this.estado = "Disponible";
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

    //Valida si la habitacion se encuentra disponible para ser reservada
    public boolean estaDisponible() {
        return estado.equals("Disponible");
    }

    //Se ejecuta cuando se confirma una reserva en la que la habitacion quedo incluida
    public boolean reservar() {
        if (estaDisponible()) {
            estado = "Reservada";
            return true;
        }
        return false;
    }

    //Se ejecuta cuando la reserva en la que participaba finaliza o se cancela
    public void liberar() {
        estado = "Disponible";
    }
}
