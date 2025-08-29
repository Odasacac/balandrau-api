package CCASolutions.BalandrauAPI.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="habitaciones")
public class Habitaciones implements Serializable
{

	private static final long serialVersionUID = 8655749167143720759L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String nombre;
	private String descripcion;
	private int numeroDeHabitacion;
	private int numeroMaximoDeHuespedes;
	
	@OneToMany (mappedBy = "habitacion")
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
	
	public String getDescripcion() 
	{
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) 
	{
		this.descripcion = descripcion;
	}
	
	public int getNumeroDeHabitacion() 
	{
		return numeroDeHabitacion;
	}
	
	public void setNumeroDeHabitacion(int numeroDeHabitacion) 
	{
		this.numeroDeHabitacion = numeroDeHabitacion;
	}
	
	public int getNumeroMaximoDeHuespedes() 
	{
		return numeroMaximoDeHuespedes;
	}
	
	public void setNumeroMaximoDeHuespedes(int numeroMaximoDeHuespedes) 
	{
		this.numeroMaximoDeHuespedes = numeroMaximoDeHuespedes;
	}
	
	public static long getSerialversionuid() 
	{
		return serialVersionUID;
	}

	public List<Reservas> getReservas() 
	{
		return reservas;
	}

	public void setReservas(List<Reservas> reservas) 
	{
		this.reservas = reservas;
	}	
	
	
	
}
