package CCASolutions.BalandrauAPI.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	private BigDecimal precioTotal;	
	private String comentarios;
	private boolean regimenDeComidas;
	
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Clientes cliente;
	
	@OneToMany (mappedBy="reserva")
	private List<RegimenComidas> regimenComidas = new ArrayList<>();

	
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

	public BigDecimal getPrecioTotal() 
	{
		return precioTotal;
	}

	public void setPrecioTotal(BigDecimal precioTotal) 
	{
		this.precioTotal = precioTotal;
	}

	public List<RegimenComidas> getRegimenComidas() 
	{
		return regimenComidas;
	}

	public void setRegimenComidas(List<RegimenComidas> regimenComidas) 
	{
		this.regimenComidas = regimenComidas;
	}

	public String getComentarios() 
	{
		return comentarios;
	}

	public void setComentarios(String comentarios) 
	{
		this.comentarios = comentarios;
	}
		

	public boolean isRegimenDeComidas() 
	{
		return regimenDeComidas;
	}

	public void setRegimenDeComidas(boolean regimenDeComidas) 
	{
		this.regimenDeComidas = regimenDeComidas;
	}
	
	
}
