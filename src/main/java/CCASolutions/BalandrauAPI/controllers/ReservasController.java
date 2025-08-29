package CCASolutions.BalandrauAPI.controllers;

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
import CCASolutions.BalandrauAPI.dtos.RequestReservaDTO;
import CCASolutions.BalandrauAPI.services.ReservasService;

@RestController
@CrossOrigin("*")
@RequestMapping("/reservas")
public class ReservasController 
{
	@Autowired
	ReservasService reservasService;

	@PostMapping("/habitaciones")
	public ResponseEntity<List<HabitacionesDTO>> getHabitacionesPorFechaYHuespedes(@RequestParam RequestReservaDTO requestReserva)
	{
		HttpStatus status = HttpStatus.OK;
		List<HabitacionesDTO> body = this.reservasService.getHabitacionesPorFechaYHuespedes(requestReserva);
		
		return new ResponseEntity<List<HabitacionesDTO>>(body, status);
	}
}
