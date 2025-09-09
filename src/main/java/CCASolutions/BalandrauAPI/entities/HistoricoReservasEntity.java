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
@Table(name="historico_reservas")
public class HistoricoReservasEntity implements Serializable
{

	private static final long serialVersionUID = -6170364392021120450L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "habitacion_id")
	private HabitacionesEntity habitacion;
	
	private LocalDate fechaEntrada;
	private LocalDate fechaSalida;
	private LocalDate fechaCreacion;
	private int numeroHuespedes;	
	private BigDecimal precioTotal;	
	private String comentarios;
	private boolean hayComidas;
	
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private ClientesEntity cliente;
	
	@OneToMany (mappedBy="reservaHistorico")
	private List<HistoricoRegimenComidasEntity> regimenComidasHistorico = new ArrayList<>();

	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
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

	public LocalDate getFechaCreacion() 
	{
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) 
	{
		this.fechaCreacion = fechaCreacion;
	}

	public int getNumeroHuespedes() 
	{
		return numeroHuespedes;
	}

	public void setNumeroHuespedes(int numeroHuespedes) 
	{
		this.numeroHuespedes = numeroHuespedes;
	}

	public BigDecimal getPrecioTotal()
	{
		return precioTotal;
	}

	public void setPrecioTotal(BigDecimal precioTotal) 
	{
		this.precioTotal = precioTotal;
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

	public ClientesEntity getCliente()
	{
		return cliente;
	}

	public void setCliente(ClientesEntity cliente)
	{
		this.cliente = cliente;
	}

	public List<HistoricoRegimenComidasEntity> getRegimenComidasHistorico() 
	{
		return regimenComidasHistorico;
	}

	public void setRegimenComidasHistorico(List<HistoricoRegimenComidasEntity> regimenComidasHistorico) 
	{
		this.regimenComidasHistorico = regimenComidasHistorico;
	}

	public static long getSerialversionuid() 
	{
		return serialVersionUID;
	}
	
}
