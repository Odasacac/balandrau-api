package CCASolutions.BalandrauAPI.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import CCASolutions.BalandrauAPI.entities.DatosEntity;

public interface DatosDAO extends JpaRepository <DatosEntity, Long>
{
	@Query("SELECT d.valor FROM DatosEntity d WHERE d.concepto = 'descuentoCampista'")
	public abstract int getPorCientoDeDescuentoPorCampista();
}
