package CCASolutions.BalandrauAPI.services;

import CCASolutions.BalandrauAPI.dtos.RequestEliminarReserva;
import CCASolutions.BalandrauAPI.dtos.RequestHacerReserva;

public interface ReservasService 
{
	public abstract String guardarNuevaReserva(RequestHacerReserva requestReserva);
	public abstract String eliminarReserva(RequestEliminarReserva requestReserva);
}
