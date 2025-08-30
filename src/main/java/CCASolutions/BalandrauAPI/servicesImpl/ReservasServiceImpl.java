package CCASolutions.BalandrauAPI.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CCASolutions.BalandrauAPI.dao.ReservasDAO;

@Service
public class ReservasServiceImpl 
{
	@Autowired
	private ReservasDAO reservasDao;

}
