package com.ark.fileuploaddb.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ark.fileuploaddb.model.FileDb;
import com.ark.fileuploaddb.model.FileModelResponse;
import com.ark.fileuploaddb.repository.FileDbRepository;
import com.ark.fileuploaddb.service.exception.ObjNaoEncontradoException;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author emanuel.sousa
 *
 */

@Slf4j
@Service
public class FileDbService {

	@Autowired
	private FileDbRepository fileDbRepository;

	public FileModelResponse upload(MultipartFile file) throws IOException {
		log.info("Insere um arquivo por nome {}", file.getOriginalFilename());
		String fileName = file.getOriginalFilename();
		FileDb obj = FileDb.builder().id(UUID.randomUUID().toString()).nome(fileName).type(file.getContentType())
				.tamanho(file.getSize()).dados(file.getBytes()).dataCadastro(LocalDateTime.now()).build();
		fileDbRepository.save(obj);
		return this.entityToDTO(obj);
	}

	public FileModelResponse getFileById(String id) {
		log.info("Busca arquivo por ID {}", id);
		Optional<FileDb> fileOptional = fileDbRepository.findById(id);

		if (!fileOptional.isPresent()) {
			throw new ObjNaoEncontradoException("Arquivo não encontrado");
		}
		return this.entityToDTO(fileOptional.get());
	}

	public List<FileModelResponse> getFileByTipoConteudo(String type) {
		log.info("Busca arquivo pelo ContentType {}", type);
		List<FileDb> fileType = fileDbRepository.findByTypeEndingWithIgnoreCase(type);
		if (fileType.isEmpty()) {
			throw new ObjNaoEncontradoException("Nenhum tipo de ContentType encontrado neste formato");
		}

		return this.listEntityToDTO(fileType);
	}

	public FileModelResponse getFileByNome(String nome) {
		log.info("Busca arquivo por nome {}", nome);
		Optional<FileDb> fileNome = fileDbRepository.findByNomeIgnoreCaseContaining(nome);
		if (!fileNome.isPresent()) {
			throw new ObjNaoEncontradoException("Não foi encontrado arquivo com este nome");
		}

		return this.entityToDTO(fileNome.get());
	}

	public List<FileModelResponse> getFiles() {
		log.info("Exibe uma lista de arquivos");
		return fileDbRepository.findAll().stream().map(this::entityToDTO).collect(Collectors.toList());
	}

	/**
	 * Converte entidade para DTO
	 * 
	 * @author emanuel.sousa
	 * @param fileDb
	 * @return FileModelResponse
	 */
	private FileModelResponse entityToDTO(FileDb fileDb) {
		return new FileModelResponse(fileDb.getId(), fileDb.getType(), fileDb.getNome(), fileDb.getTamanho(),
				fileDb.getDados(), fileDb.getDataCadastro(), fileDb.getDataAtualizacao());
	}

	/**
	 * Converte DTO para Entidade
	 * 
	 * @param dto
	 * @return FileDb
	 */
	private FileDb dtoToEntity(FileModelResponse dto) {
		return new FileDb().builder().id(dto.getId()).nome(dto.getNome()).type(dto.getType()).tamanho(dto.getTamanho())
				.dados(dto.getDados()).build();
	}

	private List<FileModelResponse> listEntityToDTO(List<FileDb> files) {
		return files.stream().map(this::entityToDTO).collect(Collectors.toList());
	}
}
