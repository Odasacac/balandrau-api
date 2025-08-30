package CCASolutions.BalandrauAPI.services;

import CCASolutions.BalandrauAPI.entities.RegimenComidasEntity;

public interface RegimenComidasService 
{
	public abstract void guardarNuevoRegimen(RegimenComidasEntity regimen, Long reservaId, String alergias);
}
