package CCASolutions.BalandrauAPI.dtos;

import java.time.LocalDate;

public record RequestHabitacionesDTO (LocalDate fechaEntrada, LocalDate fechaSalida, int numeroHuespedes) {}
