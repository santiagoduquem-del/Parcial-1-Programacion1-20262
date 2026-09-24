package main.java.co.edu.uniquindio.parcial1.model;

public class Huesped {


    private String nombre;
    private String ID;
    private String correo;
    private int telefono;
    private String paisPorcedencia;
    private String esPerfect;

    public Huesped(String nombre, String ID, String correo, int telefono, String paisPorcedencia) {
        this.nombre = nombre;
        this.ID = ID;
        this.correo = correo;
        this.telefono = telefono;
        this.paisPorcedencia = paisPorcedencia;
        this.esPerfect = esPerfecto(telefono);
    }

    public static String esPerfecto (int n){
        String msg = "No es un número perfecto.";
        int k = 0;
        for(int i = 1; i<n; i++){
            if(n%i==0){
                k+=i;
            }


        }
        if(k==n){
            msg = "Es un número perfecto!";
        }

        return msg;
    }



    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {

        this.nombre = nombre;
    }
    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public int getTelefono() {
        return telefono;
    }
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
    public String getPaisPorcedencia() {
        return paisPorcedencia;
    }
    public void setPaisPorcedencia(String paisPorcedencia) {
        this.paisPorcedencia = paisPorcedencia;
    }
    public String getEsPerfect() {
        return esPerfect;
    }

}
