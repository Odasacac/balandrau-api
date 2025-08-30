package CCASolutions.BalandrauAPI.exceptions;

public class ReservasException extends RuntimeException
{

	private static final long serialVersionUID = -2366613683416494420L;

	public ReservasException(String mensaje) 
	{
		super(mensaje);
	}

	public ReservasException(String mensaje, Throwable causa) 
	{
		super(mensaje, causa);
	}
}
