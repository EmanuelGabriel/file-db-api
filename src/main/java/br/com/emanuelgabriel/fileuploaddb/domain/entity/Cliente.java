package br.com.emanuelgabriel.fileuploaddb.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_cliente")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_cliente", updatable = false, nullable = false)
	private Long id;

	@Column(name = "nome", length = 100, nullable = false)
	private String nome;

	@Column(name = "cpf", length = 15, nullable = false)
	private String cpf;

	@Column(name = "rg", length = 10, nullable = false)
	private String rg;

	@Column(name = "email", length = 50, nullable = false)
	private String email;

	@Column(name = "telefone", length = 10, nullable = true)
	private String telefone;

	@Column(name = "celular", length = 15, nullable = false)
	private String celular;

	@ManyToOne(cascade = CascadeType.PERSIST) // CascadeType.ALL)
	@JoinColumn(name = "fk_endereco", nullable = false)
	private Endereco endereco;

	@CreationTimestamp
	@Column(name = "data_cadastro", nullable = false)
	private LocalDateTime dataCadastro;

	@UpdateTimestamp
	@Column(name = "data_atualizacao", nullable = false)
	private LocalDateTime dataAtualizacao;

}
