package CCASolutions.BalandrauAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BalandrauApiApplication 
{

	public static void main(String[] args) 
	{
		SpringApplication.run(BalandrauApiApplication.class, args);
	}

}
