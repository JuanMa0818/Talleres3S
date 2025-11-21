package co.edu.uniquindio.poo.parcial3.Model;

import java.util.ArrayList;
import java.util.List;

public class Clinica {
    private static Clinica clinica;
    private String nombre;
    private String nit;
    private List<Medico> medicos;
    private List<Paciente> pacientes;
    private List<Cita> citas;

    private Clinica(String nombre, String nit) {
        this.nombre = nombre;
        this.nit = nit;
        this.medicos = new ArrayList<>();
        this.pacientes = new ArrayList<>();
        this.citas = new ArrayList<>();
        inicializarDatos();
    }

    public static Clinica getInstancia(String nombre, String nit) {
        if (clinica == null) {
            clinica = new Clinica(nombre, nit);
        }
        return clinica;
    }

    private void inicializarDatos() {
        // Pacientes existentes
        pacientes.add(new Paciente.Builder()
                .setNombre("Juan")
                .setApellido("Pérez")
                .setIdentificacion("1001")
                .setTelefono("1234567")
                .setEmail("juan@email.com")
                .setAlergias("Penicilina")
                .setContraseña("1111")
                .build());

        pacientes.add(new Paciente.Builder()
                .setNombre("María")
                .setApellido("González")
                .setIdentificacion("1002")
                .setTelefono("7654321")
                .setEmail("maria@email.com")
                .setAlergias("Aspirina")
                .setContraseña("2222")
                .build());

        // **Agregamos a Camila**
        pacientes.add(new Paciente.Builder()
                .setNombre("Camila")
                .setApellido("Mejía")
                .setIdentificacion("1003")
                .setTelefono("3001234567")
                .setEmail("camila@gmail.com")
                .setAlergias("Ninguna")
                .setContraseña("1234")
                .build());
    }


    public List<Medico> getMedicosPorEspecialidad(String especialidad) {
        List<Medico> resultado = new ArrayList<>();
        for (Medico medico : medicos) {
            if (medico.getEspecialidad().equalsIgnoreCase(especialidad)) {
                resultado.add(medico);
            }
        }
        return resultado;
    }

    public boolean solicitarCita(Paciente paciente, Medico medico, String fecha, String hora, String motivo) {
        Cita cita;
        String tipoCita;


        if (medico instanceof MedicoOdontologo) {
            tipoCita = "odontologica";

        } else if (medico instanceof MedicoGeneral) {
            tipoCita = "general";

        } else {
            tipoCita = "general";
        }


        double precio = medico.getPrecio(); // Usar el precio del médico
        cita = FactoryCita.crearCita(tipoCita, fecha, hora, precio);

        return citas.add(cita);
    }

    public List<Cita> getCitasPorPaciente(String identificacionPaciente) {

        return new ArrayList<>(citas);
    }

    public Paciente buscarPacientePorIdentificacion(String identificacion) {
        for (Paciente paciente : pacientes) {
            if (paciente.getIdentificacion().equals(identificacion)) {
                return paciente;
            }
        }
        return null;
    }

    public Medico buscarMedicoPorIdentificacion(String identificacion) {
        for (Medico medico : medicos) {
            if (medico.getIdentificacion().equals(identificacion)) {
                return medico;
            }
        }
        return null;
    }

    public List<String> getEspecialidades() {
        List<String> especialidades = new ArrayList<>();
        especialidades.add("General");
        especialidades.add("Odontología");
        return especialidades;
    }

    public List<String> getHorasDisponibles() {
        List<String> horas = new ArrayList<>();
        horas.add("08:00 AM");
        horas.add("09:00 AM");
        horas.add("10:00 AM");
        horas.add("11:00 AM");
        horas.add("02:00 PM");
        horas.add("03:00 PM");
        horas.add("04:00 PM");
        return horas;
    }

    public List<String> getFechasDisponibles() {
        List<String> fechas = new ArrayList<>();
        // Agregar algunas fechas de ejemplo
        fechas.add("2024-11-25");
        fechas.add("2024-11-26");
        fechas.add("2024-11-27");
        fechas.add("2024-11-28");
        fechas.add("2024-11-29");
        return fechas;
    }


    public void agregarCita(Cita cita) {
        citas.add(cita);
    }


    public List<Cita> getTodasLasCitas() {
        return new ArrayList<>(citas);
    }

    public List<Medico> getMedicosDisponibles() {
        return new ArrayList<>(medicos);
    }

    // Getters
    public List<Medico> getMedicos() { return new ArrayList<>(medicos); }
    public List<Paciente> getPacientes() { return new ArrayList<>(pacientes); }
    public List<Cita> getCitas() { return new ArrayList<>(citas); }
    public String getNombre() { return nombre; }
    public String getNit() { return nit; }
}