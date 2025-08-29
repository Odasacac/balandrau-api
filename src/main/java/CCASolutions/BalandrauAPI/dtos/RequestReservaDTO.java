package CCASolutions.BalandrauAPI.dtos;

import java.time.LocalDate;

public record RequestReservaDTO(LocalDate fechaEntrada, LocalDate fechaSalida, int numeroHuespedes) {}
