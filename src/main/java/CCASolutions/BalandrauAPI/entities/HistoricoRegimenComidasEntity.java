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
@Table(name="historico_regimen_comidas")
public class HistoricoRegimenComidasEntity implements Serializable
{
	private static final long serialVersionUID = -1505426400317382043L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="reserva_historico_id")
	private HistoricoReservasEntity reservaHistorico;
	
	private LocalDate fecha;
	private boolean desayuno;
	private boolean almuerzo;
	private boolean cena;
	private String alergias;
	private boolean picnic;
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id) 
	{
		this.id = id;
	}
	
	public HistoricoReservasEntity getReservaHistorico() 
	{
		return reservaHistorico;
	}
	
	public void setReservaHistorico(HistoricoReservasEntity reservaHistorico) 
	{
		this.reservaHistorico = reservaHistorico;
	}
	
	public LocalDate getFecha() 
	{
		return fecha;
	}
	
	public void setFecha(LocalDate fecha) 
	{
		this.fecha = fecha;
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
