package CCASolutions.BalandrauAPI.servicesImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CCASolutions.BalandrauAPI.dao.DatosDAO;
import CCASolutions.BalandrauAPI.dao.HabitacionesDAO;
import CCASolutions.BalandrauAPI.dtos.HabitacionesDTO;
import CCASolutions.BalandrauAPI.dtos.RequestHabitacion;
import CCASolutions.BalandrauAPI.dtos.RequestHabitacionesDTO;
import CCASolutions.BalandrauAPI.entities.HabitacionesEntity;
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
		Long habitacionComunitariaId = this.habitacionesDao.getHabitacionComunitariaId();
		
		if(requestHabitacion.habitacionId() == habitacionComunitariaId)
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
		
		List<Object[]> reservasHabitacionComunitariaPorFecha = this.habitacionesDao.getHabitacionComunitariaPorFecha(fechaEntrada, fechaSalida);
		
		if (!reservasHabitacionComunitariaPorFecha.isEmpty())
		{
			for (int j=0; j<reservasHabitacionComunitariaPorFecha.size() && disponibleHabitacionComunitaria; j++)
			{			
				Object[] reserva = reservasHabitacionComunitariaPorFecha.get(j);
				
				LocalDate inicio = (LocalDate) reserva[6];
				LocalDate fin = (LocalDate) reserva[7];
				Integer numeroHuespedesReserva = (Integer) reserva[4];
				Integer numeroMaximoHuespedes = (Integer) reserva[5];
				
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
			
			if (disponibleHabitacionComunitaria && !reservasHabitacionComunitariaPorFecha.isEmpty())
			{
				Object[] primeraReserva = reservasHabitacionComunitariaPorFecha.get(0);
				
				habitacionComunitaria.setNombre((String) primeraReserva[1]);
				habitacionComunitaria.setDescripcion((String) primeraReserva[2]);
				habitacionComunitaria.setPrecioPorNoche((BigDecimal) primeraReserva[3]);
				habitacionComunitaria.setId((Long) primeraReserva[0]);
			}
			else
			{
				habitacionComunitaria = null;
			}
		}
		else
		{
			Optional <HabitacionesEntity> habitacionComunitariaoptional =this.habitacionesDao.findById(this.habitacionesDao.getHabitacionComunitariaId());
			
			if(habitacionComunitariaoptional.isPresent())
			{
				habitacionComunitaria=habitacionComunitariaoptional.get();
			}
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
