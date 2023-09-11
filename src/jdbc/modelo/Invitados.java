package jdbc.modelo;

import java.sql.Date;

public class Invitados {
    private Integer IdInvitado; 
    private String Nombre;
    private String Apellido; 
    private Date Fecha_Nacimiento;
    private String Nacionalidad; 
    private String Telefono;
    private Integer IdReserva; 

    public Invitados(String nombre, String apellido, Date fechaNacimiento, String nacionalidad, String telefono,
            Integer idReserva) {
        Nombre = nombre;
        Apellido = apellido;
        Fecha_Nacimiento = fechaNacimiento;
        Nacionalidad = nacionalidad;
        Telefono = telefono;
        IdReserva = idReserva;
    }

    public Invitados(Integer id, String nombre, String apellido, Date fechaNacimiento, String nacionalidad,
            String telefono, Integer idReserva) {
        IdInvitado = id; 
        Nombre = nombre;
        Apellido = apellido;
        Fecha_Nacimiento = fechaNacimiento;
        Nacionalidad = nacionalidad;
        Telefono = telefono;
        IdReserva = idReserva;
    }

	public Integer getIdInvitado() {
		return IdInvitado;
	}

	public void setIdInvitado(Integer idInvitado) {
		IdInvitado = idInvitado;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getApellido() {
		return Apellido;
	}

	public void setApellido(String apellido) {
		Apellido = apellido;
	}

	public Date getFecha_Nacimiento() {
		return Fecha_Nacimiento;
	}

	public void setFecha_Nacimiento(Date fecha_Nacimiento) {
		Fecha_Nacimiento = fecha_Nacimiento;
	}

	public String getNacionalidad() {
		return Nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		Nacionalidad = nacionalidad;
	}

	public String getTelefono() {
		return Telefono;
	}

	public void setTelefono(String telefono) {
		Telefono = telefono;
	}

	public Integer getIdReserva() {
		return IdReserva;
	}

	public void setIdReserva(Integer idReserva) {
		IdReserva = idReserva;
	}

  
	
}
