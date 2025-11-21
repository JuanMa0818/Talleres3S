package co.edu.uniquindio.poo.parcial3.Model;

public class Paciente extends Persona {

    private String alergias;
    private String contraseña;

    private Paciente(Builder builder) {
        super(
                builder.nombre,
                builder.apellido,
                builder.identificacion,
                builder.telefono,
                builder.email
        );
        this.alergias = builder.alergias;
    }

    public String getAlergias() {
        return alergias;
    }
    public String getContraseña() {
        return contraseña;
    }


    public static class Builder {

        private String nombre;
        private String apellido;
        private String identificacion;
        private String telefono;
        private String email;
        private String alergias;
        private String contraseña;

        public Builder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder setContraseña(String contraseña) {
            this.contraseña = contraseña;
            return this;
        }


        public Builder setApellido(String apellido) {
            this.apellido = apellido;
            return this;
        }

        public Builder setIdentificacion(String identificacion) {
            this.identificacion = identificacion;
            return this;
        }

        public Builder setTelefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setAlergias(String alergias) {
            this.alergias = alergias;
            return this;
        }

        public Paciente build() {
            return new Paciente(this);
        }
    }
}
