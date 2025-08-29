package CCASolutions.BalandrauAPI.servicesImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import CCASolutions.BalandrauAPI.dtos.HabitacionesDTO;
import CCASolutions.BalandrauAPI.dtos.RequestReservaDTO;
import CCASolutions.BalandrauAPI.services.ReservasService;

@Service
public class ReservasServiceImpl implements ReservasService
{
	
	public List<HabitacionesDTO> getHabitacionesPorFechaYHuespedes(RequestReservaDTO requestReserva)
	{
		List<HabitacionesDTO> habitacionesDisponibles = new ArrayList<HabitacionesDTO>();
		
		return habitacionesDisponibles;
	}
}
