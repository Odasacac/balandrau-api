package CCASolutions.BalandrauAPI.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CCASolutions.BalandrauAPI.dao.ReservasDAO;
import CCASolutions.BalandrauAPI.services.ReservasService;

@Service
public class ReservasServiceImpl implements ReservasService
{
	@Autowired
	private ReservasDAO reservasDao;

}
