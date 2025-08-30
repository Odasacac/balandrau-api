package CCASolutions.BalandrauAPI.services;

import java.util.List;

import CCASolutions.BalandrauAPI.dtos.DatosEntityDTO;

public interface DatosService 
{
	public abstract List<DatosEntityDTO> getPreciosRegimen();
}
