package CCASolutions.BalandrauAPI.servicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CCASolutions.BalandrauAPI.dao.ReservasDAO;
import CCASolutions.BalandrauAPI.dtos.RequestHabitacion;
import CCASolutions.BalandrauAPI.dtos.RequestReservaDTO;
import CCASolutions.BalandrauAPI.entities.ClientesEntity;
import CCASolutions.BalandrauAPI.entities.HabitacionesEntity;
import CCASolutions.BalandrauAPI.entities.RegimenComidasEntity;
import CCASolutions.BalandrauAPI.entities.ReservasEntity;
import CCASolutions.BalandrauAPI.exceptions.RegimenComidasException;
import CCASolutions.BalandrauAPI.exceptions.ReservasException;
import CCASolutions.BalandrauAPI.services.HabitacionesService;
import CCASolutions.BalandrauAPI.services.RegimenComidasService;
import CCASolutions.BalandrauAPI.services.ReservasService;
import jakarta.transaction.Transactional;

@Service
public class ReservasServiceImpl implements ReservasService
{
	@Autowired
	private ReservasDAO reservasDao;
	
	@Autowired
	private RegimenComidasService regimenComidasService;
	
	@Autowired
	private HabitacionesService habitacionesService;
	
	@Transactional
	public String guardarNuevaReserva(RequestReservaDTO requestReserva)
	{
		String resultado = "";
		
		RequestHabitacion requestHabitacion = new RequestHabitacion(requestReserva.fechaEntrada(), requestReserva.fechaSalida(), requestReserva.numeroHuespedes(), requestReserva.habitacionId());
		
		
		if(!this.habitacionesService.habitacionDisponible(requestHabitacion))
		{
			resultado = "La habitación ya está reservada, prueba con otras opciones.";
		}
		else
		{
			try
			{
				Long reservaGuardadaId = guardarReserva(requestReserva);
				
				if (!requestReserva.comidas().isEmpty()) 
				{
					guardarRegimen(reservaGuardadaId, requestReserva.comidas());
				}
				
				resultado = "Reserva guardada con éxito.";
			}
			catch (ReservasException e)
			{
				resultado = "Error al guardar la reserva.";
			}
			catch (RegimenComidasException e)
			{
				resultado = "Error al guardar el régimen.";
			}
			catch(Exception e)
			{
		
				resultado = "Error general.";
			}
		}
		
		
		return resultado;
	}
	
	
	private Long guardarReserva(RequestReservaDTO requestReserva)
	{
		try
		{
			ReservasEntity reserva = new ReservasEntity();
		
			reserva.setFechaEntrada(requestReserva.fechaEntrada());
			reserva.setFechaSalida(requestReserva.fechaSalida());
			reserva.setNumeroHuespedes(requestReserva.numeroHuespedes());
			reserva.setPrecioTotal(requestReserva.precioTotal());
			reserva.setComentarios(requestReserva.comentarios());
			
			HabitacionesEntity habitacion = new HabitacionesEntity();
			habitacion.setId(requestReserva.habitacionId());
			reserva.setHabitacion(habitacion);
		
			if(!requestReserva.comidas().isEmpty())
			{
				reserva.setHayComidas(true);
			}
		
			ClientesEntity cliente = new ClientesEntity();
			cliente.setId(requestReserva.clienteId());
			reserva.setCliente(cliente);

			ReservasEntity reservaGuardada = this.reservasDao.save(reserva);
		
			return reservaGuardada.getId();
		}
		catch (Exception e)
		{
			System.out.println("Error en la reserva: " + e);
			throw new ReservasException("Error en la reserva", e);
		}
	
	}
	
	private void guardarRegimen(Long reservaGuardadaId, List<RegimenComidasEntity> comidas)
	{
		for (int i = 0; i<comidas.size(); i++)
		{
			try
			{
				this.regimenComidasService.guardarNuevoRegimen(comidas.get(i), reservaGuardadaId);
			}
			catch (Exception e)
			{
				System.out.println("Error en el régimen: " + e);
				throw new RegimenComidasException("Error en el régimen", e);
			}
		}
	}
}
