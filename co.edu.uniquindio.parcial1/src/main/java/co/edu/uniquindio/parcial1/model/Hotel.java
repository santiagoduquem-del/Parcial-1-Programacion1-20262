package model;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private String nombreComercial;
    private String nit;
    private String direccion;
    private String telefono;
    private String paginaWeb;
    private List<Huesped> listaHuespedes;
    private List<Habitacion> listaHabitaciones;
    private List<Reserva> listaReservas;
    private List<ServicioAdicional> listaServicios;

    public Hotel(String nombreComercial, String nit, String direccion, String telefono, String paginaWeb) {
        this.nombreComercial = nombreComercial;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.paginaWeb = paginaWeb;
        this.listaHuespedes = new ArrayList<>();
        this.listaHabitaciones = new ArrayList<>();
        this.listaReservas = new ArrayList<>();
        this.listaServicios = new ArrayList<>();
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public List<Huesped> getListaHuespedes() {
        return listaHuespedes;
    }

    public void setListaHuespedes(List<Huesped> listaHuespedes) {
        this.listaHuespedes = listaHuespedes;
    }

    public List<Habitacion> getListaHabitaciones() {
        return listaHabitaciones;
    }

    public void setListaHabitaciones(List<Habitacion> listaHabitaciones) {
        this.listaHabitaciones = listaHabitaciones;
    }

    public List<Reserva> getListaReservas() {
        return listaReservas;
    }

    public void setListaReservas(List<Reserva> listaReservas) {
        this.listaReservas = listaReservas;
    }

    public List<ServicioAdicional> getListaServicios() {
        return listaServicios;
    }

    public void setListaServicios(List<ServicioAdicional> listaServicios) {
        this.listaServicios = listaServicios;
    }

    public int buscarHuesped(String documento) {
        for (int i = 0; i < listaHuespedes.size(); i++) {
            if (listaHuespedes.get(i).getDocumento().equals(documento)) {
                return i;
            }
        }
        return -1;
    }

    public boolean registrarHuesped(Huesped huesped) {
        if (buscarHuesped(huesped.getDocumento()) == -1) {
            listaHuespedes.add(huesped);
            return true;
        }
        return false;
    }

    public boolean actualizarHuesped(String documento, String nombre, String telefono, String correo, String pais) {
        int posicion = buscarHuesped(documento);
        if (posicion != -1) {
            Huesped huesped = listaHuespedes.get(posicion);
            huesped.setNombre(nombre);
            huesped.setTelefono(telefono);
            huesped.setCorreo(correo);
            huesped.setPais(pais);
            return true;
        }
        return false;
    }

    public boolean eliminarHuesped(String documento) {
        int posicion = buscarHuesped(documento);
        if (posicion != -1) {
            listaHuespedes.remove(posicion);
            return true;
        }
        return false;
    }

    public Huesped buscarHuespedPorTelefono(String telefono) {
        for (Huesped huesped : listaHuespedes) {
            if (huesped.getTelefono().equals(telefono)) {
                return huesped;
            }
        }
        return null;
    }

    public String mostrarHuespedes() {
        String mensaje = "";
        for (Huesped huesped : listaHuespedes) {
            mensaje += "\nDocumento: " + huesped.getDocumento() +
                    "\nNombre: " + huesped.getNombre() +
                    "\nTelefono: " + huesped.getTelefono() +
                    "\nCorreo: " + huesped.getCorreo() +
                    "\nPais: " + huesped.getPais() +
                    "\nReservas realizadas: " + huesped.getListaReservas().size() + "\n";
        }
        return mensaje;
    }

    public int buscarHabitacion(String numero) {
        for (int i = 0; i < listaHabitaciones.size(); i++) {
            if (listaHabitaciones.get(i).getNumero().equals(numero)) {
                return i;
            }
        }
        return -1;
    }

    public boolean registrarHabitacion(Habitacion habitacion) {
        if (buscarHabitacion(habitacion.getNumero()) == -1) {
            listaHabitaciones.add(habitacion);
            return true;
        }
        return false;
    }

    public boolean eliminarHabitacion(String numero) {
        int posicion = buscarHabitacion(numero);
        if (posicion != -1) {
            listaHabitaciones.remove(posicion);
            return true;
        }
        return false;
    }

    public boolean actualizarHabitacion(String numero, String piso, String tipo,
                                        int capacidadMaxima, double precioPorNoche) {
        int posicion = buscarHabitacion(numero);
        if (posicion != -1) {
            Habitacion habitacion = listaHabitaciones.get(posicion);
            habitacion.setPiso(piso);
            habitacion.setTipo(tipo);
            habitacion.setCapacidadMaxima(capacidadMaxima);
            habitacion.setPrecioPorNoche(precioPorNoche);
            return true;
        }
        return false;
    }

    public String mostrarHabitaciones() {
        String mensaje = "";
        for (Habitacion habitacion : listaHabitaciones) {
            mensaje += "\nNumero: " + habitacion.getNumero() +
                    "\nPiso: " + habitacion.getPiso() +
                    "\nTipo: " + habitacion.getTipo() +
                    "\nPrecio por noche: " + habitacion.getPrecioPorNoche() +
                    "\nEstado: " + habitacion.getEstado() +
                    "\nReservas actuales: " + habitacion.getCantidadReservasActuales() + "\n";
        }
        return mensaje;
    }

    public int buscarReserva(String codigo) {
        for (int i = 0; i < listaReservas.size(); i++) {
            if (listaReservas.get(i).getCodigoReserva().equals(codigo)) {
                return i;
            }
        }
        return -1;
    }

    public boolean registrarReserva(Reserva reserva) {
        if (buscarReserva(reserva.getCodigoReserva()) == -1) {
            listaReservas.add(reserva);
            reserva.getHuesped().agregarReserva(reserva);
            return true;
        }
        return false;
    }

    public boolean eliminarReserva(String codigo) {
        int posicion = buscarReserva(codigo);
        if (posicion != -1) {
            listaReservas.remove(posicion);
            return true;
        }
        return false;
    }

    public String mostrarReservas() {
        String mensaje = "";
        for (Reserva reserva : listaReservas) {
            mensaje += "\nCodigo: " + reserva.getCodigoReserva() +
                    "\nHuesped: " + reserva.getHuesped().getNombre() +
                    "\nEstado: " + reserva.getEstado() +
                    "\nFecha realizacion: " + reserva.getFechaRealizacion() +
                    "\nMetodo de pago: " + reserva.getMetodoPago() +
                    "\nHabitaciones asignadas: " + reserva.getCantidadHabitacionesAsignadas() +
                    "\nValor total: " + reserva.getValorTotal() + "\n";
        }
        return mensaje;
    }

    public int buscarServicio(String codigo) {
        for (int i = 0; i < listaServicios.size(); i++) {
            if (listaServicios.get(i).getCodigo().equals(codigo)) {
                return i;
            }
        }
        return -1;
    }

    public boolean registrarServicio(ServicioAdicional servicio) {
        if (buscarServicio(servicio.getCodigo()) == -1) {
            listaServicios.add(servicio);
            return true;
        }
        return false;
    }

    public boolean eliminarServicio(String codigo) {
        int posicion = buscarServicio(codigo);
        if (posicion != -1) {
            listaServicios.remove(posicion);
            return true;
        }
        return false;
    }

    public boolean actualizarServicio(String codigo, String nombre, String descripcion, double precio, boolean disponible) {
        int posicion = buscarServicio(codigo);
        if (posicion != -1) {
            ServicioAdicional servicio = listaServicios.get(posicion);
            servicio.setNombre(nombre);
            servicio.setDescripcion(descripcion);
            servicio.setPrecio(precio);
            servicio.setDisponible(disponible);
            return true;
        }
        return false;
    }

    public String mostrarServicios() {
        String mensaje = "";
        for (ServicioAdicional servicio : listaServicios) {
            mensaje += "\nCodigo: " + servicio.getCodigo() +
                    "\nNombre: " + servicio.getNombre() +
                    "\nDescripcion: " + servicio.getDescripcion() +
                    "\nPrecio: " + servicio.getPrecio() +
                    "\nDisponible: " + servicio.isDisponible() + "\n";
        }
        return mensaje;
    }

    public boolean agregarServicioAReserva(String codigoReserva, String codigoServicio) {
        int posicionReserva = buscarReserva(codigoReserva);
        int posicionServicio = buscarServicio(codigoServicio);
        if (posicionReserva != -1 && posicionServicio != -1) {
            ServicioAdicional servicio = listaServicios.get(posicionServicio);
            if (servicio.isDisponible()) {
                listaReservas.get(posicionReserva).agregarServicioAdicional(servicio.getPrecio());
                return true;
            }
        }
        return false;
    }

    public boolean esNumeroPerfecto(String telefono) {
        String soloDigitos = telefono.replaceAll("[^0-9]", "");
        if (soloDigitos.isEmpty() || soloDigitos.length() > 18) {
            return false;
        }
        long numero = Long.parseLong(soloDigitos);
        if (numero <= 1) {
            return false;
        }
        long suma = 1;
        for (long i = 2; i * i <= numero; i++) {
            if (numero % i == 0) {
                suma += i;
                long pareja = numero / i;
                if (pareja != i) {
                    suma += pareja;
                }
            }
        }
        return suma == numero;
    }

    public double calcularIngresosPorFecha(String fecha) {
        double total = 0;
        for (Reserva reserva : listaReservas) {
            if (reserva.getFechaRealizacion().equals(fecha)) {
                total += reserva.calcularValorTotal();
            }
        }
        return total;
    }
}
