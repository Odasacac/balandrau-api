package CCASolutions.BalandrauAPI.servicesImpl;

import CCASolutions.BalandrauAPI.services.ClientesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientesServiceImpl implements ClientesService
{
	@Autowired
	private BCryptPasswordEncoder encoder;

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

}
