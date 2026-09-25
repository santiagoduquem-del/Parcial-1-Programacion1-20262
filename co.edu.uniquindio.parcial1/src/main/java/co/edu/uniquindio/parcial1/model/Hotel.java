package co.edu.uniquindio.parcial1.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Hotel - Representa el hotel StayPlus.
 * Es la clase raíz del sistema: administra por composición a Huesped,
 * Habitacion y ServicioAdicional (Reserva se alcanza de forma transitiva
 * a través de Huesped, por eso no tiene una lista propia de reservas).
 */
public class Hotel {

    // Atributos
    private String nombreComercial;
    private String nit;
    private String telefono;
    private String correo;
    private String direccion;
    private String paginaWeb;

    // Listas de composición (Hotel es el "dueño" de estos objetos)
    private List<Huesped> listHotelHuespedes;
    private List<Habitacion> listHotelHabitaciones;
    private List<ServicioAdicional> listHotelServiciosAdicionales;

    // Constructor
    public Hotel(String nombreComercial, String nit, String direccion, String telefono, String paginaWeb) {
        this.nombreComercial = nombreComercial;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.paginaWeb = paginaWeb;
        this.listHotelHuespedes = new ArrayList<>();
        this.listHotelHabitaciones = new ArrayList<>();
        this.listHotelServiciosAdicionales = new ArrayList<>();
    }

    // ---------------------------------------------------------------
    // Métodos de registro / administración
    // ---------------------------------------------------------------

    /** Registra un nuevo huésped en el hotel. */
    public void registrarHuesped(Huesped h) {
        listHotelHuespedes.add(h);
    }

    /** Agrega una nueva habitación al catálogo del hotel. */
    public void agregarHabitacion(Habitacion h) {
        listHotelHabitaciones.add(h);
    }

    /** Agrega un nuevo servicio adicional al catálogo del hotel. */
    public void agregarServicioAdicional(ServicioAdicional s) {
        listHotelServiciosAdicionales.add(s);
    }

    // ---------------------------------------------------------------
    // Búsqueda de huésped por teléfono
    // ---------------------------------------------------------------

    /**
     * Busca un huésped a partir de su número de teléfono.
     * @param telefono número de teléfono a buscar
     * @return el Huesped encontrado, o null si no existe ninguno con ese teléfono
     */
    public Huesped buscarHuespedPorTelefono(int telefono) {
        for (Huesped h : listHotelHuespedes) {
            if (h.getTelefono() == telefono) {
                return h;
            }
        }
        return null;
    }

    // ---------------------------------------------------------------
    // Número perfecto
    // ---------------------------------------------------------------

    /**
     * Determina si un número es perfecto, es decir, si es igual a la
     * suma de sus divisores propios (sin incluir el mismo número).
     * Ejemplo: 6 es perfecto porque 1 + 2 + 3 = 6.
     * @param numero número a evaluar
     * @return true si el número es perfecto, false en caso contrario
     */
    public boolean esNumeroPerfecto(int numero) {
        if (numero <= 0) {
            return false;
        }
        int sumaDivisores = 0;
        for (int i = 1; i <= numero / 2; i++) {
            if (numero % i == 0) {
                sumaDivisores += i;
            }
        }
        return sumaDivisores == numero;
    }

    /**
     * Verifica si el número de teléfono de un huésped es un número perfecto.
     * Combina la búsqueda por teléfono con la validación de número perfecto.
     * @param telefono teléfono del huésped a consultar
     * @return true si el teléfono del huésped corresponde a un número perfecto
     */
    public boolean telefonoHuespedEsPerfecto(int telefono) {
        Huesped huesped = buscarHuespedPorTelefono(telefono);
        if (huesped == null) {
            return false;
        }
        return esNumeroPerfecto(telefono);
    }



    /**
     * Calcula el total de ingresos correspondientes a las reservas cuya
     * fecha de realización coincide con la fecha consultada.
     * Recorre todos los huéspedes del hotel y, dentro de cada uno,
     * todas sus reservas (Reserva se alcanza transitivamente vía Huesped).
     * @param fecha fecha de realización a consultar
     * @return valor total acumulado de las reservas de esa fecha
     */
    public double calcularIngresosPorFecha(LocalDate fecha) {
        double totalIngresos = 0.0;
        for (Huesped huesped : listHotelHuespedes) {
            for (Reserva reserva : huesped.getListReservasHuesped()) {
                if (reserva.getFechaRealizacion().equals(fecha)) {
                    totalIngresos += reserva.getValorTotal();
                }
            }
        }
        return totalIngresos;
    }


    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public List<Huesped> getListHotelHuespedes() {
        return listHotelHuespedes;
    }

    public List<Habitacion> getListHotelHabitaciones() {
        return listHotelHabitaciones;
    }

    public List<ServicioAdicional> getListHotelServiciosAdicionales() {
        return listHotelServiciosAdicionales;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "nombreComercial='" + nombreComercial + '\'' +
                ", nit='" + nit + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", direccion='" + direccion + '\'' +
                ", paginaWeb='" + paginaWeb + '\'' +
                '}';
    }
}