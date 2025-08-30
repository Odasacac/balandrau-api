package CCASolutions.BalandrauAPI.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="clientes")
public class Clientes implements Serializable 
{

	private static final long serialVersionUID = 6521759965322384298L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String dni;
	private String email;
	private String password;
	private boolean esCampista;
	private LocalDate fechaNacimiento;
	
	@OneToMany(mappedBy="cliente")
	private List<Reservas> reservas = new ArrayList<Reservas>();

	
	public long getId() 
	{
		return id;
	}

	public void setId(long id) 
	{
		this.id = id;
	}

	public String getNombre() 
	{
		return nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public String getApellido1() 
	{
		return apellido1;
	}

	public void setApellido1(String apellido1) 
	{
		this.apellido1 = apellido1;
	}

	public String getApellido2() 
	{
		return apellido2;
	}

	public void setApellido2(String apellido2) 
	{
		this.apellido2 = apellido2;
	}

	public String getDni() 
	{
		return dni;
	}

	public void setDni(String dni) 
	{
		this.dni = dni;
	}

	public boolean isEsCampista() 
	{
		return esCampista;
	}

	public void setEsCampista(boolean esCampista) 
	{
		this.esCampista = esCampista;
	}

	public LocalDate getFechaNacimiento() 
	{
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) 
	{
		this.fechaNacimiento = fechaNacimiento;
	}

	public List<Reservas> getReservas() 
	{
		return reservas;
	}

	public void setReservas(List<Reservas> reservas) 
	{
		this.reservas = reservas;
	}

	public static long getSerialversionuid() 
	{
		return serialVersionUID;
	}

	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}

	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}
	
	
	

}
