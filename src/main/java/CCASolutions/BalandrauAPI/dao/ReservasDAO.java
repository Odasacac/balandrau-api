package CCASolutions.BalandrauAPI.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import CCASolutions.BalandrauAPI.entities.Reservas;


public interface ReservasDAO extends JpaRepository <Reservas, Long>
{

}
