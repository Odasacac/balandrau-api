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
@Table(name="cocina")
public class RegimenComidas implements Serializable
{

	private static final long serialVersionUID = -60186433391184311L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name="reserva_id")
	private Reservas reserva;
	
	private LocalDate fecha;	
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
	
	public Reservas getReserva() 
	{
		return reserva;
	}
	
	public void setReserva(Reservas reserva)
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
	
	
}
