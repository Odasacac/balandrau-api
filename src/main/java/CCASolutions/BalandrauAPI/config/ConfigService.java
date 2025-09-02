package CCASolutions.BalandrauAPI.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import CCASolutions.BalandrauAPI.dao.ClientesDAO;
import CCASolutions.BalandrauAPI.dao.DatosDAO;
import CCASolutions.BalandrauAPI.dao.HabitacionesDAO;
import CCASolutions.BalandrauAPI.dao.RegimenComidasDAO;
import CCASolutions.BalandrauAPI.dao.ReservasDAO;
import CCASolutions.BalandrauAPI.entities.ClientesEntity;
import CCASolutions.BalandrauAPI.entities.DatosEntity;
import CCASolutions.BalandrauAPI.entities.HabitacionesEntity;
import CCASolutions.BalandrauAPI.services.ClientesService;

@Service
public class ConfigService implements IConfigService, CommandLineRunner
{
	@Autowired
	private DatosDAO datosDao;
	
	@Autowired
	private HabitacionesDAO habitacionesDao;
	
	@Autowired
	private RegimenComidasDAO regimenComidasDao;
	
	@Autowired
	private ReservasDAO reservasDao;
	
	@Autowired
	private ClientesDAO clientesDao;
	
	@Autowired
	private ClientesService clientesService;
	
	@Value("${precioDesayunoString}") 
	private String precioDesayunoString;
	
	@Value("${precioComidaString}") 
	private String precioComidaString;
	
	@Value("${precioCenaString}") 
	private String precioCenaString;
	
	@Value("${descuentoPicnicString}") 
	private String descuentoPicnicString;
	
	@Value("${descuentoCampistaString}") 
	private String descuentoCampistaString;
	
	@Value("${permisoAdminString}") 
	private String permisoAdminString;	

	@Override
	public void run(String... args) throws Exception 
	{		
		cargarDatosIniciales();
	}
	
	public boolean restablecerBaseDeDatos()
	{
		boolean restablecida = false;
		
		try
		{
			if(vaciarTodasLasEntidades())
			{
				if (cargarDatosIniciales())
				{
					restablecida = true;
				}
			}
		}
		catch (Exception e)
		{
			restablecida = false;
			System.out.println("Error al restablecer la base de datos: " + e.getMessage());
		}

		
		return restablecida;
	}		
	
	public boolean vaciarTodasLasEntidades()
	{
		boolean entidadesVacias = false;		
		
		try
		{
			regimenComidasDao.deleteAll();
			reservasDao.deleteAll();

			habitacionesDao.deleteAll();
			clientesDao.deleteAll();
			
			datosDao.deleteAll();			
		}
		catch (Exception e)
		{
			entidadesVacias = false;
		}
		
		if(hayInfoEnLaBaseDeDatos())
		{
			entidadesVacias=false;
		}
		
		
		return entidadesVacias;
	}
	
	public boolean cargarDatosIniciales()
	{
		boolean datosInicialesCargados = false;
		
		boolean clientesCargados = false;
		boolean habitacionesCargadas = false;
		boolean datosCargados = false;
		
		if(clientesDao.count() == 0)
		{
				List<ClientesEntity> listaClientes = new ArrayList<>();
				
				ClientesEntity cliente1 = crearCliente("admin", "A1", "A2", "admin@balandrau.com", "adminDNI", "12-05-1993", true, true, "1234");			
				listaClientes.add(cliente1);
				
				clientesDao.saveAll(listaClientes);
				clientesCargados=true;
		}
		
		if (habitacionesDao.count() == 0) 
		{
			List<HabitacionesEntity> listaHabitaciones = new ArrayList<>();			
				
			HabitacionesEntity habitacion1 = crearHabitacion("Habitación comunitaria", "Habitacion comunitaria con baño compartido", 101, 8, 15.00, 1);
			listaHabitaciones.add(habitacion1);

			HabitacionesEntity habitacion2 = crearHabitacion("Habitación privada con orientacion norte", "Habitación para cuatro personas con baño incluido", 201, 4, 100.00, 2);
			listaHabitaciones.add(habitacion2);
				
			HabitacionesEntity habitacion3 = crearHabitacion("Habitación privada con orientacion este", "Habitación para cuatro personas con baño incluido", 203, 4, 120.00, 2);
			listaHabitaciones.add(habitacion3);

			HabitacionesEntity habitacion4 = crearHabitacion("Habitación privada con orientacion sur", "Habitación para cuatro personas con baño incluido", 205, 4, 150.00, 2);
			listaHabitaciones.add(habitacion4);
				
			HabitacionesEntity habitacion5 = crearHabitacion("Habitación privada con orientacion oeste", "Habitación para cuatro personas con baño incluido", 207, 4, 130.00, 2);
			listaHabitaciones.add(habitacion5);

			HabitacionesEntity habitacion6 = crearHabitacion("Habitación privada con orientacion noreste", "Habitación para dos personas con baño incluido", 202, 2, 60.00, 3);
			listaHabitaciones.add(habitacion6);

			HabitacionesEntity habitacion7 = crearHabitacion("Habitación privada con orientacion sureste", "Habitación para dos personas con baño incluido", 204, 2, 70.00, 3);
			listaHabitaciones.add(habitacion7);

			HabitacionesEntity habitacion8 = crearHabitacion("Habitación privada con orientacion suroeste", "Habitación para una persona con baño incluido", 206, 1, 35.00, 4);
			listaHabitaciones.add(habitacion8);
				
			HabitacionesEntity habitacion9 = crearHabitacion("Habitación privada con orientacion noroeste", "Habitación para una persona con baño incluido", 208, 1, 30.00, 4);
			listaHabitaciones.add(habitacion9);	
				
			habitacionesDao.saveAll(listaHabitaciones);
			habitacionesCargadas = true;
		}
			
		if (datosDao.count() == 0) 
		{
			List<DatosEntity> listaDatos = new ArrayList<>();
				
			DatosEntity dato1 = crearDato(precioDesayunoString, "7");
			listaDatos.add(dato1);

			DatosEntity dato2 = crearDato(precioComidaString, "10");
			listaDatos.add(dato2);

			DatosEntity dato3 = crearDato(precioCenaString, "8");
			listaDatos.add(dato3);
				
			DatosEntity dato4 = crearDato(descuentoCampistaString, "20");
			listaDatos.add(dato4);

			DatosEntity dato5 = crearDato(descuentoPicnicString, "2");
			listaDatos.add(dato5);
			
			DatosEntity dato6 = crearDato(permisoAdminString, "admin1234");
			listaDatos.add(dato6);	
				
			datosDao.saveAll(listaDatos);
			datosCargados=true;
		}		
		
		if(datosCargados || clientesCargados || habitacionesCargadas)
		{			
			datosInicialesCargados=true;
		}
				
		return datosInicialesCargados;
	}
	
	private boolean hayInfoEnLaBaseDeDatos()
	{
		boolean hayDatos = true;
		
		if (datosDao.count() == 0)
		{
			hayDatos = false;
		}
		else if (habitacionesDao.count() == 0)
		{
			hayDatos = false;
		}
		else if (regimenComidasDao.count() == 0)
		{
			hayDatos = false;
		}
		else if (reservasDao.count() == 0)
		{
			hayDatos = false;
		}
		else if (clientesDao.count()==0)
		{
			hayDatos=false;
		}

		return hayDatos;
	}
	
	private HabitacionesEntity crearHabitacion(String nombre, String descripcion, int numero, int maxHuespedes, double precio, int tipo) 
	{
		HabitacionesEntity habitacion = new HabitacionesEntity();
		habitacion.setNombre(nombre);
		habitacion.setDescripcion(descripcion);
		habitacion.setNumeroDeHabitacion(numero);
		habitacion.setNumeroMaximoDeHuespedes(maxHuespedes);
		habitacion.setPrecioPorNoche(BigDecimal.valueOf(precio));
		habitacion.setTipoHabitacion(tipo);
		return habitacion;
	}

	private ClientesEntity crearCliente(String nombre, String apellido1, String apellido2, String email, String dni, String fechaNacimiento, boolean esCampista, boolean esAdmin, String password) 
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		ClientesEntity cliente = new ClientesEntity();
		cliente.setNombre(nombre);
		cliente.setApellido1(apellido1);
		cliente.setApellido2(apellido2);
		cliente.setEmail(email);
		cliente.setDni(dni);
		cliente.setFechaNacimiento(LocalDate.parse(fechaNacimiento, formatter));
		cliente.setEsCampista(esCampista);
		cliente.setEsAdmin(esAdmin);
		String passwordEncriptado = this.clientesService.encriptarPassword(password);
		cliente.setPassword(passwordEncriptado);
		return cliente;
	}

	private DatosEntity crearDato(String concepto, String valor) 
	{
		DatosEntity dato = new DatosEntity();
		dato.setConcepto(concepto);
		dato.setValor(valor);
		return dato;
	}

}
