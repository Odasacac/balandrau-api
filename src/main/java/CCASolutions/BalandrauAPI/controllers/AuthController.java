package CCASolutions.BalandrauAPI.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import CCASolutions.BalandrauAPI.dtos.CredentialsDTO;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class AuthController 
{

	@PostMapping("/login")
	public ResponseEntity<String> logIn(@RequestParam CredentialsDTO credentials)
	{
		HttpStatus status = HttpStatus.OK;
		String body = "";
		
		return new ResponseEntity<String>(body, status);
	}
}
