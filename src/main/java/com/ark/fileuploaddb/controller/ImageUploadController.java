package com.ark.fileuploaddb.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ark.fileuploaddb.model.ImageModel;
import com.ark.fileuploaddb.model.ImageModelResponse;
import com.ark.fileuploaddb.repository.ImageModelRepository;
import com.ark.fileuploaddb.service.exception.ObjNaoEncontradoException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "image", produces = MediaType.APPLICATION_JSON_VALUE)
public class ImageUploadController {

	@Autowired
	ImageModelRepository imageModelRepository;

	@PostMapping("/upload")
	public ImageModelResponse uploadImagem(@RequestParam("imageFile") MultipartFile file) throws IOException {
		
		log.info("Tamanho original da imagem em Byte {}", file.getBytes().length);
		
		ImageModel imagemModel = new ImageModel(file.getOriginalFilename(), file.getContentType(), compressorZLib(file.getBytes()));

		// ImageModelResponse response = ImageModelResponse.builder().name(file.getOriginalFilename()).type(file.getContentType()).picByte(compressorZLib(file.getBytes())).build();

		imageModelRepository.save(imagemModel);

		return this.entityToDTO(imagemModel);

	}
	
	@GetMapping
	public ResponseEntity<List<ImageModelResponse>> getAll(){
		log.info("GET /image");
		List<ImageModel> listImage = imageModelRepository.findAll();
		return listImage != null ? ResponseEntity.ok().body(listEntityToDTO(listImage)) : ResponseEntity.ok().build(); 
	}

	@GetMapping(value = "/get/{imageName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ImageModelResponse> getImagem(@PathVariable("imageName") String imageName) throws IOException {

		final Optional<ImageModel> imageModelOpt = imageModelRepository.findByNameIgnoreCaseContaining(imageName);
		if (!imageModelOpt.isPresent()) {
			throw new ObjNaoEncontradoException("Arquivo não encontrado");
		}

		ImageModel imageModel = new ImageModel(imageModelOpt.get().getName(), imageModelOpt.get().getType(), descomprimirZLib(imageModelOpt.get().getPicByte()));

		ImageModelResponse response = this.entityToDTO(imageModel);
		
		return ResponseEntity.ok().body(response);
	}

	// compress the image bytes before storing it in the database
	public static byte[] compressorZLib(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressão do tamanho da imagem em Byte - " + outputStream.toByteArray().length);

		return outputStream.toByteArray();
	}

	// uncompress the image bytes before returning it to the angular application
	public static byte[] descomprimirZLib(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}

	/**
	 * Converte entidade para DTO
	 * 
	 * @author emanuel.sousa
	 * @param 
	 * @return ImageModelResponse
	 */
	private ImageModelResponse entityToDTO(ImageModel imageModel) {
		return new ImageModelResponse(imageModel.getId(), imageModel.getName(), imageModel.getType(), imageModel.getPicByte());
	}

	private ImageModel dtoToEntity(ImageModelResponse dto) {
		return new ImageModel().builder().id(dto.getId()).name(dto.getName()).type(dto.getType())
				.picByte(dto.getPicByte()).build();
	}
	
	private List<ImageModelResponse> listEntityToDTO(List<ImageModel> images){
		return images.stream().map(this::entityToDTO).collect(Collectors.toList());
	}

}