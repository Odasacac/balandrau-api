package CCASolutions.BalandrauAPI.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import CCASolutions.BalandrauAPI.entities.ReservasEntity;

public interface ReservasDAO extends JpaRepository <ReservasEntity, Long>
{

}
