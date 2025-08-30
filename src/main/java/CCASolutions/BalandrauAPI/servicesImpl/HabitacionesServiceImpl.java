package CCASolutions.BalandrauAPI.servicesImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CCASolutions.BalandrauAPI.dao.DatosDAO;
import CCASolutions.BalandrauAPI.dao.HabitacionesDAO;
import CCASolutions.BalandrauAPI.dtos.HabitacionesDTO;
import CCASolutions.BalandrauAPI.dtos.RequestHabitacion;
import CCASolutions.BalandrauAPI.dtos.RequestHabitacionesDTO;
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
		
		List<Object[]> habitacionesPrivadasDisponibles = this.habitacionesDao.getHabitacionesPrivadasDisponiblesPorFechaYHuespedes(fechaEntrada, fechaSalida, requestHabitaciones.numeroHuespedes());
		
		List<Object[]> habitacionComunitariaPorFecha = this.habitacionesDao.getHabitacionComunitariaPorFecha(fechaEntrada, fechaSalida);
		
		long noches = ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
		
		List<HabitacionesDTO> habitacionesDisponiblesDTO = new ArrayList<HabitacionesDTO>();
		
		
		
		for (int i = 0; i < habitacionesPrivadasDisponibles.size(); i++)
		{
			Object[] habitacion = habitacionesPrivadasDisponibles.get(i);

			String nombre = (String) habitacion[0];
			String descripcion = (String) habitacion[1];
			BigDecimal precioPorNoche = (BigDecimal) habitacion[2];
			Long habitacionId = (Long) habitacion[3];
		    
			BigDecimal precioTotal = precioPorNoche.multiply(BigDecimal.valueOf(noches));
			
			if(requestHabitaciones.esCampista())
			{
				int descuentoInt = this.datosDao.getPorCientoDeDescuentoPorCampista();
				
				BigDecimal descuento = new BigDecimal(descuentoInt).divide(new BigDecimal("100"));

				precioTotal = precioTotal.subtract(precioTotal.multiply(descuento)).setScale(2, RoundingMode.HALF_UP);
			}
		    
			HabitacionesDTO habitacionDTO = new HabitacionesDTO(nombre, descripcion, precioTotal, habitacionId);
			
			habitacionesDisponiblesDTO.add(habitacionDTO); 		
		}
		
		boolean disponibleHabitacionComunitaria = true;
		
		for (int j=0; j<habitacionComunitariaPorFecha.size() && disponibleHabitacionComunitaria; j++)
		{
			Object[] registro = habitacionComunitariaPorFecha.get(j);
			int numeroPersonasEsteDiaEnLaHabitacion = ((Number) registro[3]).intValue();
			int numeroNuevosHuespedes = requestHabitaciones.numeroHuespedes();
			int numeroMaximoHuespedesHabitacionComunitaria = ((Number) registro[4]).intValue();
			
			if(numeroPersonasEsteDiaEnLaHabitacion + numeroNuevosHuespedes > numeroMaximoHuespedesHabitacionComunitaria)
			{
				disponibleHabitacionComunitaria = false;
			}
		}
		
		
		if(disponibleHabitacionComunitaria)
		{
			String nombre = (String) habitacionComunitariaPorFecha.get(0)[0];
			String descripcion = (String) habitacionComunitariaPorFecha.get(0)[1];
			BigDecimal precioPorNoche = (BigDecimal) habitacionComunitariaPorFecha.get(0)[2];
			
			BigDecimal precioTotal = precioPorNoche.multiply(BigDecimal.valueOf(noches));
			
			if(requestHabitaciones.esCampista())
			{
				int descuentoInt = this.datosDao.getPorCientoDeDescuentoPorCampista();
				
				BigDecimal descuento = new BigDecimal(descuentoInt).divide(new BigDecimal("100"));

				precioTotal = precioTotal.subtract(precioTotal.multiply(descuento)).setScale(2, RoundingMode.HALF_UP);
			}
		    
			
			Integer habitacionIdInt = (Integer) habitacionComunitariaPorFecha.get(0)[3];
			Long habitacionId = habitacionIdInt.longValue();
			
			HabitacionesDTO habitacionDTO = new HabitacionesDTO(nombre, descripcion, precioTotal, habitacionId);			
			habitacionesDisponiblesDTO.add(habitacionDTO); 	
		}
		
		
		return habitacionesDisponiblesDTO;
	}
	
	public boolean habitacionDisponible(RequestHabitacion requestHabitacion)
	{		
		boolean habitacionDisponible = true;
		
		if(requestHabitacion.habitacionId() == 1)
		{
			List<Object[]> habitacionComunitariaPorFecha = this.habitacionesDao.getHabitacionComunitariaPorFecha(requestHabitacion.fechaEntrada(), requestHabitacion.fechaSalida());
			
			for (int j=0; j<habitacionComunitariaPorFecha.size() && habitacionDisponible; j++)
			{
				Object[] registro = habitacionComunitariaPorFecha.get(j);
				int numeroPersonasEsteDiaEnLaHabitacion = ((Number) registro[3]).intValue();
				int numeroNuevosHuespedes = requestHabitacion.numeroHuespedes();
				int numeroMaximoHuespedesHabitacionComunitaria = ((Number) registro[4]).intValue();
				
				if (numeroPersonasEsteDiaEnLaHabitacion + numeroNuevosHuespedes > numeroMaximoHuespedesHabitacionComunitaria)
				{
					habitacionDisponible = false;
				}
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

}
