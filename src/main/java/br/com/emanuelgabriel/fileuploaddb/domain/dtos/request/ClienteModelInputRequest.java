package br.com.emanuelgabriel.fileuploaddb.domain.dtos.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

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
public class ClienteModelInputRequest {

	@NotBlank(message = "O campo nome não pode ser vazio")
	private String nome;
	
	@CPF(message = "CPF inválido")
	private String cpf;
	
	private String rg;
	private String email;
	private String telefone;
	private String celular;
	
	@NotNull(message = "Endereço não pode ser vazio")
	private EnderecoModelInputRequest endereco;

}
