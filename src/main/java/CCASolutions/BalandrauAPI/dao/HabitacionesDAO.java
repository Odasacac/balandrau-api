package CCASolutions.BalandrauAPI.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import CCASolutions.BalandrauAPI.entities.HabitacionesEntity;



public interface HabitacionesDAO extends JpaRepository <HabitacionesEntity, Long>
{
	@Query("SELECT h.nombre, h.descripcion, h.precioPorNoche, h.id FROM HabitacionesEntity h WHERE :numeroHuespedes <= h.numeroMaximoDeHuespedes AND h.id NOT IN (1) AND h.id NOT IN (SELECT r.habitacion.id FROM ReservasEntity r WHERE r.fechaEntrada < :fechaSalida AND r.fechaSalida > :fechaEntrada)")
	public abstract List<Object[]> getHabitacionesPrivadasDisponiblesPorFechaYHuespedes(@Param("fechaEntrada") LocalDate fechaEntrada, @Param("fechaSalida") LocalDate fechaSalida, @Param ("numeroHuespedes") int numeroHuespedes);

	@Query("SELECT h.nombre, h.descripcion, h.precioPorNoche, r.numeroHuespedes, h.numeroMaximoDeHuespedes, r.fechaEntrada, r.fechaSalida FROM ReservasEntity r JOIN r.habitacion h WHERE h.id = 1 AND r.fechaEntrada <= :fechaSalida AND r.fechaSalida >= :fechaEntrada")
	public abstract List<Object[]> getHabitacionComunitariaPorFecha(@Param("fechaEntrada") LocalDate fechaEntrada, @Param("fechaSalida") LocalDate fechaSalida);

	@Query("SELECT h.numeroDeHabitacion FROM HabitacionesEntity h WHERE :numeroHuespedes <= h.numeroMaximoDeHuespedes AND h.id = :habitacionId AND h.id NOT IN (SELECT r.habitacion.id FROM ReservasEntity r WHERE r.fechaEntrada < :fechaSalida AND r.fechaSalida > :fechaEntrada)")
	public abstract Integer habitacionDisponible (@Param("fechaEntrada") LocalDate fechaEntrada, @Param("fechaSalida") LocalDate fechaSalida, @Param ("numeroHuespedes") int numeroHuespedes, @Param ("habitacionId") long habitacionId);

}
