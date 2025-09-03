package CCASolutions.BalandrauAPI.servicesImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CCASolutions.BalandrauAPI.dao.ClientesDAO;
import CCASolutions.BalandrauAPI.dao.ReservasDAO;
import CCASolutions.BalandrauAPI.dtos.RequestEliminarReserva;
import CCASolutions.BalandrauAPI.dtos.RequestHacerReserva;
import CCASolutions.BalandrauAPI.entities.ClientesEntity;
import CCASolutions.BalandrauAPI.entities.HabitacionesEntity;
import CCASolutions.BalandrauAPI.entities.RegimenComidasEntity;
import CCASolutions.BalandrauAPI.entities.ReservasEntity;
import CCASolutions.BalandrauAPI.exceptions.RegimenComidasException;
import CCASolutions.BalandrauAPI.exceptions.ReservasException;
import CCASolutions.BalandrauAPI.services.ClientesService;
import CCASolutions.BalandrauAPI.services.RegimenComidasService;
import CCASolutions.BalandrauAPI.services.ReservasService;
import jakarta.transaction.Transactional;

@Service
public class ReservasServiceImpl implements ReservasService
{
	@Autowired
	private ReservasDAO reservasDao;
	
	@Autowired
	private ClientesDAO clientesDao;
	
	@Autowired
	private RegimenComidasService regimenComidasService;
	
	@Autowired
	private ClientesService clientesService;
	
	public String eliminarReserva(RequestEliminarReserva requestEliminarReserva)
	{
		String resultado = "";
		
		Long usuarioQueQuiereBorrarId = requestEliminarReserva.idUsuarioQueQuiereBorrar();
		Long reservaABorrarId = requestEliminarReserva.idReservaABorrar();
		
		if(this.clientesService.clienteExists(usuarioQueQuiereBorrarId))
		{			
			Optional<ReservasEntity> reservaEnBBDD = this.reservasDao.findById(reservaABorrarId);
			
			if(reservaEnBBDD.isEmpty())
			{
				resultado = new String("No se ha encontrado esa reserva, no se ha podido eliminar.");				
			}
			else
			{
				Long clienteDeLaReservaId = reservaEnBBDD.get().getCliente().getId();
				
				if(clienteDeLaReservaId.equals(usuarioQueQuiereBorrarId))
				{
					try
					{
						this.regimenComidasService.eliminarRegimenesPorReservaId(reservaABorrarId);
						this.reservasDao.deleteById(reservaABorrarId);
						resultado = new String ("Reserva eliminada.");
					}
					catch(Exception e)
					{
						resultado = new String("Error al eliminar la reserva.");	
						System.out.println(e.getMessage());
					}
		
				}
				else
				{
					if(this.clientesDao.isAdmin(usuarioQueQuiereBorrarId))
					{
						this.reservasDao.deleteById(reservaABorrarId);
						resultado = new String ("Reserva eliminada.");
					}
					else
					{
						resultado = new String ("No tiene permisos para eliminar esta reserva.");
					}
				}
			}
		}
		else
		{
			resultado = new String ("No existe este cliente.");	
		}
		return resultado;
	}
	
	@Transactional
	public String guardarNuevaReserva(RequestHacerReserva requestHacerReserva)
	{
		String resultado = "";
			
		try
		{
			Long reservaGuardadaId = guardarReserva(requestHacerReserva);
				
			if (!requestHacerReserva.comidas().isEmpty()) 
			{
				guardarRegimen(reservaGuardadaId, requestHacerReserva.comidas(), requestHacerReserva.alergias());
			}
				
			resultado = "Reserva guardada con éxito.";
		}
		catch (ReservasException e)
		{
			resultado = "Error al guardar la reserva.";
			System.out.println(e.getMessage());
		}
		catch (RegimenComidasException e)
		{
			resultado = "Error al guardar el régimen.";
			System.out.println(e.getMessage());
		}
		catch(Exception e)
		{		
			resultado = "Error general.";
			System.out.println(e.getMessage());
		}		
		
		return resultado;
	}
	
	
	private Long guardarReserva(RequestHacerReserva requestReserva)
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
	
	private void guardarRegimen(Long reservaGuardadaId, List<RegimenComidasEntity> comidas, String alergias)
	{
		for (int i = 0; i<comidas.size(); i++)
		{
			try
			{
				this.regimenComidasService.guardarNuevoRegimen(comidas.get(i), reservaGuardadaId, alergias);
			}
			catch (Exception e)
			{
				System.out.println("Error en el régimen: " + e);
				throw new RegimenComidasException("Error en el régimen", e);
			}
		}
	}
	
	
}
