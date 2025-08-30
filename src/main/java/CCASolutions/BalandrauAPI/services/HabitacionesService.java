package CCASolutions.BalandrauAPI.services;

import java.util.List;
import CCASolutions.BalandrauAPI.dtos.HabitacionesDTO;
import CCASolutions.BalandrauAPI.dtos.RequestHabitacion;
import CCASolutions.BalandrauAPI.dtos.RequestHabitacionesDTO;


public interface HabitacionesService 
{
	public abstract List<HabitacionesDTO> getHabitacionesDisponiblesPorFechaYHuespedes(RequestHabitacionesDTO requestHabitaciones);
	public abstract boolean habitacionDisponible(RequestHabitacion requestHabitacion);
}
