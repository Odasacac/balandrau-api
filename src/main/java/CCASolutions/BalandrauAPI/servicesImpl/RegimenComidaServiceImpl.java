package CCASolutions.BalandrauAPI.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;

import CCASolutions.BalandrauAPI.dao.RegimenComidasDAO;
import CCASolutions.BalandrauAPI.services.RegimenComidasService;

public class RegimenComidaServiceImpl implements RegimenComidasService
{
	@Autowired
	private RegimenComidasDAO regimenComidasDao;

}
