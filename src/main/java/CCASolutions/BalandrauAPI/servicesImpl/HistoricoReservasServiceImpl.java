package CCASolutions.BalandrauAPI.servicesImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import CCASolutions.BalandrauAPI.dao.DatosDAO;
import CCASolutions.BalandrauAPI.dao.HistoricoRegimenComidasDAO;
import CCASolutions.BalandrauAPI.dao.HistoricoReservasDAO;
import CCASolutions.BalandrauAPI.dao.RegimenComidasDAO;
import CCASolutions.BalandrauAPI.dao.ReservasDAO;
import CCASolutions.BalandrauAPI.entities.HistoricoRegimenComidasEntity;
import CCASolutions.BalandrauAPI.entities.HistoricoReservasEntity;
import CCASolutions.BalandrauAPI.entities.RegimenComidasEntity;
import CCASolutions.BalandrauAPI.entities.ReservasEntity;
import CCASolutions.BalandrauAPI.services.HistoricoReservasService;
import jakarta.transaction.Transactional;

@Service
public class HistoricoReservasServiceImpl implements HistoricoReservasService
{
	@Autowired
	private ReservasDAO reservasDao;
	
	@Autowired
	private RegimenComidasDAO regimenComidasDao;
	
	@Autowired
	private HistoricoReservasDAO historicoReservasDAO;
	
	@Autowired
	private HistoricoRegimenComidasDAO historicoRegimenComidasDao;
	
	@Autowired
	private DatosDAO datosDao;
	
	@Value("${mesesMaximosEnHistoricoString}") 
	private String mesesMaximosEnHistoricoString;
	
	@Scheduled(cron = "0 0 0 * * ?")
	@Transactional
	public void limpiarHistorico()
	{
		int mesesMaximoEnHistorico = Integer.parseInt(this.datosDao.getDatoConcreto(mesesMaximosEnHistoricoString));
		LocalDate fechaLimite = LocalDate.now().minusMonths(mesesMaximoEnHistorico);
		
		List<HistoricoReservasEntity> historicoReservasABorrar = new ArrayList<>();
		
		try
		{
			historicoReservasABorrar = this.historicoReservasDAO.findReservasAnterioresA(fechaLimite);
			
			if(!historicoReservasABorrar.isEmpty())
			{
				for (int i = 0; i<historicoReservasABorrar.size(); i++)
				{
					Long historicoReservaId = historicoReservasABorrar.get(i).getId();
					this.historicoRegimenComidasDao.deleteByReservaHistoricoId(historicoReservaId);
				}
				this.historicoReservasDAO.deleteAll(historicoReservasABorrar);
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}	
	
	
	
	
	@Scheduled(cron = "0 0 0 * * ?")
	@Transactional
	public void moverReservasAHistorico()
	{
		List<ReservasEntity> reservasPasadas = new ArrayList<>();
		
		try
		{
			reservasPasadas = this.reservasDao.findReservasConFechaSalidaAnterioresA(LocalDate.now());
			
			if(!reservasPasadas.isEmpty())
			{
				Map<Long, HistoricoReservasEntity> mapaReservas = new HashMap<>();
				List<RegimenComidasEntity> regimenesDeReservasPasadas = new ArrayList<>();
				
				for (int i = 0; i<reservasPasadas.size(); i++)
				{					
					ReservasEntity reservaConcreta = reservasPasadas.get(i);
					Long reservaId = reservaConcreta.getId();
					
					List<RegimenComidasEntity> regimenesDeReservaConcreta = this.regimenComidasDao.findByReservaIdOrderByFechaDesc(reservaId);
					
					regimenesDeReservasPasadas.addAll(regimenesDeReservaConcreta);
					
					this.regimenComidasDao.deleteByReservaId(reservaId);
					
					HistoricoReservasEntity historicoReserva = new HistoricoReservasEntity();
					
					historicoReserva.setHabitacion(reservaConcreta.getHabitacion());
					historicoReserva.setFechaEntrada(reservaConcreta.getFechaEntrada());
					historicoReserva.setFechaSalida(reservaConcreta.getFechaSalida());
					historicoReserva.setFechaCreacion(reservaConcreta.getFechaCreacion());
					historicoReserva.setComentarios(reservaConcreta.getComentarios());
					historicoReserva.setCliente(reservaConcreta.getCliente());
					historicoReserva.setNumeroHuespedes(reservaConcreta.getNumeroHuespedes());
					historicoReserva.setPrecioTotal(reservaConcreta.getPrecioTotal());
					historicoReserva.setHayComidas(reservaConcreta.isHayComidas());
					
					this.historicoReservasDAO.save(historicoReserva);					
					mapaReservas.put(reservaId, historicoReserva);
					
					this.reservasDao.deleteById(reservaId);	
				}
				
				List<HistoricoRegimenComidasEntity> historicoRegimenes = new ArrayList<>();
				   
				for (int j = 0; j<regimenesDeReservasPasadas.size(); j++)
				{
					RegimenComidasEntity regimenConcreto = regimenesDeReservasPasadas.get(j);
					
					HistoricoRegimenComidasEntity historicoRegimenConcreto = new HistoricoRegimenComidasEntity();
					
					historicoRegimenConcreto.setAlergias(regimenConcreto.getAlergias());
					historicoRegimenConcreto.setAlmuerzo(regimenConcreto.isAlmuerzo());
					historicoRegimenConcreto.setCena(regimenConcreto.isCena());
					historicoRegimenConcreto.setDesayuno(regimenConcreto.isDesayuno());
					historicoRegimenConcreto.setFecha(regimenConcreto.getFecha());
					historicoRegimenConcreto.setPicnic(regimenConcreto.isPicnic());
					
					HistoricoReservasEntity historicoReserva = mapaReservas.get(regimenConcreto.getReserva().getId());
					historicoRegimenConcreto.setReservaHistorico(historicoReserva);
					
					historicoRegimenes.add(historicoRegimenConcreto);
				}
				
				this.historicoRegimenComidasDao.saveAll(historicoRegimenes);
				
			}
			
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
	}
}
