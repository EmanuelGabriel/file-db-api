package br.com.emanuelgabriel.fileuploaddb.domain.dtos.request;

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

	private String nome;
	private String cpf;
	private String rg;
	private String email;
	private String telefone;
	private String celular;
	private EnderecoModelInputRequest endereco;

}
