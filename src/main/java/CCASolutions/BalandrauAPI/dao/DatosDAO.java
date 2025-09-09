package CCASolutions.BalandrauAPI.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import CCASolutions.BalandrauAPI.entities.DatosEntity;

public interface DatosDAO extends JpaRepository <DatosEntity, Long>
{
	@Query("SELECT d FROM DatosEntity d WHERE inicial = TRUE")
	public abstract List<DatosEntity> getDatosIniciales();
	
	@Query("SELECT d.valor FROM DatosEntity d WHERE d.concepto = 'descuentoCampista'")
	public abstract int getPorCientoDeDescuentoPorCampista();
	
	@Query("SELECT d FROM DatosEntity d WHERE d.concepto IN(:conceptos)")
	public abstract List<DatosEntity> getDatosPorConceptos(@Param("conceptos") List<String> conceptos);
	
	@Query("SELECT d.valor FROM DatosEntity d WHERE d.concepto = :concepto")
	public abstract String getDatoConcreto(@Param("concepto") String concepto);
}
