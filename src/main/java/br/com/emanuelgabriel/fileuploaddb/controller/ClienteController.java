package br.com.emanuelgabriel.fileuploaddb.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.emanuelgabriel.fileuploaddb.domain.dtos.reponse.ClienteModelResponse;
import br.com.emanuelgabriel.fileuploaddb.domain.dtos.request.ClienteModelInputRequest;
import br.com.emanuelgabriel.fileuploaddb.service.ClienteService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/clientes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ResponseEntity<ClienteModelResponse> criar(@RequestBody ClienteModelInputRequest request) {
		log.info("POST /clientes - body {}", request);
		ClienteModelResponse clienteModelResponse = clienteService.criar(request);
		URI location = getUri(clienteModelResponse.getId());
		return ResponseEntity.created(location).build();
	}

	@GetMapping
	public ResponseEntity<Page<ClienteModelResponse>> getAll() {
		log.info("GET /clientes");
		Page<ClienteModelResponse> pageClientes = clienteService.getAll();
		return pageClientes != null ? ResponseEntity.ok(pageClientes) : ResponseEntity.ok().build();
	}

	private URI getUri(Long id) {
		return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
	}
}
