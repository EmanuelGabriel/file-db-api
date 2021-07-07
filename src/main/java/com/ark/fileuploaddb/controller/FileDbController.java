package com.ark.fileuploaddb.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ark.fileuploaddb.domain.dtos.reponse.FileModelResponse;
import com.ark.fileuploaddb.service.FileDbService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author emanuel.sousa
 *
 */

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileDbController {

	@Autowired
	private FileDbService fileDbService;

	@PostMapping
	public ResponseEntity<FileModelResponse> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		log.info("POST /upload - body '{}' - ContentType '{}'", file.getOriginalFilename(), file.getContentType());
		return ResponseEntity.ok().body(fileDbService.upload(file));
	}

	@GetMapping("/{id}")
	public ResponseEntity<FileModelResponse> getFile(@PathVariable String id) {
		log.info("GET /upload/{}", id);
		FileModelResponse response = fileDbService.getFileById(id);
		return response != null ? ResponseEntity.ok().body(response) : ResponseEntity.notFound().build();
	}

	@GetMapping(value = "/por-nome", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FileModelResponse> getFileNome(@RequestParam(value = "nome") String nome) {
		log.info("GET /upload/por-nome/{}", nome);
		FileModelResponse porNome = fileDbService.getFileByNome(nome);
		return porNome != null ? ResponseEntity.ok().body(porNome) : ResponseEntity.notFound().build();
	}

	@GetMapping(value = "/por-tipo-conteudo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FileModelResponse>> getFileContentType(@RequestParam(value = "type") String type) {
		log.info("GET /upload/por-tipo-conteudo/{}", type);
		List<FileModelResponse> filesResponse = fileDbService.getFileByTipoConteudo(type);
		return filesResponse != null ? ResponseEntity.ok().body(filesResponse) : ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<List<FileModelResponse>> getFileList() {
		log.info("GET /upload");
		List<FileModelResponse> files = fileDbService.getFiles();
		return files != null ? ResponseEntity.ok().body(files) : ResponseEntity.ok().build();
	}

}
