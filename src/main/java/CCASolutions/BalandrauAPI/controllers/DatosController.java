package CCASolutions.BalandrauAPI.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CCASolutions.BalandrauAPI.dtos.DatosEntityDTO;
import CCASolutions.BalandrauAPI.services.DatosService;

@RestController
@RequestMapping("/datos")
@CrossOrigin("*")
public class DatosController 
{
	@Autowired
	private DatosService datosService;

	@GetMapping("/preciosRegimen")
	public ResponseEntity<List<DatosEntityDTO>> getPreciosRegimen()
	{
		HttpStatus status = HttpStatus.OK;
		List<DatosEntityDTO> body = new ArrayList<DatosEntityDTO>();
		
		try
		{
			body = this.datosService.getPreciosRegimen();
		}
		catch (Exception e)
		{
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<List<DatosEntityDTO>> (body, status);
	}
}
