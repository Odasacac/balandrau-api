package CCASolutions.BalandrauAPI.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import CCASolutions.BalandrauAPI.entities.ReservasEntity;

public interface ReservasDAO extends JpaRepository <ReservasEntity, Long>
{
	public abstract List<ReservasEntity> findByCliente_Id(Long clienteId);
	
	@Query("SELECT r FROM ReservasEntity r WHERE r.fechaSalida < :fechaSalida")
	public abstract List<ReservasEntity> findReservasConFechaSalidaAnterioresA(@Param("fechaSalida")LocalDate fechaSalida);
}
