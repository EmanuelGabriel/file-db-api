package com.ark.fileuploaddb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ark.fileuploaddb.model.ImageModel;

@Repository
public interface ImageModelRepository extends JpaRepository<ImageModel, Long> {

	Optional<ImageModel> findByNameIgnoreCaseContaining(String nome);
}
