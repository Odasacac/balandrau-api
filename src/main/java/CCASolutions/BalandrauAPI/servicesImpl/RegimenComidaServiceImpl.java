package CCASolutions.BalandrauAPI.servicesImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import CCASolutions.BalandrauAPI.dao.RegimenComidasDAO;
import CCASolutions.BalandrauAPI.dtos.GetRegimenDeComidasDTO;
import CCASolutions.BalandrauAPI.entities.RegimenComidasEntity;
import CCASolutions.BalandrauAPI.entities.ReservasEntity;
import CCASolutions.BalandrauAPI.services.RegimenComidasService;

@Service
public class RegimenComidaServiceImpl implements RegimenComidasService
{
	@Autowired
	private RegimenComidasDAO regimenComidasDao;
	
	public List<GetRegimenDeComidasDTO> getRegimenesPorReservaId(Long reservaId)
	{
		List<GetRegimenDeComidasDTO> respuesta = new ArrayList<>();
		
		List<RegimenComidasEntity> regimenes = new ArrayList<>();
		
		try
		{			
			regimenes = this.regimenComidasDao.findByReservaIdOrderByFechaDesc(reservaId);
			
			for(int i = 0; i<regimenes.size(); i++)
			{
				RegimenComidasEntity regimenConcreto = regimenes.get(i);
				
				Long id = regimenConcreto.getId();
				String alergias = regimenConcreto.getAlergias();
				Boolean almuerzo = regimenConcreto.isAlmuerzo();
				Boolean desayuno = regimenConcreto.isDesayuno();
				Boolean cena = regimenConcreto.isCena();
				LocalDate fecha = regimenConcreto.getFecha();
				Boolean picnic = regimenConcreto.isPicnic();
				
				
				
				GetRegimenDeComidasDTO regimenDTO = new GetRegimenDeComidasDTO(id, alergias, desayuno, almuerzo, cena, fecha, picnic);
				
				respuesta.add(regimenDTO);
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		
		return respuesta;
	}
	
	public void eliminarRegimenesPorReservaId(Long reservaId)
	{
		this.regimenComidasDao.deleteByReservaId(reservaId);
	}
	
	public void guardarNuevoRegimen(RegimenComidasEntity regimen, Long reservaId, String alergias)
	{
		ReservasEntity reservaParaId = new ReservasEntity();
		reservaParaId.setId(reservaId);		
		regimen.setReserva(reservaParaId);
		regimen.setAlergias(alergias);
		
		this.regimenComidasDao.save(regimen);
	}
}
