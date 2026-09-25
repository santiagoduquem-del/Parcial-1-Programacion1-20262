package model;

public class Habitacion {
    private String numero;
    private String piso;
    private String tipo;
    private int capacidadMaxima;
    private double precioPorNoche;
    private String estado;
    private int cantidadReservasActuales;

    public Habitacion(String numero, String piso, String tipo, int capacidadMaxima, double precioPorNoche) {

        this.numero = numero;
        this.piso = piso;
        this.tipo = tipo;
        this.capacidadMaxima = capacidadMaxima;
        this.precioPorNoche = precioPorNoche;
        this.estado = "Disponible";
        this.cantidadReservasActuales = 0;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
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

    public int getCantidadReservasActuales() {
        return cantidadReservasActuales;
    }

    public void setCantidadReservasActuales(int cantidadReservasActuales) {
        this.cantidadReservasActuales = cantidadReservasActuales;
    }

    public boolean estaDisponible() {
        return cantidadReservasActuales < capacidadMaxima;
    }

    public boolean reservar() {
        if (estaDisponible()) {
            cantidadReservasActuales++;

            if (cantidadReservasActuales >= capacidadMaxima) {
                estado = "Ocupada";
            } else {
                estado = "Reservada";
            }
            return true;
        }
        return false;
    }

    public void liberar() {
        if (cantidadReservasActuales > 0) {
            cantidadReservasActuales--;
        }

        if (cantidadReservasActuales == 0) {
            estado = "Disponible";
        } else {
            estado = "Reservada";
        }
    }
}
