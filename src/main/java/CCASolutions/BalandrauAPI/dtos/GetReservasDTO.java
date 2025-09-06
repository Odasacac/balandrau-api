package CCASolutions.BalandrauAPI.dtos;

import java.time.LocalDate;

public record GetReservasDTO
(
	Long id,
	String comentarios,
	LocalDate fechaEntrada,
	LocalDate fechaSalida,
	Boolean hayComidas,
	Integer numeroDeHuespedes,
	Long habitacionId,
	String nombreHabitacion
) 
{
	
	
}
