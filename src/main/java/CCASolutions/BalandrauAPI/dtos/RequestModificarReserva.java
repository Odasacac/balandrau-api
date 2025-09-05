package CCASolutions.BalandrauAPI.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import CCASolutions.BalandrauAPI.entities.RegimenComidasEntity;

public record RequestModificarReserva(Long clienteId, Long reservaId, LocalDate fechaEntrada, LocalDate fechaSalida, String comentarios, Integer numeroHuespedes, Long habitacionId, BigDecimal precioTotal, List<RegimenComidasEntity> comidas, String alergias) {}
