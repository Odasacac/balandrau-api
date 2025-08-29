package CCASolutions.BalandrauAPI.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import CCASolutions.BalandrauAPI.entities.Habitaciones;

public interface HabitacionesDAO extends JpaRepository <Habitaciones, Long>
{
	@Query("SELECT h FROM Habitaciones h WHERE :numeroHuespedes <= h.numeroMaximoDeHuespedes AND h.id NOT IN (SELECT r.habitacion.id FROM Reservas r WHERE r.fechaEntrada < :fechaSalida AND r.fechaSalida > :fechaEntrada)")
	public abstract List<Habitaciones> getHabitacionesDisponiblesPorFechaYHuespedes(@Param("fechaEntrada") LocalDate fechaEntrada, @Param("fechaSalida") LocalDate fechaSalida, @Param ("numeroHuespedes") int numeroHuespedes);
}
