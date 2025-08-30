package CCASolutions.BalandrauAPI.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="cocina")
public class Cocina implements Serializable
{

	private static final long serialVersionUID = -60186433391184311L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

}
