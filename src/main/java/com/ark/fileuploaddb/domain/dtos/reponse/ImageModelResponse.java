package com.ark.fileuploaddb.domain.dtos.reponse;

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
public class ImageModelResponse {

	private Long id;

	private String name;

	private String type;

	private byte[] picByte;

}
