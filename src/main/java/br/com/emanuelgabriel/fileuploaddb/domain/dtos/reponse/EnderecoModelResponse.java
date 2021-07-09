package br.com.emanuelgabriel.fileuploaddb.domain.dtos.reponse;

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
public class EnderecoModelResponse {

	private Long id;

	private String logradouro;

	private String numero;

	private String complemento;

	private String bairro;

	private String cep;

}
