package br.com.emanuelgabriel.fileuploaddb.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.emanuelgabriel.fileuploaddb.domain.entity.ImageModel;

/**
 * 
 * @author emanuel.sousa
 *
 */

@Repository
public interface ImageModelRepository extends JpaRepository<ImageModel, Long> {

	Optional<ImageModel> findByNameIgnoreCaseContaining(String nome);
}
