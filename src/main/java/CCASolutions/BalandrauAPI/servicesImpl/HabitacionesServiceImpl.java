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
		
		List<Object[]> habitacionesDisponibles = this.habitacionesDao.getHabitacionesDisponiblesPorFechaYHuespedes(fechaEntrada, fechaSalida, requestHabitaciones.numeroHuespedes());
		
		long noches = ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
		
		List<HabitacionesDTO> habitacionesDisponiblesDTO = new ArrayList<HabitacionesDTO>();
		
		for (int i = 0; i < habitacionesDisponibles.size(); i++)
		{
			Object[] habitacion = habitacionesDisponibles.get(i);

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
		
		return habitacionesDisponiblesDTO;
	}
	
	public boolean habitacionDisponible(RequestHabitacion requestHabitacion)
	{		
		boolean habitacionDisponible = false;
		
		Integer habitacion = this.habitacionesDao.habitacionDisponible(requestHabitacion.fechaEntrada(), requestHabitacion.fechaSalida(), requestHabitacion.numeroHuespedes(), requestHabitacion.habitacionId());
		
		if (habitacion != null)
		{
			habitacionDisponible = true;
		}
		
		return habitacionDisponible;
	}

}
