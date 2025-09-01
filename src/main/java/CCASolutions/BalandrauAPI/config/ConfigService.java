package CCASolutions.BalandrauAPI.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import CCASolutions.BalandrauAPI.dao.ClientesDAO;
import CCASolutions.BalandrauAPI.dao.DatosDAO;
import CCASolutions.BalandrauAPI.dao.HabitacionesDAO;
import CCASolutions.BalandrauAPI.dao.RegimenComidasDAO;
import CCASolutions.BalandrauAPI.dao.ReservasDAO;
import CCASolutions.BalandrauAPI.entities.ClientesEntity;
import CCASolutions.BalandrauAPI.entities.DatosEntity;
import CCASolutions.BalandrauAPI.entities.HabitacionesEntity;

@Service
public class ConfigService implements IConfigService 
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
	
	public boolean restablecerBaseDeDatos()
	{
		boolean restablecida = false;
		
		try
		{
			vaciarTodasLasEntidades();
			cargarDatosIniciales();
			restablecida = true;
		}
		catch (Exception e)
		{
			restablecida = false;
			System.out.println(e.getMessage());
		}

		
		return restablecida;
	}
	
	public boolean hayDatosEnLaBaseDeDatos()
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
			
			entidadesVacias = true;			
			
		}
		catch (Exception e)
		{
			entidadesVacias = false;
		}
		
		if(hayDatosEnLaBaseDeDatos())
		{
			entidadesVacias=false;
		}
		
		
		return entidadesVacias;
	}
	
	public boolean cargarDatosIniciales()
	{
		boolean datosCargados = false;
		
		if(clientesDao.count() == 0)
		{
				List<ClientesEntity> listaClientes = new ArrayList<>();
				
				ClientesEntity cliente1 = new ClientesEntity();
				cliente1.setNombre("admin");
				cliente1.setApellido1("A1");
				cliente1.setApellido2("A2");
				cliente1.setEmail("admin@balandrau.com");
				cliente1.setDni("adminDNI");				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				cliente1.setFechaNacimiento(LocalDate.parse("12-05-1993", formatter));				
				cliente1.setEsAdmin(true);
				cliente1.setEsCampista(true);
				cliente1.setPassword("1234");
				listaClientes.add(cliente1);
				
				clientesDao.saveAll(listaClientes);				
		}
		
		if (habitacionesDao.count() == 0) 
		{
			List<HabitacionesEntity> listaHabitaciones = new ArrayList<>();			
				
			HabitacionesEntity habitacion1 = new HabitacionesEntity();
			habitacion1.setNombre("Habitación comunitaria");
			habitacion1.setDescripcion("Habitacion comunitaria con baño compartido");
			habitacion1.setNumeroDeHabitacion(101);
			habitacion1.setNumeroMaximoDeHuespedes(8);
			habitacion1.setPrecioPorNoche(BigDecimal.valueOf(15.00));
			habitacion1.setTipoHabitacion(1);
			listaHabitaciones.add(habitacion1);

			HabitacionesEntity habitacion2 = new HabitacionesEntity();
			habitacion2.setNombre("Habitación privada con orientacion norte");
			habitacion2.setDescripcion("Habitación para cuatro personas con baño incluido");
			habitacion2.setNumeroDeHabitacion(201);
			habitacion2.setNumeroMaximoDeHuespedes(4);
			habitacion2.setPrecioPorNoche(BigDecimal.valueOf(100.00));
			habitacion2.setTipoHabitacion(2);
			listaHabitaciones.add(habitacion2);
				
			HabitacionesEntity habitacion3 = new HabitacionesEntity();
			habitacion3.setNombre("Habitación privada con orientacion este");
			habitacion3.setDescripcion("Habitación para cuatro personas con baño incluido");
			habitacion3.setNumeroDeHabitacion(203);
			habitacion3.setNumeroMaximoDeHuespedes(4);
			habitacion3.setPrecioPorNoche(BigDecimal.valueOf(120.00));
			habitacion3.setTipoHabitacion(2);
			listaHabitaciones.add(habitacion3);

			HabitacionesEntity habitacion4 = new HabitacionesEntity();
			habitacion4.setNombre("Habitación privada con orientacion sur");
			habitacion4.setDescripcion("Habitación para cuatro personas con baño incluido");
			habitacion4.setNumeroDeHabitacion(205);
			habitacion4.setNumeroMaximoDeHuespedes(4);
			habitacion4.setPrecioPorNoche(BigDecimal.valueOf(150.00));
			habitacion4.setTipoHabitacion(2);
			listaHabitaciones.add(habitacion4);
				
			HabitacionesEntity habitacion5 = new HabitacionesEntity();
			habitacion5.setNombre("Habitación privada con orientacion oeste");
			habitacion5.setDescripcion("Habitación para cuatro personas con baño incluido");
			habitacion5.setNumeroDeHabitacion(207);
			habitacion5.setNumeroMaximoDeHuespedes(4);
			habitacion5.setPrecioPorNoche(BigDecimal.valueOf(130.00));
			habitacion5.setTipoHabitacion(2);
			listaHabitaciones.add(habitacion5);

			HabitacionesEntity habitacion6 = new HabitacionesEntity();
			habitacion6.setNombre("Habitación privada con orientacion noreste");
			habitacion6.setDescripcion("Habitación para dos personas con baño incluido");
			habitacion6.setNumeroDeHabitacion(202);
			habitacion6.setNumeroMaximoDeHuespedes(2);
			habitacion6.setPrecioPorNoche(BigDecimal.valueOf(60.00));
			habitacion6.setTipoHabitacion(3);
			listaHabitaciones.add(habitacion6);

			HabitacionesEntity habitacion7 = new HabitacionesEntity();
			habitacion7.setNombre("Habitación privada con orientacion sureste");
			habitacion7.setDescripcion("Habitación para dos personas con baño incluido");
			habitacion7.setNumeroDeHabitacion(204);
			habitacion7.setNumeroMaximoDeHuespedes(2);
			habitacion7.setPrecioPorNoche(BigDecimal.valueOf(70.00));
			habitacion7.setTipoHabitacion(3);
			listaHabitaciones.add(habitacion7);

			HabitacionesEntity habitacion8 = new HabitacionesEntity();
			habitacion8.setNombre("Habitación privada con orientacion suroeste");
			habitacion8.setDescripcion("Habitación para una persona con baño incluido");
			habitacion8.setNumeroDeHabitacion(206);
			habitacion8.setNumeroMaximoDeHuespedes(1);
			habitacion8.setPrecioPorNoche(BigDecimal.valueOf(35.00));
			habitacion8.setTipoHabitacion(4);
			listaHabitaciones.add(habitacion8);
				
			HabitacionesEntity habitacion9 = new HabitacionesEntity();
			habitacion9.setNombre("Habitación privada con orientacion noroeste");
			habitacion9.setDescripcion("Habitación para una persona con baño incluido");
			habitacion9.setNumeroDeHabitacion(208);
			habitacion9.setNumeroMaximoDeHuespedes(1);
			habitacion9.setPrecioPorNoche(BigDecimal.valueOf(30.00));
			habitacion9.setTipoHabitacion(4);
			listaHabitaciones.add(habitacion9);	
				
	         habitacionesDao.saveAll(listaHabitaciones);
		}
			
		if (datosDao.count() == 0) 
		{
			List<DatosEntity> listaDatos = new ArrayList<>();
				
			DatosEntity dato1 = new DatosEntity();
			dato1.setConcepto(precioDesayunoString);
			dato1.setValor("7");
			listaDatos.add(dato1);

			DatosEntity dato2 = new DatosEntity();
			dato2.setConcepto(precioComidaString);
			dato2.setValor("10");
			listaDatos.add(dato2);

			DatosEntity dato3 = new DatosEntity();
			dato3.setConcepto(precioCenaString);
			dato3.setValor("8");
			listaDatos.add(dato3);
				
			DatosEntity dato4 = new DatosEntity();
			dato4.setConcepto(descuentoCampistaString);
			dato4.setValor("20");
			listaDatos.add(dato4);

			DatosEntity dato5 = new DatosEntity();
			dato5.setConcepto(descuentoPicnicString);
			dato5.setValor("2");
			listaDatos.add(dato5);			
				
			datosDao.saveAll(listaDatos);
		}		
		
		return datosCargados;
	}

}
