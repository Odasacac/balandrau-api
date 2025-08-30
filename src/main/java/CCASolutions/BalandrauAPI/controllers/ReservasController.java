package CCASolutions.BalandrauAPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CCASolutions.BalandrauAPI.services.ReservasService;

@RestController
@CrossOrigin("*")
@RequestMapping("/reservas")
public class ReservasController 
{
	@Autowired
	private ReservasService	reservasService;
}
