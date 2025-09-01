package CCASolutions.BalandrauAPI.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import CCASolutions.BalandrauAPI.entities.ClientesEntity;

public interface ClientesDAO extends JpaRepository <ClientesEntity, Long>
{

}
