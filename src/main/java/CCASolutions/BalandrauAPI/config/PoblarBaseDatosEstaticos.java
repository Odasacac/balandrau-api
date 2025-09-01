package CCASolutions.BalandrauAPI.config;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import CCASolutions.BalandrauAPI.dao.DatosDAO;
import CCASolutions.BalandrauAPI.dao.HabitacionesDAO;
import CCASolutions.BalandrauAPI.entities.HabitacionesEntity;
import CCASolutions.BalandrauAPI.entities.DatosEntity;

@Component
public class PoblarBaseDatosEstaticos implements CommandLineRunner 
{

	private final HabitacionesDAO habitacionesDao;
	private final DatosDAO datosDao;

	public PoblarBaseDatosEstaticos(HabitacionesDAO habitacionesDao, DatosDAO datosDao) 
	{
		this.habitacionesDao = habitacionesDao;
		this.datosDao = datosDao;
	}

	@Override
	public void run(String... args) throws Exception 
	{

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
			dato1.setConcepto("descuentoCampista");
			dato1.setValor("20");
			listaDatos.add(dato1);
			
			DatosEntity dato2 = new DatosEntity();
			dato2.setConcepto("precioDesayuno");
			dato2.setValor("7");
			listaDatos.add(dato2);

			DatosEntity dato3 = new DatosEntity();
			dato3.setConcepto("precioAlmuerzo");
			dato3.setValor("10");
			listaDatos.add(dato3);

			DatosEntity dato4 = new DatosEntity();
			dato4.setConcepto("precioCena");
			dato4.setValor("8");
			listaDatos.add(dato4);

			DatosEntity dato5 = new DatosEntity();
			dato5.setConcepto("descuentoPicnic");
			dato5.setValor("2");
			listaDatos.add(dato5);		
			
			
			datosDao.saveAll(listaDatos);
		}
	}
}
