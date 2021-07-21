package br.com.emanuelgabriel.fileuploaddb.domain.dtos.reponse;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author emanuel.sousa
 *
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteModelResponse {

	private Long id;
	private String nome;
	private String cpf;
	private String rg;
	private String email;
	private String telefone;
	private String celular;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private LocalDateTime dataCadastro;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private LocalDateTime dataAtualizacao;

	private EnderecoModelResponse endereco;

}
