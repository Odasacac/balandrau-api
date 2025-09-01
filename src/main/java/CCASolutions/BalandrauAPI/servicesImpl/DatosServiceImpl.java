package CCASolutions.BalandrauAPI.servicesImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import CCASolutions.BalandrauAPI.dao.DatosDAO;
import CCASolutions.BalandrauAPI.dtos.DatosEntityDTO;
import CCASolutions.BalandrauAPI.entities.DatosEntity;
import CCASolutions.BalandrauAPI.services.DatosService;
import jakarta.annotation.PostConstruct;

@Service
public class DatosServiceImpl implements DatosService 
{
	@Autowired
	private DatosDAO datosDao;
	
	@Value("${precioDesayunoString}") 
	private String precioDesayunoString;
	
	@Value("${precioComidaString}") 
	private String precioComidaString;
	
	@Value("${precioCenaString}") 
	private String precioCenaString;
	
	@Value("${descuentoPicnicString}") 
	private String descuentoPicnicString;
	
	private List<String> conceptos = new ArrayList<>();
	
	 @PostConstruct
	 public void initConceptos()
	 {
		this.conceptos.add(precioDesayunoString);
		this.conceptos.add(precioComidaString);
		this.conceptos.add(precioCenaString);
		this.conceptos.add(descuentoPicnicString);
	}
	
	public List<DatosEntityDTO> getPreciosRegimen()
	{
		List<DatosEntityDTO> preciosRegimen = new ArrayList<DatosEntityDTO>();

		
		List<DatosEntity> preciosRegimenObject = this.datosDao.getPreciosRegimen(conceptos);
		
		for (int i = 0; i<preciosRegimenObject.size(); i++)
		{
			DatosEntity datos = preciosRegimenObject.get(i);
			DatosEntityDTO precioRegimen = new DatosEntityDTO(datos.getConcepto(), datos.getValor());
			
			preciosRegimen.add(precioRegimen);
			
		}

		return preciosRegimen;
	}
}
