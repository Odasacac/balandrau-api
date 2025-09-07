package CCASolutions.BalandrauAPI.servicesImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import CCASolutions.BalandrauAPI.dao.ClientesDAO;
import CCASolutions.BalandrauAPI.dao.HabitacionesDAO;
import CCASolutions.BalandrauAPI.dao.ReservasDAO;
import CCASolutions.BalandrauAPI.dtos.GetReservasDTO;
import CCASolutions.BalandrauAPI.dtos.RequestEliminarReserva;
import CCASolutions.BalandrauAPI.dtos.RequestHacerReserva;
import CCASolutions.BalandrauAPI.dtos.RequestModificarReserva;
import CCASolutions.BalandrauAPI.entities.ClientesEntity;
import CCASolutions.BalandrauAPI.entities.HabitacionesEntity;
import CCASolutions.BalandrauAPI.entities.RegimenComidasEntity;
import CCASolutions.BalandrauAPI.entities.ReservasEntity;
import CCASolutions.BalandrauAPI.exceptions.RegimenComidasException;
import CCASolutions.BalandrauAPI.exceptions.ReservasException;
import CCASolutions.BalandrauAPI.services.ClientesService;
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
	private ClientesDAO clientesDao;
	
	@Autowired
	private HabitacionesDAO habitacionesDao;
	
	@Autowired
	private RegimenComidasService regimenComidasService;
	
	@Autowired
	private ClientesService clientesService;
	
	@Autowired
	private HabitacionesService habitacionesService;
	
	@Value("${tipoHabitacionComunitaria}") 
	private int tipoHabitacionComunitaria;
	
	
	public List<GetReservasDTO> getReservasByClienteId (Long clienteId)
	{
		List<GetReservasDTO> respuesta = new ArrayList<>();
		
		List<ReservasEntity> reservas = new ArrayList<>();
		
		if(this.clientesDao.isAdmin(clienteId))
		{
			reservas = this.reservasDao.findAll();
		}
		else
		{
			reservas = this.reservasDao.findByCliente_Id(clienteId);
		}
		
		if (!reservas.isEmpty())
		{
			for (int i = 0; i<reservas.size(); i++)
			{
				ReservasEntity reservaConcreta = reservas.get(i);
				
				Long id = reservaConcreta.getId();
				String comentarios = reservaConcreta.getComentarios();
				LocalDate fechaEntrada = reservaConcreta.getFechaEntrada();
				LocalDate fechaSalida = reservaConcreta.getFechaSalida();
				Boolean hayComidas = reservaConcreta.isHayComidas();
				Integer numeroDeHuespedes = reservaConcreta.getNumeroHuespedes();
				Long habitacionId = reservaConcreta.getHabitacion().getId();
				
				Optional<HabitacionesEntity> habitacionDeLaReserva = this.habitacionesDao.findById(habitacionId);
				String nombreHabitacion = "";
				if(habitacionDeLaReserva.isPresent())
				{
					nombreHabitacion = habitacionDeLaReserva.get().getNombre();
				}
				
				GetReservasDTO reservaDTO = new GetReservasDTO(id, comentarios, fechaEntrada, fechaSalida, hayComidas, numeroDeHuespedes, habitacionId, nombreHabitacion);
				respuesta.add(reservaDTO);
			}
			
		}
		
		
		return respuesta;
	}
	
	@Transactional
	public String modificarReserva(ReservasEntity reservaAModificar, RequestModificarReserva requestReserva)
	{
		String resultado = "";
		
		if(this.esPosibleModificarLaReserva(requestReserva, reservaAModificar))
		{	
		
			reservaAModificar.setComentarios(requestReserva.comentarios());
			reservaAModificar.setFechaEntrada(requestReserva.fechaEntrada());
			reservaAModificar.setFechaSalida(requestReserva.fechaSalida());
			reservaAModificar.setNumeroHuespedes(requestReserva.numeroHuespedes());
			reservaAModificar.setPrecioTotal(requestReserva.precioTotal());
			reservaAModificar.setFechaUltimaModificacion(LocalDate.now());
			reservaAModificar.setIdClienteUltimaModificacion(requestReserva.clienteId());
			
			HabitacionesEntity habitacion = new HabitacionesEntity();
			habitacion.setId(requestReserva.habitacionId());
			reservaAModificar.setHabitacion(habitacion);			
			
			
			if(requestReserva.comidas() == null || requestReserva.comidas().isEmpty())
			{
				reservaAModificar.setHayComidas(false);
			}
			else
			{
				reservaAModificar.setHayComidas(true);
			}
			

			this.reservasDao.save(reservaAModificar);
			
			this.regimenComidasService.eliminarRegimenesPorReservaId(requestReserva.reservaId());
			if(reservaAModificar.isHayComidas())
			{
				guardarRegimen(requestReserva.reservaId(), requestReserva.comidas(), requestReserva.alergias());
			}			
			
			
			resultado = new String("Reserva modificada con éxito.");
		}
		else
		{
			resultado = new String("No es posible modificar la reserva.");
		}
		
		return resultado;
	}
	
	
	
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
			LocalDate hoy = LocalDate.now();
			
			reserva.setFechaEntrada(requestReserva.fechaEntrada());
			reserva.setFechaSalida(requestReserva.fechaSalida());
			reserva.setFechaCreacion(hoy);
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
			
			reserva.setIdClienteUltimaModificacion(requestReserva.clienteId());
			reserva.setFechaUltimaModificacion(hoy);

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
			}
		}
	}
	
	private boolean esPosibleModificarLaReserva(RequestModificarReserva requestReserva, ReservasEntity reservaAModificar)
	{
		boolean esPosible = false;
		
		Long habitacionComunitariaId = this.habitacionesDao.getHabitacionComunitariaId(tipoHabitacionComunitaria);
		
		if(reservaAModificar.getHabitacion().getId().equals(habitacionComunitariaId))
		{
			HabitacionesEntity habitacionComunitaria = this.habitacionesService.getHabitacionComunitariaIfDisponible(requestReserva.fechaEntrada(), requestReserva.fechaSalida(), requestReserva.numeroHuespedes(), true, requestReserva.reservaId());
			
			if (habitacionComunitaria != null)
			{
				esPosible = true;
			}
		}
		else
		{
			Integer habitacionId = this.habitacionesDao.habitacionDisponibleParaModificar(requestReserva.fechaEntrada(), requestReserva.fechaSalida(), requestReserva.numeroHuespedes(), requestReserva.habitacionId(), requestReserva.reservaId());
			
			if (habitacionId != null)
			{
				esPosible = true;
			}
		}
		
		return esPosible;
	}
	
	
}
