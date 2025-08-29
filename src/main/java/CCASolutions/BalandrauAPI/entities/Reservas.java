package CCASolutions.BalandrauAPI.entities;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="reservas")
public class Reservas implements Serializable 
{

	private static final long serialVersionUID = -8859903864529687712L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "habitacion_id")
	private Habitaciones habitacion;
	
	private LocalDate fechaEntrada;
	private LocalDate fechaSalida;
	
	private int numeroHuespedes;
	
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Clientes cliente;

	
	public long getId() 
	{
		return id;
	}

	public void setId(long id) 
	{
		this.id = id;
	}

	public Habitaciones getHabitacion() 
	{
		return habitacion;
	}

	public void setHabitacion(Habitaciones habitacion) 
	{
		this.habitacion = habitacion;
	}

	public LocalDate getFechaEntrada() 
	{
		return fechaEntrada;
	}

	public void setFechaEntrada(LocalDate fechaEntrada) 
	{
		this.fechaEntrada = fechaEntrada;
	}

	public LocalDate getFechaSalida() 
	{
		return fechaSalida;
	}

	public void setFechaSalida(LocalDate fechaSalida) 
	{
		this.fechaSalida = fechaSalida;
	}

	public int getNumeroHuespedes() 
	{
		return numeroHuespedes;
	}

	public void setNumeroHuespedes(int numeroHuespedes) 
	{
		this.numeroHuespedes = numeroHuespedes;
	}

	public Clientes getCliente() 
	{
		return cliente;
	}

	public void setCliente(Clientes cliente) 
	{
		this.cliente = cliente;
	}

	public static long getSerialversionuid() 
	{
		return serialVersionUID;
	}

	
	
}
