package CCASolutions.BalandrauAPI.services;

import java.time.LocalDate;
import java.util.List;
import CCASolutions.BalandrauAPI.dtos.HabitacionesDTO;
import CCASolutions.BalandrauAPI.dtos.RequestHabitacion;
import CCASolutions.BalandrauAPI.dtos.RequestHabitacionesDTO;
import CCASolutions.BalandrauAPI.entities.HabitacionesEntity;


public interface HabitacionesService 
{
	public abstract List<HabitacionesDTO> getHabitacionesDisponiblesPorFechaYHuespedes(RequestHabitacionesDTO requestHabitaciones);
	public abstract boolean habitacionDisponible(RequestHabitacion requestHabitacion);
	public abstract HabitacionesEntity getHabitacionComunitariaIfDisponible(LocalDate fechaEntrada, LocalDate fechaSalida, int numeroNuevosHuespedes, boolean paraModificar, Long reservaId);
}
