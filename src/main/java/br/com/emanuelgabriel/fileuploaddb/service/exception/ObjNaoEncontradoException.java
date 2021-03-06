package br.com.emanuelgabriel.fileuploaddb.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author emanuel.sousa
 *
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ObjNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ObjNaoEncontradoException(String msg) {
		super(msg);
	}
}
