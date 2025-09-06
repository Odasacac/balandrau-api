package CCASolutions.BalandrauAPI.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import CCASolutions.BalandrauAPI.entities.ReservasEntity;

public interface ReservasDAO extends JpaRepository <ReservasEntity, Long>
{
	public abstract List<ReservasEntity> findByCliente_Id(Long clienteId);
}
