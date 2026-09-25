package model;

import java.util.ArrayList;
import java.util.List;

public class Huesped {
    private String documento;
    private static String nombre;
    private String telefono;
    private String correo;
    private String pais;

    private List<Reserva> listaReservas;

    public Huesped(String documento, String nombre, String telefono, String correo, String pais) {
        this.documento = documento;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.pais = pais;
        this.listaReservas = new ArrayList<>();
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public static String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public List<Reserva> getListaReservas() {
        return listaReservas;
    }

    public void setListaReservas(List<Reserva> listaReservas) {
        this.listaReservas = listaReservas;
    }

    public void agregarReserva(Reserva reserva) {
        listaReservas.add(reserva);
    }
}
