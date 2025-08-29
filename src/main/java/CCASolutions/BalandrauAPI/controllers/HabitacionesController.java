package CCASolutions.BalandrauAPI.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import CCASolutions.BalandrauAPI.dtos.HabitacionesDTO;
import CCASolutions.BalandrauAPI.dtos.RequestHabitacionesDTO;
import CCASolutions.BalandrauAPI.services.HabitacionesService;


@RestController
@CrossOrigin("*")
@RequestMapping("/habitaciones")
public class HabitacionesController 
{
	@Autowired
	private HabitacionesService habitacionesService;

	@PostMapping("/disponibilidad")
	public ResponseEntity<List<HabitacionesDTO>> getHabitacionesDisponiblesPorFechaYHuespedes(@RequestParam RequestHabitacionesDTO requestHabitaciones)
	{
		HttpStatus status = HttpStatus.OK;
		List<HabitacionesDTO> body = new ArrayList<HabitacionesDTO>();
		
		try
		{
			body = this.habitacionesService.getHabitacionesDisponiblesPorFechaYHuespedes(requestHabitaciones);
		}
		catch (Exception e)
		{
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		return new ResponseEntity<List<HabitacionesDTO>>(body, status);
	}
}
