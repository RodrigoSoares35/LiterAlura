package com.Liter.Alura;

import com.Liter.Alura.principal.Principal;
import com.Liter.Alura.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AluraApplication implements CommandLineRunner {


	@Autowired
	private LivroRepository repositorio;

	public static void main(String[] args) {

		SpringApplication.run(AluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repositorio);
		principal.exibeMenu();
	}
}
