package CCASolutions.BalandrauAPI.servicesImpl;

import CCASolutions.BalandrauAPI.dao.ClientesDAO;
import CCASolutions.BalandrauAPI.entities.ClientesEntity;
import CCASolutions.BalandrauAPI.services.ClientesService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientesServiceImpl implements ClientesService
{
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private ClientesDAO clientesDao;

	public boolean verificarPassword (String passwordIngresado, String hashAlmacenado)
	{
		boolean coinciden = encoder.matches(passwordIngresado, hashAlmacenado);
		
		return coinciden;

	}
	
	public String encriptarPassword (String password)
	{				
		String passwordEncriptado = encoder.encode(password);
		
		return passwordEncriptado;
	}
	
	public boolean clienteExists(Long clienteId)
	{
		boolean clienteExiste = false;
		
		try
		{
			Optional<ClientesEntity> clienteIdBBDDOpt = this.clientesDao.findById(clienteId);
			
			if(clienteIdBBDDOpt.isPresent())
			{
				clienteExiste=true;
			}
			
		}
		catch(Exception e)
		{
			clienteExiste = false;
		}
		
		return clienteExiste;
	}
}
