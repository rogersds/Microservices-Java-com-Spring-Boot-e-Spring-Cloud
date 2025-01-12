package com.devsuperior.hrworker.resources;

import java.util.List;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.hrworker.entities.Worker;
import com.devsuperior.hrworker.repositories.WorkerRepository;

@RefreshScope
@RestController
@RequestMapping(value = "/workers")
public class WorkerResource {

	// para imprimir informações
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(WorkerResource.class);

	@Value("${test.config}")
	private String testConfig;
	@Autowired
	private Environment env;

	@Autowired // forma de fazer injeção de dependência de forma automática
	private WorkerRepository repository;

	
	// teste 
	@GetMapping(value = "/configs")
	public ResponseEntity<Void> getConfig() {
		logger.info("CONFIG = " + testConfig);
		
		return ResponseEntity.noContent().build();
	}

	
	
	// responseEntity encapsula uma resposta http na saida
	@GetMapping
	public ResponseEntity<List<Worker>> findAll() {
		List<Worker> list = repository.findAll();
		return ResponseEntity.ok(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Worker> findById(@PathVariable Long id) {

		logger.info("PORT = " + env.getProperty("local.server.port"));

		Worker obj = repository.findById(id).get();
		return ResponseEntity.ok(obj);

		// para que o id passado na requisição seja reconehcido e cai na variavel
		// @GetMapping(value = "/{id}"),
		// usamos a anotation @Pathvariable
	}

}
