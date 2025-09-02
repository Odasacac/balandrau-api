package CCASolutions.BalandrauAPI.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import CCASolutions.BalandrauAPI.entities.RegimenComidasEntity;
import jakarta.transaction.Transactional;

public interface RegimenComidasDAO extends JpaRepository <RegimenComidasEntity, Long>
{
	@Modifying
	@Transactional
	@Query("DELETE FROM RegimenComidasEntity r WHERE r.reserva.id = :reservaId")
	public abstract void deleteByReservaId(@Param ("reservaId") Long reservaId);
}
