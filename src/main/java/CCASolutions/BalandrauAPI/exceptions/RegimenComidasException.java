package CCASolutions.BalandrauAPI.exceptions;

public class RegimenComidasException extends RuntimeException
{

	private static final long serialVersionUID = 3469856988996460393L;

	public RegimenComidasException(String mensaje) 
    {
		super(mensaje);
    }

	public RegimenComidasException(String mensaje, Throwable causa) 
	{
		super(mensaje, causa);
	}
}
