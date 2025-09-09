package CCASolutions.BalandrauAPI.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import CCASolutions.BalandrauAPI.entities.HistoricoRegimenComidasEntity;
import jakarta.transaction.Transactional;

public interface HistoricoRegimenComidasDAO extends JpaRepository <HistoricoRegimenComidasEntity, Long>
{
	@Transactional
	public abstract void deleteByReservaHistoricoId(Long reservaId);
}
