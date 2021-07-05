package com.ark.fileuploaddb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ark.fileuploaddb.model.FileDb;

/**
 * 
 * @author emanuel.sousa
 *
 */

@Repository
public interface FileDbRepository extends JpaRepository<FileDb, String> {

	Optional<FileDb> findByNomeIgnoreCaseContaining(String nome);

	List<FileDb> findByTypeEndingWithIgnoreCase(String type);

}
