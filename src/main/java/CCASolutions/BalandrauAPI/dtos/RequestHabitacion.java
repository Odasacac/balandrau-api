package CCASolutions.BalandrauAPI.dtos;

import java.time.LocalDate;

public record RequestHabitacion(LocalDate fechaEntrada, LocalDate fechaSalida, int numeroHuespedes, long habitacionId) {}
