package CCASolutions.BalandrauAPI.servicesImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CCASolutions.BalandrauAPI.dao.HabitacionesDAO;
import CCASolutions.BalandrauAPI.dtos.HabitacionesDTO;
import CCASolutions.BalandrauAPI.dtos.RequestHabitacionesDTO;
import CCASolutions.BalandrauAPI.entities.Habitaciones;
import CCASolutions.BalandrauAPI.services.HabitacionesService;


@Service
public class HabitacionesServiceImpl implements HabitacionesService
{
	@Autowired
	private HabitacionesDAO habitacionesDao;
	
	public List<HabitacionesDTO> getHabitacionesDisponiblesPorFechaYHuespedes(RequestHabitacionesDTO requestHabitaciones)
	{
		LocalDate fechaEntrada = requestHabitaciones.fechaEntrada();
		LocalDate fechaSalida = requestHabitaciones.fechaSalida();
		
		List<Habitaciones> habitacionesDisponibles = this.habitacionesDao.getHabitacionesDisponiblesPorFechaYHuespedes(fechaEntrada, fechaSalida, requestHabitaciones.numeroHuespedes());
		
		long noches = ChronoUnit.DAYS.between(fechaEntrada, fechaSalida);
		
		List<HabitacionesDTO> habitacionesDisponiblesDTO = new ArrayList<HabitacionesDTO>();
		
		for (int i = 0; i < habitacionesDisponibles.size(); i++)
		{
			Habitaciones habitacion = habitacionesDisponibles.get(i);
			BigDecimal precioTotal = habitacion.getPrecioPorNoche().multiply(BigDecimal.valueOf(noches));
			
			HabitacionesDTO habitacionDTO = new HabitacionesDTO(habitacion.getNombre(), habitacion.getDescripcion(), precioTotal);
			
			habitacionesDisponiblesDTO.add(habitacionDTO);			
		}
		
		return habitacionesDisponiblesDTO;
	}

}
