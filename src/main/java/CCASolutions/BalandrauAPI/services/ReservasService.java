package CCASolutions.BalandrauAPI.services;

import java.util.List;

import CCASolutions.BalandrauAPI.dtos.HabitacionesDTO;
import CCASolutions.BalandrauAPI.dtos.RequestReservaDTO;

public interface ReservasService 
{
	public abstract List<HabitacionesDTO> getHabitacionesPorFechaYHuespedes(RequestReservaDTO requestReserva);
}
