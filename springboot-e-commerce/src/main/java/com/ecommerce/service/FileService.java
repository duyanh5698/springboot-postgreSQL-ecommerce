package com.ecommerce.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.model.File;
import com.ecommerce.model.Product;
import com.ecommerce.repository.FileRepository;
import com.ecommerce.repository.ProductRepository;


@Service
public class FileService {

	@Autowired
    private final FileRepository fileRepository;
	
	@Autowired
	ProductRepository productRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void save(MultipartFile file, Long id) throws IOException {
    	try {
    		Optional<Product> product = productRepository.findById(id);
        	if(product.isPresent()) {
        		File fileEntity = new File();
        		fileEntity.setProduct(product.get());
                fileEntity.setName(StringUtils.cleanPath(file.getOriginalFilename()));
                fileEntity.setContentType(file.getContentType());
                fileEntity.setData(file.getBytes());
                fileEntity.setSize(file.getSize());
                fileEntity.setProductId(id);
                fileRepository.save(fileEntity);
    		}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RestClientException("");
		}
    }

    public Optional<File> getFile(String id) {
        return fileRepository.findById(id);
    }

    public List<File> getAllFiles() {
        return fileRepository.findAll();
    }
}