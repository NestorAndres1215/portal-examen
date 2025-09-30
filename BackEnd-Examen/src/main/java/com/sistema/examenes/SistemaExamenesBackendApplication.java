package com.sistema.examenes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SistemaExamenesBackendApplication implements CommandLineRunner {



	public static void main(String[] args) {
		SpringApplication.run(SistemaExamenesBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
 
		System.out.println("Iniciando el proyecto de sistema de examenes");
		System.out.println("Sistema de examenes iniciado correctamente");
		System.out.println("Sistema de examenes backend en el puerto 8080");
	}
}
