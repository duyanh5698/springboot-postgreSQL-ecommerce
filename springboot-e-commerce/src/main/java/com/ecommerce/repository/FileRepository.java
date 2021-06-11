package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.File;

public interface FileRepository extends JpaRepository<File, String>{

}
