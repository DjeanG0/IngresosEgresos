package com.egresosingresos.Ciclo3;

import com.egresosingresos.Ciclo3.models.Empresa;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Ciclo3Application {

	@GetMapping("/hello")
	public String hello(){
		return "heollo my friends";
	}

	@GetMapping("/test")
	public String test(){
		Empresa emp = new Empresa("Solar SAS", "Calle 12 324 43", "1213123123", "23424");
		emp.setNombre("COlombia");
		return emp.getNombre();
	}

	public static void main(String[] args) {
		SpringApplication.run(Ciclo3Application.class, args);
	}

}
