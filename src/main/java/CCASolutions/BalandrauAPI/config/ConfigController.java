package CCASolutions.BalandrauAPI.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
@CrossOrigin("*")
public class ConfigController
{
	
	@Autowired
	private IConfigService configService;
	
	@GetMapping("/restablecerBaseDeDatos")
	public ResponseEntity<String> restablecerBaseDeDatos()
	{
		HttpStatus status = HttpStatus.OK;
		String body = new String();
		
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
		
		return new ResponseEntity<String>(body, status);
	}
}
