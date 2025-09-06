package CCASolutions.BalandrauAPI.services;

import java.util.List;

import CCASolutions.BalandrauAPI.dtos.GetReservasDTO;
import CCASolutions.BalandrauAPI.dtos.RequestEliminarReserva;
import CCASolutions.BalandrauAPI.dtos.RequestHacerReserva;
import CCASolutions.BalandrauAPI.dtos.RequestModificarReserva;
import CCASolutions.BalandrauAPI.entities.ReservasEntity;

public interface ReservasService 
{
	public abstract String guardarNuevaReserva(RequestHacerReserva requestReserva);
	public abstract String eliminarReserva(RequestEliminarReserva requestReserva);
	public abstract String modificarReserva(ReservasEntity reservaAModificar, RequestModificarReserva requestReserva);
	public abstract List<GetReservasDTO> getReservasByClienteId(Long clienteId);
}
