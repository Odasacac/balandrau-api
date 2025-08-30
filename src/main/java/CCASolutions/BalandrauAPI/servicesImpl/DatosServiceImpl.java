package CCASolutions.BalandrauAPI.servicesImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CCASolutions.BalandrauAPI.dao.DatosDAO;
import CCASolutions.BalandrauAPI.dtos.DatosEntityDTO;
import CCASolutions.BalandrauAPI.services.DatosService;

@Service
public class DatosServiceImpl implements DatosService 
{
	@Autowired
	private DatosDAO datosDao;
	
	public List<DatosEntityDTO> getPreciosRegimen()
	{
		List<DatosEntityDTO> preciosRegimen = new ArrayList<DatosEntityDTO>();
		
		List<Object[]> preciosRegimenObject = this.datosDao.getPreciosRegimen();
		
		for (int i = 0; i<preciosRegimenObject.size(); i++)
		{
			Object[] precioObject = preciosRegimenObject.get(i);
			DatosEntityDTO precioRegimen = new DatosEntityDTO((String)precioObject[0], (String) precioObject[1]);
			
			preciosRegimen.add(precioRegimen);
			
		}

		return preciosRegimen;
	}
}
