package CCASolutions.BalandrauAPI.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import CCASolutions.BalandrauAPI.entities.HabitacionesEntity;



public interface HabitacionesDAO extends JpaRepository <HabitacionesEntity, Long>
{
	@Query("SELECT h FROM HabitacionesEntity h LEFT JOIN ReservasEntity r ON r.habitacion = h AND r.fechaEntrada < :fechaSalida AND r.fechaSalida > :fechaEntrada WHERE h.numeroMaximoDeHuespedes >= :numeroHuespedes AND h.id != 1 AND r.id IS NULL")
	List<HabitacionesEntity> getHabitacionesPrivadasDisponiblesPorFechaYHuespedes(@Param("fechaEntrada") LocalDate fechaEntrada, @Param("fechaSalida") LocalDate fechaSalida, @Param("numeroHuespedes") int numeroHuespedes);

	@Query("SELECT h.id, h.nombre, h.descripcion, h.precioPorNoche, r.numeroHuespedes, h.numeroMaximoDeHuespedes, r.fechaEntrada, r.fechaSalida FROM ReservasEntity r JOIN r.habitacion h WHERE h.id = 1 AND r.fechaEntrada < :fechaSalida AND r.fechaSalida > :fechaEntrada")
	public abstract List<Object[]> getHabitacionComunitariaPorFecha(@Param("fechaEntrada") LocalDate fechaEntrada, @Param("fechaSalida") LocalDate fechaSalida);

	@Query("SELECT h.numeroDeHabitacion FROM HabitacionesEntity h LEFT JOIN ReservasEntity r ON r.habitacion = h AND r.fechaEntrada < :fechaSalida AND r.fechaSalida > :fechaEntrada WHERE h.numeroMaximoDeHuespedes >= :numeroHuespedes AND h.id = :habitacionId AND r.id IS NULL")
	Integer habitacionDisponible(@Param("fechaEntrada") LocalDate fechaEntrada, @Param("fechaSalida") LocalDate fechaSalida, @Param("numeroHuespedes") int numeroHuespedes,	@Param("habitacionId") long habitacionId);
	
	@Query("SELECT h.id FROM HabitacionesEntity h WHERE h.tipoHabitacion = 1")
	public abstract Long getHabitacionComunitariaId ();
}
