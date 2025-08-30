package CCASolutions.BalandrauAPI.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import CCASolutions.BalandrauAPI.entities.DatosEntity;

public interface DatosDAO extends JpaRepository <DatosEntity, Long>
{
	@Query("SELECT d.valor FROM DatosEntity d WHERE d.concepto = 'descuentoCampista'")
	public abstract int getPorCientoDeDescuentoPorCampista();
	
	@Query("SELECT d.concepto, d.valor FROM DatosEntity d WHERE d.concepto = 'precioDesayuno' OR d.concepto = 'precioAlmuerzo' OR d.concepto = 'precioCena' OR d.concepto = 'descuentoPicnic'")
	public abstract List<Object[]> getPreciosRegimen();
}
