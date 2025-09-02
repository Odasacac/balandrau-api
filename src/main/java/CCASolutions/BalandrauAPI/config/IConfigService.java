package CCASolutions.BalandrauAPI.config;

public interface IConfigService 
{
	public abstract boolean vaciarTodasLasEntidades();
	
	public abstract boolean cargarDatosIniciales();
	
	public abstract boolean restablecerBaseDeDatos();

}
