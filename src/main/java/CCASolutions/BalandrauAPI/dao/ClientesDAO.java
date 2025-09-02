package CCASolutions.BalandrauAPI.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import CCASolutions.BalandrauAPI.entities.ClientesEntity;

public interface ClientesDAO extends JpaRepository <ClientesEntity, Long>
{
	@Query("SELECT c.esAdmin FROM ClientesEntity c WHERE c.id = :clienteId")
	public abstract Boolean isAdmin(@Param ("clienteId") Long clienteId);

}
