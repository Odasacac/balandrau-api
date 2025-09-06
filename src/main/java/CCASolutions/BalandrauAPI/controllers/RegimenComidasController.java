package CCASolutions.BalandrauAPI.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CCASolutions.BalandrauAPI.dao.ReservasDAO;
import CCASolutions.BalandrauAPI.dtos.GetRegimenDeComidasDTO;
import CCASolutions.BalandrauAPI.entities.ReservasEntity;
import CCASolutions.BalandrauAPI.services.RegimenComidasService;

@RestController
@RequestMapping("regimen/")
@CrossOrigin("*")
public class RegimenComidasController 
{
	@Autowired
	private RegimenComidasService regimenComidasService;
	
	@Autowired
	private ReservasDAO reservasDao;
	
	@GetMapping("obtener/{id}")
	public ResponseEntity<List<GetRegimenDeComidasDTO>> getRegimenDeComidasPorReservaId(@PathVariable("id") Long reservaId)
	{
		HttpStatus status = HttpStatus.OK;
		List<GetRegimenDeComidasDTO> body = new ArrayList<>();
		
		Optional<ReservasEntity> reserva = this.reservasDao.findById(reservaId);
		
		if(reserva.isPresent())
		{
			body = this.regimenComidasService.getRegimenesPorReservaId(reservaId);
		}
		else
		{
			status = HttpStatus.BAD_REQUEST;
		}
		
		
		return new ResponseEntity<List<GetRegimenDeComidasDTO>>(body, status);		
	}
}
