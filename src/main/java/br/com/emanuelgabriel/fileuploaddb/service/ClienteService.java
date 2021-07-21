package br.com.emanuelgabriel.fileuploaddb.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.emanuelgabriel.fileuploaddb.domain.dtos.reponse.ClienteModelResponse;
import br.com.emanuelgabriel.fileuploaddb.domain.dtos.request.ClienteModelInputRequest;
import br.com.emanuelgabriel.fileuploaddb.domain.entity.Cliente;
import br.com.emanuelgabriel.fileuploaddb.domain.mapper.ClienteMapper;
import br.com.emanuelgabriel.fileuploaddb.domain.repository.ClienteRepository;
import br.com.emanuelgabriel.fileuploaddb.service.exception.ObjNaoEncontradoException;
import br.com.emanuelgabriel.fileuploaddb.service.exception.RegraNegocioException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClienteService {

	private static final String MSG_IMPOSSIVEL_REMOVER = "O cliente não pode ser removido, pois está em uso";

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ClienteMapper clienteMapper;

	@Transactional
	public ClienteModelResponse criar(ClienteModelInputRequest request) {
		log.info("Cria um cliente {}", request);

		Cliente cpfClienteExistente = this.clienteRepository.findByCpf(request.getCpf());
		if (cpfClienteExistente != null && !cpfClienteExistente.equals(request)) {
			throw new RegraNegocioException("Já existe cliente registrado com este CPF");
		}

		Cliente clienteSalvo = this.clienteMapper.dtoToEntity(request);
		clienteSalvo.setDataCadastro(LocalDateTime.now());

		return this.clienteMapper.entityToDTO(clienteRepository.saveAndFlush(clienteSalvo));

	}

	public Page<ClienteModelResponse> getAll() {
		log.info("Busca todos os clientes");
		Pageable pageable = PageRequest.of(0, 10, Sort.by("id").ascending().and(Sort.by("nome").ascending()));
		Page<Cliente> pageCliente = clienteRepository.findAll(pageable);
		return this.clienteMapper.mapEntityPageToDTO(pageable, pageCliente);
	}

	public ClienteModelResponse buscarPorId(Long idCliente) {
		log.info("Busca um cliente por ID {}", idCliente);
		Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);
		if (!clienteOpt.isPresent()) {
			throw new ObjNaoEncontradoException("Cliente de ID não encontrado");
		}
		return this.clienteMapper.entityToDTO(clienteOpt.get());
	}

	@Transactional
	public void remover(Long idCliente) {
		try {

			buscarPorId(idCliente);
			clienteRepository.deleteById(idCliente);
		} catch (Exception e) {
			throw new DataIntegrityViolationException(MSG_IMPOSSIVEL_REMOVER);
		}
	}

}
