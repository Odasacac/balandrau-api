package CCASolutions.BalandrauAPI.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CCASolutions.BalandrauAPI.dao.RegimenComidasDAO;
import CCASolutions.BalandrauAPI.entities.RegimenComidasEntity;
import CCASolutions.BalandrauAPI.entities.ReservasEntity;
import CCASolutions.BalandrauAPI.services.RegimenComidasService;

@Service
public class RegimenComidaServiceImpl implements RegimenComidasService
{
	@Autowired
	private RegimenComidasDAO regimenComidasDao;
	
	public void guardarNuevoRegimen(RegimenComidasEntity regimen, Long reservaId, String alergias)
	{
		ReservasEntity reservaParaId = new ReservasEntity();
		reservaParaId.setId(reservaId);		
		regimen.setReserva(reservaParaId);
		regimen.setAlergias(alergias);
		
		this.regimenComidasDao.save(regimen);
	}
}
