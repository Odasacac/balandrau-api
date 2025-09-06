package CCASolutions.BalandrauAPI.services;

import java.util.List;

import CCASolutions.BalandrauAPI.dtos.GetRegimenDeComidasDTO;
import CCASolutions.BalandrauAPI.entities.RegimenComidasEntity;

public interface RegimenComidasService 
{
	public abstract void guardarNuevoRegimen(RegimenComidasEntity regimen, Long reservaId, String alergias);
	
	public abstract void eliminarRegimenesPorReservaId(Long reservaId);
	
	public abstract List<GetRegimenDeComidasDTO> getRegimenesPorReservaId(Long reservaId);
}
