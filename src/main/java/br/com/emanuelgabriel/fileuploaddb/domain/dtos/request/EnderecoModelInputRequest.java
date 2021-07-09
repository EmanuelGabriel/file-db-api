package br.com.emanuelgabriel.fileuploaddb.domain.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoModelInputRequest {

	private String logradouro;

	private String numero;

	private String complemento;

	private String bairro;

	private String cep;
}
