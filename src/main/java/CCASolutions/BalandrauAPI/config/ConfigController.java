package CCASolutions.BalandrauAPI.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CCASolutions.BalandrauAPI.dao.DatosDAO;
import CCASolutions.BalandrauAPI.services.ClientesService;

@RestController
@RequestMapping("/config")
@CrossOrigin("*")
public class ConfigController
{
	
	@Autowired
	private IConfigService configService;
	
	@Autowired
	private DatosDAO datosDao;
	
	@Autowired
	private ClientesService clientesService;
	
	@Value("${permisoAdminString}") 
	private String permisoAdminString;
	
	@GetMapping("/restablecerBaseDeDatos/{permisoAdmin}")
	public ResponseEntity<String> restablecerBaseDeDatos(@PathVariable("permisoAdmin") String permisoAdminRecibido)
	{
		HttpStatus status = HttpStatus.OK;
		String body = new String();
		
		String permisoAdminBBDDHash = this.datosDao.getDatoConcreto(permisoAdminString);
		
		boolean coincidenPasswords = this.clientesService.verificarPassword(permisoAdminRecibido, permisoAdminBBDDHash);
		
		if(coincidenPasswords)
		{
			try
			{
				if(configService.restablecerBaseDeDatos())
				{
					body = new String("Base de datos restaurada.");				
				}
				else
				{
					body = new String("Error al restablecer la base de datos.");
				}
				
			}
			catch (Exception e)
			{
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				body = new String("Ha habido un error: " + e.getMessage());
			}
		}
		else
		{
			status = HttpStatus.BAD_REQUEST;
			body = new String("No tiene permisos para realizar esta accion.");
		}		
		
	
		
		return new ResponseEntity<String>(body, status);
	}
	
}
