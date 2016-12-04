package es.uem.tfg.trasano;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class TrasanowsApplication {
	
	public static void main(String[] args) {		
		SpringApplication.run(TrasanowsApplication.class, args);	
	}
}
