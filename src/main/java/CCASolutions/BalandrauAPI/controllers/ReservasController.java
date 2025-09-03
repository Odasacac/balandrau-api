package CCASolutions.BalandrauAPI.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CCASolutions.BalandrauAPI.dtos.RequestEliminarReserva;
import CCASolutions.BalandrauAPI.dtos.RequestHabitacion;
import CCASolutions.BalandrauAPI.dtos.RequestHacerReserva;
import CCASolutions.BalandrauAPI.services.HabitacionesService;
import CCASolutions.BalandrauAPI.services.ReservasService;


@RestController
@CrossOrigin("*")
@RequestMapping("/reservas")
public class ReservasController 
{
	@Autowired
	private ReservasService	reservasService;
	
	@Autowired
	private HabitacionesService habitacionesService;	

	
	@PostMapping("/guardar")
	public ResponseEntity <String> guardarReserva (@RequestBody RequestHacerReserva requestReserva)
	{
		HttpStatus status = HttpStatus.OK;
		String body = "";
		
		if (requestReserva.fechaSalida().isBefore(requestReserva.fechaEntrada()) || requestReserva.fechaSalida().isEqual(requestReserva.fechaEntrada()) || requestReserva.numeroHuespedes() < 1)
		{
			status = HttpStatus.BAD_REQUEST;
			body = "Datos incorrectos.";
			return new ResponseEntity<String>(body, status);
		}
		
		LocalDate fechaEntrada = requestReserva.fechaEntrada();
		LocalDate fechaSalida = requestReserva.fechaSalida();
		int numeroHuespedes = requestReserva.numeroHuespedes();
		Long habitacionId = requestReserva.habitacionId();
		
		RequestHabitacion requestHabitacion = new RequestHabitacion(fechaEntrada, fechaSalida, numeroHuespedes, habitacionId);
		
		if(!this.habitacionesService.habitacionDisponible(requestHabitacion))
		{
			body = "La habitación ya está reservada, prueba con otras opciones.";
			status = HttpStatus.NOT_FOUND;
		}
		else
		{
			try
			{
				body = this.reservasService.guardarNuevaReserva(requestReserva);			
			}
			catch (Exception e)
			{
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				body="Error al guardar la reserva.";
			}		
		}		
		
		return new ResponseEntity<String>(body, status);
	}
	
	@PostMapping("/eliminar")
	public ResponseEntity <String> eliminarReserva (@RequestBody RequestEliminarReserva requestEliminarReserva)
	{
		HttpStatus status = HttpStatus.OK;
		String body = "";
		
		try
		{
			body = this.reservasService.eliminarReserva(requestEliminarReserva);
		}
		catch(Exception e)
		{
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			body="Error al eliminar la reserva.";
		}
		
		return new ResponseEntity<String>(body, status);
	}
}
