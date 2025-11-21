package co.edu.uniquindio.poo.parcial3.Model;

public abstract class Persona {
    protected String nombre;
    protected String apellido;
    protected String identificacion;
    protected String telefono;
    protected String email;
    protected String contraseña;

    public Persona(String nombre, String apellido, String identificacion, String telefono, String email, String contraseña) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.identificacion = identificacion;
        this.telefono = telefono;
        this.email = email;
        this.contraseña= contraseña;
    }

    public String getNombre() {
        return nombre;
    }
    public void setContraseña(String contraseña){this.contraseña= contraseña;}

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }
    public String getContraseña(){
        return contraseña;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
