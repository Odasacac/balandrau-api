package CCASolutions.BalandrauAPI.services;

public interface ClientesService 
{
	public abstract String encriptarPassword(String password);
	
	public abstract boolean verificarPassword(String password, String hashAlmacenado);
}
