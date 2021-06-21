package com.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.model.File;
import com.ecommerce.repository.FileRepository;

@Service
public class FileService {

	@Autowired
	FileRepository fileRepository;

	public File save(File file){
		return fileRepository.save(file);
	}

}