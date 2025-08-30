package CCASolutions.BalandrauAPI.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import CCASolutions.BalandrauAPI.entities.RegimenComidasEntity;

public record RequestReservaDTO(Long clienteId, LocalDate fechaEntrada, LocalDate fechaSalida, int numeroHuespedes, BigDecimal precioTotal, long habitacionId, String comentarios, String alergias, List<RegimenComidasEntity> comidas) {}
