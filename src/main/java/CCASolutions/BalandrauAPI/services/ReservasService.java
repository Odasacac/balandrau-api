package CCASolutions.BalandrauAPI.services;

import CCASolutions.BalandrauAPI.dtos.RequestReservaDTO;

public interface ReservasService 
{
	public abstract String guardarNuevaReserva(RequestReservaDTO requestReserva);
}
