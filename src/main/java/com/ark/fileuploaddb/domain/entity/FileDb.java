package com.ark.fileuploaddb.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Entity
@Table(name = "tb_filedb")
public class FileDb implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_file", nullable = false)
	private String id;

	@Column(name = "nome", length = 100, nullable = false)
	private String nome;

	@Column(name = "type", nullable = false)
	private String type;

	@Column(name = "tamanho_file", length = 40)
	private long tamanho;

	@CreationTimestamp
	@Column(name = "data_cadastro", nullable = false)
	private LocalDateTime dataCadastro;

	@UpdateTimestamp
	@Column(name = "data_atualizacao", nullable = false)
	private LocalDateTime dataAtualizacao;

	@Lob
	@Column(name = "dados", length = 1000)
	private byte[] dados;

}
