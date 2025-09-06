package CCASolutions.BalandrauAPI.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CCASolutions.BalandrauAPI.dao.ClientesDAO;
import CCASolutions.BalandrauAPI.dao.ReservasDAO;
import CCASolutions.BalandrauAPI.dtos.GetReservasDTO;
import CCASolutions.BalandrauAPI.dtos.RequestEliminarReserva;
import CCASolutions.BalandrauAPI.dtos.RequestHabitacion;
import CCASolutions.BalandrauAPI.dtos.RequestHacerReserva;
import CCASolutions.BalandrauAPI.dtos.RequestModificarReserva;
import CCASolutions.BalandrauAPI.entities.ClientesEntity;
import CCASolutions.BalandrauAPI.entities.ReservasEntity;
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
	
	@Autowired
	private ReservasDAO reservasDao;
	
	@Autowired
	private ClientesDAO clientesDao;

	@GetMapping("/obtener/{id}")
	public ResponseEntity<List<GetReservasDTO>> getReservasByClienteId(@PathVariable("id") Long clienteId)
	{
		HttpStatus status = HttpStatus.OK;
		List<GetReservasDTO> body = new ArrayList<>();
		
		Optional<ClientesEntity> clienteOpt = this.clientesDao.findById(clienteId);
		
		if(clienteOpt.isPresent())
		{
			body = this.reservasService.getReservasByClienteId(clienteId);
		}
		else
		{
			status = HttpStatus.NOT_FOUND;
		}
		
		return new ResponseEntity<List<GetReservasDTO>>(body, status);
	}
	
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
	
	@PostMapping("/modificar")
	public ResponseEntity<String> modificarReserva (@RequestBody RequestModificarReserva requestModificarReserva)
	{
		HttpStatus status = HttpStatus.OK;
		String body = "";
		
		Long usuarioQueQuiereModificar = requestModificarReserva.clienteId();
		Optional<ReservasEntity> reservaEnBaseDeDatosOpt = this.reservasDao.findById(requestModificarReserva.reservaId());
		
		if(reservaEnBaseDeDatosOpt.isEmpty())
		{
			body = new String("La reserva no existe.");
			status = HttpStatus.BAD_REQUEST;
		}
		else
		{
			Long idClienteDeLaReserva = reservaEnBaseDeDatosOpt.get().getCliente().getId();
			
			if(idClienteDeLaReserva.equals(usuarioQueQuiereModificar))
			{
				body = this.reservasService.modificarReserva(reservaEnBaseDeDatosOpt.get(), requestModificarReserva);
			}
			else
			{
				if(this.clientesDao.isAdmin(usuarioQueQuiereModificar))
				{
					body = this.reservasService.modificarReserva(reservaEnBaseDeDatosOpt.get(), requestModificarReserva);
				}
				else
				{
					body = new String("No tienes permisos para realizar esta accion.");
					status = HttpStatus.BAD_REQUEST;
				}
			}
	
		}
		
		return new ResponseEntity<String>(body, status);
	}
}
