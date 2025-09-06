package CCASolutions.BalandrauAPI.dtos;

import java.time.LocalDate;

public record GetRegimenDeComidasDTO
(
		Long id,
		String alergias,
		Boolean desayuno,
		Boolean almuerzo,
		Boolean cena,
		LocalDate fecha,
		Boolean picnic	
)
{

}
