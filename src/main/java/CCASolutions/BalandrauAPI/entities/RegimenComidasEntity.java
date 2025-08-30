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
@Table(name="regimen_de_comidas")
public class RegimenComidasEntity implements Serializable
{

	private static final long serialVersionUID = -60186433391184311L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name="reserva_id")
	private ReservasEntity reserva;
	
	private LocalDate fecha;
	private boolean desayuno;
	private boolean almuerzo;
	private boolean cena;
	private String alergias;
	private boolean picnic;
	
	public long getId() 
	{
		return id;
	}
	
	public void setId(long id) 
	{
		this.id = id;
	}
	
	public ReservasEntity getReserva() 
	{
		return reserva;
	}
	
	public void setReserva(ReservasEntity reserva)
	{
		this.reserva = reserva;
	}
	
	public LocalDate getFecha() 
	{
		return fecha;
	}
	
	public void setFecha(LocalDate fecha) 
	{
		this.fecha = fecha;
	}
	
	public String getAlergias() 
	{
		return alergias;
	}
	
	public void setAlergias(String alergias) 
	{
		this.alergias = alergias;
	}
	
	public boolean isPicnic() 
	{
		return picnic;
	}
	
	public void setPicnic(boolean picnic) 
	{
		this.picnic = picnic;
	}
	
	public static long getSerialversionuid() 
	{
		return serialVersionUID;
	}

	public boolean isDesayuno() 
	{
		return desayuno;
	}

	public void setDesayuno(boolean desayuno) 
	{
		this.desayuno = desayuno;
	}

	public boolean isAlmuerzo() 
	{
		return almuerzo;
	}

	public void setAlmuerzo(boolean almuerzo) 
	{
		this.almuerzo = almuerzo;
	}

	public boolean isCena() 
	{
		return cena;
	}

	public void setCena(boolean cena) 
	{
		this.cena = cena;
	}
	
	
}
