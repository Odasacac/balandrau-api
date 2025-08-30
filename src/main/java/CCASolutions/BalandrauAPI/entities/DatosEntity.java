package CCASolutions.BalandrauAPI.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="datos")
public class DatosEntity implements Serializable
{
	private static final long serialVersionUID = -6371714830192755237L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String concepto;
	private String valor;
	
	public long getId() 
	{
		return id;
	}
	
	public void setId(long id) 
	{
		this.id = id;
	}
	
	public String getConcepto() 
	{
		return concepto;
	}
	
	public void setConcepto(String concepto) 
	{
		this.concepto = concepto;
	}
	
	public String getValor() 
	{
		return valor;
	}
	
	public void setValor(String valor) 
	{
		this.valor = valor;
	}
	
	public static long getSerialversionuid() 
	{
		return serialVersionUID;
	}
	
	
}
