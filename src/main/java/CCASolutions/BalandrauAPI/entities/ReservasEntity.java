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
public class ReservasEntity implements Serializable 
{

	private static final long serialVersionUID = -8859903864529687712L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "habitacion_id")
	private HabitacionesEntity habitacion;
	
	private LocalDate fechaEntrada;
	private LocalDate fechaSalida;
	private LocalDate fechaCreacion;
	private LocalDate fechaUltimaModificacion;
	private Long idClienteUltimaModificacion;
	private int numeroHuespedes;	
	private BigDecimal precioTotal;	
	private String comentarios;
	private boolean hayComidas;
	
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private ClientesEntity cliente;
	
	@OneToMany (mappedBy="reserva")
	private List<RegimenComidasEntity> regimenComidas = new ArrayList<>();

	
	
	public LocalDate getFechaUltimaModificacion() 
	{
		return fechaUltimaModificacion;
	}

	public void setFechaUltimaModificacion(LocalDate fechaUltimaModificacion) 
	{
		this.fechaUltimaModificacion = fechaUltimaModificacion;
	}

	public Long getIdClienteUltimaModificacion() 
	{
		return idClienteUltimaModificacion;
	}

	public void setIdClienteUltimaModificacion(Long idClienteUltimaModificacion) 
	{
		this.idClienteUltimaModificacion = idClienteUltimaModificacion;
	}

	public LocalDate getFechaCreacion() 
	{
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) 
	{
		this.fechaCreacion = fechaCreacion;
	}

	public long getId() 
	{
		return id;
	}

	public void setId(long id) 
	{
		this.id = id;
	}

	public HabitacionesEntity getHabitacion() 
	{
		return habitacion;
	}

	public void setHabitacion(HabitacionesEntity habitacion) 
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

	public ClientesEntity getCliente() 
	{
		return cliente;
	}

	public void setCliente(ClientesEntity cliente) 
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

	public List<RegimenComidasEntity> getRegimenComidas() 
	{
		return regimenComidas;
	}

	public void setRegimenComidas(List<RegimenComidasEntity> regimenComidas) 
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

	public boolean isHayComidas() 
	{
		return hayComidas;
	}

	public void setHayComidas(boolean hayComidas) 
	{
		this.hayComidas = hayComidas;
	}
		

	
}
