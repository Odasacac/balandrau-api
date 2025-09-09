package CCASolutions.BalandrauAPI.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import CCASolutions.BalandrauAPI.entities.HistoricoReservasEntity;

public interface HistoricoReservasDAO extends JpaRepository <HistoricoReservasEntity, Long>
{
	@Query("SELECT h FROM HistoricoReservasEntity h WHERE h.fechaSalida < :fechaLimite")
	public abstract List<HistoricoReservasEntity> findReservasAnterioresA(@Param("fechaLimite") LocalDate fechaLimite);
}
