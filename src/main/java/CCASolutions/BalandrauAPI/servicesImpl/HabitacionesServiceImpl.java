package CCASolutions.BalandrauAPI.servicesImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CCASolutions.BalandrauAPI.dao.DatosDAO;
import CCASolutions.BalandrauAPI.dao.HabitacionesDAO;
import CCASolutions.BalandrauAPI.dtos.HabitacionesDTO;
import CCASolutions.BalandrauAPI.dtos.RequestHabitacion;
import CCASolutions.BalandrauAPI.dtos.RequestHabitacionesDTO;
import CCASolutions.BalandrauAPI.entities.HabitacionesEntity;
import CCASolutions.BalandrauAPI.interfaces.IHabitacionComunitaria;
import CCASolutions.BalandrauAPI.services.HabitacionesService;


@Service
public class HabitacionesServiceImpl implements HabitacionesService
{
	@Autowired
	private HabitacionesDAO habitacionesDao;
	
	@Autowired
	private DatosDAO datosDao;
	
	public List<HabitacionesDTO> getHabitacionesDisponiblesPorFechaYHuespedes(RequestHabitacionesDTO requestHabitaciones)
	{
		LocalDate fechaEntrada = requestHabitaciones.fechaEntrada();
		LocalDate fechaSalida = requestHabitaciones.fechaSalida();						
		int numeroHuespedes = requestHabitaciones.numeroHuespedes();
		boolean esCampista = requestHabitaciones.esCampista();
		
		List<HabitacionesDTO> habitacionesDisponiblesDTO = new ArrayList<HabitacionesDTO>();
		
		List<HabitacionesDTO> habitacionesPrivadasDisponibles = getHabitacionesPrivadasDisponibles(fechaEntrada, fechaSalida, numeroHuespedes, esCampista);
		
		if(!habitacionesPrivadasDisponibles.isEmpty())
		{
			habitacionesDisponiblesDTO.addAll(habitacionesPrivadasDisponibles);
		}		
		
		HabitacionesDTO habitacionComunitariaDisponible = getHabitacionComunitariaCompletaIfDisponible(fechaEntrada, fechaSalida, numeroHuespedes, esCampista);
		
		if(habitacionComunitariaDisponible != null)
		{
			habitacionesDisponiblesDTO.add(habitacionComunitariaDisponible);
		}
		
		return habitacionesDisponiblesDTO;
	}
	
	public boolean habitacionDisponible(RequestHabitacion requestHabitacion)
	{		
		boolean habitacionDisponible = true;		
		
		if(requestHabitacion.habitacionId() == this.habitacionesDao.getHabitacionComunitariaId())
		{
			HabitacionesEntity habitacionComunitaria = getHabitacionComunitariaIfDisponible(requestHabitacion.fechaEntrada(), requestHabitacion.fechaSalida(), requestHabitacion.numeroHuespedes());
			if (habitacionComunitaria == null)
			{
				habitacionDisponible = false;
			}
		}
		else
		{
			Integer habitacion = this.habitacionesDao.habitacionDisponible(requestHabitacion.fechaEntrada(), requestHabitacion.fechaSalida(), requestHabitacion.numeroHuespedes(), requestHabitacion.habitacionId());
			
			if (habitacion == null)
			{
				habitacionDisponible = false;
			}
		}		
		
		return habitacionDisponible;
	}
	
	
	
	private List<HabitacionesDTO> getHabitacionesPrivadasDisponibles(LocalDate fechaEntrada, LocalDate fechaSalida, int numeroHuespedes, boolean esCampista)
	{
		
		List<HabitacionesDTO> habitacionesPrivadasDisponiblesDTO = new ArrayList<HabitacionesDTO>();
		long noches = ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
		
		List<HabitacionesEntity> habitacionesPrivadasDisponibles = this.habitacionesDao.getHabitacionesPrivadasDisponiblesPorFechaYHuespedes(fechaEntrada, fechaSalida, numeroHuespedes);
		
		for (int i = 0; i < habitacionesPrivadasDisponibles.size(); i++)
		{
			HabitacionesEntity habitacion = habitacionesPrivadasDisponibles.get(i);

			String nombre = habitacion.getNombre();
			String descripcion = habitacion.getDescripcion();
			BigDecimal precioPorNoche = habitacion.getPrecioPorNoche();
			Long habitacionId = habitacion.getId();
		    
			BigDecimal precioTotal = precioPorNoche.multiply(BigDecimal.valueOf(noches));
			
			precioTotal = aplicarDescuentoCampista(precioTotal, esCampista);		    
						
			HabitacionesDTO habitacionDTO = new HabitacionesDTO(nombre, descripcion, precioTotal, habitacionId);
			
			habitacionesPrivadasDisponiblesDTO.add(habitacionDTO); 		
		}
		
		return habitacionesPrivadasDisponiblesDTO;
	}
	
	private HabitacionesDTO getHabitacionComunitariaCompletaIfDisponible(LocalDate fechaEntrada, LocalDate fechaSalida, int numeroHuespedes, boolean esCampista)
	{
		HabitacionesDTO habitacionComunitariaCompleta;
		
		HabitacionesEntity habitacionComunitaria = getHabitacionComunitariaIfDisponible(fechaEntrada, fechaSalida, numeroHuespedes);
		
		if(habitacionComunitaria != null)
		{
			String nombre = habitacionComunitaria.getNombre();
			String descripcion = habitacionComunitaria.getDescripcion();
			BigDecimal precioPorNoche = habitacionComunitaria.getPrecioPorNoche();
			
			long noches = ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
			BigDecimal precioTotal = precioPorNoche.multiply(BigDecimal.valueOf(noches));
			
			precioTotal = aplicarDescuentoCampista(precioTotal, esCampista);
	
			Long habitacionComunitariaId = habitacionComunitaria.getId();
			
			habitacionComunitariaCompleta = new HabitacionesDTO(nombre, descripcion, precioTotal, habitacionComunitariaId);			
				
		}
		else
		{
			habitacionComunitariaCompleta = null;
		}
		
		return habitacionComunitariaCompleta;
		
	}
	
	private HabitacionesEntity getHabitacionComunitariaIfDisponible(LocalDate fechaEntrada, LocalDate fechaSalida, int numeroNuevosHuespedes)
	{	
		HabitacionesEntity habitacionComunitaria = new HabitacionesEntity();
		boolean disponibleHabitacionComunitaria = true;		
		Map<LocalDate, Integer> ocupacionPorDia = new HashMap<>();
		List<IHabitacionComunitaria> habitacionComunitariaPorFecha = this.habitacionesDao.getHabitacionComunitariaPorFecha(fechaEntrada, fechaSalida);
		
		for (int j=0; j<habitacionComunitariaPorFecha.size() && disponibleHabitacionComunitaria; j++)
		{			
			IHabitacionComunitaria reserva = habitacionComunitariaPorFecha.get(j);
			
			LocalDate inicio = reserva.getFechaEntrada();
			LocalDate fin = reserva.getFechaSalida();
			int numeroHuespedesReserva = reserva.getNumeroHuespedes();
			int numeroMaximoHuespedes = reserva.getNumeroMaximoDeHuespedes();
			
			for (LocalDate d = inicio; !d.isAfter(fin.minusDays(1)) && disponibleHabitacionComunitaria; d = d.plusDays(1)) 
			{
				int ocupacionActual = ocupacionPorDia.getOrDefault(d, 0);

				if (ocupacionActual + numeroHuespedesReserva + numeroNuevosHuespedes > numeroMaximoHuespedes) 
				{
					disponibleHabitacionComunitaria = false;
				}
				else
				{
					ocupacionPorDia.put(d, ocupacionActual + numeroHuespedesReserva);
				}		
			}
		}
		
		if (disponibleHabitacionComunitaria && !habitacionComunitariaPorFecha.isEmpty())
		{
			habitacionComunitaria.setNombre(habitacionComunitariaPorFecha.get(0).getNombre());
			habitacionComunitaria.setDescripcion(habitacionComunitariaPorFecha.get(0).getDescripcion());
			habitacionComunitaria.setPrecioPorNoche(habitacionComunitariaPorFecha.get(0).getPrecioPorNoche());
			habitacionComunitaria.setId(habitacionComunitariaPorFecha.get(0).getId());
		}
		else
		{
			habitacionComunitaria = null;
		}
		
		return habitacionComunitaria;
	}
	

	private BigDecimal aplicarDescuentoCampista(BigDecimal precio, boolean esCampista) 
	{
		BigDecimal descuento = precio;
		
		if (esCampista)
		{
			int descuentoInt = this.datosDao.getPorCientoDeDescuentoPorCampista();
			descuento = precio.multiply(BigDecimal.valueOf(1 - descuentoInt / 100.0)).setScale(2, RoundingMode.HALF_UP);
		}
		
		return descuento;
	}

	

}
